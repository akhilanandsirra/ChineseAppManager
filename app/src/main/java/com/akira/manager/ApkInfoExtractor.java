package com.akira.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.akira.manager.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ApkInfoExtractor {

    Context context1;
    HashSet<String> chineseApps;

    public ApkInfoExtractor(Context context2){

        context1 = context2;
    }

    public List<String> GetAllInstalledApkInfo(){

        List<String> ApkPackageName = new ArrayList<>();

        //Toast.makeText(context1, ""+chineseApps, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_MAIN,null);

        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED );

        List<ResolveInfo> resolveInfoList = context1.getPackageManager().queryIntentActivities(intent,0);

        for(ResolveInfo resolveInfo : resolveInfoList){

            ActivityInfo activityInfo = resolveInfo.activityInfo;

            if(!isSystemPackage(resolveInfo) && !isSelfAppPackage(activityInfo.applicationInfo.packageName)&&isChineseApp(activityInfo.applicationInfo.packageName)){

                ApkPackageName.add(activityInfo.applicationInfo.packageName);
            }
        }

        return ApkPackageName;

    }

    public boolean isSystemPackage(ResolveInfo resolveInfo){

        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public boolean isSelfAppPackage(String packageName){
        String thisAppPackageName = context1.getPackageName();
        return thisAppPackageName.equals(packageName);
    }

    public boolean isChineseApp(String packageName){
        String thisAppPackageName = context1.getPackageName();
        chineseApps = new HashSet<String>(Arrays.asList(context1.getResources().getStringArray(R.array.chineseApps)));
        return chineseApps.contains(packageName);
    }

    public Drawable getAppIconByPackageName(String ApkTempPackageName){

        Drawable drawable;

        try{
            drawable = context1.getPackageManager().getApplicationIcon(ApkTempPackageName);

        }
        catch (PackageManager.NameNotFoundException e){

            e.printStackTrace();

            drawable = ContextCompat.getDrawable(context1, R.mipmap.ic_launcher);
        }
        return drawable;
    }

    public String GetAppName(String ApkPackageName){

        String Name = "";

        ApplicationInfo applicationInfo;

        PackageManager packageManager = context1.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);

            if(applicationInfo!=null){

                Name = (String)packageManager.getApplicationLabel(applicationInfo);
            }

        }catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Name;
    }

    public double GetAppSize(String ApkPackageName){

        double Size = 0;

        ApplicationInfo applicationInfo;

        PackageManager packageManager = context1.getPackageManager();

        try {

            applicationInfo = packageManager.getApplicationInfo(ApkPackageName, 0);

            if(applicationInfo!=null){

                //Size = (String)packageManager.getApplicationLabel(applicationInfo);
                Size = getApkSize(context1, ApkPackageName)/1024000.0;

            }

        }catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();
        }
        return Size;
    }

    public static long getApkSize(Context context, String packageName)
            throws PackageManager.NameNotFoundException {
        return new File(context.getPackageManager().getApplicationInfo(
                packageName, 0).publicSourceDir).length();
    }
}