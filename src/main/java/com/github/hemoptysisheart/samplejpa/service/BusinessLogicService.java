package com.github.hemoptysisheart.samplejpa.service;

import com.github.hemoptysisheart.samplejpa.entity.Todo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BusinessLogicService {
  Todo createEpicWithNewTodo();

  List<Todo> filterTodoWithUser(String filter);
}