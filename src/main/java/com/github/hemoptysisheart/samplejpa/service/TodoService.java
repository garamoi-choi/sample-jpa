package com.github.hemoptysisheart.samplejpa.service;

import com.github.hemoptysisheart.samplejpa.entity.Todo;
import com.github.hemoptysisheart.samplejpa.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TodoService {
  Todo create(User user, String title, String detail);

  Todo create(Todo todo);

  Todo read(long id);

  long count();

  List<Todo> list();
}