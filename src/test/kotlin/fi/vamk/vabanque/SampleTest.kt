package fi.vamk.vabanque

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SampleTest {
  @Nested
  inner class Add {
    @Test
    fun `should return calculated number`() {
      val calculator = Calculator()

      val result = calculator.add(2, 2)

      assertThat(result).isEqualTo(4)
    }

    @Test
    fun `should return calculated number with mockk`() {
      val calculator: Calculator = mockk()
      every { calculator.add(2, 2) } returns 4

      val result = calculator.add(2, 2)

      assertThat(result).isEqualTo(4)
    }

    @Test
    fun `should not throw an exception`() {
      val calculator = Calculator()

      assertThatCode {
        calculator.add(2, 2)
      }.doesNotThrowAnyException()
    }
  }
}

class Calculator {
  fun add(number1: Int, number2: Int) = number1 + number2
}
