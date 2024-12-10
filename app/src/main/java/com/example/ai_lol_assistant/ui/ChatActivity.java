package com.example.ai_lol_assistant.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ai_lol_assistant.R;
import com.example.ai_lol_assistant.model.ChatRequest;
import com.example.ai_lol_assistant.model.ChatResponse;
import com.example.ai_lol_assistant.network.OpenAIClient;
import com.example.ai_lol_assistant.network.OpenAIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private OpenAIService openAIService;
    private TextView tvChatOutput;
    private EditText etChatInput;
    private Button btnSendChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // UI 초기화
        tvChatOutput = findViewById(R.id.tvChatOutput);
        etChatInput = findViewById(R.id.etChatInput);
        btnSendChat = findViewById(R.id.btnSendChat);

        // OpenAI API 초기화
        openAIService = OpenAIClient.getClient().create(OpenAIService.class);

        // 전송 버튼 클릭 이벤트
        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = etChatInput.getText().toString().trim();
                if (!userMessage.isEmpty()) {
                    sendMessageToChatGPT(userMessage);
                }
            }
        });
    }

    private void sendMessageToChatGPT(String message) {
        tvChatOutput.append("\n[사용자]: " + message);
        etChatInput.setText(""); // 입력창 초기화

        // ChatGPT에 요청 생성
        ChatRequest chatRequest = new ChatRequest("text-davinci-003", message, 150);

        openAIService.getChatResponse(chatRequest).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String chatGPTResponse = response.body().getChoices().get(0).getText().trim();
                    tvChatOutput.append("\n[ChatGPT]: " + chatGPTResponse);
                } else {
                    Log.e(TAG, "API 응답 실패: " + response.message());
                    tvChatOutput.append("\n[Error]: ChatGPT 응답을 가져올 수 없습니다.");
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Log.e(TAG, "API 호출 실패", t);
                tvChatOutput.append("\n[Error]: 네트워크 오류가 발생했습니다.");
            }
        });
    }
}
