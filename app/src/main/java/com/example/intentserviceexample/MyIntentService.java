package com.example.intentserviceexample;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    boolean runCommand;
    private static String TAG=MyIntentService.class.getSimpleName();
    ResultReceiver resultReceiver;
    int k;
    int durum=1;
    public MyIntentService() {
        super("");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "onHandleIntent: "+Thread.currentThread().getName());

        resultReceiver=intent.getParcelableExtra("receiver");
        k=intent.getIntExtra("input",0);
        runCommand=true;
        hesapla(runCommand);

    }
    @Override
    public void onCreate() {
        super.onCreate();;
        Log.d(TAG, "onCreate: "+Thread.currentThread().getName());
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        String c=intent.getStringExtra("girdi");
        Log.d(TAG, "onStartCommand: "+Thread.currentThread().getName()+" Girdi: "+c);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        runCommand=false;
        hesapla(runCommand);
        Log.d(TAG, "onDestroy: "+Thread.currentThread().getName());
    }
    public void hesapla (boolean al)
    {
        while(durum<k)
        {
            if(al==true)
            {
                try {
                    Thread.sleep(1000);
                    k--;
                    Bundle bundle= new Bundle();
                    bundle.putInt("time",k);
                    Log.d(TAG, "run: Thread "+Thread.currentThread().getName()+" Timer: "+k);
                    resultReceiver.send(16,bundle);
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }

    }
}
