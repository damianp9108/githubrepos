package com.example.githubrepos.controller;

import com.example.githubrepos.model.GitHubBranch;
import com.example.githubrepos.model.GitHubRepository;
import com.example.githubrepos.service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<?> getRepositories(@PathVariable("username") String username,
                                             @RequestHeader("Accept") String accept) {
        try {
            List<GitHubRepository> repositories = gitHubService.getRepositories(username, accept);
            return new ResponseEntity<>(repositories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User not found"),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/repos/{owner}/{repo}/branches")
    public ResponseEntity<List<GitHubBranch>> getBranches(@PathVariable("owner") String owner,
                                                          @PathVariable("repo") String repo,
                                                          @RequestHeader("Accept") String accept) {
        List<GitHubBranch> branches = gitHubService.getBranches(owner, repo, accept);
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    public static class ErrorResponse {
        private int status;
        private String message;

        public ErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

