package com.example.githubrepos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GitHubBranch {

    private String name;

    @JsonProperty("commit")
    private Commit commit;
}
