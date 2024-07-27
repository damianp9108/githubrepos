package com.example.githubrepos.client;

import com.example.githubrepos.entity.GitHubBranch;
import com.example.githubrepos.entity.GitHubRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "githubClient", url = "https://api.github.com")
public interface GitHubClient {

    @GetMapping("/users/{username}/repos")
    List<GitHubRepository> getRepositories(@PathVariable("username") String username,
                                           @RequestHeader("Accept") String accept);

    @GetMapping("/repos/{owner}/{repo}/branches")
    List<GitHubBranch> getBranches(@PathVariable("owner") String owner,
                                   @PathVariable("repo") String repo,
                                   @RequestHeader("Accept") String accept);
}
