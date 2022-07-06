package com.github.hemoptysisheart.samplejpa.service

import com.github.hemoptysisheart.samplejpa.SampleJpaTestConfiguration
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import mu.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID.randomUUID

@SpringBootTest(classes = [SampleJpaTestConfiguration::class])
class UserServiceImplSpec(
    private val service: UserService
) : AnnotationSpec() {
  private val logger = KotlinLogging.logger { }

  @Test
  fun `create - multiple user`() {
    // GIVEN
    val beforeCount = service.count()
    logger.info("[GIVEN] beforeCount=$beforeCount")

    val count = 10
    logger.info("[GIVEN] count=$count")

    // WHEN
    repeat(count) {
      val user = service.create("name ${randomUUID()}", "bio #${randomUUID()}")
      logger.info("[WHEN] user=$user")
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
    val expected = service.create("user #${randomUUID()}", "read - a user #${randomUUID()}")
    logger.info("[GIVEN] expected=$expected")

    // WHEN
    val actual = service.read(expected.id)
    logger.info("[WHEN] actual=$actual")

    // THEN
    actual shouldBe expected
    actual shouldNotBeSameInstanceAs expected
  }

  @Test
  fun `list - all user`() {
    // GIVEN
    val user = service.create("user #${randomUUID()}", "list - all user #${randomUUID()}")
    logger.info("[GIVEN] user=$user")

    // WHEN
    val list = service.list()
    logger.info("[WHEN] list=$list")

    // THEN
    list.shouldNotBeEmpty()
        .shouldContain(user)
  }
}
