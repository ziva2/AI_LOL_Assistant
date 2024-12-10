package com.example.ai_lol_assistant.network;

import com.example.ai_lol_assistant.model.ChatRequest;
import com.example.ai_lol_assistant.model.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer YOUR_API_KEY" // Replace with your API key
    })
    @POST("v1/completions")
    Call<ChatResponse> getChatResponse(@Body ChatRequest request);
}
