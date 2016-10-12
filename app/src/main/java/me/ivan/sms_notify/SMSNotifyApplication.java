package me.ivan.sms_notify;

import android.app.Application;
import android.preference.PreferenceManager;

/**
 * Created by ivan.
 */
public class SMSNotifyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.setDefaultValues(this,R.xml.default_settings,false);
    }
}
