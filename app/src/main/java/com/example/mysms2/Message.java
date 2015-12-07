package com.example.mysms2;

/**
 * Created by paradroid on 12/1/15.
 */
import android.util.Log;

import java.util.Date;
import android.database.Cursor;

/**
 * Created by sdavies on 09/01/2014.
 */

public class Message {

    private String Content;
    private String Sender;
    private String Recipient;
    private Date Time;
    //private String Name;
    //SmsContactManager manager = new SmsContactManager();


    static final String DEBUG_TAG = "AppChat Logging";

    public Message(String content, String sender, String recipient, Date time) {
        Content = content;
        Sender = sender;
        Recipient = recipient;
        Time = time;
        //Name = getName();


        //Log.w(DEBUG_TAG, "phoneNumberEntry is Null");
    }


    //////  ?????/ No longer needed?
    public void sendToDataProvider(Message msg){
        DataProvider.getInstance().addMessage(msg);
    }
    //////

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }

    public Date getTime() {
        return Time;
    }



    /*
    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
    public String getName(){
        Name = manager.fetchName(getApplicationContext().context, Recipient);
        Log.v(DEBUG_TAG, "Message: name:  " + Name);
        return Name; }

    public void setName(String n){Name = n;}
    */






    public void setTime(Date time) {
        Time = time;
    }

    @Override
    public String toString() {
        return getContent() + "  -  " + getTime().toString();
    }
}