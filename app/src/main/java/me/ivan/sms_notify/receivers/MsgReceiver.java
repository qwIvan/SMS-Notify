package me.ivan.sms_notify.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

import javax.mail.MessagingException;

import me.ivan.sms_notify.services.SMTPService;

/**
 * Created by ivan.
 */
public class MsgReceiver extends BroadcastReceiver{
    
    private SMTPService smtpService;
    private SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sharedPreferences.getBoolean("listening", true)) {
            return;
        }

        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        if (messages!=null) {
            for (Object object:messages){
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
                if (sms!=null) {
                    String displayMessageBody = sms.getDisplayMessageBody();
                    String displayOriginatingAddress = sms.getDisplayOriginatingAddress();
//                    System.out.println(displayMessageBody);
//                    Toast.makeText(context,displayMessageBody,Toast.LENGTH_LONG).show();

                    this.send(context, displayOriginatingAddress +"发来的短信：",displayMessageBody);
                }
            }
        }
    }

    private void send(Context context, final String title, final String content) {
        String from = sharedPreferences.getString("from", "");
        String to = sharedPreferences.getString("to", "");
        String password = sharedPreferences.getString("password", "");
        String host = sharedPreferences.getString("host", "smtp.qq.com");
        String port = sharedPreferences.getString("port","587");
        boolean tls = sharedPreferences.getBoolean("tls", true);

        smtpService = new SMTPService(from,tls,host,Integer.valueOf(port),password,to);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    smtpService.send(title,content);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    //// TODO: 16-4-26
                }
                return null;
            }
        }.execute();
    }

}
