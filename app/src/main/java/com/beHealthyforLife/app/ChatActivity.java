package com.beHealthyforLife.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    private EditText inputMessage;
    private Button btnSend;
    private String mode = "food"; // default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMessage = findViewById(R.id.inputMessage);
        btnSend = findViewById(R.id.btnSend);

        // BACA MODE DARI INTENT
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "food";

        // ================================
        //        EVENT TOMBOL KIRIM
        // ================================
        btnSend.setOnClickListener(v -> sendMessage());

        // Tetap dukung Enter di keyboard
        inputMessage.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
    }

    private void sendMessage() {
        String text = inputMessage.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "Tulis sesuatu dulu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mode.equals("food")) {
            // MODE ANALISIS MAKANAN
            Intent intent = new Intent(ChatActivity.this, DetailActivity.class);
            intent.putExtra("food_name", text);
            startActivity(intent);

        } else if (mode.equals("general")) {
            // MODE CHAT BEBAS SENOPATI
            Intent intent = new Intent(ChatActivity.this, SenopatiApi.class);
            intent.putExtra("query", text);
            startActivity(intent);
        }
    }
}
