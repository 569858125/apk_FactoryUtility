package com.datamax.factorytest;

//import static android.net.wifi.WifiConfiguration.INVALID_NETWORK_ID;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.IpConfiguration.IpAssignment;
import android.net.IpConfiguration.ProxySettings;
import android.net.LinkProperties;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.StaticIpConfiguration;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
//import android.net.wifi.WifiManager.ActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.security.KeyStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import com.datamax.factorytest.R;

@SuppressLint("NewApi")
public class WifiSettings extends NetWorkFragment {
    private final static String TAG = "SettingsEx_WifiSettings";

    private static final String KEYSTORE_SPACE = "keystore://";
    private final int INVALID_SECTION_ID = 0;

    private static final int INVALID_NETWORK_ID = -1;

    private final int SECTION_ID_WIFI_OFF = 0;
    private final int SECTION_ID_WIFI_ON = 1;
    private final int SECTION_ID_WIFI_ADD = 2;
    private final int SECTION_ID_WIFI_CONNECT = 3;
    private final int SECTION_ID_WIFI_CONNECTED = 4;
    private final int SECTION_ID_WIFI_ADVANCED = 5;
    private final int SECTION_ID_WIFI_FORGET = 6;
    private final int SECTION_ID_WIFI_MODIFY = 7;

    private Context mContext = null;
    private InputMethodManager mInputMethodManager;
    // private PppoeOperation mOperation;

    private int mKeyStoreNetworkId = INVALID_NETWORK_ID;
    private boolean isEthprefer = false;
    private String mWifiPassword = "";
    private boolean mbModifyToAdvancedFlag = false;
    private boolean mbOnOffCanSwith = true;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.network_wifi, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        isEthprefer = isEthHasFirstPriority();
        WifiManagerInit();

        SectionInit();
        WirelessSettingsInit();
        SectionWifiOnInit();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mWifiReceiver, mWifiFilter);
        if (mKeyStoreNetworkId != INVALID_NETWORK_ID && KeyStore.getInstance().state() == KeyStore.State.UNLOCKED) {
            mWifiManager.connect(mKeyStoreNetworkId, mWifiConnectListener);
            // mWifiManager.connectNetwork(mKeyStoreNetworkId);

        }
        mKeyStoreNetworkId = INVALID_NETWORK_ID;

        updateWifiAccessPoints();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mWifiReceiver);
        mWifiScanner.pause();
    }

    /**********************************************************************************
     * section control part
     ***********************************************************************************/
    private int mSectionCount = 2;
    private int mCurrentSectionId = 0;
    private int mSectionIdArray[] = {INVALID_SECTION_ID, R.id.network_wifi_on};
    private View mSectionViewArray[];

    private void SectionInit() {

        mSectionViewArray = new View[mSectionCount];
        for (int i = 0; i < mSectionCount; i++) {
            if (mSectionIdArray[i] == INVALID_SECTION_ID)
                mSectionViewArray[i] = null;
            else
                mSectionViewArray[i] = getView().findViewById(mSectionIdArray[i]);
        }
    }

    private void SectionSwitch(int id) {
        mCurrentSectionId = id;

        for (int i = 0; i < mSectionCount; i++) {
            if (id == i) {
                if (mSectionViewArray[i] != null)
                    mSectionViewArray[i].setVisibility(View.VISIBLE);

                // WifiAddNetworkShow(i == SECTION_ID_WIFI_ON ? true : false);
                // //only wifi on section have the add network
                if (/* i== SECTION_ID_WIFI_CONNECT || */i == SECTION_ID_WIFI_ADD)
                    mIsEdit = true;
                else
                    mIsEdit = false;

                if (i == SECTION_ID_WIFI_ON && isEthConnected()) {
                    if (isEthConnected()) {

                        mSectionViewArray[i].setVisibility(View.GONE);
                        WifiAddNetworkShow(false);
                        // WifiStatusInfoUpdate(R.string.wifi_disable_ethernet_enable);
                    } else {

                        // mWifiPasswordEditText.requestFocus();

                        WifiStatusInfoUpdate(0);
                    }
                }
            } else {
                if (mSectionViewArray[i] != null)
                    mSectionViewArray[i].setVisibility(View.GONE);
            }
            // forget connect get focus when enter
            if (id == SECTION_ID_WIFI_FORGET)
                mWifiForgetConnectTextView.requestFocus();
        }
    }

    private void WifiStatusInfoUpdate(int resId) {
    }

    private void WifiAddNetworkShow(boolean showFlag) {
    }

    private void WifiSetOnOffStatus(boolean on) {
    }

    /**********************************************************************************
     * right part wireless section
     ***********************************************************************************/
    // LinearLayout mOnOffTextLinearLayout;
    // TextView mOnTextView, mOffTextView, mWifiStatusInfoTextView;
    // ImageView mSearchImageView;
    // TextView mAddNetworkTextView;
    private void WirelessSettingsInit() {
    }

    /**********************************************************************************
     * right part wifi on section
     ***********************************************************************************/
    private ListView mSectionWifiOnList;
    private static SectionWifiOnList mSectionWifiOnListAdapter = null;
    private int mFirstVisibleItem = 0, mVisibleItemCount = 8;

    private void SectionWifiOnInit() {
        // mOnOffTextLinearLayout.setNextFocusDownId(R.id.wifi_ssid_list);
        mSectionWifiOnList = (ListView) getView().findViewById(R.id.wifi_ssid_list);
        mSectionWifiOnList.setSelector(R.drawable.general_blank_bg);
        mSectionWifiOnList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
			public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSectionWifiOnList.setSelector(R.drawable.general_focus_bg);
                } else {
                    mSectionWifiOnList.setSelector(R.drawable.general_blank_bg);
                }
            }
        });
        mSectionWifiOnList.setNextFocusDownId(R.id.wifi_ssid_list);

        mSectionWifiOnListAdapter = new SectionWifiOnList(getActivity());
        mSectionWifiOnList.setAdapter(mSectionWifiOnListAdapter);
        mSectionWifiOnList.setOnItemClickListener(mSectionWifiOnListItemClickListener);
        // mSectionWifiOnList.setDivider(mContext.getResources().getDrawable(R.drawable.system_info_horizontal_line));
        mSectionWifiOnList.setOnScrollListener(new listOnScroll());
        mSectionWifiOnList.setOnKeyListener(new listOnKeyListener());
        System.out.println("SectionWifiOnInit");
        // mInputMethodManager.hideSoftInputFromWindow(getView().getApplicationWindowToken(),
        // 0);

    }

    class listOnScroll implements OnScrollListener {

        @Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // Log.d(TAG, "=====onScroll() firstVisibleItem "+ firstVisibleItem
            // +
            // " visibleItemCount "+visibleItemCount+" totalItemCount "+totalItemCount);
            mFirstVisibleItem = firstVisibleItem;
            mVisibleItemCount = visibleItemCount;
            mSectionWifiOnListAdapter.setSelectItem(view.getSelectedItemPosition());
            // Log.d(TAG, "=====onScroll() "+ view.getSelectedItemPosition() +
            // " count "+view.getCount());
        }

        @Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
            // TODO Auto-generated method stub
            // Log.d(TAG, "=====onScrollStateChanged() "+ scrollState);
        }

    }

    class listOnKeyListener implements OnKeyListener {
        @Override
		public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
            // TODO Auto-generated method stub
            // Log.d(TAG,
            // "=====listOnKeyListener onkey() "+arg2.getAction()+" arg1 "+arg1);
            if (arg2.getAction() == KeyEvent.ACTION_DOWN) {
                switch (arg1) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        break;

                    case KeyEvent.KEYCODE_DPAD_UP: {
                        int selectItem = mSectionWifiOnListAdapter.getSelectItem();
                        // Log.d(TAG,
                        // "=====listOnKeyListener KEYCODE_DPAD_UP "+selectItem+" mFirstVisibleItem "+mFirstVisibleItem+" mVisibleItemCount "+mVisibleItemCount);
                        if (selectItem == mFirstVisibleItem && selectItem > 0) {
                            mSectionWifiOnList.setSelection(selectItem - 1);
                            mSectionWifiOnListAdapter.setSelectItem(selectItem - 1);
                            mSectionWifiOnListAdapter.notifyDataSetChanged();
                            return true;

                        }
                        break;
                    }

                    case KeyEvent.KEYCODE_DPAD_DOWN: {
                        int selectItem = mSectionWifiOnListAdapter.getSelectItem();
                        // Log.d(TAG,
                        // "=====listOnKeyListener KEYCODE_DPAD_DOWN "+selectItem+" mFirstVisibleItem "+mFirstVisibleItem+" mVisibleItemCount "+mVisibleItemCount);
                        if ((selectItem + 1 == mFirstVisibleItem + mVisibleItemCount)
                                && selectItem < (mWifiAccessPointArray.size() - 1)) {
                            mSectionWifiOnList.setSelection(selectItem + 1);
                            mSectionWifiOnListAdapter.setSelectItem(selectItem + 1);
                            mSectionWifiOnListAdapter.notifyDataSetChanged();
                            return true;
                        }
                        break;
                    }
                }
            } else if (arg2.getAction() == KeyEvent.ACTION_UP) {
                switch (arg1) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                        break;

                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        break;
                }
            }

            return false;
        }
    }

    private AdapterView.OnItemClickListener mSectionWifiOnListItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
		public void onItemClick(AdapterView parent, View v, int position, long id) {
            mSelectedConnectId = position;
            mSelectedAccessPoint = mWifiAccessPointArray.get(mSelectedConnectId);
            if (mSelectedAccessPoint.getState() == DetailedState.CONNECTED) {

                SectionWifiConnectedInit();
                SectionSwitch(SECTION_ID_WIFI_CONNECTED);
            } else if (mSelectedAccessPoint.networkId != INVALID_NETWORK_ID && !requireKeyStore(mSelectedAccessPoint.getConfig())
                    && getSignalStrengthInt() != -1) {

                SectionWifiForgetInit();
                SectionSwitch(SECTION_ID_WIFI_FORGET);
            } else {

                mWifiPassword = "";
                mbDisplayPasswordStatus = false;
                SectionWifiConnectInit();
                SectionSwitch(SECTION_ID_WIFI_CONNECT);
                if (getSecurityInt() == WifiAccessPoint.SECURITY_NONE || getSignalStrengthInt() == -1)
                    mWifiConnectConnectTextView.requestFocus();
                else {
                    mWifiPasswordEditText.requestFocus();
                    mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

                }
            }
        }
    };

    private class SectionWifiOnList extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context cont;
        private int selectItem;

        class ViewHolder {
            // ImageView connect;
            TextView name;
            TextView status;
            ImageView lock;
            ImageView signal;
        }

        public SectionWifiOnList(Context context) {
            super();
            cont = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
		public int getCount() {
            System.out.println("s");
            return mWifiAccessPointArray != null ? mWifiAccessPointArray.size() : 0;
        }

        @Override
		public Object getItem(int position) {
            return position;
        }

        @Override
		public long getItemId(int position) {
            return position;
        }

        public void setSelectItem(int position) {
            selectItem = position;
        }

        public int getSelectItem() {
            return selectItem;
        }

        @Override
		public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.network_wifi_on_list_item, null);

                holder = new ViewHolder();

                // holder.connect = (ImageView)
                // convertView.findViewById(R.id.wifi_connect);
                holder.name = (TextView) convertView.findViewById(R.id.wifi_name);
                holder.status = (TextView) convertView.findViewById(R.id.wifi_status);
                holder.lock = (ImageView) convertView.findViewById(R.id.wifi_lock);
                holder.signal = (ImageView) convertView.findViewById(R.id.wifi_signal);
                holder.name.setTextSize(13);
                holder.status.setTextSize(13);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            System.out.println("ddddddddddddddddd");
            // holder.connect.setImageResource(R.drawable.connected_white_hook);
            // holder.connect.setVisibility(mWifiAccessPointArray.get(position).getState()
            // == DetailedState.CONNECTED ? View.VISIBLE : View.INVISIBLE);
            holder.name.setText(mWifiAccessPointArray.get(position).getSSID());
            holder.name.setTextColor(Color.WHITE);
            // String statusStr = getWifiStatusStr(mWifiAccessPointArray
            // .get(position));
            int nSigLevel = mWifiAccessPointArray.get(position).getLevel();
            String str = "null";
            if (nSigLevel == -1) {

            } else {
                str = nSigLevel + "dBm";
            }
            holder.status.setText(str);
            // holder.status.setVisibility(statusStr != null ? View.VISIBLE
            // : View.GONE);
            // holder.lock.setImageResource(R.drawable.lock);
            holder.lock
                    .setVisibility(mWifiAccessPointArray.get(position).getSecurity() != WifiAccessPoint.SECURITY_NONE ? View.VISIBLE
                            : View.INVISIBLE);
            // holder.signal
            // .setImageResource(getSignalStrengthImage(mWifiAccessPointArray
            // .get(position)));
            // holder.signal.setVisibility(mWifiAccessPointArray.get(position)
            // .getLevel() == -1 ? View.INVISIBLE : View.VISIBLE);

            // convertView.setBackgroundColor(Color.TRANSPARENT);

            return convertView;
        }
    }

    /**********************************************************************************
     * right part wifi add section
     ***********************************************************************************/
    LinearLayout mAddPasswordLinearlayout, mAddDisplayPasswordLinearlayout;
    TextView mAddCancelTextView, mAddSaveTextView;
    EditText mAddSsidEditText, mAddPasswordEditText;
    CheckBox mAddDisplayPasswordCheckBox;
    // Spinner mAddSecuritySpinner;
    SpinnerDialog mSpinnerDialog = null;
    TextView mAddSecurityTextView;
    int mAddSecuritySelectedId = 0;

    private void SectionWifiAddInit() {
    }

    private String getAddSecurityString() {
        String[] secStrArray = mContext.getResources().getStringArray(R.array.wifi_add_security);
        if (secStrArray != null) {
            return secStrArray[mAddSecuritySelectedId];
        }

        return "";
    }

    private void showSecurityField(int security) {
        if (security == WifiAccessPoint.SECURITY_NONE) {
            mAddPasswordLinearlayout.setVisibility(View.INVISIBLE);
            mAddDisplayPasswordLinearlayout.setVisibility(View.INVISIBLE);
        } else {
            mAddPasswordLinearlayout.setVisibility(View.VISIBLE);
            mAddDisplayPasswordLinearlayout.setVisibility(View.VISIBLE);
        }
    }

    /**********************************************************************************
     * right part wifi connect section
     ***********************************************************************************/
    LinearLayout mPwdEditLinearLayout, mDisplayPwdLinearLayout;
    TextView mWifiConnectCannelTextView, mWifiConnectConnectTextView;
    TextView mSignalNameTextView;
    TextView mSignalStrengthTextView, mSecurityTextView;
    CheckBox mDisplayPasswordCheckBox, mAdvancedSettingsCheckBox;
    EditText mWifiPasswordEditText;
    private boolean mbDisplayPasswordStatus = false;

    private void SectionWifiConnectInit() {
    }

    private class WifiPasswordEditTextWatcher implements TextWatcher {
        public WifiPasswordEditTextWatcher() {

        }

        @Override
		public void afterTextChanged(Editable paramEditable) {
            if (mWifiPasswordEditText.length() != 0) {
                mWifiPassword = mWifiPasswordEditText.getText().toString();
            } else {
                mWifiPassword = "";
            }
        }

        @Override
		public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

        }

        @Override
		public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

        }
    }

    private void SectionWifiConnectShowField() {
    }

    /**********************************************************************************
     * right part connected section
     ***********************************************************************************/
    TextView mConnectedCannelTextView, mConnectUnsaveTextView, mConnectModifyTextView;
    TextView mConnectedNameTextView, mConnectedStatusTextView;
    TextView mConnectedSignalStrengthTextView, mConnectedSecurityTextView;
    TextView mConnectedSpeedTextView, mConnectedIpAddressTextView;

    public String getWifiIpAddress() {

        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff));

    }

    private void SectionWifiConnectedInit() {
    }

    /**********************************************************************************
     * right part wifi advanced section
     ***********************************************************************************/
    TextView mAdvancedDynamicTextView, mAdvancedStaticTextView;
    LinearLayout mAdvancedIpSettingsLinearlayout;
    TextView mAdvancedCancelTextView, mAdvancedSaveTextView;
    LinearLayout mAdvancedIpAddressLinearlayout, mAdvancedSubMaskLinearlayout, mAdvancedGetwayLinearlayout;
    LinearLayout mAdvancedMainDnsLinearlayout, mAdvancedSubDnsLinearlayout;
    EditText mAdvancedIpAddressEditText, mAdvancedSubMaskEditText, mAdvancedGatewayEditText;
    EditText mAdvancedMainDnsEditText, mAdvancedSubDnsEditText;
    private boolean mbDynamicStatus = true;

    @SuppressLint("NewApi")
    private void SectionWifiAdvancedInit() {
    }

    private void IPSettingsFieldShow(boolean show) {
    }

    private void SetDynamicAndStaticBg() {
    }

    public static Integer converMaskToPrefixLength(String strIP) {
        int position1 = strIP.indexOf(".");
        int position2 = strIP.indexOf(".", position1 + 1);
        int position3 = strIP.indexOf(".", position2 + 1);
        try {
            if ((position1 != -1) && (position2 != -1) && (position3 != -1) && !(strIP.length() < (position3 + 1))) {
                String ip1 = strIP.substring(0, position1);
                String ip2 = strIP.substring(position1 + 1, position2);
                String ip3 = strIP.substring(position2 + 1, position3);
                String ip4 = strIP.substring(position3 + 1);
                BigInteger b1 = new BigInteger(ip1);
                BigInteger b2 = new BigInteger(ip2);
                BigInteger b3 = new BigInteger(ip3);
                BigInteger b4 = new BigInteger(ip4);

                String newIp = b1.toString(2) + b2.toString(2) + b3.toString(2) + b4.toString(2);
                String findStr = "1";
                int PrefixLength = (newIp.length() - newIp.replaceAll(findStr, "").length()) / findStr.length();

                return PrefixLength;
            } else {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public static String converPrefixLengthToMask(int length) {
        int mask = -1 << (32 - length);
        int partsNum = 4;
        int bitsOfPart = 8;
        int maskParts[] = new int[partsNum];
        int selector = 0x000000ff;

        for (int i = 0; i < maskParts.length; i++) {
            int pos = maskParts.length - 1 - i;
            maskParts[pos] = (mask >> (i * bitsOfPart)) & selector;
        }

        String result = "";
        result = result + maskParts[0];
        for (int i = 1; i < maskParts.length; i++) {
            result = result + "." + maskParts[i];
        }

        return result;
    }

    private boolean ipSettingsSave() {
        mLinkProperties.clear();
        Log.d(TAG, "ipSettingsSave " + mbDynamicStatus);
        mIpAssignment = !mbDynamicStatus ? IpAssignment.STATIC : IpAssignment.DHCP;
        if (mIpAssignment == IpAssignment.STATIC) {
        }

        // mProxySettings = ProxySettings.NONE;
        return true;
    }

    /**********************************************************************************
     * right part wifi forget section
     ***********************************************************************************/
    TextView mWifiForgetCannelTextView, mWifiForgetUnsaveTextView, mWifiForgetConnectTextView;
    TextView mForgetSignalNameTextView, mForgetSignalStrengthTextView, mForgetSecurityTextView;

    private void SectionWifiForgetInit() {

        if (mForgetSignalStrengthTextView != null) // already be initAudio
        {
        }

        mForgetSignalNameTextView.setText(getNameStr());

        mForgetSecurityTextView.setText(getSecurityStr());

        // forget cancel select

        mWifiForgetCannelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                SectionWifiOnInit();
                SectionSwitch(SECTION_ID_WIFI_ON);
                mSectionWifiOnList.requestFocus();
                mSectionWifiOnList.setSelection(mSelectedConnectId);
            }
        });

        // forget unsave select

        mWifiForgetUnsaveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                forget();

                SectionWifiOnInit();
                SectionSwitch(SECTION_ID_WIFI_ON);
                mSectionWifiOnList.requestFocus();
                mSectionWifiOnList.setSelection(mSelectedConnectId);
            }
        });

        mWifiForgetConnectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                if (IsPersonSecurityFailure(mSelectedAccessPoint)) {
                    mWifiPassword = "";
                    mbModifyDisplayPasswordStatus = false;
                    SectionWifiModifyInit();
                    SectionSwitch(SECTION_ID_WIFI_MODIFY);
                    if (getSecurityInt() == WifiAccessPoint.SECURITY_NONE)
                        mWifiModifySaveTextView.requestFocus();
                    else
                        mModifyWifiPasswordEditText.requestFocus();
                } else {
                    submit();

                    SectionWifiOnInit();
                    SectionSwitch(SECTION_ID_WIFI_ON);
                    mSectionWifiOnList.requestFocus();
                    mSectionWifiOnList.setSelection(0);
                }
            }
        });

        if (IsPersonSecurityFailure(mSelectedAccessPoint)) // password error, we
        // must to modidy
        // section
        {
            // mWifiForgetConnectTextView.setText(R.string.wifi_modify);
        } else {
            // mWifiForgetConnectTextView.setText(R.string.wifi_connect);
        }
    }

    /**********************************************************************************
     * right part wifi modify section
     ***********************************************************************************/
    LinearLayout mModifyPwdEditLinearLayout, mModifyDisplayPwdLinearLayout;
    TextView mWifiModifyCannelTextView, mWifiModifySaveTextView;
    TextView mModifySignalStrengthTextView, mModifySecurityTextView;
    CheckBox mModifyDisplayPasswordCheckBox, mModifyAdvancedSettingsCheckBox;
    EditText mModifyWifiPasswordEditText;
    private boolean mbModifyDisplayPasswordStatus = false;

    private void SectionWifiModifyInit() {
        if (mModifySignalStrengthTextView != null) // already be initAudio
        {
            // mModifySignalStrengthTextView.setText(getSignalStrengthStr());
            mModifySecurityTextView.setText(getSecurityStr());
            SectionWifiModifyShowField();
            mModifyWifiPasswordEditText.setText(mWifiPassword);
            mModifyWifiPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT
                    | (mbModifyDisplayPasswordStatus ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : InputType.TYPE_TEXT_VARIATION_PASSWORD));
            mModifyDisplayPasswordCheckBox.setChecked(mbModifyDisplayPasswordStatus);
            mModifyAdvancedSettingsCheckBox.setChecked(false);
            return;
        }

        mModifySecurityTextView.setText(getSecurityStr());

        mModifyWifiPasswordEditText.addTextChangedListener(new ModifyWifiPasswordEditTextWatcher());
        /*
         * mModifyWifiPasswordEditText.setOnFocusChangeListener (new
         * View.OnFocusChangeListener() { public void onFocusChange(View v,
         * boolean hasFocus) { final boolean isFocus = hasFocus; (new
         * Handler()).postDelayed(new Runnable() { public void run() {
         * if(isFocus) { mInputMethodManager.toggleSoftInput(0,
         * InputMethodManager.HIDE_NOT_ALWAYS); } else {
         * mInputMethodManager.hideSoftInputFromWindow
         * (mWifiPasswordEditText.getWindowToken(), 0); } } }, 100); } });
         */

        mModifyDisplayPasswordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                mbModifyDisplayPasswordStatus = ((CheckBox) v).isChecked();
                mModifyWifiPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT
                        | (mbModifyDisplayPasswordStatus ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        : InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        });

        mModifyAdvancedSettingsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                mbModifyToAdvancedFlag = true;
                SectionWifiAdvancedInit();
                SectionSwitch(SECTION_ID_WIFI_ADVANCED);
            }
        });

        // cancel select

        mWifiModifyCannelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                // cancel will clear status
                mWifiPassword = "";
                mModifyWifiPasswordEditText.setText(mWifiPassword);
                mbModifyDisplayPasswordStatus = false;
                mModifyDisplayPasswordCheckBox.setChecked(mbModifyDisplayPasswordStatus);
                mModifyAdvancedSettingsCheckBox.setChecked(false);

                SectionWifiOnInit();
                SectionSwitch(SECTION_ID_WIFI_ON);
                mSectionWifiOnList.requestFocus();
                mSectionWifiOnList.setSelection(mSelectedConnectId);
            }
        });

        // save select

        mWifiModifySaveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
                if (getSignalStrengthInt() != -1 && getSecurityInt() == WifiAccessPoint.SECURITY_WEP
                        && mWifiPassword.length() < 5) {
                    // Toast.makeText(mContext,
                    // R.string.wifi_mininum_password_length_wep,
                    // Toast.LENGTH_SHORT).show();

                } else if (getSignalStrengthInt() != -1
                        && (getSecurityInt() == WifiAccessPoint.SECURITY_PSK || getSecurityInt() == WifiAccessPoint.SECURITY_EAP)
                        && mWifiPassword.length() < 8) {
                    // Toast.makeText(mContext,
                    // R.string.wifi_mininum_password_length,
                    // Toast.LENGTH_SHORT).show();

                } else {
                    mIsEdit = true; // set edit to reconnect network
                    submit();

                    // clear status
                    mWifiPassword = "";
                    mModifyWifiPasswordEditText.setText(mWifiPassword);
                    mbModifyDisplayPasswordStatus = false;
                    mModifyDisplayPasswordCheckBox.setChecked(mbModifyDisplayPasswordStatus);
                    mModifyAdvancedSettingsCheckBox.setChecked(false);

                    SectionWifiOnInit();
                    SectionSwitch(SECTION_ID_WIFI_ON);
                    mSectionWifiOnList.requestFocus();
                    mSectionWifiOnList.setSelection(0);
                }
            }
        });

        SectionWifiModifyShowField();
    }

    private class ModifyWifiPasswordEditTextWatcher implements TextWatcher {
        public ModifyWifiPasswordEditTextWatcher() {

        }

        @Override
		public void afterTextChanged(Editable paramEditable) {
            if (mModifyWifiPasswordEditText.length() != 0) {
                mWifiPassword = mModifyWifiPasswordEditText.getText().toString();
            } else {
                mWifiPassword = "";
            }
        }

        @Override
		public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

        }

        @Override
		public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {

        }
    }

    private void SectionWifiModifyShowField() {
    }

    /**********************************************************************************
     * function part
     ***********************************************************************************/
    private static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private IntentFilter mWifiFilter;
    private BroadcastReceiver mWifiReceiver;
    private WifiScanner mWifiScanner;
    private int wifinum = 0;
    private WifiManager mWifiManager;
    private WifiManager.ActionListener mWifiConnectListener;
    private WifiManager.ActionListener mWifiSaveListener;
    private WifiManager.ActionListener mWifiForgetListener;
    private boolean mWifiP2pSupported;

    private DetailedState mWifiLastState;
    private WifiInfo mWifiLastInfo;
    private AtomicBoolean mWifiConnected = new AtomicBoolean(false);

    private List<WifiAccessPoint> mWifiAccessPointArray = null;
    private WifiAccessPoint mSelectedAccessPoint = null;
    private int mSelectedConnectId = 0;
    private boolean mIsEdit = false;
    private int mAccessPointSecurity = 0;

    // Combo scans can take 5-6s to complete - set to 10s.
    private static final int WIFI_RESCAN_INTERVAL_MS = 10 * 1000;
    private IpAssignment mIpAssignment = IpAssignment.UNASSIGNED;
    private ProxySettings mProxySettings = ProxySettings.UNASSIGNED;

    //    private LinkProperties mLinkProperties = new LinkProperties();
    private LinkProperties mLinkProperties = new LinkProperties();

    private StaticIpConfiguration staticConfig;

    private void WifiManagerInit() {
        mWifiFilter = new IntentFilter();
        mWifiFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mWifiFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mWifiFilter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION);
        mWifiFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        mWifiFilter.addAction(WifiManager.CONFIGURED_NETWORKS_CHANGED_ACTION);
        mWifiFilter.addAction(WifiManager.LINK_CONFIGURATION_CHANGED_ACTION);
        mWifiFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mWifiFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        mWifiFilter.addAction(CONNECTIVITY_CHANGE_ACTION);

        mWifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleWifiEvent(context, intent);

            }
        };
        mWifiScanner = new WifiScanner();

        mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // mWifiManager.asyncConnect(getActivity(), new WifiServiceHandler());
        mWifiConnectListener = new WifiManager.ActionListener() {

            @Override
			public void onSuccess() {

            }

            @Override
			public void onFailure(int reason) {
                // Toast.makeText(mContext,
                // R.string.wifi_failed_connect_message,
                // Toast.LENGTH_SHORT).show();
                //
                // Constant.makeText(mContext,
                // R.string.wifi_failed_connect_message,
                // Toast.LENGTH_SHORT);
            }
        };
        mWifiSaveListener = new WifiManager.ActionListener() {

            @Override
			public void onSuccess() {

            }

            @Override
			public void onFailure(int reason) {
                // Toast.makeText(mContext, R.string.wifi_failed_save_message,
                // Toast.LENGTH_SHORT).show();
                // Constant.makeText(mContext,
                // R.string.wifi_failed_save_message,
                // Toast.LENGTH_SHORT);
            }
        };

        mWifiForgetListener = new WifiManager.ActionListener() {

            @Override
			public void onSuccess() {

            }

            @Override
			public void onFailure(int reason) {
                // Toast.makeText(mContext, R.string.wifi_failed_forget_message,
                // Toast.LENGTH_SHORT).show();
                // Constant.makeText(mContext,
                // R.string.wifi_failed_forget_message, Toast.LENGTH_SHORT);
            }
        };
    }

    private void handleWifiEvent(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "handleWifiEvent action " + action);
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)) {
            updateWifiState(intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN));
        } else if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)
                || WifiManager.CONFIGURED_NETWORKS_CHANGED_ACTION.equals(action)
                || WifiManager.LINK_CONFIGURATION_CHANGED_ACTION.equals(action)) {
            updateWifiAccessPoints();
        } else if (WifiManager.SUPPLICANT_STATE_CHANGED_ACTION.equals(action)) {
            // Ignore supplicant state changes when network is connected
            // TODO: we should deprecate SUPPLICANT_STATE_CHANGED_ACTION and
            // introduce a broadcast that combines the supplicant and network
            // network state change events so the apps dont have to worry about
            // ignoring supplicant state change when network is connected
            // to get more fine grained information.
            SupplicantState state = (SupplicantState) intent.getParcelableExtra(WifiManager.EXTRA_NEW_STATE);
            // if ((!mWifiConnected.get() &&
            // SupplicantState.isHandshakeState(state))
            // ||(intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, -1) ==
            // WifiManager.ERROR_AUTHENTICATING)
            // ||(SupplicantState.DISCONNECTED.equals(state)))
            if (!mWifiConnected.get()) {
                updateWifiConnectionState(WifiInfo.getDetailedStateOf(state));
            }
        } else if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
            NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            mWifiConnected.set(info.isConnected());
            if (!(isEthprefer && processWifiEthBothOn()))
                updateWifiAccessPoints();

            updateWifiConnectionState(info.getDetailedState());
            /*
             * if (mAutoFinishOnConnection && info.isConnected()) { Activity
             * activity = getActivity(); if (activity != null) {
             * activity.setResult(Activity.RESULT_OK); activity.finish(); }
             * return; }
             */
        } else if (WifiManager.RSSI_CHANGED_ACTION.equals(action)) {
            updateWifiConnectionState(null);
        } else if (CONNECTIVITY_CHANGE_ACTION.equals(action)) {
            NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            mWifiConnected.set(info.isConnected());
            if (!(isEthprefer && processWifiEthBothOn()))
                updateWifiAccessPoints();

            updateWifiConnectionState(info.getDetailedState());
        }
    }

    private boolean processWifiEthBothOn() {
        int wifiState = mWifiManager.getWifiState();
        if (wifiState != WifiManager.WIFI_STATE_ENABLED)
            return false;

        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info.isConnected()) {
            // SectionSwitch(SECTION_ID_WIFI_OFF);
            // WifiStatusInfoUpdate(R.string.wifi_disable_ethernet_enable);
            Log.d(TAG, "processWifiEthBothOn() true");
            return true;
        }

        return false;
    }

    private boolean isWifiConnected() {
        if (mWifiManager.isWifiEnabled()) {
            ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info.isConnected()) {
                Log.d(TAG, "isWifiConnected() true");
                return true;
            }
        }

        return false;
    }

    private boolean isEthConnected() {
        int wifiState = mWifiManager.getWifiState();
        if (wifiState != WifiManager.WIFI_STATE_ENABLED)
            return false;

        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info.isConnected()) {
            Log.d(TAG, "isEthConnected() true");
            return true;
        }

        return false;
    }

    @SuppressLint("NewApi")
    private boolean isEthHasFirstPriority() {
        ContentResolver cr = mContext.getContentResolver();
        // int networkPrefSetting = Settings.Secure.getInt(cr,
        // Settings.Secure.NETWORK_PREFERENCE, -1);
        int networkPrefSetting = Settings.Global.getInt(cr, Settings.Global.NETWORK_PREFERENCE, -1);
        if (networkPrefSetting == ConnectivityManager.TYPE_ETHERNET)
            return true;
        else
            return false;
    }

    private void updateWifiAccessPoints() {
        final int wifiState = mWifiManager.getWifiState();
        Log.d(TAG, "updateWifiAccessPoints wifiState " + wifiState);
        switch (wifiState) {
            case WifiManager.WIFI_STATE_ENABLED:
                // AccessPoints are automatically sorted with TreeSet.
                List<WifiAccessPoint> tempiAccessPointArray = constructWifiAccessPoints();
                if (tempiAccessPointArray.size() == 0) {
                    mWifiAccessPointArray = tempiAccessPointArray;
                    SectionSwitch(SECTION_ID_WIFI_OFF);
                    // WifiStatusInfoUpdate(R.string.wifi_empty_list_wifi_on);
                } else {
                    if (mCurrentSectionId <= SECTION_ID_WIFI_ON) {
                        WifiStatusInfoUpdate(0);
                        if (mCurrentSectionId == SECTION_ID_WIFI_OFF)
                            SectionSwitch(SECTION_ID_WIFI_ON);

                        if (mCurrentSectionId == SECTION_ID_WIFI_ON) {
                            mWifiAccessPointArray = tempiAccessPointArray;
                            Log.d(TAG, "=====updateWifiAccessPoints  updatelist " + mWifiAccessPointArray.size()
                                    + " mCurrentSectionId " + mCurrentSectionId);
                            mSectionWifiOnListAdapter.notifyDataSetChanged();
                        }
                    }
                }
                mbOnOffCanSwith = true;
                break;

            case WifiManager.WIFI_STATE_ENABLING:
                mbOnOffCanSwith = false;
                break;

            case WifiManager.WIFI_STATE_DISABLING:
                // WifiStatusInfoUpdate(R.string.wifi_stopping);
                mbOnOffCanSwith = false;
                break;

            case WifiManager.WIFI_STATE_DISABLED:
                // WifiStatusInfoUpdate(R.string.wifi_empty_list_wifi_off);
                mbOnOffCanSwith = true;
                break;
        }
    }

    // A restricted multimap for use in constructAccessPoints
    private class Multimap<K, V> {
        private HashMap<K, List<V>> store = new HashMap<K, List<V>>();

        /**
         * retrieve a non-null list of values with key K
         */
        List<V> getAll(K key) {
            List<V> values = store.get(key);
            return values != null ? values : Collections.<V>emptyList();
        }

        void put(K key, V val) {
            List<V> curVals = store.get(key);
            if (curVals == null) {
                curVals = new ArrayList<V>(3);
                store.put(key, curVals);
            }
            curVals.add(val);
        }
    }

    private List<WifiAccessPoint> constructWifiAccessPoints() {
        ArrayList<WifiAccessPoint> accessPoints = new ArrayList<WifiAccessPoint>();
        /**
         * Lookup table to more quickly update AccessPoints by only considering
         * objects with the correct SSID. Maps SSID -> List of AccessPoints with
         * the given SSID.
         */
        Multimap<String, WifiAccessPoint> apMap = new Multimap<String, WifiAccessPoint>();

        final List<WifiConfiguration> configs = mWifiManager.getConfiguredNetworks();
        if (configs != null) {
            for (WifiConfiguration config : configs) {
                WifiAccessPoint accessPoint = new WifiAccessPoint(mContext, config);
                accessPoint.update(mWifiLastInfo, mWifiLastState);
                accessPoints.add(accessPoint);
                apMap.put(accessPoint.ssid, accessPoint);
            }
        }

        final List<ScanResult> results = mWifiManager.getScanResults();
        if (results != null) {
            for (ScanResult result : results) {
                // Ignore hidden and ad-hoc networks.
                if (result.SSID == null || result.SSID.length() == 0 || result.capabilities.contains("[IBSS]")) {
                    continue;
                }

                boolean found = false;
                for (WifiAccessPoint accessPoint : apMap.getAll(result.SSID)) {
                    if (accessPoint.update(result))
                        found = true;
                }

                if (!found) {
                    WifiAccessPoint accessPoint = new WifiAccessPoint(mContext, result);
                    accessPoints.add(accessPoint);
                    apMap.put(accessPoint.ssid, accessPoint);
                }
            }
        }

        // Pre-sort accessPoints to speed preference insertion
        Collections.sort(accessPoints);
        return accessPoints;
    }

    private void updateWifiConnectionState(DetailedState state) {
        Log.d(TAG, "updateWifiConnectionState state " + state);

        // sticky broadcasts can call this when wifi is disabled
        if (!mWifiManager.isWifiEnabled()) {
            mWifiScanner.pause();
            return;
        }

        if (state == DetailedState.OBTAINING_IPADDR) {
            mWifiScanner.pause();
        } else {
            mWifiScanner.resume();
        }

        mWifiLastInfo = mWifiManager.getConnectionInfo();
        if (state != null) {
            mWifiLastState = state;
            if (mWifiAccessPointArray != null) {
                for (int i = 0; i < mWifiAccessPointArray.size(); i++) {
                    final WifiAccessPoint accessPoint = mWifiAccessPointArray.get(i);
                    if (accessPoint.networkId == mWifiLastInfo.getNetworkId()) {
                        if (accessPoint.update(mWifiLastInfo, mWifiLastState)) {
                            Log.d(TAG,
                                    "===== networkId " + accessPoint.networkId + " mWifiLastInfo.getNetworkId() "
                                            + mWifiLastInfo.getNetworkId());
                            if (mCurrentSectionId == SECTION_ID_WIFI_ON)
                                mSectionWifiOnListAdapter.notifyDataSetChanged();
                        }
                        break;
                    }
                }
            }
        }

    }

    private void updateWifiState(int state) {
        Log.d(TAG, "updateWifiState state " + state);
        switch (state) {
            case WifiManager.WIFI_STATE_ENABLED:
                mWifiScanner.resume();
                mbOnOffCanSwith = true;
                return; // not break, to avoid the call to pause() below

            case WifiManager.WIFI_STATE_ENABLING:
                // WifiStatusInfoUpdate(R.string.wifi_starting);
                mbOnOffCanSwith = false;
                break;

            case WifiManager.WIFI_STATE_DISABLED:
                // WifiStatusInfoUpdate(R.string.wifi_empty_list_wifi_off);
                mbOnOffCanSwith = true;
                break;

            case WifiManager.WIFI_STATE_DISABLING:
                mbOnOffCanSwith = false;
                break;

            default:
                mbOnOffCanSwith = true;
                break;
        }

        mWifiLastInfo = null;
        mWifiLastState = null;
        mWifiScanner.pause();
    }

    private class WifiScanner extends Handler {
        private int mRetry = 0;

        void resume() {
            if (!hasMessages(0)) {
                sendEmptyMessage(0);
            }
        }

        void forceScan() {
            removeMessages(0);
            sendEmptyMessage(0);
        }

        void pause() {
            mRetry = 0;
            removeMessages(0);
        }

        @Override
        public void handleMessage(Message message) {
            Log.d(TAG, "updateWifiState mWifiManager.isWifiEnabled() " + mWifiManager.isWifiEnabled());
            if (mWifiManager.startScan()) {
                mRetry = 0;
            } else if (++mRetry >= 3) {
                mRetry = 0;
                // Toast.makeText(mContext, R.string.wifi_fail_to_scan,
                // Toast.LENGTH_LONG).show();
                // Constant.makeText(mContext, R.string.wifi_fail_to_scan,
                // Toast.LENGTH_LONG);
                return;
            }
            sendEmptyMessageDelayed(0, WIFI_RESCAN_INTERVAL_MS);
        }
    }

    private String getNameStr() {
        if (mSelectedAccessPoint != null) {
            return mSelectedAccessPoint.getSSID();
        } else if (mWifiAccessPointArray != null && mWifiAccessPointArray.size() > 0) {
            if (mSelectedConnectId >= mWifiAccessPointArray.size())
                mSelectedConnectId = mWifiAccessPointArray.size() - 1;

            return mWifiAccessPointArray.get(mSelectedConnectId).getSSID();
        }

        return "";
    }

    private String getConnectedSpeedStr() {
        if (mSelectedAccessPoint != null) {
            return mSelectedAccessPoint.getInfo().getLinkSpeed() + WifiInfo.LINK_SPEED_UNITS;
        } else if (mWifiAccessPointArray != null && mWifiAccessPointArray.size() > 0) {
            if (mSelectedConnectId >= mWifiAccessPointArray.size())
                mSelectedConnectId = mWifiAccessPointArray.size() - 1;

            return mWifiAccessPointArray.get(mSelectedConnectId).getInfo().getLinkSpeed() + WifiInfo.LINK_SPEED_UNITS;
        }

        return "";
    }

    private int getSecurityInt() {
        if (mSelectedAccessPoint != null) {
            return mSelectedAccessPoint.getSecurity();
        } else if (mWifiAccessPointArray != null && mWifiAccessPointArray.size() > 0) {
            if (mSelectedConnectId >= mWifiAccessPointArray.size())
                mSelectedConnectId = mWifiAccessPointArray.size() - 1;

            return mWifiAccessPointArray.get(mSelectedConnectId).getSecurity();
        }

        return 0;
    }

    /*
     * private class WifiServiceHandler extends Handler {
     *
     * @Override public void handleMessage(Message msg) { switch (msg.what) { //
     * case AsyncChannel.CMD_CHANNEL_HALF_CONNECTED: // if (msg.arg1 ==
     * AsyncChannel.STATUS_SUCCESSFUL) // { // //AsyncChannel in msg.obj // } //
     * else // { // //AsyncChannel set up failure, ignore // Log.e(TAG,
     * "Failed to establish AsyncChannel connection"); // } // break;
     *
     * case WifiManager.CMD_WPS_COMPLETED: // WpsResult result = (WpsResult)
     * msg.obj; // if (result == null) // break; // AlertDialog.Builder dialog =
     * new AlertDialog.Builder(getActivity()) //
     * .setTitle(R.string.wifi_wps_setup_title) //
     * .setPositiveButton(android.R.string.ok, null); // switch (result.status)
     * // { // case FAILURE: // dialog.setMessage(R.string.wifi_wps_failed); //
     * dialog.show(); // break; // // case IN_PROGRESS: //
     * dialog.setMessage(R.string.wifi_wps_in_progress); // dialog.show(); //
     * break; // // default: // if (result.pin != null) // { //
     * dialog.setMessage(getResources().getString(R.string.wifi_wps_pin_output,
     * result.pin)); // dialog.show(); // } // break; // } break;
     *
     * //TODO: more connectivity feedback default: //Ignore break; } } }
     */
    private String getSecurityStr() {
        if (mSelectedAccessPoint != null) {
            return mSelectedAccessPoint.getSecurityString(false);
        } else if (mWifiAccessPointArray != null && mWifiAccessPointArray.size() > 0) {
            if (mSelectedConnectId >= mWifiAccessPointArray.size())
                mSelectedConnectId = mWifiAccessPointArray.size() - 1;

            // Log.d(TAG,
            // "getSecurityStr security "+mWifiAccessPointArray.get(mSelectedConnectId).getSecurity());
            return mWifiAccessPointArray.get(mSelectedConnectId).getSecurityString(false);
        }

        return "";
    }

    private int getSignalStrengthInt() {
        if (mSelectedAccessPoint != null) {
            return mSelectedAccessPoint.getLevel();
        } else if (mWifiAccessPointArray != null && mWifiAccessPointArray.size() > 0) {
            if (mSelectedConnectId >= mWifiAccessPointArray.size())
                mSelectedConnectId = mWifiAccessPointArray.size() - 1;

            return mWifiAccessPointArray.get(mSelectedConnectId).getLevel();
        }

        return -1;
    }

    private boolean IsPersonSecurityFailure(WifiAccessPoint accessPoint) {
        boolean ret = false;
        if (accessPoint.getConfig() != null && accessPoint.getConfig().status == WifiConfiguration.Status.DISABLED) {
//            switch (accessPoint.getConfig().disableReason) {
//                case WifiConfiguration.DISABLED_AUTH_FAILURE:
//                    ret = true;
//
//                    // case WifiConfiguration.DISABLED_DHCP_FAILURE:
//                    // case WifiConfiguration.DISABLED_DNS_FAILURE:
//                    // ret = true;
//
//                    // case WifiConfiguration.DISABLED_UNKNOWN_REASON:
//                    // ret = true;
//            }
        }

        Log.d(TAG, "IsPersonSecurityFailure " + ret);
        return ret;
    }

    /*
     * static boolean checkKeyStore(WifiConfiguration config) { if (config ==
     * null) { return false; }
     *
     * if (!TextUtils.isEmpty(config.key_id.value())) { return true; }
     *
     * String values[] = { config.ca_cert.value(), config.client_cert.value() };
     * for (String value : values) { if (value != null &&
     * value.startsWith(WifiConfiguration.KEYSTORE_URI)) { return true; } }
     *
     * return false; }
     */
    /*
     * static boolean checkKeyStore(WifiConfiguration config) { if (config ==
     * null) { return false; }
     *
     * String values[] = {config.ca_cert.value(), config.client_cert.value(),
     * config.private_key.value()}; for (String value : values) { if (value !=
     * null && value.startsWith(KEYSTORE_SPACE)) { return true; } } return
     * false; }
     */
    private boolean requireKeyStore(WifiConfiguration config) {
        if (/* checkKeyStore(config) && */KeyStore.getInstance().state() != KeyStore.State.UNLOCKED) {
            return false;
            /*
             * mKeyStoreNetworkId = config.networkId;
             * Credentials.getInstance().unlock(getActivity()); return true;
             */
        }

        return false;
    }

    WifiConfiguration getConfig() {
        if (mSelectedAccessPoint != null && mSelectedAccessPoint.networkId != INVALID_NETWORK_ID && !mIsEdit) {
            return null;
        }

        WifiConfiguration config = new WifiConfiguration();
        int security = (mSelectedAccessPoint == null) ? mAccessPointSecurity : mSelectedAccessPoint.security;

        if (mSelectedAccessPoint == null) {
            config.SSID = WifiAccessPoint.convertToQuotedString(mAddSsidEditText.getText().toString());
            // If the user adds a network manually, assume that it is hidden.
            config.hiddenSSID = true;
        } else if (mSelectedAccessPoint.networkId == INVALID_NETWORK_ID) {
            config.SSID = WifiAccessPoint.convertToQuotedString(mSelectedAccessPoint.ssid);
        } else {
            config.networkId = mSelectedAccessPoint.networkId;
        }

        switch (security) {
            case WifiAccessPoint.SECURITY_NONE:
                config.allowedKeyManagement.set(KeyMgmt.NONE);
                break;

            case WifiAccessPoint.SECURITY_WEP:
                config.allowedKeyManagement.set(KeyMgmt.NONE);
                config.allowedAuthAlgorithms.set(AuthAlgorithm.OPEN);
                config.allowedAuthAlgorithms.set(AuthAlgorithm.SHARED);
                if (mWifiPassword.length() != 0) {
                    int length = mWifiPassword.length();
                    // WEP-40, WEP-104, and 256-bit WEP (WEP-232?)
                    if ((length == 10 || length == 26 || length == 58) && mWifiPassword.matches("[0-9A-Fa-f]*")) {
                        config.wepKeys[0] = mWifiPassword;
                    } else {
                        config.wepKeys[0] = '"' + mWifiPassword + '"';
                    }
                }
                break;

            case WifiAccessPoint.SECURITY_PSK:
                config.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
                if (mWifiPassword.length() != 0) {
                    if (mWifiPassword.matches("[0-9A-Fa-f]{64}")) {
                        config.preSharedKey = mWifiPassword;
                    } else {
                        config.preSharedKey = '"' + mWifiPassword + '"';
                    }
                }
                break;

            case WifiAccessPoint.SECURITY_EAP:
                config.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
                /*
                 * config.allowedKeyManagement.set(KeyMgmt.IEEE8021X);
                 * config.eap.setValue((String)
                 * mEapMethodSpinner.getSelectedItem());
                 *
                 * config.phase2.setValue((mPhase2Spinner.getSelectedItemPosition()
                 * == 0) ? "" : PHASE2_PREFIX + mPhase2Spinner.getSelectedItem());
                 * config
                 * .ca_cert.setValue((mEapCaCertSpinner.getSelectedItemPosition() ==
                 * 0) ? "" : KEYSTORE_SPACE + Credentials.CA_CERTIFICATE + (String)
                 * mEapCaCertSpinner.getSelectedItem());
                 * config.client_cert.setValue(
                 * (mEapUserCertSpinner.getSelectedItemPosition() == 0) ? "" :
                 * KEYSTORE_SPACE + Credentials.USER_CERTIFICATE + (String)
                 * mEapUserCertSpinner.getSelectedItem()); final boolean
                 * isEmptyKeyId = (mEapUserCertSpinner.getSelectedItemPosition() ==
                 * 0); config.key_id.setValue(isEmptyKeyId ? "" :
                 * Credentials.USER_PRIVATE_KEY + (String)
                 * mEapUserCertSpinner.getSelectedItem());
                 * config.engine.setValue(isEmptyKeyId ?
                 * WifiConfiguration.ENGINE_DISABLE :
                 * WifiConfiguration.ENGINE_ENABLE);
                 * config.engine_id.setValue(isEmptyKeyId ? "" :
                 * WifiConfiguration.KEYSTORE_ENGINE_ID);
                 * config.identity.setValue((mEapIdentityView.length() == 0) ? "" :
                 * mEapIdentityView.getText().toString());
                 * config.anonymous_identity.setValue((mEapAnonymousView.length() ==
                 * 0) ? "" : mEapAnonymousView.getText().toString());
                 */
                if (mWifiPassword.length() != 0) {
                    // config.password.setValue(mWifiPassword);
                }
                break;

            default:
                return null;
        }

        config.setProxySettings(mProxySettings);
        config.setIpAssignment(mIpAssignment);
        config.setStaticIpConfiguration(staticConfig);
        return config;
    }

    void submit() {
        final WifiConfiguration config = getConfig();

        Log.d(TAG, " ===== submit mIsEdit " + mIsEdit + " mWifiPassword " + mWifiPassword + " config " + config);
        if (config == null) {
            if (mSelectedAccessPoint != null && !requireKeyStore(mSelectedAccessPoint.getConfig())
                    && mSelectedAccessPoint.networkId != INVALID_NETWORK_ID) {
                mWifiManager.connect(mSelectedAccessPoint.networkId, mWifiConnectListener);
                // mWifiManager.connectNetwork(mSelectedAccessPoint.networkId);
            }
        } else if (config.networkId != INVALID_NETWORK_ID) {
            if (mSelectedAccessPoint != null) {
                // mWifiManager.save(config, mWifiSaveListener);
                // if (IsPersonSecurityFailure(mSelectedAccessPoint)) //password
                // error, need to forget
                // {
                // mWifiManager.forgetNetwork(mSelectedAccessPoint.networkId);
                // }
                System.out.println("a");
                mWifiManager.save(config, mWifiSaveListener);
                mWifiManager.connect(config, mWifiConnectListener);
                // mWifiManager.saveNetwork(config);
                // mWifiManager.connectNetwork(config);
            }
        } else {
            if (mIsEdit || requireKeyStore(config)) {
                mWifiManager.save(config, mWifiSaveListener);
                mWifiManager.connect(config, mWifiConnectListener);
                // mWifiManager.saveNetwork(config);
                // mWifiManager.connectNetwork(config);
            } else {
                mWifiManager.connect(config, mWifiConnectListener);
                // mWifiManager.connectNetwork(config);
            }
        }

        if (mWifiManager.isWifiEnabled()) {
            mWifiScanner.resume();
        }
        updateWifiAccessPoints();

    }

    void forget() {
        if (mSelectedAccessPoint.networkId == INVALID_NETWORK_ID) {
            // Should not happen, but a monkey seems to triger it
            Log.e(TAG, "Failed to forget invalid network " + mSelectedAccessPoint.getConfig());
            return;
        }

        mWifiManager.forget(mSelectedAccessPoint.networkId, mWifiForgetListener);
        // mWifiManager.forgetNetwork(mSelectedAccessPoint.networkId);

        if (mWifiManager.isWifiEnabled()) {
            mWifiScanner.resume();
        }

        updateWifiAccessPoints();
    }

    public static void updatewifi() {
        System.out.println("ssss");
        mSectionWifiOnListAdapter.notifyDataSetChanged();
    }
}
