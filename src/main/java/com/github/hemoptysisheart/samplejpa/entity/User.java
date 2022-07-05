package com.github.hemoptysisheart.samplejpa.entity;

import static java.util.Objects.requireNonNull;

public class User {
  private long id;
  private String name;
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
}