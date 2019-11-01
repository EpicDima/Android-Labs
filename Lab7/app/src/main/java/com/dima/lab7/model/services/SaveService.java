package com.dima.lab7.model.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.dima.lab7.model.CustomerList;

public class SaveService extends Service {

    private static final int PAUSE = 10000;

    private Thread mThread;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(PAUSE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                if (CustomerList.getInstance() != null) {
                    CustomerList.writeFile(SaveService.this);
                    Log.d("MAIN", "Service write to file");
                }
            }
        }
    };

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        mThread = new Thread(runnable);
        mThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mThread.interrupt();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
