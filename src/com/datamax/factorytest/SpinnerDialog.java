package com.datamax.factorytest;

import java.util.ArrayList;
import java.util.List;

import com.datamax.factorytest.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SpinnerDialog extends AlertDialog {
    private Context mContext = null;

    private ListView mSpinnerList;
    private List<String> mSpinnerStringList = null;
    private SpinnerListAdapter mSpinnerListAdapter = null;
    private AdapterView.OnItemClickListener mSpinnerListItemClickListener;
    int mSelectedId = 0;
    int mResId = 0;

    public SpinnerDialog(Context context, int theme, int resId, AdapterView.OnItemClickListener listener) {
        super(context, theme);
        mContext = context;

        mResId = resId;
        mSpinnerListItemClickListener = listener;
        if (mSpinnerListItemClickListener == null) {
            mSpinnerListItemClickListener = new AdapterView.OnItemClickListener() {
                @Override
				public void onItemClick(AdapterView parent, View v, int position, long id) {
                    switch (position) {
                        case 0:
                            if (mSelectedId != 0) {

                            }
                            break;

                        default:
                            break;
                    }
                }
            };
        }
    }

    public SpinnerDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_security_spinner_dialog);

        mSpinnerList = (ListView) findViewById(R.id.spinner_list);
        mSpinnerStringList = new ArrayList<String>();
        String[] secStrArray = mContext.getResources().getStringArray(mResId > 0 ? mResId : R.array.wifi_add_security);
        for (String secStr : secStrArray) {
            mSpinnerStringList.add(secStr);
        }

        mSpinnerListAdapter = new SpinnerListAdapter(mContext);
        mSpinnerList.setAdapter(mSpinnerListAdapter);
        mSpinnerList.setOnItemClickListener(mSpinnerListItemClickListener);
        mSpinnerList.setDivider(null);
    }

    private class SpinnerListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context cont;

        class ViewHolder {
            ImageView icon;
            TextView text;
        }

        public SpinnerListAdapter(Context context) {
            super();
            cont = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
		public int getCount() {
            return mSpinnerStringList != null ? mSpinnerStringList.size() : 0;
        }

        @Override
		public Object getItem(int position) {
            return position;
        }

        @Override
		public long getItemId(int position) {
            return position;
        }

        @Override
		public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.wifi_security_spinner_dialog_item, null);

                holder = new ViewHolder();

                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.text = (TextView) convertView.findViewById(R.id.text);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(mSpinnerStringList.get(position));
            holder.text.setTextColor(Color.WHITE);

            return convertView;
        }
    }
}
