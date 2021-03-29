package com.zz.appstartfast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.zz.appstartfast.task.ATask;
import com.zz.appstartfast.task.BTask;
import com.zz.appstartfast.task.CTask;
import com.zz.appstartfast.task.DTask;
import com.zz.appstartfast.task.ETask;
import com.zz.appstartfast.task.FTask;
import com.zz.appstartfast.task.HTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("kang333", "start");
        AppStartFast appStartFast = new AppStartFast.Builder(this)
                .addTask(new DTask())
                .addTask(new FTask())
                .addTask(new CTask())
                .addTask(new ATask())
                .addTask(new BTask())
                .addTask(new ETask())
                .addTask(new HTask())
                .build();
        appStartFast.start();
    }
}