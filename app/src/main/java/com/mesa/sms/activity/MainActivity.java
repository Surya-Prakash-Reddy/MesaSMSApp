package com.mesa.sms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mesa.sms.R;

import java.util.ArrayList;

import com.mesa.sms.adapter.RootMessagesAdapter;
import com.mesa.sms.layout.RootMessageItem;

public class MainActivity extends AppCompatActivity {

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;

    private RecyclerView.Adapter mRecyclerViewAdapter;

    private RecyclerView.LayoutManager mRecyclerViewLayoutManager;

    ArrayList<RootMessageItem> homeScreenMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeScreenMessages = new ArrayList<>();

        mRecyclerView = findViewById(R.id.root_messages_list);
        mRecyclerViewLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewAdapter = new RootMessagesAdapter(homeScreenMessages);

        //Populate screen
        if (shouldRequestForPermissions()) {
            getPermissionToReadSMS();
        } else {
            refreshSmsInbox();
            mRecyclerView.setAdapter(mRecyclerViewAdapter);
            mRecyclerView.setLayoutManager(mRecyclerViewLayoutManager);
        }

//        homeScreenMessages = new ArrayList<>();
//        homeScreenMessages.add(new RootMessageItem(R.drawable.ic_root_message_default_icon,
//                "This is small",
//                "The content is also small"));
//        homeScreenMessages.add(new RootMessageItem(R.drawable.ic_root_message_default_icon,
//                "This is small but not",
//                "The content is also small but not very small too."));
//        homeScreenMessages.add(new RootMessageItem(R.drawable.ic_root_message_default_icon,
//                "This is small but not so small that",
//                "The content is also small but not very small too. This can get big some times."));
//        homeScreenMessages.add(new RootMessageItem(R.drawable.ic_root_message_default_icon,
//                "This is small but not so small that it's not even visible",
//                "The content is also small ut not very small too. This can get big some times which is exactly what is happening here."));


    }

    private boolean shouldRequestForPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED;
    }

    private void getPermissionToReadSMS() {
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

    private void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) {
            Log.i(TAG, "refreshSmsInbox: Couldn't get SMS details");
            return;
        }

        do {
            final String indexAddressString = smsInboxCursor.getString(indexAddress);
            final String indexBodyString = smsInboxCursor.getString(indexBody);
            RootMessageItem messageItem = new RootMessageItem(R.drawable.ic_root_message_default_icon,
                    indexAddressString,
                    indexBodyString);
            homeScreenMessages.add(messageItem);
        } while (smsInboxCursor.moveToNext());
    }
}