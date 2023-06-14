package br.com.brunaenaira.creditapplicationsystem.service.impl

import br.com.brunaenaira.creditapplicationsystem.entity.Credit
import br.com.brunaenaira.creditapplicationsystem.entity.Customer
import br.com.brunaenaira.creditapplicationsystem.exception.BusinessException
import br.com.lucolimac.credit.api.repository.CreditRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK
    lateinit var creditRepository: CreditRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should create credit `() {
        //given
        val credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit = buildCredit()
        val customerId: Long = 1L

        every { customerService.findById(customerId) } returns credit.customer!!
        every { creditRepository.save(credit) } returns credit
        //when
        val actual: br.com.brunaenaira.creditapplicationsystem.entity.Credit = this.creditService.save(credit)
        //then
        verify(exactly = 1) { customerService.findById(customerId) }
        verify(exactly = 1) { creditRepository.save(credit) }

        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(credit)
    }

    @Test
    fun `should not create credit when invalid day first installment`() {
        //given
        val invalidDayFirstInstallment: LocalDate = LocalDate.now().plusMonths(5)
        val credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit = buildCredit(dayFirstInstallment = invalidDayFirstInstallment)

        every { creditRepository.save(credit) } answers { credit }
        //when
        Assertions.assertThatThrownBy { creditService.save(credit) }.isInstanceOf(br.com.brunaenaira.creditapplicationsystem.exception.BusinessException::class.java)
            .hasMessage("Invalid Date")
        //then
        verify(exactly = 0) { creditRepository.save(any()) }
    }

    @Test
    fun `should return list of credits for a customer`() {
        //given
        val customerId: Long = 1L
        val expectedCredits: List<br.com.brunaenaira.creditapplicationsystem.entity.Credit> = listOf(buildCredit(), buildCredit(), buildCredit())

        every { creditRepository.findAllByCustomerId(customerId) } returns expectedCredits
        //when
        val actual: List<br.com.brunaenaira.creditapplicationsystem.entity.Credit> = creditService.findAllByCustomer(customerId)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isNotEmpty
        Assertions.assertThat(actual).isSameAs(expectedCredits)

        verify(exactly = 1) { creditRepository.findAllByCustomerId(customerId) }
    }

    @Test
    fun `should return credit for a valid customer and credit code`() {
        //given
        val customerId: Long = 1L
        val creditCode: UUID = UUID.randomUUID()
        val credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit = buildCredit(customer = br.com.brunaenaira.creditapplicationsystem.entity.Customer(
            id = customerId
        )
        )

        every { creditRepository.findByCreditCode(creditCode) } returns credit
        //when
        val actual: br.com.brunaenaira.creditapplicationsystem.entity.Credit = creditService.findByCreditCode(customerId, creditCode)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(credit)

        verify(exactly = 1) { creditRepository.findByCreditCode(creditCode) }
    }

    @Test
    fun `should throw BusinessException for invalid credit code`() {
        //given
        val customerId: Long = 1L
        val invalidCreditCode: UUID = UUID.randomUUID()

        every { creditRepository.findByCreditCode(invalidCreditCode) } returns null
        //when
        //then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, invalidCreditCode) }
            .isInstanceOf(br.com.brunaenaira.creditapplicationsystem.exception.BusinessException::class.java).hasMessage("Creditcode $invalidCreditCode not found")
        //then
        verify(exactly = 1) { creditRepository.findByCreditCode(invalidCreditCode) }
    }

    @Test
    fun `should throw IllegalArgumentException for different customer ID`() {
        //given
        val customerId: Long = 1L
        val creditCode: UUID = UUID.randomUUID()
        val credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit = buildCredit(customer = br.com.brunaenaira.creditapplicationsystem.entity.Customer(
            id = 2L
        )
        )

        every { creditRepository.findByCreditCode(creditCode) } returns credit
        //when
        //then
        Assertions.assertThatThrownBy { creditService.findByCreditCode(customerId, creditCode) }
            .isInstanceOf(IllegalArgumentException::class.java).hasMessage("Contact admin")

        verify { creditRepository.findByCreditCode(creditCode) }
    }

    companion object {
        private fun buildCredit(
            creditValue: BigDecimal = BigDecimal.valueOf(1030.0),
            dayFirstInstallment: LocalDate = LocalDate.now().plusMonths(2L),
            numberOfInstallments: Int = 15,
            customer: br.com.brunaenaira.creditapplicationsystem.entity.Customer = CustomerServiceTest.buildCustomer()
        ): br.com.brunaenaira.creditapplicationsystem.entity.Credit =
            br.com.brunaenaira.creditapplicationsystem.entity.Credit(
                creditValue = creditValue,
                dayFirstInstallment = dayFirstInstallment,
                numberOfInstallments = numberOfInstallments,
                customer = customer
            )
    }
}