package we.travel.slack;

import okhttp3.*;

import java.io.IOException;

public class SlackSender {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String WEBHOOK_URL = System.getenv("WEBHOOK_URL");
    public static void sendSimpleMessage(String message) {
        String json = String.format("{\"text\": \"%s\"}",
                message.replace("\"", "\\\""));
        RequestBody body = RequestBody.create(
                json,
                MediaType.parse("application/json; charset=utf-8")
        );
        Request request = new Request.Builder()
                .url(WEBHOOK_URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ Mensagem enviada com sucesso!");
            } else {
                System.out.println("❌ Erro: " + response.code() + " - " + response.message());
            }
        } catch (IOException e) {
            System.err.println("❌ Erro de conexão: " + e.getMessage());
        }
    }
}
