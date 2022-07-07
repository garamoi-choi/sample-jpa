package com.github.hemoptysisheart.samplejpa.service

import com.github.hemoptysisheart.samplejpa.SampleJpaTestConfiguration
import io.kotest.core.spec.style.AnnotationSpec
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID.randomUUID

@SpringBootTest(classes = [SampleJpaTestConfiguration::class])
class BusinessLogicServiceImplSpec(
    private val service: BusinessLogicService,
    private val userService: UserService,
    private val todoService: TodoService
) : AnnotationSpec() {
  private val logger = KotlinLogging.logger { }

  @Test
  fun `createEpicWithNewTodo - create epic with todo(s)`() {
    // PREPARE
    val users = (1..10).map {
      userService.create("user #${randomUUID()}", "")
    }
    logger.info("[PREPARE] users=$users")

    // DO
    val epic = service.createEpicWithNewTodo()
    logger.info("[DO] epic=$epic")
  }
}