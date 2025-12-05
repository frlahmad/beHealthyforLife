package com.example.healthylife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerWorkout;
    List<WorkoutModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // ======================
        // 1. TOMBOL CEK KALORI
        // ======================
        Button btnOpenScan = findViewById(R.id.btnOpenScan);
        btnOpenScan.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ChatActivity.class))
        );

        // ======================
        // 2. TANGGAL OTOMATIS
        // ======================
        TextView txtTodayDate = findViewById(R.id.txtTodayDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        txtTodayDate.setText(sdf.format(new Date()));

        Button btnWorkout = findViewById(R.id.btnWorkout);
        btnWorkout.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, WorkoutActivity.class));
        });

    }
}
