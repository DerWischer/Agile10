package adp.group10.roomates.backend;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.LinkedList;

/**
 * Created by calle on 2017-05-04.
 */

public class GroupNotificationSubscriber {
    private static FirebaseMessaging messager = FirebaseMessaging.getInstance();
    private static LinkedList<String> subscribedTopics = new LinkedList<String>();

    public static void Subscribe(String group) {
        subscribedTopics.add(group);
        messager.subscribeToTopic(group);
    }

    public static void UnSubscribe(String group) {
        subscribedTopics.remove(group);
        messager.unsubscribeFromTopic(group);
    }

    public static void UnSubscribeAll() {
        for (String group : subscribedTopics) {
            messager.unsubscribeFromTopic(group);
        }
        subscribedTopics = new LinkedList<>();
    }


}
