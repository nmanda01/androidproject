package com.example.mysms2;

/**
 * Created by paradroid on 12/1/15.
 */
import android.util.Log;

import java.util.Date;

/**
 * Created by sdavies on 09/01/2014.
 */

public class Message {

    private String Content;
    private String Sender;
    private String Recipient;
    private Date Time;
    private String Name;


    static final String DEBUG_TAG = "AppChat Logging";

    public Message(String content, String sender, String recipient, Date time, String name) {
        Content = content;
        Sender = sender;
        Recipient = recipient;
        Time = time;
        Name = name;


        //Log.w(DEBUG_TAG, "phoneNumberEntry is Null");
    }

    public void sendToDataProvider(Message msg){
        DataProvider.getInstance().addMessage(msg);
    }

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

    public void setTime(Date time) {
        Time = time;
    }

    @Override
    public String toString() {
        return getContent() + "  -  " + getTime().toString();
    }
}