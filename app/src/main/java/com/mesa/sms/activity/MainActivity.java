package com.mesa.sms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mesa.sms.R;

public class MainActivity extends AppCompatActivity {

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Populate screen
        if (shouldRequestForPermissions()) {
            getPermissionToReadSMS();
        } else {
            refreshSmsInbox();
        }
    }

    public boolean shouldRequestForPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED;
    }

    public void getPermissionToReadSMS() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
            Toast.makeText(this, "Please allow required permissions for app to function", Toast.LENGTH_SHORT).show();
        }
        requestPermissions(new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSIONS_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                refreshSmsInbox();
                return;
            }
            Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
            Log.i(TAG, "refreshSmsInbox: Couldn't get SMS details");
            return;
        }

        do {
            String indexBodyString = smsInboxCursor.getString(indexBody);
            String indexAddressString = smsInboxCursor.getString(indexAddress);
            Log.i(TAG, "refreshSmsInbox: Iterating over element " + smsInboxCursor.getString(smsInboxCursor.getColumnIndex("_id")));
            Log.i(TAG, "refreshSmsInbox: SMS Body: " + indexBodyString);
            Log.i(TAG, "refreshSmsInbox: SMS Address: " + indexAddressString);
        } while (smsInboxCursor.moveToNext());
    }
}