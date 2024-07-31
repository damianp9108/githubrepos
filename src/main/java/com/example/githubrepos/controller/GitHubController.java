package com.example.githubrepos.controller;

import com.example.githubrepos.dto.GitHubRepositoryWithBranches;
import com.example.githubrepos.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<List<GitHubRepositoryWithBranches>> getRepositories(@PathVariable("username") String username,
                                                                              @RequestHeader("Accept") String accept) {
        List<GitHubRepositoryWithBranches> repositories = gitHubService.getRepositoriesWithBranches(username, accept);
        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }
}

