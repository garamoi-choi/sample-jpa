package com.github.hemoptysisheart.samplejpa.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Entity(name = "Todo")
@Table(name = "todo",
    indexes = {@Index(name = "fk_todo_pk_user", columnList = "user ASC"),
        @Index(name = "fk_todo_pk_status", columnList = "status ASC")})
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @ManyToOne(targetEntity = User.class, optional = false)
  @JoinColumn(name = "user", nullable = false, updatable = false,
      foreignKey = @ForeignKey(name = "fk_todo_pk_user"), referencedColumnName = "id")
  private User user;
  @Column(name = "title", nullable = false)
  private String title;
  @Column(name = "detail", nullable = false)
  private String detail;
  @Column(name = "status", nullable = false)
  @Enumerated
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    return this.id == ((Todo) o).id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Todo.class.getSimpleName() + "[", "]")
        .add("id=" + this.id)
        .add("user=" + this.user)
        .add("title='" + this.title + "'")
        .add("detail='" + this.detail + "'")
        .add("status=" + this.status)
        .toString();
  }
}