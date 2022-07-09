package com.github.hemoptysisheart.samplejpa.repository

import com.github.hemoptysisheart.samplejpa.entity.Todo
import com.github.hemoptysisheart.samplejpa.entity.User
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import mu.KotlinLogging
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import java.util.UUID.randomUUID

@SpringBootTest
@Transactional
class TodoRepositorySpec(
    private val repository: TodoRepository,
    private val userRepository: UserRepository
) : AnnotationSpec() {
  private val logger = KotlinLogging.logger { }

  @Test
  fun `save & findById`() {
    // GIVEN
    val user = userRepository.save(User(randomAlphanumeric(10)))
    logger.info("[GIVEN] user=$user")

    // WHEN
    val id = repository.save(Todo(user, "title #${randomUUID()}")).id
    val todo = repository.findByIdOrNull(id)
    logger.info("[WHEN] id=$id, todo=$todo")

    // THEN
    todo.shouldNotBeNull()
    todo.id shouldBe id
  }
}
