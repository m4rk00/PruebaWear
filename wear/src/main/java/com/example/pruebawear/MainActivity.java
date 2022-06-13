package com.example.pruebawear;

import android.app.Activity;
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

import com.example.pruebawear.databinding.ActivityMainBinding;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private Button wButton = null;
    private ActivityMainBinding binding;
    private Intent intent;
    private PendingIntent pendingIntent;

    private NotificationCompat.Builder notification;
    private NotificationCompat.Builder SegNotification;
    private NotificationManagerCompat nm;
    private NotificationCompat.WearableExtender wearableExtender;
    String idChannel = "Mi_canal";
    int idNotification = 001;

    private NotificationCompat.BigTextStyle bigTextStyle;

    String longText = "Without BigStyle, only a single line of text would be visible"
            + "any additional text would not appear directly in the notification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wButton = findViewById(R.id.wButton);

        intent = new Intent(MainActivity.this, MainActivity.class);

        nm = NotificationManagerCompat.from(MainActivity.this);

        wearableExtender = new NotificationCompat.WearableExtender();
        bigTextStyle = new NotificationCompat.BigTextStyle().bigText(longText);


        wButton.setOnClickListener(new View.OnClickListener() {

            public void start(View view) {
                Timer t = new Timer();
                TimerTask tt = new TimerTask() {
                    @Override
                    public void run() {
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        String name = "Segunda Notificación";
                        NotificationChannel notificationChannel = new NotificationChannel(idChannel, name, importance);
                        nm.createNotificationChannel(notificationChannel);
                        pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                        notification = new NotificationCompat.Builder(MainActivity.this, idChannel)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Otra Notificacion")
                                .setContentText("Segunda Notificación")
                                .extend(wearableExtender);
                        nm.notify(idNotification, notification.build());
                    }
                };
                t.schedule(tt, 7000);
            }

            @Override
            public void onClick(View view) {
                start(view);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                String name = "Notificacion";
                NotificationChannel notificationChannel= new NotificationChannel(idChannel, name, importance);
                nm.createNotificationChannel(notificationChannel);
                pendingIntent = PendingIntent.getActivity(MainActivity.this, 0,intent,0);

                notification = new NotificationCompat.Builder(MainActivity.this, idChannel)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Notificación")
                        .setContentText("Notificación uno")
                        .extend(wearableExtender);

                nm.notify(idNotification, notification.build());

            }
        });

    }
}
