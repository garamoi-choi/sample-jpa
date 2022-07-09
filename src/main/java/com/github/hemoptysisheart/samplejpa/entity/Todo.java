package com.github.hemoptysisheart.samplejpa.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Entity(name = "Todo")
@Table(name = "todo",
    indexes = {@Index(name = "fk_todo_pk_user", columnList = "user ASC"),
        @Index(name = "fk_todo_pk_status", columnList = "status ASC"),
        @Index(name = "fk_todo_parent_pk_todo", columnList = "parent ASC")})
public class Todo {
  private static final Logger LOGGER = LoggerFactory.getLogger(Todo.class);

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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent", foreignKey = @ForeignKey(name = "fk_todo_parent_pk_todo"), referencedColumnName = "id")
  private Todo parent;
  @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
  private List<Todo> subTodos = new ArrayList<>();

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

  public Todo getParent() {
    return this.parent;
  }

  public void setParent(Todo parent) {
    parent = requireNonNull(parent);
    parent.addSub(this);
  }

  public List<Todo> getSubTodos() {
    return this.subTodos;
  }

  private void addSub(Todo sub) {
    this.subTodos.add(sub);
  }

  public boolean matches(String filter) {
    boolean result = false;

    if (filter.startsWith("user:")) {
      result = this.user.matches(filter.substring("user:".length()));
    }
    result = result || this.title.contains(filter) || this.detail.contains(filter);

    LOGGER.trace("#matches : filter={}, this={}, result={}", filter, this, result);
    return result;
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
        .add("parent=" + (null == this.parent ? null : "(" + this.parent.id + ", " + this.parent.title + ")"))
        .add("subTodos.size=" + this.subTodos.size())
        .toString();
  }
}