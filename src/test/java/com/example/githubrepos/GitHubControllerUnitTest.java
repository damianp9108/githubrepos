package com.example.githubrepos;

import com.example.githubrepos.client.GitHubClient;
import com.example.githubrepos.entity.GitHubBranch;
import com.example.githubrepos.entity.GitHubRepository;
import com.example.githubrepos.exception.UserNotFoundException;
import com.example.githubrepos.service.GitHubService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class GitHubControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private GitHubService gitHubService;

    @MockBean
    private GitHubClient gitHubClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static final String USERNAME = "mojombo";
    private static final String OWNER = "mojombo";
    private static final String REPO_30DAYS = "30daysoflaptops.github.io";
    private static final String REPO_ASTEROIDS = "asteroids";
    private static final String REPO_BERT = "bert";
    private static final String REPO_BERT_ERL = "bert.erl";
    private static final String REPO_BERT_RPC = "bertrpc";
    private static final String ACCEPT_HEADER = "application/json";


    @Test
    public void shouldReturnDataEqualToMocked_dto_jsonFile() throws Exception {

        // Load mock data from JSON files
        String reposJson = new String(Files.readAllBytes(Paths.get("src/test/resources/repos.json")));

        String repo30daysJson = new String(Files.readAllBytes(Paths.get("src/test/resources/30daysoflaptops.github.io.json")));
        String repoAsteroidsJson = new String(Files.readAllBytes(Paths.get("src/test/resources/asteroids.json")));
        String repoBertJson = new String(Files.readAllBytes(Paths.get("src/test/resources/bert.json")));
        String repoBertErlJson = new String(Files.readAllBytes(Paths.get("src/test/resources/bert.erl.json")));
        String repoBertRpcJson = new String(Files.readAllBytes(Paths.get("src/test/resources/bertrpc.json")));

        String dtoJson = new String(Files.readAllBytes(Paths.get("src/test/resources/dto.json")));


        // Mock GitHub API responses
        when(gitHubClient.getRepositories(USERNAME, ACCEPT_HEADER)).thenReturn(getGitHubRepositoryEntity(reposJson));
        when(gitHubClient.getBranches(OWNER, REPO_30DAYS, ACCEPT_HEADER)).thenReturn(getGitHubBranchEntity(repo30daysJson));
        when(gitHubClient.getBranches(OWNER, REPO_ASTEROIDS, ACCEPT_HEADER)).thenReturn(getGitHubBranchEntity(repoAsteroidsJson));
        when(gitHubClient.getBranches(OWNER, REPO_BERT, ACCEPT_HEADER)).thenReturn(getGitHubBranchEntity(repoBertJson));
        when(gitHubClient.getBranches(OWNER, REPO_BERT_ERL, ACCEPT_HEADER)).thenReturn(getGitHubBranchEntity(repoBertErlJson));
        when(gitHubClient.getBranches(OWNER, REPO_BERT_RPC, ACCEPT_HEADER)).thenReturn(getGitHubBranchEntity(repoBertRpcJson));


        // Perform GET request and assert response
        mockMvc.perform(get("/api/users/{username}/repos", USERNAME)
                        .header("Accept", ACCEPT_HEADER))
                .andExpect(status().isOk())
                .andExpect(content().json(dtoJson));
    }

    private List<GitHubRepository> getGitHubRepositoryEntity(String jsonFromResponse) throws JsonProcessingException {
        return mapper.readValue(jsonFromResponse, new TypeReference<List<GitHubRepository>>() {});
    }

    private List<GitHubBranch> getGitHubBranchEntity(String jsonFromResponse) throws JsonProcessingException {
        return mapper.readValue(jsonFromResponse, new TypeReference<List<GitHubBranch>>() {});
    }


    @Test
    public void getRepositories_UserNotFound_ShouldReturn404() throws Exception {
        String userName = "nonExistentUser";

        when(gitHubService.getRepositoriesWithBranches(userName, ACCEPT_HEADER))
                .thenThrow(new UserNotFoundException("User " + userName + " not found"));

        mockMvc.perform(get("/api/users/{username}/repos", userName)
                        .header("Accept", ACCEPT_HEADER))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"User nonExistentUser not found\"}"));
    }
}
