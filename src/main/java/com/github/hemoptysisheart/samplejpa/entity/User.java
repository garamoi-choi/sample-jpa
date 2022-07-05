package com.github.hemoptysisheart.samplejpa.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Entity(name = "User")
@Table(name = "user",
    uniqueConstraints = {@UniqueConstraint(name = "uq_user_name", columnNames = {"name"})})
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, insertable = false, updatable = false)
  private long id;
  @Column(name = "name", nullable = false, updatable = false, unique = true)
  private String name;
  @Column(name = "bio")
  private String bio;

  public User() { // JPA only
  }

  public User(String name) {
    this(name, "");
  }

  public User(String name, String bio) {
    this.name = requireNonNull(name);
    this.bio = requireNonNull(bio);
  }

  public long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getBio() {
    return this.bio;
  }

  public void setBio(String bio) {
    this.bio = requireNonNull(bio);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.id == ((User) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
        .add("id=" + this.id)
        .add("name='" + this.name + "'")
        .add("bio='" + this.bio + "'")
        .toString();
  }
}