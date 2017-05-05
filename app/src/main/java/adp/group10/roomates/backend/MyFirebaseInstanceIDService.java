package adp.group10.roomates.backend;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by calle on 2017-05-05.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {



    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


        //TODO  On token refresh

    }

}
