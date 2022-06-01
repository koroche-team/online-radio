package org.korocheteam.api.models.dtos.responses;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.korocheteam.api.models.dtos.AccountDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "response for sign up")
public class SignUpResponse {
    @ApiModelProperty(value = "dto of account")
    private AccountDto account;
}
