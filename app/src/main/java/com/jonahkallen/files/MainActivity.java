package com.jonahkallen.files;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

public class MainActivity extends Activity {
    static String filesPackageNew = "com.google.android.documentsui";
    static String filesPackageOld = "com.android.documentsui";
    static String packageName = BuildConfig.APPLICATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PackageManager pm = this.getPackageManager();
        if(filesAppExists(filesPackageNew, pm)){
            startFiles(this, filesPackageNew);
            finish();
        } else if (filesAppExists(filesPackageOld, pm)){
           startFiles(this, filesPackageOld);
            finish();
        } else {
            incompatibleDevice();
        }
    }

    public void startFiles(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            incompatibleDevice();
        }
        assert intent != null;
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean filesAppExists(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void incompatibleDevice(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Device Incompatible.");
        builder.setMessage("The stock Android file manager could not be found. Click the \"HELP\" button to learn more or to request device support, which must be done manually due to limitations in Android. Click the \"UNINSTALL\" button to uninstall this app. Sorry for any inconvenience!");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Help",
                (dialog, id) -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/TheOkayJK/Android-Files-Shortcut/blob/main/HELP.md"));
                    startActivity(browserIntent);
                    dialog.cancel();
                });

        builder.setNegativeButton(
                "UNINSTALL",
                (dialog, id) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                    dialog.cancel();
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public String getPackageName(){
        return packageName;
    }
}