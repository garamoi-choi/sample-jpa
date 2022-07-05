package com.github.hemoptysisheart.samplejpa.entity;

import static java.util.Objects.requireNonNull;

public class Todo {
  private long id;
  private User user;
  private String title;
  private String detail;
  private Status status;

  public Todo() { // JPA only
  }

  public Todo(User user, String title) {
    this(user, title, "");
  }

  public Todo(User user, String title, String detail) {
    this(user, title, detail, Status.TODO);
  }

  public Todo(User user, String title, String detail, Status status) {
    this.user = requireNonNull(user);
    this.title = requireNonNull(title);
    this.detail = requireNonNull(detail);
    this.status = requireNonNull(status);
  }

  public long getId() {
    return this.id;
  }

  public User getUser() {
    return this.user;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = requireNonNull(title);
  }

  public String getDetail() {
    return this.detail;
  }

  public void setDetail(String detail) {
    this.detail = requireNonNull(detail);
  }

  public Status getStatus() {
    return this.status;
  }

  public void setStatus(Status status) {
    this.status = requireNonNull(status);
  }
}