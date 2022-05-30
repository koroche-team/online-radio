package org.korocheteam.api.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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
	private String hashPassword;

	private String token;

	private Integer score;

	@OneToMany(mappedBy = "account")
	@ToString.Exclude
	private List<Like> likes;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Account account = (Account) o;
		return id != null && Objects.equals(id, account.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
