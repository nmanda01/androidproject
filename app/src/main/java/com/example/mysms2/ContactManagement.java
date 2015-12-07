package com.example.mysms2;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Telephony;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.ContactsContract;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.EditText;
import com.example.mysms2.DataProvider;

import java.util.Date;



/**
 * Created by paradroid on 12/6/15.
 */
public class ContactManagement {

    static Context context;

     public static String getDisplayNameByNumber(String number) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor contactLookup = context.getContentResolver().query(uri, new String[] {ContactsContract.PhoneLookup._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        int indexName = contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME);
        try {
            if (contactLookup != null && contactLookup.moveToNext()) {
                number = contactLookup.getString(indexName);
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }
        return number;
    }


    /*
    static final String DEBUG_TAG = "AppChat Logging";

    ////////////////////////////////////////////
    private static final int CONTACT_PICKER_RESULT = 1001;

    public void doLaunchContactPicker(View view) {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
    }


    ////////////////////////////////////////////
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String phoneNumber = "";
                    try {
                        Uri result = data.getData();
                        Log.v(DEBUG_TAG, "Got a contact result: "
                                + result.toString());

                        // get the contact id from the Uri
                        String id = result.getLastPathSegment();

                        // query for everything phoneNumber
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                        // let's just get the first phoneNumber
                        if (cursor.moveToFirst()) {
                            phoneNumber = cursor.getString(phoneNumberIdx);
                            Log.v(DEBUG_TAG, "Got number: " + phoneNumber);
                        } else {
                            Log.w(DEBUG_TAG, "No results");
                        }
                    } catch (Exception e) {
                        Log.e(DEBUG_TAG, "Failed to get phoneNumber data", e);
                    } finally {

                        Log.v(DEBUG_TAG, "Finally");

                        if (cursor != null) {
                            cursor.close();
                        }

                        EditText phoneNumberEntry = (EditText) findViewById(R.id.composeEditTextTo);


                        if(phoneNumberEntry == null){
                            Log.w(DEBUG_TAG, "phoneNumberEntry is Null");
                        }

                        if(phoneNumber == null){
                            Log.w(DEBUG_TAG, "phoneNumber is Null");
                        }


                        phoneNumberEntry.setText(phoneNumber);
                        if (phoneNumber.length() == 0) {
                            Toast.makeText(this, "No phoneNumber found for contact.",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
            }

        } else {
            Log.w(DEBUG_TAG, "Warning: activity result not ok");
        }
    }
    */



}
