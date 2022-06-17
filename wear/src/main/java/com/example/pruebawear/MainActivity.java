package com.example.pruebawear;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.example.pruebawear.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private Button wButton = null;
    private ActivityMainBinding binding;
    private Intent intent,llamadaIntent;
    private PendingIntent pendingIntent;
    private NotificationCompat.Builder notification;
    private NotificationManagerCompat nm;
    private NotificationCompat.WearableExtender wearableExtender;

    String idChannel = "Mi_Canal";
    public static final int idNotification = 001;
    private  NotificationCompat.BigTextStyle bigTextStyle;
    String longText = "Without BigStyle, only a single line of text would be visible" +
            "Any additional text would not appear directly in the notification";

    //inputType for Android Direct Reply
    //respuesta desde el reloj
    private static final String KEY_TEXT_REPLY = "key_text_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wButton= findViewById(R.id.wButton);
        intent = new Intent(MainActivity.this, MainActivity.class);
        nm = NotificationManagerCompat.from(MainActivity.this);
        wearableExtender = new NotificationCompat.WearableExtender();
        bigTextStyle = new NotificationCompat.BigTextStyle().bigText(longText);
        wButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Agregar activity de llamada
                llamadaIntent = new Intent(MainActivity.this,LlamadaActivity.class);
                llamadaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent LlamadaPendingIntent = PendingIntent.getActivity(MainActivity.this,0,llamadaIntent,PendingIntent.FLAG_ONE_SHOT);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                String name = "Notification";
                NotificationChannel notificationChannel = new NotificationChannel(idChannel, name, importance);

                nm.createNotificationChannel(notificationChannel);

                pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                // Agregar Respuesta rapida por mensaje
                String replyLabel = getResources().getString(R.string.response);
                RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                        .setLabel(replyLabel)
                        .build();


                pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                // Mensaje
                NotificationCompat.Action action =
                        new NotificationCompat.Action.Builder(android.R.drawable.ic_dialog_email,
                                getString(R.string.action), pendingIntent)
                                .addRemoteInput(remoteInput)
                                .build();

                // Llamada
                notification = new NotificationCompat.Builder( MainActivity.this,idChannel)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Multi acción")
                        .setContentText("Hola! esta es una notificación multiaccion")
                        .addAction(action)
                        .setContentIntent(pendingIntent)
                        .addAction(android.R.drawable.sym_action_call,
                                getString(R.string.llamar), LlamadaPendingIntent);

                nm.notify(idNotification,notification.build());


            }
        });
    }
}