package me.ivan.sms_notify.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import me.ivan.sms_notify.R;

/**
 * Created by ivan.
 */
public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.default_settings);
    }

}
