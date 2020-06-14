package com.example.chinesemanager;

import androidx.appcompat.app.AppCompatActivity;
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

    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
        adapter.destroyer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
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

