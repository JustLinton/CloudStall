package com.berrysdu.cloudstall.func;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class PermissionChecker {

    Activity activity;

    public PermissionChecker(Activity activity_){
        activity=activity_;
    }

    public void checkPermission(){
        if(!permissionIsOkay()){showNoPermission();}
    }

    public boolean permissionIsOkay(){
        if(!hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)){return false;}
        if(!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)){return false;}
        if(!hasPermission(Manifest.permission.CHANGE_WIFI_STATE)){return false;}
        if(!hasPermission(Manifest.permission.READ_PHONE_STATE)){return false;}
        if(!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){return false;}
        if(!hasPermission(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)){return false;}
        return true;
    }

    public void showNoPermission() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle("啊呀！")
                .setMessage("没有获得足够的权限。\n请到设置检查app的定位、电话、存储权限。").setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(activity, "请给予app权限。", Toast.LENGTH_LONG).show();
                        activity.finish();
                    }
                });
        builder.create().show();
    }

    public boolean hasPermission(String permission){
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
