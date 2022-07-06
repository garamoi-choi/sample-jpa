package com.github.hemoptysisheart.samplejpa.service;

import com.github.hemoptysisheart.samplejpa.entity.Todo;
import com.github.hemoptysisheart.samplejpa.entity.User;
import com.github.hemoptysisheart.samplejpa.repository.TodoRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
class TodoServiceImpl implements TodoService {
  private final TodoRepository repository;

  TodoServiceImpl(TodoRepository repository) {
    this.repository = requireNonNull(repository);
  }

  @Override
  public Todo create(User user, String title, String detail) {
    Todo todo = new Todo(user, title, detail);
    todo = this.repository.save(todo);
    return todo;
  }

  @Override
  public Todo read(long id) {
    return this.repository.findById(id)
        .orElse(null);
  }

  @Override
  public long count() {
    return this.repository.count();
  }
}