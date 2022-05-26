package org.korocheteam.api.models.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.korocheteam.api.models.dtos.ValidationErrorDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValidationExceptionResponse {
	private List<ValidationErrorDto> errors;
}
