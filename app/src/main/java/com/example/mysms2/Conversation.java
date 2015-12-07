package com.example.mysms2;

/**
 * Created by paradroid on 12/1/15.
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.Context;

/**
 * Created by sdavies on 09/01/2014.
 */
public class Conversation implements  Comparable<Conversation>{

    private String sender;
    private List<Message> messages;
    private Date newest;
    String senderName;

    public Conversation(String sender){
        this.sender = sender;
        checkContacts();
        this.messages = new ArrayList<Message>();
    }

    public Conversation(String sender, List<Message> messages) {
        this.sender = sender;
        checkContacts();
        this.messages = messages;
    }



    public void checkContacts(){
        SmsContactManager manager = new SmsContactManager();
        this.senderName = manager.fetchName(MainActivity.context, sender);
    }

    public void setNewest(){

    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getSender() {
        return sender;
    }

    public void addMessage(Message message) {
        newest = message.getTime();
        this.messages.add(message);
    }



    @Override
    public String toString() {
        if(senderName != null){
            return senderName;
        }
        else{
            return sender;
        }

    }

    @Override
    public int compareTo(Conversation another) {
        return newest.compareTo(another.newest);

    }


}
