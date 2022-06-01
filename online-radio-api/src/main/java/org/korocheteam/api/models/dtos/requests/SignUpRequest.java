package org.korocheteam.api.models.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "params for sign up")
public class SignUpRequest {

    @ApiModelProperty(value = "nickname must be more than 4 characters and less than 20, also unique", example = "username")
    @Size(min = 4, max = 20)
    @NotBlank
    private String nickname;

    @ApiModelProperty(value = "email must be in correct format", example = "username@mail.ru")
    @Email
    private String email;

    @ApiModelProperty(value = "password must be more than 4 characters and less than 20", example = "qwerty")
    @Size(min = 4, max = 20)
    @NotBlank
    private String password;
}
