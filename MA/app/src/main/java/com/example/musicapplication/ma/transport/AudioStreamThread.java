package com.example.musicapplication.ma.transport;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.widget.Toast;

import com.example.musicapplication.ma.BluetoothActivity;
import com.example.musicapplication.ma.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by tasmeen on 11/13/2015.
 */
public class AudioStreamThread extends Thread {

    private int mBufferSize;
    private final BluetoothSocket bluetoothSocket;
    private final InputStream socketInputStream;
    private final OutputStream socketOutputStream;
    private Handler mHandler;

    public AudioStreamThread(BluetoothSocket bluetoothSocket, Handler dataHandler, int bufferSize) {
        this.bluetoothSocket = bluetoothSocket;
        mHandler = dataHandler;
        mBufferSize = bufferSize;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = bluetoothSocket.getInputStream();
            outputStream = bluetoothSocket.getOutputStream();
        } catch (IOException ioe) {

            ioe.printStackTrace();
        }
        socketInputStream = inputStream;
        socketOutputStream = outputStream;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[mBufferSize];
        int bytes;

        while (true) {
            try {
                bytes = socketInputStream.read(buffer);
                mHandler.obtainMessage(Constants.DATA_READ, bytes, -1, buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                mHandler.obtainMessage(Constants.CONNECTION_INTERRUPTED).sendToTarget();
                break;
            }
        }
    }

    public void write(byte[] buffer) {
        try {
            socketOutputStream.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            mHandler.obtainMessage(Constants.CONNECTION_INTERRUPTED).sendToTarget();
        }
    }

    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

