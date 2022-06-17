package com.example.pruebawear;

import static  com.example.pruebawear.MainActivity.idNotification;
import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.pruebawear.databinding.ActivityLlamadaBinding;

public class LlamadaActivity extends Activity {

    private TextView mTextView;
    private ActivityLlamadaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamada);

        binding = ActivityLlamadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;

        NotificationManager notifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifMan.cancel(idNotification);

    }
}