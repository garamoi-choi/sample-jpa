package com.github.hemoptysisheart.samplejpa.service

import com.github.hemoptysisheart.samplejpa.SampleJpaTestConfiguration
import com.github.hemoptysisheart.samplejpa.entity.Todo
import io.kotest.core.spec.style.AnnotationSpec
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.UUID.randomUUID
import java.util.concurrent.ThreadLocalRandom

@SpringBootTest(classes = [SampleJpaTestConfiguration::class])
@Transactional
class BusinessLogicServiceImplSpec(
    private val service: BusinessLogicService,
    private val userService: UserService,
    private val todoService: TodoService
) : AnnotationSpec() {
  private val logger = KotlinLogging.logger { }

  @Test
  fun `createEpicWithNewTodo - insert todo with new sub-todos`() {
    // PREPARE
    val users = (1..10).map {
      userService.create("user #${randomUUID()}", "")
    }
    logger.info("[PREPARE] users=$users")

    // DO
    val epic = service.createEpicWithNewTodo()
    logger.info("[DO] epic=$epic")
  }

  @Test
  fun `filterTodoWithUser - filter todo with user name & bio`() {
    // PREPARE
    val users = (1..10).map {
      userService.create("user-$it #${randomUUID()}", "bio ${it}th user")
    }
    val todos = (1..100).map {
      todoService.create(Todo(users.random(), "todo #${randomUUID()}", "todo detail #${randomUUID()}"))
    }
    logger.info("[PREPARE] users=$users")


    // DO
    val number = ThreadLocalRandom.current().nextInt(0, 10)
    val filter = "user:$number"
    logger.info("[DO] filter=$filter")

    val filtered = service.filterTodoWithUser(filter)
    logger.info("[DO] filtered=$filtered")
  }
}