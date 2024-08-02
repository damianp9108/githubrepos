package com.example.githubrepos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitHubBranch {

    private String name;

    @JsonProperty("commit")
    private Commit commit;
}
