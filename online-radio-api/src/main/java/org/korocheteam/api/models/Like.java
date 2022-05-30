package org.korocheteam.api.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private Song song;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Like like = (Like) o;
		return id != null && Objects.equals(id, like.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
