package com.example.healthylife;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    private EditText inputMessage;
    private String mode = "food"; // default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMessage = findViewById(R.id.inputMessage);

        // BACA MODE DARI INTENT
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "food";

        inputMessage.setOnEditorActionListener((v, actionId, event) -> {
            String text = inputMessage.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(this, "Tulis sesuatu dulu", Toast.LENGTH_SHORT).show();
                return false;
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

            return true;
        });
    }
}
