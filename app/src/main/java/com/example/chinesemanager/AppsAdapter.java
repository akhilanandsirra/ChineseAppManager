package com.example.chinesemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

    Context context1;
    List<String> stringList;
    IntentFilter intentFilter;
    //BroadcastReceiver br;
    int index;

    public AppsAdapter(Context context, List<String> list){

        context1 = context;

        stringList = list;
        start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView imageView;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;

        public ViewHolder (View view){

            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view2 = LayoutInflater.from(context1).inflate(R.layout.cardview_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view2);

        return viewHolder;
    }

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                Log.e(" BroadcastReceiver ", "onReceive called "
                        + " PACKAGE_REMOVED ");
                Toast.makeText(context, " onReceive !!!! PACKAGE_REMOVED",
                        Toast.LENGTH_LONG).show();
                stringList.remove(index);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, getItemCount());
            }
        }
    };


    public void destroyer(){
        context1.unregisterReceiver(br);
    }

    public void start(){
        intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addDataScheme("package");
        context1.registerReceiver(br, intentFilter);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){

        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);

        final String ApplicationPackageName = (String) stringList.get(position);

        double ApplicationSize= apkInfoExtractor.GetAppSize(ApplicationPackageName);

        final String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);

        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);

        viewHolder.textView_App_Name.setText(ApplicationLabelName);

        viewHolder.textView_App_Package_Name.setText(String.format("%.2f MB", ApplicationSize));

        viewHolder.imageView.setImageDrawable(drawable);


        //Adding click listener on CardView to open clicked application directly from here .
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);

                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:"+ApplicationPackageName));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                index = position;
                context1.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount(){
        return stringList.size();
    }

}