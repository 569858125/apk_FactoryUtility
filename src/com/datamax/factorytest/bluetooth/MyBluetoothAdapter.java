package com.datamax.factorytest.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.datamax.factorytest.R;

import java.util.List;

/**
 * 创建者      xiekang
 * 创建时间    2019-5-28
 * 描述        ${DESCRIPTION}
 * <p>
 * 更新者      xiekang
 * <p>
 * 更新时间    2019-5-28
 * 更新描述    ${DESCRIPTION}
 */
public class MyBluetoothAdapter extends BaseAdapter {

    private List<BluetoothDevice> bluetoothDevices;
    private Context mContext;

    public MyBluetoothAdapter(List<BluetoothDevice> bluetoothDevices, Context mContext) {
        this.bluetoothDevices = bluetoothDevices;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return bluetoothDevices == null ? 0 : bluetoothDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return bluetoothDevices == null ? null : bluetoothDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bluetooth_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BluetoothDevice bluetoothDevice = bluetoothDevices.get(position);
        viewHolder.name.setText(bluetoothDevice.getName());
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            viewHolder.address.setText(bluetoothDevice.getAddress() + "  未匹配");
        } else if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
            viewHolder.address.setText(bluetoothDevice.getAddress() + "  匹配中");
        } else if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
            viewHolder.address.setText(bluetoothDevice.getAddress() + "  已匹配");
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView address;
        private TextView name;

        ViewHolder(View view) {
            address = view.findViewById(R.id.item_tv_address);
            name = view.findViewById(R.id.item_tv_name);
            view.setTag(this);
        }
    }
}
