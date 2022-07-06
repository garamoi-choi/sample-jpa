package com.github.hemoptysisheart.samplejpa.service;

import com.github.hemoptysisheart.samplejpa.entity.User;
import com.github.hemoptysisheart.samplejpa.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
class UserServiceImpl implements UserService {
  private final UserRepository repository;

  public UserServiceImpl(UserRepository repository) {
    this.repository = requireNonNull(repository);
  }

  @Override
  public long count() {
    return this.repository.count();
  }

  @Override
  public User create(@NotNull String name, @NotNull String bio) {
    User user = new User(name, bio);
    user = this.repository.save(user);
    return user;
  }

  @Override
  public User read(long id) {
    return this.repository.findById(id)
        .orElse(null);
  }

  @Override
  public List<User> list() {
    return this.repository.findAll();
  }
}