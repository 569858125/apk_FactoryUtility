package com.datamax.factorytest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

@SuppressLint("NewApi")
public class DonwAPK {
    private static final int DOWN = 1;
    private static final int DOWN_FINISH = 0;
    private HashMap<String, String> hashMap;
    private String fileSavePath;
    private String updateVersionXMLPath;
    private boolean cancelUpdate = false;
    private Context context;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((Integer) msg.obj) {

                case DOWN_FINISH:
                    System.out.println("finish");
                    install(fileSavePath + "/" + hashMap.get("fileName") + ".apk");

                    break;

                default:
                    break;
            }
        }
    };

    private String install(String apkAbsolutePath) {
        String[] args = {"pm", "install", "-r", apkAbsolutePath};
        String result = "";
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        Process process = null;
        InputStream errIs = null;
        InputStream inIs = null;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int read = -1;
            process = processBuilder.start();
            errIs = process.getErrorStream();

            while ((read = errIs.read()) != -1) {

                baos.write(read);
            }

            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                System.out.println("2 " + read);

                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("re " + result);
        String temp[] = result.split("\n");
        System.out.println("t " + temp[1]);
        if ("Success".equals(temp[1])) {
            System.out.println("ddd");
        }
        return result;

    }

    public DonwAPK(String updateVersionXMLPath, Context context) {
        super();
        this.updateVersionXMLPath = updateVersionXMLPath;
        this.context = context;
    }

    public void checkUpdate() {
        System.out.println("a");

        if (isUpdate()) {
            new downloadApkThread().start();
        } else {

        }
    }

    /**
     * ��װapk�ļ�
     */
    private void installAPK() {
        File apkfile = new File(fileSavePath, hashMap.get("fileName") + ".apk");
        if (!apkfile.exists()) {
            apkfile.delete();

            return;
        }
        // ͨ��Intent��װAPK�ļ�
        Intent i = new Intent(Intent.ACTION_VIEW);

        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);

    }

    /**
     * ж��Ӧ�ó���(û���õ�)
     */
    public void uninstallAPK() {
        Uri packageURI = Uri.parse("package:com.datamax.DmLauncher");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(uninstallIntent);

    }

    /**
     * ����apk�ķ���
     *
     * @author rongsheng
     */
    public class downloadApkThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // ��ô洢����·��

                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    fileSavePath = sdpath + "download";
                    URL url = new URL(hashMap.get("loadUrl"));
                    // ��������
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5 * 1000);// ���ó�ʱʱ��
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charser", "GBK,utf-8;q=0.7,*;q=0.3");

                    // ��ȡ�ļ���С
                    int length = conn.getContentLength();
                    // ����������
                    InputStream is = conn.getInputStream();

                    File file = new File(fileSavePath);
                    // �ж��ļ�Ŀ¼�Ƿ����

                    if (!file.exists()) {

                        file.mkdir();
                    }
                    File apkFile = new File(fileSavePath, hashMap.get("fileName") + ".apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // ����
                    System.out.println("aa");
                    byte buf[] = new byte[1024];
                    // д�뵽�ļ���
                    do {

                        int numread = is.read(buf);
                        count += numread;

                        Message message = new Message();
                        message.obj = DOWN;
                        handler.sendMessage(message);
                        if (numread <= 0) {

                            Message message2 = new Message();
                            message2.obj = DOWN_FINISH;
                            handler.sendMessage(message2);
                            break;
                        }
                        // д���ļ�
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// ���ȡ����ֹͣ����.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException " + e);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IOException " + e);
                e.printStackTrace();
            }

        }

    }

    private boolean isUpdate() {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        try {

            URL url = new URL(updateVersionXMLPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setRequestMethod("GET");
            InputStream inputStream = conn.getInputStream();

            ParseXmlUtil service = new ParseXmlUtil();
            hashMap = service.parseXml(inputStream);
        } catch (Exception e) {
            System.out.println("eer " + e);
            e.printStackTrace();
        }
        if (null != hashMap) {
            System.out.println("connect");
            return true;

        }
        return false;

    }
}