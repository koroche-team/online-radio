package org.korocheteam.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
	public enum Role {
		USER, ADMIN
	};

	public enum State {
		NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(value = EnumType.STRING)
	private Role role;

	@Enumerated(value = EnumType.STRING)
	private State state;

	private String email;
	private String nickname;
	private String password;

	private String token;
}
