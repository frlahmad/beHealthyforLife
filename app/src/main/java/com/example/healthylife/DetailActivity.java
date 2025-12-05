package com.example.healthylife;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TextView txtNutrition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        txtNutrition = findViewById(R.id.txtNutrition);

        String foodName = getIntent().getStringExtra("food_name");

        if (foodName != null && !foodName.isEmpty()) {
            txtNutrition.setText("Menganalisis: " + foodName + " ...");
            sendToSenopatiAI(foodName);
        } else {
            txtNutrition.setText("Tidak ada makanan yang dikirim dari ChatActivity.");
        }
    }

    private void sendToSenopatiAI(String foodName) {

        SenopatiApi api = RetroFitClient.getApi();

        List<SenopatiRequest.Message> messages = new ArrayList<>();
        messages.add(new SenopatiRequest.Message(
                "user",
                "Analisis kandungan gizi dari makanan berikut: " + foodName +
                        ". Jelaskan kalori, nutrisi, manfaat dalam Bahasa Indonesia."
        ));

        SenopatiRequest req = new SenopatiRequest(
                foodName,
                messages,
                "You are a helpful Indonesian nutrition assistant"
        );

        api.sendChat(req).enqueue(new Callback<SenopatiResponse>() {
            @Override
            public void onResponse(Call<SenopatiResponse> call, Response<SenopatiResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    SenopatiResponse res = response.body();

                    if (res.isSuccess() && res.getData() != null) {

                        // 🔥🔥 HAPUS SIMBOL "*"
                        String cleanText = res.getData().getReply()
                                .replace("*", "")
                                .replace("**", "");

                        txtNutrition.setText(cleanText);

                    } else {
                        txtNutrition.setText("Error dari server: " + res.getError());
                    }
                } else {
                    txtNutrition.setText("Response tidak sukses");
                }
            }

            @Override
            public void onFailure(Call<SenopatiResponse> call, Throwable t) {
                txtNutrition.setText("Gagal terhubung: " + t.getMessage());
            }
        });

    }
}
