package com.jonahkallen.files;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    static String filesPackage = "com.google.android.documentsui";
    static String filesClass = "com.android.documentsui.files.FilesActivity";
    static Boolean filesAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName(filesPackage, filesClass);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "yay?", Toast.LENGTH_SHORT).show();
            finish();
            } catch(ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Sorry, your device is incompatible. :(", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}