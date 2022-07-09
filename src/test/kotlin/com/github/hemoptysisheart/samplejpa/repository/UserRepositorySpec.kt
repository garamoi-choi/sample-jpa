package com.github.hemoptysisheart.samplejpa.repository

import com.github.hemoptysisheart.samplejpa.SampleJpaTestConfiguration
import com.github.hemoptysisheart.samplejpa.entity.User
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldBeEmpty
import io.kotest.matchers.types.shouldBeSameInstanceAs
import mu.KotlinLogging
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(classes = [SampleJpaTestConfiguration::class])
@Transactional
class UserRepositorySpec(
    private val repository: UserRepository
) : AnnotationSpec() {
  private val logger = KotlinLogging.logger { }

  @Before
  fun setUp() {
    logger.info("[SETUP] repository=$repository")
  }

  @Test
  fun `findAll - not null`() {
    // WHEN
    val list = repository.findAll()
    logger.info("[WHEN] list.size=${list.size}")

    // THEN
    list.shouldNotBeNull()
  }

  @Test
  fun `save - random user`() {
    // GIVEN
    val name = randomAlphanumeric(10)
    val user = User(name)
    logger.info("[GIVEN] user=$user")

    // WHEN
    val actual = repository.save(user)
    logger.info("[WHEN] actual=$actual")

    // THEN
    actual.shouldNotBeNull()
    actual.id shouldBeGreaterThan 0L
    actual.name shouldBe name
    actual.bio.shouldBeEmpty()
  }

  @Test
  fun `findAll - includes new user`() {
    // GIVEN
    val user = repository.save(User(randomAlphanumeric(10)))
    logger.info("[GIVEN] user=$user")

    // WHEN
    val list = repository.findAll()
    logger.info("[WHEN] list.size=${list.size}")

    // THEN
    list.shouldNotBeNull()
    list shouldContain user
    list.find { it == user } shouldBeSameInstanceAs user
  }

  @Test
  fun `findByIdOrNull - cache hit`() {
    // GIVEN
    val expected = repository.save(User(randomAlphanumeric(10)))
    logger.info("[GIVEN] expected=$expected")

    // WHEN
    val actual = repository.findByIdOrNull(expected.id)
    logger.info("[WHEN] actual=$actual")

    // THEN
    actual.shouldNotBeNull()
    actual shouldBeSameInstanceAs expected
  }
}
