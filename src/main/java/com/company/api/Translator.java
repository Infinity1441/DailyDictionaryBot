package com.company.api;

import com.company.entity.Definitions;
import com.company.entity.Meanings;
import com.company.entity.Transcript;
import com.company.entity.Word;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Translator {

    public static Word getEnglishWordsAndDefinitions(String word, String chatId) {
        try {
            HttpRequest postRequest = HttpRequest.newBuilder().uri(new URI("https://api.dictionaryapi.dev/api/v2/entries/en/" + word)).GET().build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(postResponse.body());

            Gson gson = new Gson();

            Word[] words = gson.fromJson(postResponse.body(), Word[].class);
            words[0].setChatId(chatId);
            return words[0];
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String audioTranscribe(String audio) {
        Gson gson = new Gson();
        Transcript transcript = new Transcript();

        transcript.setAudio_url(audio);
        String json = gson.toJson(transcript);
        System.out.println(json);


        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.assemblyai.com/v2/transcript"))
                    .header("Authorization", "5f226a5332e0465da294bbdf29f66ea5")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> postResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            transcript = gson.fromJson(postResponse.body(), Transcript.class);
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(new URI("https://api.assemblyai.com/v2/transcript/"+ transcript.getId()))
                    .header("Authorization", "5f226a5332e0465da294bbdf29f66ea5")
                    .build();

            while (true) {
                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
                transcript = gson.fromJson(response.body(), Transcript.class);
                System.out.println(transcript.getStatus());

                if (transcript.getStatus().equals("completed") || transcript.getStatus().equals("error")){
                    break;
                }
                Thread.sleep(3000);
            }
            return transcript.getText();

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUzbekTranslation(String text) {
        //TODO
        return null;
    }
}
