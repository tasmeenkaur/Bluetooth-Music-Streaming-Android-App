package com.example.musicapplication.ma.Connection;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.musicapplication.ma.Constants;

import java.io.IOException;

/**
 * Created by tasmeen on 11/13/2015.
 */
public class AcceptThread extends Thread {

    private final BluetoothServerSocket mServerSocket;
    private BluetoothSocket connectedSocket;
    private BluetoothAdapter mBluetoothAdapter;
    private Context context;
    private Handler mHandler;

    public AcceptThread(Context context, Handler handler) {
        this.context = context;
        mHandler = handler;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket bluetoothServerSocket = null;
        try {
            bluetoothServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(Constants.SERVICE_NAME, Constants.SERVICE_UUID);
        } catch (Exception exception) {
        }
        mServerSocket = bluetoothServerSocket;
    }

    @Override
    public void run() {
        BluetoothSocket bluetoothSocket = null;
        while (true) {
            try {
                bluetoothSocket = mServerSocket.accept();
            } catch (IOException e) {
                Log.i(getClass().getSimpleName(), "Socket closed");
                break;
            }
            if (bluetoothSocket != null) {
                Log.i("From Server", "client connected");
                connectedSocket = bluetoothSocket;
                mHandler.obtainMessage(Constants.STATE_CONNECTED, connectedSocket).sendToTarget();
                break;
            }
        }
    }

    public void cancel() {
        try {
            mServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
