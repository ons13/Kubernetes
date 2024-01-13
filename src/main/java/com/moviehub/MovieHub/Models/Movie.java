package com.moviehub.MovieHub.Models;

import com.moviehub.MovieHub.Configuration.StringToSetDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.attoparser.dom.Text;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    private String description;
    private String name;
    private Integer opened;
    @Column(length = 500)
    private String image;

    @ElementCollection
    @CollectionTable(name = "movie_genders", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "gender")
    @JsonDeserialize(using = StringToSetDeserializer.class) // Add this annotation
    private Set<String> genders = new HashSet<>();

    @ManyToMany(mappedBy = "favoritemovies")
    @JsonIgnoreProperties("favoritemovies")
    private Set<User> favoritedBy = new HashSet<>();

    // @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    // private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "movie")
    private List<Comment> comments;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<>();

    public Movie() {
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    public void addFavoritedBy(User user) {
        favoritedBy.add(user);
        user.getFavoritemovies().add(this);
    }

    public void removeFavoritedBy(User user) {
        favoritedBy.remove(user);
        user.getFavoritemovies().remove(this);
    }
}
