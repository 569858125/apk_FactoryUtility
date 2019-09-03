package com.datamax.factorytest.Apk;

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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.datamax.factorytest.ParseXmlUtil;
import com.datamax.factorytest.R;

public class Apktest extends Activity {
    private TextView count_down_success;
    private TextView count_install_success;
    private TextView count_uninstall_success;
    private int count_down = 0;
    private int count_install = 0;
    private int count_uninstall = 0;
    private int count_down_f = 0;
    private int count_install_f = 0;
    private int count_uninstall_f = 0;
    private TextView count_down_flase;
    private TextView count_install_flase;
    private TextView count_uninstall_flase;
    private TextView apk_connect;
    private EditText apk_IP;
    private Button btn_change;
    private Button btn_start;
    private TextView down_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO �Զ����ɵķ������AC
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apkdown_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        count_down_success = (TextView) findViewById(R.id.count_down_success);
        count_install_success = (TextView) findViewById(R.id.count_install_success);
        count_uninstall_success = (TextView) findViewById(R.id.count_uninstall_success);
        count_down_flase = (TextView) findViewById(R.id.count_down_flase);
        count_install_flase = (TextView) findViewById(R.id.count_install_flase);
        count_uninstall_flase = (TextView) findViewById(R.id.count_uninstall_flase);
        apk_connect = (TextView) findViewById(R.id.apk_connect);
        down_state = (TextView) findViewById(R.id.down_state);

        apk_IP = (EditText) findViewById(R.id.edit_IP);
        btn_change = (Button) findViewById(R.id.btn_change);
        btn_start = (Button) findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO �Զ����ɵķ������
                if (arg0.getId() == R.id.btn_start) {
                    cancelUpdate = false;
                    String str = apk_IP.getText().toString();
                    IP = "http://" + str;
                    updateVersionXMLPath = IP + "/upData/luacher.xml";
                    if (isUpdate()) {
                        down_state.setText("��������");
                        new downloadApkThread().start();
                    }
                }
            }
        });
        btn_change.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO �Զ����ɵķ������
                if (arg0.getId() == R.id.btn_change) {
                    down_state.setText("��ͣ");
                    cancelUpdate = true;
                    String str = apk_IP.getText().toString();
                    IP = "http://" + str;
                    updateVersionXMLPath = IP + "/upData/luacher.xml";
                }

            }
        });
        if (isUpdate()) {
            down_state.setText("��������");
            new downloadApkThread().start();
        }
    }

    private static final int DOWN_FINISH = 0;// ���������������
    private static final int INSTALL_FINISH = 1;
    private static final int UNINSTALL_FINISH = 2;
    private static final int RESTART_DOWN = 3;
    private HashMap<String, String> hashMap;// �洢���İ汾��xml��Ϣ
    private String fileSavePath;// ������apk�ĳ����ص�
    private static String IP = "http://172.10.1.121";
    private String updateVersionXMLPath = IP + "/upData/luacher.xml";// �����µ�xml�ļ�
    private boolean cancelUpdate = false;// �Ƿ�ȡ������
    private Context context;

    private Handler handler = new Handler() {// ����ui

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((Integer) msg.obj) {

                case DOWN_FINISH:
                    down_state.setText("���ڰ�װ");
                    count_down++;
                    count_down_success.setText(count_down + "��");
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (!cancelUpdate) {
                                // if(TextUtils.isEmpty(hashMap.get("fileName")))

                                install(fileSavePath + "/" + hashMap.get("fileName") + ".apk");
                            }
                        }

                    }, 1000);// 2��֮��ִ��
                    break;
                case INSTALL_FINISH:

                    break;
                case UNINSTALL_FINISH:

                    break;
                case RESTART_DOWN:
                    count_down_f++;
                    String str = count_down_f + "��";
                    System.out.println("str " + str);
                    count_down_flase.setText(str);
                    System.out.println("err ");
                    AlertDialog dialog = new AlertDialog.Builder(Apktest.this).setTitle("δ�ҵ�����Ŀ�꣬�Ƿ����¼�飿" + "  IP��ַ�� " + IP)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                    if (isUpdate() && !cancelUpdate) {
                                        down_state.setText("��������");
                                        new downloadApkThread().start();
                                    }
                                }
                            })

                            .setNegativeButton("no", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog1, int which) {

                                }
                            }).show();

                    break;
                default:
                    break;
            }
        }

    };
    private String packageName;

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

                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {
            // TODO: handle exception

        }

        String temp[] = result.split("\n");
        System.out.println("t " + temp[1]);
        if ("Success".equals(temp[1])) {
            count_install++;
            count_install_success.setText(count_install + "��");
            down_state.setText("��װ�ɹ�");

        } else {
            count_install_f++;
            // count_install_flase.setTextColor(Color.RED);
            count_install_flase.setText(count_install_f + "��");
            down_state.setText("��װʧ��");
        }
        down_state.setText("����ж��");
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (!cancelUpdate)

                    uninstall();
            }

        }, 1000);// 2��֮��ִ��

        return result;

    }

    private void uninstall() {

        PackageManager pm = getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(fileSavePath + "/" + hashMap.get("fileName") + ".apk",
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;

            packageName = appInfo.packageName;

        }

        String[] args = {"pm", "uninstall", packageName};
        String result = null;
        ProcessBuilder processBuilder = new ProcessBuilder(args);
        ;
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
            baos.write('\n');
            inIs = process.getInputStream();
            while ((read = inIs.read()) != -1) {
                baos.write(read);
            }
            byte[] data = baos.toByteArray();
            result = new String(data);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            System.out.println("finally");
            try {
                if (errIs != null) {
                    errIs.close();
                }
                if (inIs != null) {
                    inIs.close();
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }
        System.out.println("re " + result);
        String temp[] = result.split("\n");
        System.out.println("t " + temp[1]);
        if ("Success".equals(temp[1])) {
            count_uninstall++;
            count_uninstall_success.setText(count_uninstall + "��");
            down_state.setText("ж�سɹ�");

        } else {
            count_uninstall_f++;
            // count_uninstall_flase.setTextColor(Color.RED);
            count_uninstall_flase.setText(count_uninstall_f + "��");
            down_state.setText("��װʧ��");
        }
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (isUpdate() && !cancelUpdate) {
                    down_state.setText("��������");
                    new downloadApkThread().start();
                }
            }

        }, 1000);// 2��֮��ִ��

    }

    /**
     * ����apk�ķ���
     */
    public class downloadApkThread extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                // down_state.setText("��������");
                // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // ��ô洢����·��

                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    fileSavePath = sdpath + "download";
                    URL url = new URL(IP + hashMap.get("loadUrl"));
                    // ��������
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5 * 1000);// ���ó�ʱʱ��
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charser", "GBK,utf-8;q=0.7,*;q=0.3");

                    // ��ȡ�ļ���С
                    int length = conn.getContentLength();
                    System.out.println("length " + length);

                    // ����������
                    InputStream is = conn.getInputStream();
                    System.out.println("is " + is);
                    File file = new File(fileSavePath);
                    // �ж��ļ�Ŀ¼�Ƿ����

                    if (!file.exists()) {

                        file.mkdir();
                    }
                    File apkFile = new File(fileSavePath, hashMap.get("fileName") + ".apk");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    // д�뵽�ļ���
                    do {

                        int numread = is.read(buf);
                        count += numread;

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
                Message message = new Message();
                message.obj = RESTART_DOWN;
                handler.sendMessage(message);
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("IOExceptiondown " + e);

                Message message = new Message();
                message.obj = RESTART_DOWN;
                handler.sendMessage(message);
                e.printStackTrace();
            }

        }

    }

    @SuppressLint("NewApi")
    private boolean isUpdate() {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        try {
            // ��version.xml�ŵ������ϣ�Ȼ���ȡ�ļ���Ϣ
            URL url = new URL(updateVersionXMLPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5 * 1000);
            conn.setRequestMethod("GET");// ����Ҫ��д
            InputStream inputStream = conn.getInputStream();
            // ����XML�ļ���
            ParseXmlUtil service = new ParseXmlUtil();
            hashMap = service.parseXml(inputStream);
        } catch (Exception e) {
            System.out.println("eer " + e);
            // apk_connect.setTextColor(Color.RED);

            e.printStackTrace();
        }
        if (null != hashMap) {
            apk_connect.setText("���ӳɹ�");
            return true;

        } else {
            apk_connect.setText("����ʧ��");
        }
        return false;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO �Զ����ɵķ������
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            cancelUpdate = true;
            finish();

            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
