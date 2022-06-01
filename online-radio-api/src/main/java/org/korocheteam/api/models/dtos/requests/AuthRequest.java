package org.korocheteam.api.models.dtos.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("request for authentication")
public class AuthRequest {

    @ApiModelProperty(example = "username@mail.ru")
    private String email;

    @ApiModelProperty(example = "qwerty")
    private String password;
}
