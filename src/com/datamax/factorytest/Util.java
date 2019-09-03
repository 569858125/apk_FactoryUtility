package com.datamax.factorytest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.droidlogic.app.SystemControlManager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.text.TextUtils;

public class Util {

    private static String TAG = "Util";


    private static String Str2Ascc(String mac) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] b = mac.getBytes();
            for (byte b1 : b) {
                sb.append(Integer.toString((b1 & 0xff) + 0x100, 16).substring(1));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static boolean isValid12mac(String mac) {
        if (mac == null || mac.equals("") || mac.length() != 12)
            return false;
        for (int i = 0; i < mac.length(); i++) {
            char c = mac.charAt(i);
            if (((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')) || ((c >= '0') && (c <= '9')))
                continue;
            else
                return false;
        }
        return true;
    }


    private static boolean isValid17mac(String mac) {
        if (mac == null || mac.equals("") || mac.length() != 17)
            return false;
        for (int i = 0; i < mac.length(); i++) {
            char c = mac.charAt(i);
            if (i % 3 == 2) {
                if (c == ':')
                    continue;
                else {
                    return false;
                }
            } else {
                if (((c >= 'a') && (c <= 'f')) || ((c >= 'A') && (c <= 'F')) || ((c >= '0') && (c <= '9')))
                    continue;
                else
                    return false;
            }

        }
        return true;
    }


    public static boolean burnSN(String sn) {
        String nameNode = "/sys/class/unifykeys/name";
        String writeNode = "/sys/class/unifykeys/write";
        int length = sn.length();
        if (length <= 16 && length >= 12) {

            for (int i = 0; i < length; i++) {
                char c = sn.charAt(i);
                if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')))
                    continue;
                else
                    return false;
            }

            String sncount = sn.substring(sn.length() - 6, sn.length());
            String snhead = sn.substring(0, sn.length() - 6);
            String dmsnhead = SystemProperties.get("ro.datamax.serial.head", "");
            if (!(snhead.equals(dmsnhead) || dmsnhead.equals(""))) {
                sn = dmsnhead + sncount + sn;
            }
            setString(nameNode, "usid");
            setString(writeNode, sn);
            return true;
        }
        return false;
    }


    static String readSN() {
        String nameNode = "/sys/class/unifykeys/name";
        String readNode = "/sys/class/unifykeys/read";

        setString(nameNode, "usid");

        return readFile(readNode);
    }


    static String readMAC() {
        String nameNode = "/sys/class/unifykeys/name";
        String readNode = "/sys/class/unifykeys/read";

        setString(nameNode, "mac");

        return readFile(readNode);
    }

    static boolean burn17MAC(String mac) {
        String nameNode = "/sys/class/unifykeys/name";
        String writeNode = "/sys/class/unifykeys/write";
        if (isValid17mac(mac)) {
            setString(nameNode, "mac");
            setString(writeNode, mac);
            return true;
        }
        return false;
    }


    static boolean burnSN_customer(String sn) {
        String nameNode = "/sys/class/unifykeys/name";
        String writeNode = "/sys/class/unifykeys/write";
        int length = sn.length();
        if (sn.length() <= 32) {
            for (int i = 0; i < length; i++) {
                char c = sn.charAt(i);
                if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')))

                    continue;
                else
                    return false;
            }
            setString(nameNode, "usid");
            setString(writeNode, sn);
            return true;
        }
        return false;
    }


    private static String readFile(String fileName) {
        String b = SystemControlManager.getInstance().readSysFs(fileName);
        return b;//.toString();

    }

    public static boolean isValidsn(String sn) {
        if (sn == null || sn.equals("") || (12 > sn.length() || sn.length() > 20))
            return false;
        for (int i = 0; i < sn.length(); i++) {
            char c = sn.charAt(i);
            if ((c >= '0') && (c <= '9'))
                continue;
            else
                return false;
        }
        return true;
    }


    static String str2macStr(String mac) {
        if (!TextUtils.isEmpty(mac) && mac.contains(":")) {
            mac = mac.replace(":", "");
        }
        String sb = "";
        byte[] b = mac.getBytes();
        for (int i = 0; i < b.length; i = i + 2) {
            if (i <= (b.length - 4))
                sb += mac.substring(i, i + 2).toString() + ":";
            else
                sb += mac.substring(i, i + 2).toString();
        }
        return sb;
    }

    static void setgree() {// ¼æÈÝs905xºÍs905d
        setString("/sys/devices/meson-vfd.50/greenled", "1");
        setString("/sys/devices/sysled.14/leds/led-sys/brightness", "1");

    }

    static void setred() {
        setString("/sys/devices/meson-vfd.50/greenled", "0");// s905D
        setString("/sys/devices/sysled.14/leds/led-sys/brightness", "0");// s905x

    }

    private static boolean setString(String c, String v) {
        SystemControlManager.getInstance().writeSysFs(c, v);
        return true;

    }

    static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        int max = 0;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        max = Integer.parseInt(result.trim());

        result = " " + max / 1000 + " MHz";

        return result;
    }

    // »ñÈ¡CPU×îÐ¡ÆµÂÊ£¨µ¥Î»KHZ£©
    static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        int min = 0;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        min = Integer.parseInt(result.trim());

        result = " " + min / 1000 + " MHz";

        return result;
    }

    // ÊµÊ±»ñÈ¡CPUµ±Ç°ÆµÂÊ£¨µ¥Î»KHZ£©
    static String getCurCpuFreq() {
        String result = "N/A";
        int now = 0;
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        now = Integer.parseInt(result);

        result = " " + now / 1000 + " MHz";

        return result;

    }


    /**
     * @param context
     * @return 获取版本号
     */
    static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        int version = 0;
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * @param context
     * @return 获取版本名
     */
    static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String version = "";
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * @return 获取语言
     */
    static String GetLanguageSummary(Context context) {
        // phone language
        Configuration conf = context.getResources().getConfiguration();
        String language = conf.locale.getLanguage();
        String localeString;
        if (hasOnlyOneLanguageInstance(language, Resources.getSystem().getAssets().getLocales())) {
            localeString = conf.locale.getDisplayLanguage(conf.locale);
        } else {
            localeString = conf.locale.getDisplayName(conf.locale);
        }

        if (localeString.length() > 1) {
            localeString = Character.toUpperCase(localeString.charAt(0)) + localeString.substring(1);
        }

        return localeString;

    }

    private static boolean hasOnlyOneLanguageInstance(String languageCode, String[] locales) {
        int count = 0;
        for (String localeCode : locales) {
            if (localeCode.length() > 2 && localeCode.startsWith(languageCode)) {
                count++;
                if (count > 1) {
                    return false;
                }
            }
        }
        return count == 1;
    }


    /**
     * @param now
     * @return 获取时区
     */
    static String getTimeZoneText(Calendar now) {
        TimeZone tz = now.getTimeZone();
        Date dateNow = new Date();
        return formatOffset(new StringBuilder(), tz, dateNow).append(", ")
                .append(tz.getDisplayName(tz.inDaylightTime(dateNow), TimeZone.LONG)).toString();
    }

    private static StringBuilder formatOffset(StringBuilder sb, TimeZone tz, Date d) {
        int off = tz.getOffset(d.getTime()) / 1000 / 60;
        sb.append("GMT");
        if (off < 0) {
            sb.append('-');
            off = -off;
        } else {
            sb.append('+');
        }
        int hours = off / 60;
        int minutes = off % 60;

        sb.append((char) ('0' + hours / 10));
        sb.append((char) ('0' + hours % 10));

        sb.append(':');

        sb.append((char) ('0' + minutes / 10));
        sb.append((char) ('0' + minutes % 10));

        return sb;
    }


}
