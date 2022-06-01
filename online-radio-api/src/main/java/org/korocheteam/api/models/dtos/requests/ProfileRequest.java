package org.korocheteam.api.models.dtos.requests;

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
    @Pattern(message = "Nickname must be between 4 and 20 letters", regexp = "^(|.{4,20})$")
    private String nickname;
    @Email
    private String email;
    @Pattern(message = "Password must be between 4 and 20 letters", regexp = "^(|.{4,20})$")
    private String password;
}
