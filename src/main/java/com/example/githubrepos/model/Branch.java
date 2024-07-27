package com.example.githubrepos.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Branch {

    String name;

    String lastCommitSha;
}
