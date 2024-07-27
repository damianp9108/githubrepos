package com.example.githubrepos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GitHubRepository {

    private String name;

    @JsonProperty("owner")
    private Owner owner;

    @JsonProperty("fork")
    private boolean isFork;
}