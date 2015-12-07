package com.example.mysms2;

/**
 * Created by paradroid on 12/1/15.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by sdavies on 09/01/2014.
 */
public class DataProvider extends Observable {
    private static DataProvider ourInstance = new DataProvider();

    public static DataProvider getInstance() {
        return ourInstance;
    }

    private Map<String, Conversation> conversationMap;
    private List<Conversation> conversationList;

    private DataProvider() {
        // Create the store
        conversationMap = new HashMap<String, Conversation>();
        conversationList = new ArrayList<Conversation>();

        // Create some sample data
        /*
        addMessage(new Message("content1", "sender1", "recipient", new Date()));
        addMessage(new Message("content2", "sender1", "recipient", new Date()));
        addMessage(new Message("content3", "sender2", "recipient", new Date()));
        addMessage(new Message("content4", "sender3", "recipient", new Date()));
        addMessage(new Message("content5", "sender4", "recipient", new Date()));
        addMessage(new Message("content6", "sender4", "recipient", new Date()));
        addMessage(new Message("content7", "sender4", "recipient", new Date()));
        addMessage(new Message("content8", "sender4", "recipient", new Date()));
        addMessage(new Message("content9", "sender4", "recipient", new Date()));
        */
    }

    public void addMessage(Message message) {
        if(conversationMap.containsKey(message.getSender())) {
            // Can add the message to an existing conversation
            conversationMap.get(message.getSender()).addMessage(message);
        } else {
            // Need to create a new conversation
            Conversation conversation = new Conversation(message.getSender());
            conversation.addMessage(message);
            conversationMap.put(message.getSender(), conversation);
            conversationList.add(conversation);
        }
        // Ensure that everything gets updated
        setChanged();
        notifyObservers();
    }

    public List<Conversation> getConversations() {
        return conversationList;
    }

    public Map<String, Conversation> getConversationMap() {
        return conversationMap;
    }
}