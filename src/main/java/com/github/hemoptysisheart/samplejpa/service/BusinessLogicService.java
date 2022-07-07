package com.github.hemoptysisheart.samplejpa.service;

import com.github.hemoptysisheart.samplejpa.entity.Todo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BusinessLogicService {
  Todo createEpicWithNewTodo();
}