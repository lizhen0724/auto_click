package com.example.mytest;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MySend {
    private LocalSocket mLocalSocket;
    private OutputStream mOutputStream;
    private InputStream mInputStream;

    public MySend() {
        mLocalSocket = new LocalSocket();
    }

    public void connect(String abstractName) {
        if (mLocalSocket != null) {
            try {
                mLocalSocket.connect(new LocalSocketAddress(abstractName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mOutputStream = mLocalSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mInputStream = mLocalSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeClickEvent(int content) {
        try {
            mOutputStream.write(content);
        } catch (IOException e) {
            Log.d("lizhen",e.toString());
        }
    }

}
