package org.korocheteam.api.models.dtos.requests;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileRequest {

    @ApiModelProperty(value = "nickname must be more than 4 characters and less than 20, also unique", example = "username")
    @Pattern(message = "Nickname must be between 4 and 20 letters", regexp = "^(|.{4,20})$")
    private String nickname;

    @ApiModelProperty(value = "email must be in correct format", example = "username@mail.ru")
    @Email
    private String email;

    @ApiModelProperty(value = "password must be more than 4 characters and less than 20", example = "qwerty")
    @Pattern(message = "Password must be between 4 and 20 letters", regexp = "^(|.{4,20})$")
    private String password;
}
