package com.datamax.factorytest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.util.Log;

public class DtvControl {

    public static final String TAG = "DmLauncher_DtvControl";

    private String mControlPath = null;
    private String mControlValue = null;
    private int maxValueSize = 32;

    public DtvControl() {
    }

    public DtvControl(String c) {
        mControlPath = c;
    }

    public void setValueMaxSize(int size) {
        maxValueSize = size;
    }

    public DtvControl setValue(String v) {
        setString(mControlPath, v, false);
        mControlValue = v;
        return this;
    }

    public DtvControl setValueForce(String v) {
        setString(mControlPath, v, true);
        mControlValue = v;
        return this;
    }

    public String getValue() {
        File file = new File(mControlPath);
        if (!file.exists()) {
            return null;
        }

        //read
        try {
            BufferedReader in = new BufferedReader(new FileReader(mControlPath), maxValueSize);
            try {
                return in.readLine();
            } finally {
                in.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException when read " + mControlPath);
        }
        return null;
    }

    public boolean setString(String c, String v, boolean force) {

        File file = new File(c);
        if (!file.exists()) {
            return false;
        }

        if (!force) {
            //read
            try {
                BufferedReader in = new BufferedReader(new FileReader(c), maxValueSize);
                try {
                    String vr = in.readLine();
                    if (vr != null) {
                        if (vr.equals(v)) {
                            Log.d(TAG, "control status ready, ignored");
                            return true;
                        }
                    }
                } finally {
                    in.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "IOException when read " + c);
            }
        }

        //write
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(c), 32);
            try {
                out.write(v);
                Log.d(TAG, "control status.");
            } finally {
                out.close();
            }
            return true;
        } catch (IOException e) {
            Log.e(TAG, "IOException when write " + c);
            return false;
        }
    }


}
