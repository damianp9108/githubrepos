package com.example.githubrepos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GitHubRepositoryWithBranches {

        private String repositoryName;

        private String ownerLogin;

        private List<Branch> branches;
}
