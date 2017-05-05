package adp.group10.roomates.backend;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by calle on 2017-05-05.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage message) {
        //TODO
        String image = message.getNotification().getIcon();
        String title = message.getNotification().getTitle();
        String text = message.getNotification().getBody();
        String sound = message.getNotification().getSound();


        //TODO On message receive

        Log.v("TAGTAG", title);
    }
}
