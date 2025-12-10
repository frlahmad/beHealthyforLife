package com.beHealthyforLife.app;

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

                        String raw = res.getData().getReply();

                        // ----------------------------
                        // 1️⃣ CLEAN MARKDOWN & SYMBOLS
                        // ----------------------------
                        String cleaned = raw
                                .replaceAll("[*_`#>|-]", " ")
                                .replaceAll("\\d+\\.", " ")
                                .replaceAll("\\s{2,}", " ")
                                .trim();

                        // ----------------------------
                        // 2️⃣ AUTO PARAGRAPHING
                        //    - Pisahkan berdasarkan titik dua (:)
                        //    - Pisahkan berdasarkan kalimat
                        // ----------------------------
                        String[] splitByTopic = cleaned.split(":");

                        StringBuilder finalText = new StringBuilder();

                        for (String block : splitByTopic) {

                            // Pecah jadi beberapa kalimat
                            String[] sentences = block.trim().split("(?<=[.!?])\\s+");

                            int counter = 0;
                            StringBuilder paragraph = new StringBuilder();

                            for (String sentence : sentences) {
                                if (sentence.trim().isEmpty()) continue;

                                paragraph.append(sentence.trim()).append(" ");
                                counter++;

                                // setiap 2 kalimat → buat paragraf baru
                                if (counter >= 2) {
                                    finalText.append(paragraph.toString().trim()).append("\n\n");
                                    paragraph.setLength(0);
                                    counter = 0;
                                }
                            }

                            // masukkan sisa kalimat jika ada
                            if (paragraph.length() > 0) {
                                finalText.append(paragraph.toString().trim()).append("\n\n");
                            }
                        }

                        txtNutrition.setText(finalText.toString().trim());

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
