package com.studioyunseul.noticeduri.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @NotNull
    private Long universityId;

    @NotNull
    private Long majorId;

}
