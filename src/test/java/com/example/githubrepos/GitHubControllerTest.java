package com.example.githubrepos;

import com.example.githubrepos.controller.GitHubController;
import com.example.githubrepos.exception.UserNotFoundException;
import com.example.githubrepos.service.GitHubService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GitHubController.class)
public class GitHubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;

    @Test
    public void getRepositories_UserNotFound_ShouldReturn404() throws Exception {
        String userName = "nonExistentUser";
        String acceptHeader = "application/json";

        Mockito.when(gitHubService.getRepositoriesWithBranches(userName, acceptHeader))
                .thenThrow(new UserNotFoundException("User " + userName + " not found"));

        mockMvc.perform(get("/api/users/{username}/repos", userName)
                        .header("Accept", acceptHeader))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"User nonExistentUser not found\"}"));
    }
}
