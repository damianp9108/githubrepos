package com.example.githubrepos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@ToString
public final class GitHubRepositoryWithBranches {

    private final String repositoryName;

    private final String ownerLogin;

    private final List<Branch> branches;

    private GitHubRepositoryWithBranches(String repositoryName, String ownerLogin, List<Branch> branches) {
        this.repositoryName = repositoryName;
        this.ownerLogin = ownerLogin;
        this.branches = Collections.unmodifiableList(branches);
    }
}
