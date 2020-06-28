package com.akhil.unused;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.akhil.unused.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void uninstall(View v) {
        Intent intent=new Intent(this, ResultActivity.class);
        startActivity(intent);
        this.finish();
    }
}
