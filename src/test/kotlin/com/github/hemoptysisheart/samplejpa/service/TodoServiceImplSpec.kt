package com.github.hemoptysisheart.samplejpa.service

import com.github.hemoptysisheart.samplejpa.SampleJpaTestConfiguration
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID.randomUUID

@SpringBootTest(classes = [SampleJpaTestConfiguration::class])
class TodoServiceImplSpec(
    private val service: TodoService,
    private val userService: UserService
) : AnnotationSpec() {
  private val logger = KotlinLogging.logger { }

  @Test
  fun `create - a todo`() {
    // GIVEN
    val user = userService.list().random()
    val title = "todo ${randomUUID()}"
    logger.info("[GIVEN] user=$user, title=$title")

    // WHEN
    val todo = service.create(user, title, "")
    logger.info("[WHEN] todo=$todo")

    // THEN
    todo.id shouldBeGreaterThan 0L
    todo.user shouldBe user
    todo.title shouldBe title
    todo.detail shouldBe ""
  }

  @Test
  fun `create - multi todo`() {
    // GIVEN
    userService.create("user #${randomUUID()}", "")
    val users = userService.list()
    val beforeCount = service.count()
    val count = 10
    logger.info("[GIVEN] beforeCount=$beforeCount, count=$count")

    // WHEN
    repeat(count) {
      val todo = service.create(users.random(), "todo #${randomUUID()}", "")
      logger.info("[WHEN] todo=$todo")
    }

    // THEN
    val afterCount = service.count()
    logger.info("[THEN] afterCount=$afterCount")

    afterCount shouldBeGreaterThan 0L
    afterCount shouldBe beforeCount + count
  }

  @Test
  fun `read - a user`() {
    // GIVEN
    val user = userService.create("user #${randomUUID()}", "")
    val todo = service.create(user, "todo #${randomUUID()}", "")
    logger.info("[GIVEN] user=$user, todo=$todo")

    // WHEN
    val actual = service.read(todo.id)
    logger.info("[WHEN] actual=$actual")

    // THEN
    actual shouldBe todo
    actual shouldNotBeSameInstanceAs todo
  }
}
