package trek.visdrotech.com.trek_o_hunt.utils;

import com.google.firebase.messaging.RemoteMessage;
import com.hypertrack.lib.HyperTrackFirebaseMessagingService;
import com.hypertrack.lib.internal.transmitter.utils.Constants;

/**
 * Created by defcon on 09/06/18.
 */

public class MyFirebaseMessagingService extends HyperTrackFirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData() != null) {
            String sdkNotification = remoteMessage.getData().get(Constants.HT_SDK_NOTIFICATION_KEY);
            if (sdkNotification != null && sdkNotification.equalsIgnoreCase("true")) {
                /**
                 * HyperTrack notifications are received here
                 * Dont handle these notifications. This might end up in a crash
                 */
                return;
            }
        }
        // Handle your notifications here.
    }
}