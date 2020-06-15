package com.example.chinesemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //RecyclerView.Adapter adapter;
    AppsAdapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    public AppCompatTextView AppsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Passing the column number 1 to show online one column in each row.
        recyclerViewLayoutManager = new GridLayoutManager(ResultActivity.this, 1);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        adapter = new AppsAdapter(ResultActivity.this, new ApkInfoExtractor(ResultActivity.this).GetAllInstalledApkInfo());
        //adapter2 = new AppsAdapter(ResultActivity.this, new ApkInfoExtractor(ResultActivity.this).GetAllInstalledApkInfo());
        recyclerView.setAdapter(adapter);

        int count = adapter.getItemCount();

        AppsInfo=(AppCompatTextView) findViewById(R.id.chineseInfo);
        if(count==0){
            AppsInfo.setText("You have 0 Chinese Apps\nYou are Awesome!");
        }
        else {
        AppsInfo.setText(String.format(getString(R.string.AppCount), count));}

    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        //AppsInfo.setText(String.format(getString(R.string.AppCount), adapter.getItemCount()));
        adapter.destroyer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.start();
    }

    /*@Override
    public void onResume() {
        super.onResume();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), AppsAdapter.class);
        startService(intent);
    }*/


}

