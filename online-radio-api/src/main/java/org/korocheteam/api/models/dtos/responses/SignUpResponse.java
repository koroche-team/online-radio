package org.korocheteam.api.models.dtos.responses;

import lombok.*;
import org.korocheteam.api.models.dtos.AccountDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponse {
    private AccountDto account;
}
