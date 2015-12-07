/*
Copyright 2014 Scott Logic Ltd

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.example.mysms2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.util.Log;
//import android.provider.Telephony.SMS_DELIVER_ACTION;
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

import com.example.mysms2.DataProvider;
import com.example.mysms2.Message;

import java.util.Date;

public class SMSBroadcastReceiver extends BroadcastReceiver {

    String nameToDisplay;
    public SMSBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for(Object currentObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) currentObj);

                    String senderName = ContactManagement.getDisplayNameByNumber(currentMessage.getDisplayOriginatingAddress());




                    Message message = new Message(
                            currentMessage.getDisplayMessageBody(),
                            currentMessage.getDisplayOriginatingAddress(),
                            "ME",
                            new Date(), senderName
                    );
                    DataProvider.getInstance().addMessage(message);
                }
            }
        } catch (Exception e) {
            Log.e("SMS", "Exception: " + e);
        }
    }
}
