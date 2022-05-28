package org.korocheteam.api.models.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {
    @Size(min = 4, max = 20)
    private String nickname;

    @Email
    private String email;

    @Size(min = 4, max = 20)
    private String password;
}
