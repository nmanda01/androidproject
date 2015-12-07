package com.example.mysms2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class SendSmsActivity extends Activity {

    Button sendSMSBtn;
    EditText toPhoneNumberET;
    EditText smsMessageET;
    static final String DEBUG_TAG = "AppChat Logging";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        sendSMSBtn = (Button) findViewById(R.id.btnSendSMS);
        toPhoneNumberET = (EditText) findViewById(R.id.editTextPhoneNo);
        smsMessageET = (EditText) findViewById(R.id.editTextSMS);
        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMS();
            }
        });
    }

    protected void sendSMS() {
        String toPhoneNumber = toPhoneNumberET.getText().toString();
        String smsMessage = smsMessageET.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, null, smsMessage, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void goToInbox(View view) {
        Intent intent = new Intent(SendSmsActivity.this, ReceiveSmsActivity.class);
        startActivity(intent);
    }




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
                        cursor = getContentResolver().query(Phone.CONTENT_URI,
                                null, Phone.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int phoneNumberIdx = cursor.getColumnIndex(Phone.DATA);
                        //int nameIndex = cursor.getColumnIndex(Phone.DISPLAY_NAME);

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

                        EditText phoneNumberEntry = (EditText) findViewById(R.id.editTextPhoneNo);


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
}