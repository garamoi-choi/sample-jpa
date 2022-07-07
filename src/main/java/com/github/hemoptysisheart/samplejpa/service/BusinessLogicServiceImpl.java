package com.github.hemoptysisheart.samplejpa.service;

import com.github.hemoptysisheart.samplejpa.entity.Todo;
import com.github.hemoptysisheart.samplejpa.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

@Service
class BusinessLogicServiceImpl implements BusinessLogicService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BusinessLogicServiceImpl.class);

  private final UserService userService;
  private final TodoService todoService;

  public BusinessLogicServiceImpl(UserService userService, TodoService todoService) {
    this.userService = requireNonNull(userService);
    this.todoService = requireNonNull(todoService);
  }

  @Override
  public Todo createEpicWithNewTodo() {
    List<User> users = this.userService.list();
    Collections.shuffle(users);
    User user = users.get(0);
    LOGGER.info("#complexCreate : user={}", user);

    Todo epic = new Todo(user, "epic #" + randomUUID());
    for (int i = 0; i < 5; i++) {
      Todo todo = new Todo(user, "todo #" + randomUUID(), "epic : " + epic.getTitle());
      todo.setParent(epic);
      LOGGER.info("todo={}, epic.subTodos={}", todo, epic.getSubTodos());
    }

    epic = this.todoService.create(epic);

    LOGGER.info("epic={}", epic);
    return epic;
  }
}