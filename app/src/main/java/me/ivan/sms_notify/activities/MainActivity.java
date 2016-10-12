package me.ivan.sms_notify.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import me.ivan.sms_notify.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView to_email;
    private TextView tv_receiver;
    private String to;
    private String from;
    private String password;
    private Button on_off;
    //status
    private boolean listening;
    private boolean setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        updateView();

        listen();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateData() {
        to = sharedPreferences.getString("to", "");
        from =  sharedPreferences.getString("from", "");
        password = sharedPreferences.getString("password", "");
        setup = to.length()>0 && from.length()>0 && password.length()>0;
        listening = sharedPreferences.getBoolean("listening",false);
    }

    private void updateView() {
        updateData();
        //update text view
        if (setup) {
            if (to_email != null) {
                to_email.setVisibility(View.VISIBLE);
                to_email.setText(to);
            }
            if (tv_receiver != null) {
                tv_receiver.setVisibility(View.VISIBLE);
            }
        } else {
            if (to_email != null) {
                to_email.setVisibility(View.GONE);
            }
            if (tv_receiver != null) {
                tv_receiver.setVisibility(View.GONE);
            }
        }
        //update button
        if (listening) {
            if (on_off != null) {
                on_off.setBackgroundResource(R.color.colorPrimaryDark);
                on_off.setText(R.string.stop_listen);
            }
        } else {
            if (on_off != null) {
                on_off.setBackgroundResource(R.color.colorPrimary);
                on_off.setText(R.string.start_listen);
            }
        }
    }

    private void init() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        to_email = (TextView) findViewById(R.id.to_email);
        tv_receiver = (TextView) findViewById(R.id.tv_email_receiver);
        on_off = (Button) findViewById(R.id.on_off);
    }

    private void listen() {
        OnClickListener listener_to_setting = new OnClickListener() {
            @Override
            public void onClick(View v) {
                goSetting();
            }
        };

        if (to_email != null) {
            to_email.setOnClickListener(listener_to_setting);
        }
        if (tv_receiver != null) {
            tv_receiver.setOnClickListener(listener_to_setting);
        }

        if (on_off != null) {
            on_off.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateData();

                    if (!setup) {
                        goSetting();
                        return;
                    }

                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    if (listening) {
                        edit.putBoolean("listening", false);
                        Toast.makeText(MainActivity.this,getString(R.string.stoped),Toast.LENGTH_SHORT).show();
                    } else {
                        edit.putBoolean("listening",true);
                        Toast.makeText(MainActivity.this,getString(R.string.started),Toast.LENGTH_SHORT).show();
                    }
                    edit.apply();

                    updateView();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                goSetting();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void goSetting() {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

}
