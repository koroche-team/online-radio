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
public class Song {
	// enum used to make genre selection strict and avoid misspells
	public enum Genre {
		VAPORWAVE, BREAKS
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String artist;

	private String title;

	private String album;

	private String cover;

	private String path;

	@Enumerated(value = EnumType.STRING)
	private Genre genre;

	private String hash;

	@OneToMany(mappedBy = "song")
	@ToString.Exclude
	private List<SongLike> likes;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Song song = (Song) o;
		return id != null && Objects.equals(id, song.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
