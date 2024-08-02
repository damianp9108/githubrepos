package com.example.githubrepos;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testGitHubAPIIntegration() throws IOException {
        // Step 1: Perform GET request to GitHub API
        String githubUrl = "https://api.github.com/repositories";
        Request githubRequest = new Request.Builder()
                .url(githubUrl)
                .build();

        try (Response githubResponse = client.newCall(githubRequest).execute()) {
            if (!githubResponse.isSuccessful()) throw new IOException("Unexpected code " + githubResponse);

            assert githubResponse.body() != null;
            String responseBody = githubResponse.body().string();

            // Parse JSON response to get a random username
            // Assuming response is a JSON array and contains a "owner" object with a "login" field
            JsonArray repositories = JsonParser.parseString(responseBody).getAsJsonArray();
            Random random = new Random();
            JsonObject randomRepo = repositories.get(random.nextInt(repositories.size())).getAsJsonObject();
            String randomUsername = randomRepo.getAsJsonObject("owner").get("login").getAsString();

            // Step 2: Perform GET request to your Controller using the random username
            String appUrl = "/api/users/" + randomUsername + "/repos";
            ResponseEntity<String> appResponse = restTemplate.getForEntity(appUrl, String.class);

            // Validate the response
            assertThat(appResponse.getStatusCode()).isEqualTo(HttpStatus.OK);


            System.out.println(appResponse.getBody());
        }
    }
}
