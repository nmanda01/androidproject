package com.example.mysms2;

/**
 * Created by paradroid on 12/6/15.
 */

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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


public class ComposeSMSActivity extends Activity {


    static Message myMessage;

    static final String DEBUG_TAG = "AppChat Logging";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_sms);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        // Enable the 'back' button
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compose_sm, menu);
        return true;
    }


    public static void addToInbox(){
        DataProvider.getInstance().addMessage(myMessage);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if(id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ConversationListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    protected static void sendSMS(String toPhoneNumber, String smsMessage) {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNumber, "ME", smsMessage, null, null);
            Toast.makeText(MainActivity.context, "SMS sent.",
                    Toast.LENGTH_LONG).show();
            //DataProvider.addMessage(new Message(smsMessage, "ME", toPhoneNumber, new Date()));
            String displayName = ContactManagement.getDisplayNameByNumber(toPhoneNumber);
            myMessage = new Message(smsMessage, toPhoneNumber, "ME", new Date(), displayName);
            addToInbox();
            //addMessage(new Message("content9", "sender4", "recipient", new Date()));
        } catch (Exception e) {
            Toast.makeText(MainActivity.context,
                    "Sending SMS failed.",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }





    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }





        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_compose_sms, container, false);

            // Wire up some buttons
            rootView.findViewById(R.id.composeButtonCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpTo(getActivity(), new Intent(getActivity(), ConversationListActivity.class));
                }
            });

            rootView.findViewById(R.id.composeButtonSend).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recipient = ((TextView)rootView.findViewById(R.id.composeEditTextTo)).getText().toString();
                    String message   = ((TextView)rootView.findViewById(R.id.composeEditTextMessage)).getText().toString();


                    sendSMS(recipient, message);


                    //SmsManager smsManager = SmsManager.getDefault();
                    //smsManager.sendTextMessage(recipient, "ME", message, null, null);
                }
            });

            return rootView;
        }






        @Override
        public void onViewStateRestored(Bundle savedInstanceState) {
            super.onViewStateRestored(savedInstanceState);

            // Discover whether we're default
            final String packageName = getActivity().getPackageName();

            if(!Telephony.Sms.getDefaultSmsPackage(getActivity()).equals(packageName)) {
                // Not default
                View vg = getView().findViewById(R.id.composeNotDefault);
                vg.setVisibility(View.VISIBLE);

                // Add click listener to the set default button
                Button button = (Button)getView().findViewById(R.id.composeButtonSetDefault);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName);
                        startActivity(intent);
                    }
                });
            } else {
                // App is default
                View vg = getView().findViewById(R.id.composeNotDefault);
                vg.setVisibility(View.GONE);
            }
        }
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
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null, Phone.CONTACT_ID + "=?", new String[] { id },
                                null);

                        int phoneNumberIdx = cursor.getColumnIndex(Phone.DATA);

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
}


