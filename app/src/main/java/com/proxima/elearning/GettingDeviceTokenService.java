package com.proxima.elearning;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class GettingDeviceTokenService extends FirebaseInstanceIdService {
    public String DeviceToken;
    @Override
    public void onTokenRefresh() {
        DeviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Device Token", DeviceToken);
        FirebaseMessaging.getInstance().subscribeToTopic("Notification");
    }
}
