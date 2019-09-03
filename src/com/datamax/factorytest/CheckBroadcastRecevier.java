package com.datamax.factorytest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.datamax.factorytest.utils.LogUtil;

import java.io.File;
import java.util.List;

public class CheckBroadcastRecevier extends BroadcastReceiver {
    private static final String ACTION_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
private static final String USB_DEVICE_ATTACHED="android.hardware.usb.action.USB_DEVICE_ATTACHED";
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	LogUtil.i(intent.getAction()+"   time start:"+System.currentTimeMillis());
        if (ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
        	long starttime=System.currentTimeMillis();
        	LogUtil.i("time starttime:"+starttime);
            LogUtil.d("ACTION_MEDIA_MOUNTED...");
            final String USB_PATH = intent.getData().getPath();
            String filename = "/konka_starttest.MD566e39fe5b5de8b9f356acef9691c3fbf";
            LogUtil.i("Checking the initial condition...");
            String sduri = USB_PATH + filename;
            File playuri = new File(sduri);
            if (playuri.exists()) {
            	long endtime=System.currentTimeMillis();
            	LogUtil.i("time end:"+endtime);
                if (!isTopActivity(context, "com.datamax.factorytest.MainActivity")) {
                    LogUtil.i("Starting the factory utilities");
                    Intent intents = new Intent();
                    intents.setClass(context, MainActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intents);
                    LogUtil.i("time :"+(endtime-starttime));
                }
            }

            String apkname = "/factorykk.apk";
            LogUtil.i("Checking the initial condition...");
            String apkuri = USB_PATH + apkname;
            File apkfile = new File(apkuri);

            if (apkfile.exists()) {
                int oldversion = Util.getVersionCode(context);
                if (oldversion != 0) {
//				String oldverison=getVersionName(context);
//				if(!oldverison.equals("")){
                    PackageManager pkgmgr = context.getPackageManager();
                    PackageInfo p = pkgmgr.getPackageArchiveInfo(apkuri, PackageManager.GET_ACTIVITIES);
//				    String newveriosn=p.versionName;
//				    if(!oldverison.equals(newveriosn)){
                    int newveriosn = p.versionCode;
                    if (oldversion < newveriosn) {
                        Intent installintent = new Intent();
                        installintent.setAction(Intent.ACTION_VIEW);
                        installintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        installintent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
                        context.startActivity(installintent);
                    }
                }
            }
        }
        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
            String packageName = intent.getData().getSchemeSpecificPart();
            if (packageName.equals("com.datamax.factorytest")) {
                if (!isTopActivity(context, "com.datamax.factorytest.MainActivity")) {
                    Intent intents = new Intent();
                    intents.setClass(context, MainActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intents);
                }
            }
        }
    }

    boolean isStart = false;



    public boolean isTopActivity(Context context, String app) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> appTask = mActivityManager.getRunningTasks(1);

        if (appTask != null && (appTask.size() > 0)) {
            return appTask.get(0).topActivity.toString().contains(app);
        }

        return false;
    }

}
