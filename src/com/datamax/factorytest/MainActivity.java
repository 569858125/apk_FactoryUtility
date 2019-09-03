package com.datamax.factorytest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.ActionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.text.Html;
import android.text.InputType;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.datamax.factorytest.R;
import com.datamax.factorytest.Apk.Apktest;
import com.datamax.factorytest.bluetooth.InputDeviceCriteria;
import com.datamax.factorytest.bluetooth.MyBluetoothAdapter;
import com.datamax.factorytest.bluetooth.RecordPlayThread;
import com.datamax.factorytest.utils.Logcat;
//import com.datamax.factorytest.view.SegmentedRadioGroup;
import com.droidlogic.app.OutputModeManager;

import java.io.File;
import java.lang.ref.WeakReference;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.bluetooth.BluetoothHidHost;

import th.dtv.MW;
import th.dtv.mw_data;


@SuppressLint("NewApi")
public class MainActivity extends Activity {

    private TextView video;
    private TextView softward;
    private TextView hardward;
    private TextView type;
    private TextView apk;
    private TextView ratio;
    private TextView rate;
    private TextView language;
    private TextView time;
    private TextView usb;
    private TextView usb_result;
    private TextView wifi;
    private TextView wifi_result;
    private TextView eth;
    private TextView eth_result;
    // private TextView mac;
    private TextView title;
    private TextView rate_max;
    private TextView rate_min;
    private static TextView rate_now;

    private Context mContext;
    int savestate[] = {0, 0, 0, 0};
    private TextView wifi_ip;
    private TextView eth_ip;
    private TextView mTvPlayStatusTips;
    private EditText SN_edit;
    private EditText MAC_edit;
    private Button macBtn;
    private Button snBtn;
    private Button macreset;
    private Button snreset;
    private TextView sn_read;
    private TextView mac_read;
    private TextView sd;
    private TextView sd_result;
    //    private ImageView wifi_image;
    private Handler mwMsgHandler;
    protected Looper looper = Looper.myLooper();
    private Button factorybtn;
    private mw_data.dvb_t_tp t_tpInfo = new mw_data.dvb_t_tp();
    private mw_data.dvb_s_tp s_tpInfo = new mw_data.dvb_s_tp();
    private TextView video_info;
    private RadioGroup changemode;
    private RadioButton checklocal;
    private LinearLayout linearsd;
    private LinearLayout linearnet,linearwifi;
    private LinearLayout linearbuletooth;
    private TextView bule_opend;
    private TextView bule_result;
    // private OutputModeManager mOutputModeManager;

    private TextView mBasicInfo;
    private ListView lv_bt;
    private BluetoothAdapter mBluetoothAdapter;
    private InputDeviceCriteria inputDeviceCriteria;
    private List<BluetoothDevice> bluetoothDeviceList = new ArrayList<BluetoothDevice>();
    private MyBluetoothAdapter myBluetoothAdapter;
    private BluetoothHidHost mHidHost;

    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private UiHandler mUIHandler;
    int boxType = 0; //0:ott   1:dongle    2:combo(no wifi)
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    private long mExitTime;
    private WifiManager wifiManager;
    private VideoView videoView;

    private int mCheckPlayStatusCount;
    private int[] num = {0, 0, 0, 0};

    private boolean mbTvIsPlaying = false;
    public static final String DTV_PLAYER_ACTION = "com.dtv.player";
    public static final String DTV_STOP_ACTION = "com.dtv.stop";
    public static final String DTV_PLAY_STATUS_ACTION = "com.dtv.play.status";
    
    
    TextView tv_test_state;
    LinearLayout linearLayout2;
    
    private RecordPlayThread recordPlayThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.revision_main);

        initView();
        initListener();
        initReceiverIntentFilter();
        initBluetooth();
        initMatcher();
    }

    private void initView() {
        mContext = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // mOutputModeManager = new OutputModeManager(mContext);

        linearsd = (LinearLayout) findViewById(R.id.linearsd);
        linearnet = (LinearLayout) findViewById(R.id.linearnet);
        linearwifi=(LinearLayout) findViewById(R.id.linearwifi);
        linearbuletooth = (LinearLayout) findViewById(R.id.Linearbuletooth);

        video_info = (TextView) findViewById(R.id.text_videoinfo);

        changemode = (RadioGroup) findViewById(R.id.changemode);
        checkdvb = (RadioButton) findViewById(R.id.button_dvbmode);
        checklocal = (RadioButton) findViewById(R.id.button_localmode);

        sn_read = (TextView) findViewById(R.id.SN_read);
        mac_read = (TextView) findViewById(R.id.mac_read);
        SN_edit = (EditText) findViewById(R.id.test_SN);
        MAC_edit = (EditText) findViewById(R.id.test_Mac);
        macBtn = (Button) findViewById(R.id.mac_burn_btn);
        snBtn = (Button) findViewById(R.id.sn_burn_btn);
        factorybtn = (Button) findViewById(R.id.factoryrestorebtn);
        factorybtn.setVisibility(View.GONE);
        macreset = (Button) findViewById(R.id.mac_reset_burn_btn);
        snreset = (Button) findViewById(R.id.sn_reset_burn_btn);

        macBtn.setOnClickListener(new burnListener());
        snBtn.setOnClickListener(new burnListener());
        macreset.setOnClickListener(new burnListener());
        snreset.setOnClickListener(new burnListener());
        factorybtn.setOnClickListener(new burnListener());
        // changeplaer.setOnClickListener(new burnListener());

        softward = (TextView) findViewById(R.id.test_softward);
        hardward = (TextView) findViewById(R.id.test_hardward);
        type = (TextView) findViewById(R.id.test_type);
        apk = (TextView) findViewById(R.id.test_apk);
        ratio = (TextView) findViewById(R.id.test_ratio);
        rate = (TextView) findViewById(R.id.test_rate);
        rate_max = (TextView) findViewById(R.id.text_max);
        rate_min = (TextView) findViewById(R.id.text_min);
        rate_now = (TextView) findViewById(R.id.test_now);
        language = (TextView) findViewById(R.id.test_language);
        time = (TextView) findViewById(R.id.test_time);
        // mac = (TextView) findViewById(R.id.test_mac);

        usb = (TextView) findViewById(R.id.test_usb);
        usb_result = (TextView) findViewById(R.id.usb_result);
        sd = (TextView) findViewById(R.id.test_sd);
        sd_result = (TextView) findViewById(R.id.sd_result);
        wifi = (TextView) findViewById(R.id.test_wifi);
        wifi_result = (TextView) findViewById(R.id.wifi_result);
        wifi_ip = (TextView) findViewById(R.id.wifi_ip);
//        wifi_image = (ImageView) findViewById(R.id.wifi_image);
        eth = (TextView) findViewById(R.id.test_eth);
        eth_result = (TextView) findViewById(R.id.eth_result);
        eth_ip = (TextView) findViewById(R.id.eth_ip);
        title = (TextView) findViewById(R.id.test_title);
        bule_opend = (TextView) findViewById(R.id.bule_opend);
        bule_result = (TextView) findViewById(R.id.bule_result);

        if(boxType==1){
        	linearnet.setVisibility(View.GONE);
        }
        if(boxType==2){
        	linearwifi.setVisibility(View.GONE);
        }
		
		
		mBasicInfo = (TextView) findViewById(R.id.information);

        videoView = (VideoView) findViewById(R.id.videoView1);

        mTvPlayStatusTips = (TextView) findViewById(R.id.tv_status_tip);
        mac_read.setText(Util.readMAC());
        sn_read.setText(Util.readSN());
        SN_edit.setInputType(InputType.TYPE_NULL);
        SN_edit.requestFocus();

        MAC_edit.setInputType(InputType.TYPE_NULL);

        lv_bt = (ListView) findViewById(R.id.lv_bt);
        myBluetoothAdapter = new MyBluetoothAdapter(bluetoothDeviceList, this);
        lv_bt.setAdapter(myBluetoothAdapter);

        tv_test_state=(TextView)findViewById(R.id.tv_test_state);
        linearLayout2=(LinearLayout)findViewById(R.id.linearLayout2);
    }

    private void initBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            mBluetoothAdapter.startDiscovery();
            bluetoothDeviceList.addAll(mBluetoothAdapter.getBondedDevices());
            myBluetoothAdapter.notifyDataSetChanged();
            mBluetoothAdapter.getProfileProxy(this, mListener, BluetoothProfile.HID_HOST);
            recordPlayThread = new RecordPlayThread();
            recordPlayThread.start();
        } else {
            Toast.makeText(this, "蓝牙初始化失败", Toast.LENGTH_LONG).show();
            Logcat.e("蓝牙初始化失败");
        }
    }

    private BluetoothProfile.ServiceListener mListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceDisconnected(int profile) {
            Logcat.e("onServiceDisconnected");
            mHidHost = null;
        }

        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            Logcat.e("onServiceConnected");
            mHidHost = (BluetoothHidHost) proxy;
        }
    };


    private void initMatcher() {
        inputDeviceCriteria = new InputDeviceCriteria();
    }


    private void initListener() {

        lv_bt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = bluetoothDeviceList.get(position);
                int bondState = device.getBondState();
                if (bondState == BluetoothDevice.BOND_NONE) {
                    device.createBond();
                } else if (bondState == BluetoothDevice.BOND_BONDED) {
                    device.removeBond();
                }
            }
        });

        videoView.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int arg1, int arg2) {

                return false;
            }
        });

        changemode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.button_dvbmode) {
                    videoView = (VideoView) findViewById(R.id.videoView1);
                    videoView.stopPlayback();
                    mwMsgHandler = new MWmessageHandler(looper);
                    enableMwMessageCallback(mwMsgHandler);
                    MW.register_event_type(MW.EVENT_TYPE_PLAY_STATUS, true);
                    MW.register_event_type(MW.EVENT_TYPE_TUNER_STATUS, true);
                    MW.register_event_type(MW.EVENT_TYPE_DATE_TIME, true);
                    MW.register_event_type(MW.EVENT_TYPE_EPG_CURRENT_NEXT_UPDATE, true);
                    F_CreateDVBPlayer();
                } else if (checkedId == R.id.button_localmode) {
                    stopTv();
                    video_info.setText("视频");
                    videoView = (VideoView) findViewById(R.id.videoView1);
                    mHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            F_CreateOTTPlayer();
                        }
                    }, 1000);
                }
            }
        });

        MAC_edit.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MAC_edit.setSelection(MAC_edit.getText().length());
                return true;
            }
        });
        MAC_edit.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66 || keyCode == 23 || keyCode == 160) {
                    String strlength = MAC_edit.getText().toString();
                    if (strlength.length() == 12 || strlength.length() == 17) {
                        macBtn.performClick();
                    } else {
                        MAC_edit.setText("");
                        Toast.makeText(mContext, " rescan mac", 2000).show();
                    }
                    return true;
                }
                return false;
            }
        });
        SN_edit.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SN_edit.setSelection(SN_edit.getText().length());
                return true;
            }
        });
        SN_edit.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66 || keyCode == 23 || keyCode == 160) {
                    String strlength = SN_edit.getText().toString();
                    if (strlength.contains("KK") && strlength.length() == 16) {
                        snBtn.performClick();
                    } else {
                        SN_edit.setText("");
                        Toast.makeText(mContext, "rescan sn", 2000).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void wifiConnect() {
    	final WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + "KONKA_SW" + "\"";
        // nopass
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        wifiManager.removeNetwork(config.networkId);
        netID = wifiManager.addNetwork(config);
        boolean suc=wifiManager.enableNetwork(netID, true);
//        if(suc){
//        	 Toast.makeText(mContext, config.SSID + " " + netID, 3000).show();
//        }
        wifiManager.connect(config, new ActionListener() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(mContext, config.SSID + " " + netID, 3000).show();
			}
			
			@Override
			public void onFailure(int arg0) {
				Toast.makeText(mContext, config.SSID + " error  " + netID, 3000).show();
			}
		});
    }

    private static final String PROP_SW_VERSION = "ro.build.display.id";
    private static final String PROP_HW_VERSION = "ro.hardware";

    class UiHandler extends Handler {
        WeakReference<MainActivity> mActivity;
        private String mCpuCurFreqStr;
        private String formatTemplate;

        UiHandler(MainActivity activity, String str, String format) {
            mActivity = new WeakReference<MainActivity>(activity);
            mCpuCurFreqStr = str;
            formatTemplate = format;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SpannableString cpuFreqCur = new SpannableString(Html.fromHtml(String.format(formatTemplate, mCpuCurFreqStr, Util.getCurCpuFreq())));
                    SpannableStringBuilder info = (SpannableStringBuilder) msg.obj;
                    info.append(cpuFreqCur);
                    mBasicInfo.setText(info, TextView.BufferType.SPANNABLE);
                    info.clear();
                    Message message = mUIHandler.obtainMessage(1);
                    message.obj = getDeviceInfo();
                    mUIHandler.sendMessageDelayed(message, 1000);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private String getStringViaID(int id) {
        return getResources().getString(id);
    }

    private SpannableStringBuilder getDeviceInfo() {
        final SpannableStringBuilder mStaticInfo = new SpannableStringBuilder();
        String formatTemplate = getResources().getString(R.string.general_info);
        SpannableString hardware = new SpannableString(Html.fromHtml(String.format(formatTemplate, getResources().getString(R.string.hw_version), SystemProperties.get(PROP_HW_VERSION))));
        SpannableString module = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.module_number), Build.MODEL)));
        SpannableString software = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.sw_version), SystemProperties.get(PROP_SW_VERSION))));
        SpannableString apk_version = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.app_version), Util.getVersionName(mContext))));
        SpannableString ratio = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.resolution), new OutputModeManager(mContext).getCurrentOutputMode())));
        SpannableString language = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.language), Util.GetLanguageSummary(mContext))));
        SpannableString time = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.time_zone), Util.getTimeZoneText(Calendar.getInstance()))));
        SpannableString cpuFreqMin = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.min_freq), Util.getMinCpuFreq())));
        SpannableString cpuFreqMax = new SpannableString(Html.fromHtml(String.format(formatTemplate, getStringViaID(R.string.max_freq), Util.getMaxCpuFreq())));
        mStaticInfo.clear();
        mStaticInfo.append(hardware);
        mStaticInfo.append("\n");
        mStaticInfo.append(module);
        mStaticInfo.append("\n");
        mStaticInfo.append(apk_version);
        mStaticInfo.append("\n");
        mStaticInfo.append(software);
        mStaticInfo.append("\n\n");
        mStaticInfo.append(ratio);
        mStaticInfo.append("\n");
        mStaticInfo.append(language);
        mStaticInfo.append("\n");
        mStaticInfo.append(time);
        mStaticInfo.append("\n\n");
        mStaticInfo.append(cpuFreqMin);
        mStaticInfo.append("\n");
        mStaticInfo.append(cpuFreqMax);
        mStaticInfo.append("\n");
        return mStaticInfo;
    }


    public void leftInfo() {
        mUIHandler = new UiHandler(MainActivity.this, getStringViaID(R.string.current_freq), getResources().getString(R.string.general_info));
        Message message = mUIHandler.obtainMessage(1);
        message.obj = getDeviceInfo();
        mUIHandler.sendMessage(message);
        String custom = SystemProperties.get("ro.product.model", "null");
        if (!custom.equals("Etimo@T2")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BluetoothAdapter mAdapter;
                    mAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (mAdapter != null) {
                        mAdapter.enable();

                        mAdapter.startDiscovery();
                        while (mAdapter.getState() != BluetoothAdapter.STATE_ON) {
                            if (mAdapter.isDiscovering()) {
                                mAdapter.cancelDiscovery();
                            }
                            mAdapter.startDiscovery();
                        }
                    }
                }
            }).start();
        }
    }


    private void initReceiverIntentFilter() {
        IntentFilter filter_connection = new IntentFilter();
        filter_connection.addAction(CONNECTIVITY_CHANGE_ACTION);
        filter_connection.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter_connection.addAction( WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter_connection.addAction( WifiManager.RSSI_CHANGED_ACTION);
        filter_connection.addAction(DTV_STOP_ACTION);
        registerReceiver(mConnectionReceiver, filter_connection);

        IntentFilter filter_mount = new IntentFilter();
        filter_mount.addAction(Intent.ACTION_MEDIA_EJECT);
        filter_mount.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter_mount.addAction(Intent.ACTION_MEDIA_REMOVED);
        filter_mount.addDataScheme("file");
        registerReceiver(mMountReceiver, filter_mount);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBluetoothReceiver, filter);
    }

    private BroadcastReceiver mMountReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
                return;

            if (Intent.ACTION_MEDIA_EJECT.equals(action) || Intent.ACTION_MEDIA_UNMOUNTED.equals(action)
                    || Intent.ACTION_MEDIA_MOUNTED.equals(action)) {
                upData();
                if (ottflat) {
                    F_CreateOTTPlayer();
                }
            }

        }
    };

    public void upData() {
        int monutnum = Constant.isudiskExists(this);
        if (dongleflat || polytron) {
            switch (monutnum) {
                case 0:
                    usb.setText(getString(R.string.no_device));
                    usb_result.setText(getString(R.string.failed));
                    usb_result.setTextColor(Color.RED);
                    savestate[0] = 0;
                    break;
                case 1:
                    usb.setText(getString(R.string.usb_numbers_1));
                    usb_result.setText(getString(R.string.success));
                    savestate[0] = 1;
                    usb_result.setTextColor(Color.GREEN);
                    break;
                default:
                    break;
            }
        } else {
            switch (monutnum) {
                case 0:
                    usb.setText(getString(R.string.no_device));
                    usb_result.setText(getString(R.string.failed));
                    usb_result.setTextColor(Color.RED);
                    savestate[0] = 0;
                    break;
                case 1:
                    usb.setText(getString(R.string.usb_numbers_1));
                    usb_result.setText(getString(R.string.success));
                    if(boxType==1){
                    	savestate[0] = 1;
                    }else{
                    	savestate[0] = 0;
                    }
                    usb_result.setTextColor(Color.GREEN);

                    break;
                case 2:
                    usb.setText(getString(R.string.usb_numbers_2));
                    usb_result.setText(getString(R.string.success));
                    savestate[0] = 1;
                    usb_result.setTextColor(Color.GREEN);
                    break;
                case 3:
                    usb.setText(getString(R.string.usb_numbers_3));
                    usb_result.setText(getString(R.string.success));
                    savestate[0] = 1;
                    usb_result.setTextColor(Color.GREEN);
                    break;
                default:
                    break;
            }

        }
        if (Constant.isSdcardExists(this)) {
            sd.setText(getString(R.string.sd_detected));
            sd_result.setText(getString(R.string.success));
            sd_result.setTextColor(Color.GREEN);
            savestate[3] = 1;
        } else {
            sd_result.setText(getString(R.string.failed));
            sd.setText(getString(R.string.sd_not_detected));
            sd_result.setTextColor(Color.RED);
            savestate[3] = 0;
        }
        titleres();
    }


    public void titleres() {
    	//3 sd    2
        Log.d("Daniel", "Update tileres: " + savestate[0] + "/" + savestate[1] + "/" + savestate[2] + "/" + savestate[3]);
        if (boxType==2) {
            if (savestate[0] == 1 && savestate[2] == 1 && savestate[3] == 1) {
                title.setText(getString(R.string.success));
                title.setTextColor(Color.GREEN);
                showNormalDialog();
            } else {
                title.setText(getString(R.string.failed));
                title.setTextColor(Color.RED);
            }
        } else if (boxType==0){
            if (savestate[0] == 1 && savestate[1] == 1 && savestate[2] == 1 && savestate[3] == 1) {
                title.setText(getString(R.string.success));
                title.setTextColor(Color.GREEN);
                showNormalDialog();
            } else {
                title.setText(getString(R.string.failed));
                title.setTextColor(Color.RED);
            }
        }else if (boxType==1){
            if (savestate[0] == 1 && savestate[1] == 1 && savestate[3] == 1) {
                title.setText(getString(R.string.success));
                title.setTextColor(Color.GREEN);
                showNormalDialog();
            } else {
                title.setText(getString(R.string.failed));
                title.setTextColor(Color.RED);
            }
        }
    }

    private void showNormalDialog() {
    	tv_test_state.setVisibility(View.VISIBLE);
    	linearLayout2.setVisibility(View.GONE);
//        final AlertDialog.Builder normalDialog =
//                new AlertDialog.Builder(MainActivity.this);
//        normalDialog.setMessage("Test completed");
//        normalDialog.setPositiveButton("ok",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//        normalDialog.setNegativeButton("exit",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        normalDialog.show();
    }


    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                Logcat.i("name:" + device.getName() + "    address:" + device.getAddress() + "-----status:" + device.getBondState());
                if (inputDeviceCriteria.isMatchingDevice(device)) {
                    Logcat.e("Input-------------name:" + device.getName() + "    address:" + device.getAddress() + "-----status:" + device.getBondState());
                    for (int i = 0; i < bluetoothDeviceList.size(); i++) {
                        BluetoothDevice bluetoothDevice = bluetoothDeviceList.get(i);
                        if (bluetoothDevice.getAddress().equals(device.getAddress())) {
                            return;
                        }
                    }
                    bluetoothDeviceList.add(device);
                    myBluetoothAdapter.notifyDataSetChanged();
                }
            } else if (action != null && action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                mBluetoothAdapter.startDiscovery();
            } else if (action != null && action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                for (int i = 0; i < bluetoothDeviceList.size(); i++) {
                    BluetoothDevice bluetoothDevice = bluetoothDeviceList.get(i);
                    if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                        try {
                            mHidHost.connect(device);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Logcat.e(e.toString());
                        }
                    }
                    if (device.getAddress().equals(bluetoothDevice.getAddress())) {
                        bluetoothDeviceList.set(i, device);
                        myBluetoothAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    private BroadcastReceiver mConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {
                connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    if (name != null && name.equals("WIFI")) {
                        connectStatus();
                    } else {
                    	if(boxType!=1){
                    		ethConnect();
                    	}
                    }
                } else {
                    Log.d("mark", getString(R.string.NO_eth_cable_connected));
                }
                // connectStatus();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.wifi_frame, new WifiSettings());
                ft.commit();
            }else{
            	connectStatus();
            }
        }
    };

    public void ethConnect() {
        if (Constant.isEthConnected(mContext)) {

            String string = null;
            eth.setText(getString(R.string.eth_cable_connected));
            eth_result.setText(getString(R.string.success));
            eth_result.setTextColor(Color.GREEN);

            string = getEthernetIpAddress();
            eth_ip.setText(getString(R.string.ip_addr) + string + " ");
            savestate[2] = 1;
            titleres();

//            if (!netreminder) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle(getString(R.string.WIFI_lianjie))
//                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog1, int which) {
//                            }
//                        }).show();
//            }
        }

    }

    public String getEthernetIpAddress() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        LinkProperties linkProperties = mConnectivityManager.getLinkProperties(ConnectivityManager.TYPE_ETHERNET);
        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            // connected to the internet
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_ETHERNET:
                    break;
                default:
                    break;
            }
        } else {
            return "";
        }
        if (linkProperties == null) {
            return "";
        }

        for (LinkAddress linkAddress : linkProperties.getAllLinkAddresses()) {
            InetAddress address = linkAddress.getAddress();
            if (address instanceof Inet4Address) {
                return address.getHostAddress();
            }
        }

        // IPv6 address will not be shown like WifiInfo internally does.
        return "";
    }

    private static Boolean netreminder = false;

    public void connectStatus() {
        if (Constant.isWifiEnabled(mContext)) {

            wifi.setText(getString(R.string.WIFI_kaiqi));
            wifi_result.setText(getString(R.string.weilianjie));
//            wifi_image.setVisibility(View.VISIBLE);
//            wifi_image.setImageResource(R.drawable.wifi1);
            wifi_result.setTextColor(Color.RED);
        }
        if (Constant.isWifiConnected(mContext)) {

            connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                String string = (getString(R.string.yimoren) + info.getExtraInfo());
                String IP = getString(R.string.ip_addr) + getWifiIpAddress() + " ";
                // WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                netreminder = true;
                wifi_ip.setText(IP);

                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (null != wifiInfo) {
                    wifi_result.setText(string);
                    wifi_result.setTextColor(Color.GREEN);
                    savestate[1] = 1;
                }
            }
        }
        titleres();

    }

    public String getWifiIpAddress() {
        WifiManager mWifiManager = (WifiManager) mContext.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));

    }

    private void destroyReceiver() {
        this.unregisterReceiver(mConnectionReceiver);
        this.unregisterReceiver(mMountReceiver);
        wifiManager.enableNetwork(netID, false);
        wifiManager.setWifiEnabled(false);
        unregisterReceiver(mBluetoothReceiver);
    }


    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, getString(R.string.tuichu), 1000).show();
                mExitTime = System.currentTimeMillis();
            } else {
                Util.setgree();
                System.exit(0);
            }
            return true;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_5:
                num[0] = keyCode;
                num[1] = -1;
                num[2] = -1;
                num[3] = -1;
                break;
            case KeyEvent.KEYCODE_6:
                if (num[0] == 12) {
                    num[1] = KeyEvent.KEYCODE_6;
                    num[2] = -1;
                    num[3] = -1;
                }

                break;
            case KeyEvent.KEYCODE_8:
                if (num[1] == 13 && num[0] == 12) {
                    num[2] = KeyEvent.KEYCODE_8;
                    num[3] = -1;
                }
                break;
            case KeyEvent.KEYCODE_9:
                if (num[2] == 15 && num[1] == 13 && num[0] == 12) {
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.shifouxiazai))
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                    dialog1.dismiss();
                                    Intent intent = new Intent();
                                    intent.setClass(MainActivity.this, Apktest.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    mContext.startActivity(intent);
                                    stopTv();
                                }
                            }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                }
                            }).show();

                }
                break;
            default:
                num[0] = -1;
                num[1] = -1;
                num[3] = -1;
                num[2] = -1;

                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void restartsys() {
        ProgressDialog pd = new ProgressDialog(mContext);
        pd.setTitle("power off");

        pd.setMessage("shutting down...");

        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        pd.show();

        SystemProperties.set("sys.reset.uboot.data", "true");
        Thread thr = new Thread() {
            @Override
            public void run() {
                resetUbootData();
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                }
                mContext.sendBroadcast(new Intent("android.intent.action.MASTER_CLEAR"));
                System.exit(0);
            }
        };
        thr.start();
    }

    static Boolean typeflat = false;
    static Boolean T2flat = false;
    static Boolean dongleflat = false;
    static Boolean bluetoothflat = false;

    @Override
    protected void onResume() {
        super.onResume();
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        Log.d("sss", "language=" + language);
        Log.d("sss", "country=" + country);
        upData();
        leftInfo();
        opendwifi();

        if (boxType!=2) {
            wifiConnect();
        }
        connectStatus();

        Fragment mCurrentFragment = null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        mCurrentFragment = new WifiSettings();
        ft.replace(R.id.wifi_frame, mCurrentFragment);
        ft.commit();
        if (typeflat) {

            checklocal.setChecked(true);

        } else {
            video_info.setText(getString(R.string.shiping));
            videoView = (VideoView) findViewById(R.id.videoView1);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    F_CreateOTTPlayer();
                }
            }, 1000);
        }
        ledstop = false;
        ledrun();
    }

    public void opendwifi() {
        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        if (!dongleflat) {
            // mEthManager.setEthEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyReceiver();
        mBluetoothAdapter.closeProfileProxy(BluetoothProfile.HID_HOST, mHidHost);
        if (recordPlayThread != null) {
            recordPlayThread.interrupt();
        }
        if (typeflat) {
            stopTv();
        }
    }

    private String playType[] = {"DM03", "DM03S", "DM04", "DM05", "T2", "P405T", "DM03M", "DM03M1", "DM03A", "DM03I",
            "DM13", "DM13S", "DM14", "DM15", "DM13M", "DM13M1", "DM13A", "DM13I"};
    private String T2Type[] = {"DM03", "DM04", "T2", "P405T", "DM03M", "DM03M1", "DM03A", "DM03I", "DM13", "DM14",
            "DM13M", "DM13M1", "DM13A", "DM13I"};
    private String dongleType[] = {"D01"};

    private boolean polytron = false;

    @Override
    protected void onStart() {
        super.onStart();

        type2 = SystemProperties.get("ro.product.model", "null");
        if (type2 != null) {
            for (String s : playType) {
                if (type2.equals(s)) {
                    typeflat = true;
                    break;
                } else {
                    typeflat = false;

                }
            }
            if (!typeflat) {
                changemode.setVisibility(View.GONE);
            }
            if (type2.equals("PDB-F2")) {
                polytron = true;
            }
            if (type2.equals("D01") || type2.equals("D01B")) {
                dongleflat = true;
                if (type2.equals("D01B"))
                    bluetoothflat = true;
                dongleshow();
            }
        }

        // opendbuletooth();
    }

    public void opendbuletooth() {

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            bule_opend.setText(getString(R.string.NO_BL_qudong));
            savestate[2] = 0;
            bule_result.setText(getString(R.string.failed));
            bule_result.setTextColor(Color.RED);
            // finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            savestate[2] = 1;
            bule_result.setText(getString(R.string.success));
            bule_result.setTextColor(Color.GREEN);
            mBluetoothAdapter.enable();
        }

    }

    public void dongleshow() {
        linearnet.setVisibility(View.INVISIBLE);
        if (bluetoothflat) {
            linearnet.setVisibility(View.GONE);
            linearbuletooth.setVisibility(View.VISIBLE);
            opendbuletooth();
        }

        // linearsd.setVisibility(View.INVISIBLE);
    }

    private static final String TAG = "Test";
    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        @Override
		public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Log.d(TAG, "surfaceChanged " + w + " h " + h);
            // initSurface(holder);

            setVideoWindow(mContext, true);
        }

        @Override
		public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            try {
                initSurface(holder);
            } catch (Exception e) {
            }
        }

        @Override
		public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
            setVideoWindow(mContext, false);
        }

        private void initSurface(SurfaceHolder h) {
            Canvas c = null;
            try {
                Log.d(TAG, "initSurface");
                c = h.lockCanvas();
            } finally {
                if (c != null)
                    h.unlockCanvasAndPost(c);
            }
        }
    };


    public void playDVBT2() {
        Log.d(TAG, "playDVBT2() ");
        MW.tuner_dvb_t_lock_tp(MW.MAIN_TUNER_INDEX, 0, 0, false);
        MW.ts_player_play_current(true, false);
        videoView.getHolder().addCallback(mSHCallback);
        // videoView.getHolder().setFormat(PixelFormat.VIDEO_HOLE_REAL);
        Constant.putSettingsProviderInt(mContext, DTV_PLAY_STATUS_ACTION, 0);
        mCheckPlayStatusCount = 0;
        startTv();
        if (MW.db_dvb_t_get_current_tp_info(t_tpInfo) == MW.RET_OK) {
            String str = getString(R.string.sp) + getString(R.string.pinglu) + t_tpInfo.frq + getString(R.string.daikuan) + t_tpInfo.bandwidth + getString(R.string.zhuanfaqi) + t_tpInfo.channel_number + ")";
            video_info.setText(str);
        }
    }

    public void playDVBS2() {
        Log.d(TAG, "playDVBS2() ");
        MW.tuner_dvb_s_lock_tp(MW.MAIN_TUNER_INDEX, 0, 0, false);
        MW.ts_player_play_current(true, false);
        videoView.getHolder().addCallback(mSHCallback);

        // videoView.getHolder().setFormat(PixelFormat.VIDEO_HOLE_REAL);
        Constant.putSettingsProviderInt(mContext, DTV_PLAY_STATUS_ACTION, 0);
        mCheckPlayStatusCount = 0;
        startTv();
        if (MW.db_dvb_s_get_current_tp_info(MW.MAIN_TUNER_INDEX, s_tpInfo) == MW.RET_OK) {
            String str = getString(R.string.sp) + getString(R.string.pinglu) + s_tpInfo.frq + getString(R.string.fumalu) + s_tpInfo.sym + ")";
            video_info.setText(str);
        }

    }

    public void startTv() {

        Log.d(TAG, "startTv() " + mbTvIsPlaying + " mTvVideoView " + videoView);
        if (videoView != null && !mbTvIsPlaying) {
            Intent intent = new Intent();
            intent.setAction(DTV_PLAYER_ACTION);
            mContext.sendBroadcast(intent);
            mbTvIsPlaying = true;
            mCheckPlayStatusCount = 0;
            mHandler.postDelayed(mGetTvPlayStatusRunnable, 1000L);
            setVideoWindow(mContext, true);
        }

    }

    public void stopTv() {
        Log.d(TAG, "stopTv() " + mbTvIsPlaying + " mTvVideoView " + videoView);

        if (videoView != null && mbTvIsPlaying) {
            Intent intent = new Intent();
            intent.setAction(DTV_STOP_ACTION);
            mContext.sendBroadcast(intent);

            mbTvIsPlaying = false;
            mTvPlayStatusTips.setText("");
        }
        if (typeflat) {
            MW.tuner_unlock_tp(MW.MAIN_TUNER_INDEX, MW.SYSTEM_TYPE_TER, false);
            MW.tuner_unlock_tp(MW.MAIN_TUNER_INDEX, MW.SYSTEM_TYPE_SAT, false);
        }
        mHandler.removeCallbacks(mGetTvPlayStatusRunnable);
        enableMwMessageCallback(null);
        MW.ts_player_stop_play();
        videoView = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " onPause");
        ledstop = true;
        if (typeflat) {
            stopTv();
        }
        Util.setgree();
    }

    private Handler mHandler = new Handler();
    private static final int PLAY_STATUS_UNKNOW = 0;
    private static final int PLAY_STATUS_OK = PLAY_STATUS_UNKNOW + 1;
    private static final int PLAY_STATUS_LOCK = PLAY_STATUS_OK + 1;
    private static final int PLAY_STATUS_NO_AUDIO = PLAY_STATUS_LOCK + 1;
    private static final int PLAY_STATUS_NO_CHANNEL = PLAY_STATUS_NO_AUDIO + 1;
    private static final int PLAY_STATUS_NO_VIDEO = PLAY_STATUS_NO_CHANNEL + 1;
    private static final int PLAY_STATUS_SCRAMBLE = PLAY_STATUS_NO_VIDEO + 1;
    private static final int PLAY_STATUS_NO_SIGNAL = PLAY_STATUS_SCRAMBLE + 1;
    private Runnable mGetTvPlayStatusRunnable = new Runnable() {
        @Override
		public void run() {
            if (mCheckPlayStatusCount % 2 == 0) {
                Intent intent = new Intent();
                intent.setAction(DTV_PLAY_STATUS_ACTION);
                mContext.sendBroadcast(intent);

                mHandler.postDelayed(mGetTvPlayStatusRunnable, 100);
                mCheckPlayStatusCount = 1;
            } else if (mCheckPlayStatusCount % 2 == 1) {
                int status = Constant.getSettingsProviderInt(mContext, DTV_PLAY_STATUS_ACTION, 0);
                if (status == PLAY_STATUS_LOCK)// 2 means has lock.
                {
                    mTvPlayStatusTips.setVisibility(View.GONE);
                    mTvPlayStatusTips.setVisibility(View.VISIBLE);
                    mTvPlayStatusTips.setText(getString(R.string.pindaosuoding));

                } else if (status == PLAY_STATUS_NO_AUDIO) {
                    mTvPlayStatusTips.setVisibility(View.VISIBLE);
                    mTvPlayStatusTips.setText(getString(R.string.meiyouyinping));
                } else if (status == PLAY_STATUS_NO_CHANNEL) {
                } else if (status == PLAY_STATUS_NO_VIDEO) {
                    mTvPlayStatusTips.setVisibility(View.VISIBLE);
                    mTvPlayStatusTips.setText(getString(R.string.meiyoushiping));
                } else if (status == PLAY_STATUS_SCRAMBLE) {
                    mTvPlayStatusTips.setVisibility(View.VISIBLE);
                    mTvPlayStatusTips.setText(getString(R.string.jiaraopindao));
                } else if (status == PLAY_STATUS_NO_SIGNAL) {
                    mTvPlayStatusTips.setVisibility(View.VISIBLE);
                    mTvPlayStatusTips.setText(getString(R.string.meiyouxinhao));
                } else {
                    mTvPlayStatusTips.setVisibility(View.GONE);
                }
                mCheckPlayStatusCount = 0;
                mHandler.postDelayed(mGetTvPlayStatusRunnable, 900);
            }

        }

    };

    public String checkusb() {
        String uri = null;
        final String USB_PATH = "/storage/external_storage";
        String filename = "/taiwan101.mov";
        String sduri = "dm";
        File dir = new File(USB_PATH);

        if (dir.exists() && dir.isDirectory()) {
            if (dir.listFiles() != null) {
                if (dir.listFiles().length >= 1) {
                    for (File file : dir.listFiles()) {
                        sduri = file.getAbsolutePath() + filename;

                        File play = new File(sduri);
                        if (play.exists()) {

                            break;
                        }
                    }
                }
            }

        }

        File playuri = new File(sduri);

        if (!playuri.exists()) {
            uri = "android.resource://" + getPackageName() + "/" + R.raw.test;
        } else {
            uri = "file://" + sduri;
        }

        return uri;
    }

    public void F_CreateDVBPlayer() {
        ottflat = false;
        for (int i = 0; i < T2Type.length; i++) {
            if (type2.equals(T2Type[i])) {
                T2flat = true;
                break;
            } else {
                T2flat = false;
            }
        }
        int[] search_region_index_list = new int[1];
        search_region_index_list[0] = 0;
        int[] search_tp_index_list = new int[1];
        search_tp_index_list[0] = 0;
        if (T2flat) {

            MW.search_dvb_t_start(2, search_region_index_list, search_tp_index_list, 1);
        } else {
            MW.search_dvb_s_start(2, search_region_index_list, search_tp_index_list, 1);
        }
    }

    public static Boolean ottflat = false;
    public static Boolean playflat = false;

    public void F_CreateOTTPlayer() {
        String uri = null;
        // videoView.setVideoPath("/mnt/usb_storage/USB_DISK0/udisk0/test.mp4");
        uri = checkusb();
        ottflat = true;
//
        videoView.setVideoURI(Uri.parse(uri));
        videoView.start();

        videoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
			public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "Prepared ");
                mp.start();

                mp.setLooping(true);
                if (!playflat) {
                    playflat = true;
                    checkplay();
                }

            }
        });

    }

    public void checkplay() {

        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (ottflat) {
                    if (videoView.isPlaying()) {
                        if (videoView.getDuration() == videoView.getCurrentPosition())
                            F_CreateOTTPlayer();

                    } else {
                        F_CreateOTTPlayer();
                    }
                    checkplay();
                } else {
                    playflat = false;
                }
            }
        }, 5000);

        // playflat = false;
    }

    private String burn_mac_str = null;
    private String burn_sn_str = null;

    public class burnListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.mac_burn_btn:
                    burn_mac_str = MAC_edit.getText().toString();
                    burn_mac_str = burn_mac_str.replace(" ", "");
                    burn_mac_str = burn_mac_str.replace(":", "");
                    if (Util.burn17MAC(Util.str2macStr(burn_mac_str))) {
                        Log.d("FAN---", "--------burn MAC success");
                        macBtn.setTextColor(Color.GREEN);
                        mac_read.setText(burn_mac_str);

                        String mac = Util.readMAC();
                        if (mac.contains(Util.str2macStr(burn_mac_str))) {
                            mac_read.setTextColor(Color.GREEN);
                            MAC_edit.setFocusable(false);
                            MAC_edit.setFocusableInTouchMode(false);
                        } else {

                        }

                    } else {
                        MAC_edit.setText(null);
                        MAC_edit.setHint(getString(R.string.Mac_worry));
                        macBtn.setTextColor(Color.RED);
                    }

                    break;
                case R.id.mac_reset_burn_btn:
                    MAC_edit.setHint(getString(R.string.W_tip_input_mac));
                    MAC_edit.setText(null);
                    burn_mac_str = null;
                    MAC_edit.setFocusable(true);
                    MAC_edit.setFocusableInTouchMode(true);

                    break;
                case R.id.sn_burn_btn:
                    burn_sn_str = SN_edit.getText().toString();
                    if (Util.burnSN_customer(burn_sn_str)) {
                        snBtn.setTextColor(Color.GREEN);
                        MAC_edit.requestFocus();
                    } else {
                        SN_edit.setText(null);
                        SN_edit.setHint(getString(R.string.SN_worry));
                        snBtn.setTextColor(Color.RED);
                    }
                    String sn = Util.readSN();
                    sn_read.setText(sn);
                    if (sn.contains(burn_sn_str)) {
                        sn_read.setTextColor(Color.GREEN);

                        SN_edit.setFocusable(false);
                        SN_edit.setFocusableInTouchMode(false);
                    } else {
                        sn_read.setTextColor(Color.RED);
                    }
                    break;
                case R.id.sn_reset_burn_btn:
                    SN_edit.setFocusable(true);
                    SN_edit.setFocusableInTouchMode(true);
                    SN_edit.setHint(getString(R.string.W_tip_input_sn));
                    SN_edit.setText(null);
                    burn_sn_str = null;
                    break;
                case R.id.factoryrestorebtn:
                    new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.is_factory_reset))
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog1, int which) {
                                    // restartsys();
                                }
                            })

                            .setNegativeButton("no", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog1, int which) {

                                }
                            }).show();

                    break;
            }

        }
    }

    static void setVideoWindow(Context context, boolean little) {
        int[] curPosition = {0, 0, 1280, 720, 1280, 720};

        if (little) {
            int[] scalePosition = {108, 373, 722, 519};
            scalePosition = GetScalePosition(35, 346, 690, 679);
            int x = curPosition[0] + (scalePosition[0] * curPosition[2]) / curPosition[4];
            int y = curPosition[1] + (scalePosition[1] * curPosition[3]) / curPosition[5];
            int w = x + ((scalePosition[2] - scalePosition[0]) * curPosition[2]) / curPosition[4];
            int h = y + ((scalePosition[3] - scalePosition[1]) * curPosition[3]) / curPosition[5];
            if (curPosition[0] > 0 && curPosition[0] <= 10) // offset
            {

                x = x - 5 * curPosition[4] / 1280;
                y = y - 5 * curPosition[5] / 720;
            } else if (curPosition[0] > 10 && curPosition[0] <= 20) {

                x = x - 10 * curPosition[4] / 1280;
                y = y - 10 * curPosition[5] / 720;
            } else if (curPosition[0] > 20 && curPosition[0] <= 30) {

                x = x - 12 * curPosition[4] / 1280;
                y = y - 12 * curPosition[5] / 720;
            } else if (curPosition[0] > 30 && curPosition[0] <= 40) {

                x = x - 12 * curPosition[4] / 1280;
                y = y - 12 * curPosition[5] / 720;
                w = w - 8 * curPosition[4] / 1280;
                h = h - 8 * curPosition[5] / 720;
            } else if (curPosition[0] > 40 && curPosition[0] <= 50) {

                x = x - 15 * curPosition[4] / 1280;
                y = y - 15 * curPosition[5] / 720;
                w = w - 10 * curPosition[4] / 1280;
                h = h - 10 * curPosition[5] / 720;
            } else if (curPosition[0] > 50 && curPosition[0] <= 60) {

                x = x - 18 * curPosition[4] / 1280;
                y = y - 18 * curPosition[5] / 720;
                w = w - 12 * curPosition[4] / 1280;
                h = h - 12 * curPosition[5] / 720;
            } else if (curPosition[0] > 60) {

                x = x - 20 * curPosition[4] / 1280;
                y = y - 20 * curPosition[5] / 720;
                w = w - 15 * curPosition[4] / 1280;
                h = h - 15 * curPosition[5] / 720;
            }

            setDevVideoWindow(x, y, w, h);
            // DtvControl mControl = new DtvControl("/sys/class/video/axis");
            // mControl.setValue("108 173 722 519");
        } else {

            setDevVideoWindow(curPosition[0], curPosition[1], curPosition[2], curPosition[3]);
            // DtvControl mControl = new DtvControl("/sys/class/video/axis");
            // mControl.setValue("0 0 1280 720");
        }
    }

    static void setDevVideoWindow(int x, int y, int w, int h) {
        int x_t = x;
        int y_t = y;
        int w_t = w;
        int h_t = h;

        String winStr = x_t + " " + y_t + " " + (w_t) + " " + (h_t);
        Log.d(TAG, "setDevVideoWindow() " + winStr);

        DtvControl mControl = new DtvControl("/sys/class/video/axis");
        mControl.setValueForce(winStr);
    }

    static int[] GetScalePosition(int x, int y, int w, int h) {
        int[] curPosition = {0, 0, 1280, 720};
        int x_t = x;
        int y_t = y;
        int w_t = w;
        int h_t = h;
        String outputmode = SystemProperties.get("ubootenv.var.outputmode");
        if (outputmode != null) {
            if (outputmode.contains("1080p")) {
                x_t = x * 1920 / 1280;
                y_t = y * 1080 / 720;
                w_t = w * 1920 / 1280;
                h_t = h * 1080 / 720;
            } else if (outputmode.contains("720p")) {

            } else if (outputmode.contains("1080i")) {
                x_t = x * 1920 / 1280;
                y_t = y * 1080 / 720;
                w_t = w * 1920 / 1280;
                h_t = h * 1080 / 720;
            } else if (outputmode.contains("576")) {
                x_t = x * 720 / 1280;
                y_t = y * 576 / 720;
                w_t = w * 720 / 1280;
                h_t = h * 576 / 720;
            } else if (outputmode.contains("480")) {
                x_t = x * 720 / 1280;
                y_t = y * 480 / 720;
                w_t = w * 720 / 1280;
                h_t = h * 480 / 720;
            }
        }

        curPosition[0] = x_t;
        curPosition[1] = y_t;
        curPosition[2] = w_t;
        curPosition[3] = h_t;

        Log.d(TAG, "GetScalePosition() "
                + (curPosition[0] + " " + curPosition[1] + " " + curPosition[2] + " " + curPosition[3]));
        return curPosition;
    }

    private void resetUbootData() {

        String sel_480ioutput_x = "ubootenv.var.480ioutputx";
        String sel_480ioutput_y = "ubootenv.var.480ioutputy";
        String sel_480ioutput_width = "ubootenv.var.480ioutputwidth";
        String sel_480ioutput_height = "ubootenv.var.480ioutputheight";
        String sel_480poutput_x = "ubootenv.var.480poutputx";
        String sel_480poutput_y = "ubootenv.var.480poutputy";
        String sel_480poutput_width = "ubootenv.var.480poutputwidth";
        String sel_480poutput_height = "ubootenv.var.480poutputheight";
        String sel_576ioutput_x = "ubootenv.var.576ioutputx";
        String sel_576ioutput_y = "ubootenv.var.576ioutputy";
        String sel_576ioutput_width = "ubootenv.var.576ioutputwidth";
        String sel_576ioutput_height = "ubootenv.var.576ioutputheight";
        String sel_576poutput_x = "ubootenv.var.576poutputx";
        String sel_576poutput_y = "ubootenv.var.576poutputy";
        String sel_576poutput_width = "ubootenv.var.576poutputwidth";
        String sel_576poutput_height = "ubootenv.var.576poutputheight";
        String sel_720poutput_x = "ubootenv.var.720poutputx";
        String sel_720poutput_y = "ubootenv.var.720poutputy";
        String sel_720poutput_width = "ubootenv.var.720poutputwidth";
        String sel_720poutput_height = "ubootenv.var.720poutputheight";
        String sel_1080ioutput_x = "ubootenv.var.1080ioutputx";
        String sel_1080ioutput_y = "ubootenv.var.1080ioutputy";
        String sel_1080ioutput_width = "ubootenv.var.1080ioutputwidth";
        String sel_1080ioutput_height = "ubootenv.var.1080ioutputheight";
        String sel_1080poutput_x = "ubootenv.var.1080poutputx";
        String sel_1080poutput_y = "ubootenv.var.1080poutputy";
        String sel_1080poutput_width = "ubootenv.var.1080poutputwidth";
        String sel_1080poutput_height = "ubootenv.var.1080poutputheight";

        String CVBS_MODE_PROP = "ubootenv.var.cvbsmode";
        String HDMI_MODE_PROP = "ubootenv.var.hdmimode";
        String COMMON_MODE_PROP = "ubootenv.var.outputmode";
        String STR_DIGIT_AUDIO_OUTPUT = "ubootenv.var.digitaudiooutput";
        // reset uboot resolution

        // String newMode = mOutputModeManager.getBestMatchResolution();
        //
        // if (newMode != null) {
        // SystemProperties.set(COMMON_MODE_PROP, newMode);
        // if (newMode.contains("cvbs")) {
        // SystemProperties.set(CVBS_MODE_PROP, newMode);
        // } else {
        // SystemProperties.set(HDMI_MODE_PROP, newMode);
        // }
        // }

        // 480i
        SystemProperties.set(sel_480ioutput_x, "0");
        SystemProperties.set(sel_480ioutput_y, "0");
        SystemProperties.set(sel_480ioutput_width, "720");
        SystemProperties.set(sel_480ioutput_height, "480");
        // 480p
        SystemProperties.set(sel_480poutput_x, "0");
        SystemProperties.set(sel_480poutput_y, "0");
        SystemProperties.set(sel_480poutput_width, "720");
        SystemProperties.set(sel_480poutput_height, "480");
        // 576i
        SystemProperties.set(sel_576ioutput_x, "0");
        SystemProperties.set(sel_576ioutput_y, "0");
        SystemProperties.set(sel_576ioutput_width, "720");
        SystemProperties.set(sel_576ioutput_height, "576");
        // 576p
        SystemProperties.set(sel_576poutput_x, "0");
        SystemProperties.set(sel_576poutput_y, "0");
        SystemProperties.set(sel_576poutput_width, "720");
        SystemProperties.set(sel_576poutput_height, "576");
        // 720p
        SystemProperties.set(sel_720poutput_x, "0");
        SystemProperties.set(sel_720poutput_y, "0");
        SystemProperties.set(sel_720poutput_width, "1280");
        SystemProperties.set(sel_720poutput_height, "720");
        // 1080i
        SystemProperties.set(sel_1080ioutput_x, "0");
        SystemProperties.set(sel_1080ioutput_y, "0");
        SystemProperties.set(sel_1080ioutput_width, "1920");
        SystemProperties.set(sel_1080ioutput_height, "1080");
        // 1080p
        SystemProperties.set(sel_1080poutput_x, "0");
        SystemProperties.set(sel_1080poutput_y, "0");
        SystemProperties.set(sel_1080poutput_width, "1920");
        SystemProperties.set(sel_1080poutput_height, "1080");

        SystemProperties.set(STR_DIGIT_AUDIO_OUTPUT, "PCM");
        // SystemProperties.set(STR_OUTPUT_VAR, "1080p");
    }

    class MWmessageHandler extends Handler {

        int search_sat_count = 1;

        public MWmessageHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            int event_type = (msg.what >> 16) & 0xFFFF;
            int sub_event_type = msg.what & 0xFFFF;

            switch (event_type) {
                case MW.EVENT_TYPE_SEARCH_STATUS: {
                    switch (sub_event_type) {
                        case MW.SEARCH_STATUS_SEARCH_START: {
                            // RelativeLayout loadingLayout =
                            // (RelativeLayout)findViewById(R.id.RelativeLayout_Loading);
                            // loadingLayout.setVisibility(View.INVISIBLE);

                            Log.d(TAG,
                                    " mwEventCallback : MW.SEARCH_STATUS_SEARCH_START : " + event_type + " " + sub_event_type);

                        }
                        break;

                        case MW.SEARCH_STATUS_UPDATE_CHANNEL_LIST: {
                            mw_data.search_status_update_channel_list paramsInfo = (mw_data.search_status_update_channel_list) msg.obj;

                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_UPDATE_CHANNEL_LIST : " + event_type + " "
                                    + sub_event_type);

                            if (paramsInfo != null) {
                                int i;

                            }
                        }
                        break;

                        case MW.SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PROGRESS: {
                            mw_data.search_status_dvbs_blind_scan_progress paramsInfo = (mw_data.search_status_dvbs_blind_scan_progress) msg.obj;

                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PROGRESS : " + event_type
                                    + " " + sub_event_type);

                        }
                        break;

                        case MW.SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PARAMS_INFO: {
                            mw_data.search_status_dvbs_blind_scan_params paramsInfo = (mw_data.search_status_dvbs_blind_scan_params) msg.obj;

                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_PARAMS_INFO : " + event_type
                                    + " " + sub_event_type);

                            if (paramsInfo != null) {
                                String s;

                                if (paramsInfo.sym > 0) {
                                    s = paramsInfo.sat_name + "    "
                                            + String.format("%05dMHz / %05dKbps / ", paramsInfo.frq, paramsInfo.sym);

                                    if (paramsInfo.pol == MW.LNB_POL_V)
                                        s += "V";
                                    else
                                        s += "H";

                                    // if(paramsInfo.locked)
                                    // s += " Locked";
                                } else {
                                    // s = paramsInfo.sat_name + " " +
                                    // String.format("%05dMHz ", paramsInfo.frq) +
                                    // getResources().getString(R.string.Search);
                                }

                                search_sat_count = paramsInfo.search_sat_count;
                            }
                        }
                        break;
                        case MW.SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_TUNNING_INFO: {
                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_UPDATE_DVB_S_BLIND_SCAN_TUNNING_INFO : "
                                    + event_type + " " + sub_event_type);
                            int onoff_22K = msg.arg1;// 22k status(on / off )
                            int pol = msg.arg2;// pol(V / H )
                            String s_22k, s_pol;

                        }
                        break;
                        case MW.SEARCH_STATUS_UPDATE_DVB_S_PARAMS_INFO: {
                            mw_data.search_status_dvbs_params paramsInfo = (mw_data.search_status_dvbs_params) msg.obj;

                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_UPDATE_DVB_S_PARAMS_INFO : " + event_type + " "
                                    + sub_event_type);

                            if (paramsInfo != null) {
                                String s = null;
                                if (paramsInfo.pol == MW.LNB_POL_V)
                                    s = "V";
                                else
                                    s = "H";

                            }
                        }
                        break;

                        case MW.SEARCH_STATUS_UPDATE_DVB_T_PARAMS_INFO: {
                            mw_data.search_status_dvbt_params paramsInfo = (mw_data.search_status_dvbt_params) msg.obj;

                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_UPDATE_DVB_T_PARAMS_INFO : " + event_type + " "
                                    + sub_event_type);

                        }
                        break;

                        case MW.SEARCH_STATUS_SAVE_DATA_START: {
                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_SAVE_DATA_START : " + event_type + " "
                                    + sub_event_type);

                        }
                        break;

                        case MW.SEARCH_STATUS_SAVE_DATA_END: {
                            Log.d(TAG,
                                    " mwEventCallback : MW.SEARCH_STATUS_SAVE_DATA_END : " + event_type + " " + sub_event_type);
                        }
                        break;

                        case MW.SEARCH_STATUS_SEARCH_END: {
                            mw_data.search_status_result paramsInfo = (mw_data.search_status_result) msg.obj;

                            Log.d(TAG, " mwEventCallback : MW.SEARCH_STATUS_SEARCH_END : " + event_type + " " + sub_event_type);
                            if (T2flat) {

                                playDVBT2();
                            } else {
                                playDVBS2();
                            }
                            // startTv();
                        }
                        break;

                        default:
                            break;
                    }
                }
                break;

                case MW.EVENT_TYPE_TUNER_STATUS: {
                    mw_data.tuner_signal_status paramsInfo = (mw_data.tuner_signal_status) msg.obj;
                    if (paramsInfo != null) {
                        Log.d(TAG,
                                " mwEventCallback : MW.EVENT_TYPE_TUNER_STATUS : " + paramsInfo.locked + " "
                                        + paramsInfo.error + " " + sub_event_type + " " + paramsInfo.strength + " "
                                        + paramsInfo.quality);
                    }
                }
                break;

                default:
                    break;
            }
        }

    }

    Handler mwMessageHandler;
    private String type2;
    private RadioButton checkdvb;
    private int netID;

    protected void enableMwMessageCallback(Handler h) {
        mwMessageHandler = h;
        if (h != null)
            MW.register_event_cb(this);
        // onMwEventCallback(mCheckPlayStatusCount, mCheckPlayStatusCount,
        // mCheckPlayStatusCount, mCheckPlayStatusCount, 0);
    }

    protected void onMwEventCallback(int event_type, int sub_event_type, int param1,
                                     int param2, Object obj_clazz) {
        Log.e(TAG, " mwEventCallback : " + event_type + " " + sub_event_type);

        if (mwMessageHandler != null) {
            Message messageSend = mwMessageHandler.obtainMessage();

            messageSend.what = event_type << 16 | sub_event_type;
            messageSend.arg1 = param1;
            messageSend.arg2 = param2;
            messageSend.obj = obj_clazz;

            messageSend.sendToTarget();
        }
    }

    public void finishMyself() {
        this.finish();
    }

    public void finishMyself(int delayMS) {
        if (delayMS > 0) {
            (new Handler()).postDelayed(new Runnable() {
                @Override
				public void run() {
                    finishMyself();
                }
            }, delayMS);
        } else {
            finishMyself();
        }
    }

    public static Boolean ledflat = false;
    public static Boolean ledstop = false;

    public void ledrun() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (ledflat) {
                    Util.setgree();
                    ledflat = false;
                } else {
                    Util.setred();
                    ledflat = true;
                }
                if (!ledstop)
                    ledrun();
            }
        }, 1000);
    }
}