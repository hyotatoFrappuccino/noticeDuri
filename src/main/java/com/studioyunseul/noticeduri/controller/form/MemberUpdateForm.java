package com.studioyunseul.noticeduri.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdateForm {

    @NotNull
    private Long id;

    @NotEmpty
    private String name;

    private String password;

    @NotNull
    private Long universityId;

    @NotNull
    private Long majorId;

}
