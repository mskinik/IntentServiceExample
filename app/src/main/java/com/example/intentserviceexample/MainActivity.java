package com.example.intentserviceexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText input;
    TextView timer,count;
    ResultReceiver resultReceiver;
    Handler handler=new Handler();
    Button button;
    int a=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input=findViewById(R.id.input);
        timer =findViewById(R.id.timer);
        count=findViewById(R.id.count);
        button=findViewById(R.id.button2);
        button.setVisibility(View.INVISIBLE);
    }

    public void change(View view) {
        button.setText(new Random().nextInt(100)+"");
        count.setText((a++)+"");
    }

    public void startService(View view) {
        resultReceiver= new MyResultReceiver(null);
        Intent intent= new Intent(MainActivity.this,MyIntentService.class);
        intent.putExtra("input",Integer.parseInt(input.getText().toString()));
        intent.putExtra("receiver",resultReceiver);
        button.setVisibility(View.VISIBLE);
        startService(intent);
    }
    class MyResultReceiver extends ResultReceiver {

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode,final Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            final int data=resultData.getInt("time");
            if(resultCode==16&&resultData!=null)
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timer.setText(""+data);
                        if(data==1)
                        {
                            timer.setText("Süre bitti");
                            button.setVisibility(View.INVISIBLE);
                        }

                    }
                });

            }
        }
    }
}