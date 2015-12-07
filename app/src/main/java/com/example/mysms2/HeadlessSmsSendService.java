package com.example.mysms2;

/**
 * Created by paradroid on 12/6/15.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class HeadlessSmsSendService extends Service {
    public HeadlessSmsSendService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
