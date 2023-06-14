package br.com.brunaenaira.creditapplicationsystem.service.impl

import br.com.brunaenaira.creditapplicationsystem.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK
    lateinit var customerRepository: CustomerRepository

    @InjectMockKs
    lateinit var customerService: CustomerService

    @Test
    fun `Should create customer`() {
        //given
        val customer = buildCustomer()
        every { customerRepository.save(any()) } returns customer
        //when
        val actual = customerService.save(customer)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(customer)
        Assertions.assertThat(actual.id).isEqualTo(customer.id)
        verify(exactly = 1) { customerRepository.save(any()) }
    }

    @Test
    fun `Should find customer by id`() {
        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: br.com.brunaenaira.creditapplicationsystem.entity.Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        //when
        val actual: br.com.brunaenaira.creditapplicationsystem.entity.Customer = customerService.findById(fakeId)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(br.com.brunaenaira.creditapplicationsystem.entity.Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `should not find customer by invalid id and throw BusinessException`() {
        //given
        val fakeId: Long = Random().nextLong()
        every { customerRepository.findById(fakeId) } returns Optional.empty()
        //when
        //then
        Assertions.assertThatExceptionOfType(br.com.brunaenaira.creditapplicationsystem.exception.BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }.withMessage("Id $fakeId not found")
        verify(exactly = 1) { customerRepository.findById(fakeId) }
    }

    @Test
    fun `should delete customer by id`() {
        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: br.com.brunaenaira.creditapplicationsystem.entity.Customer = buildCustomer(id = fakeId)
        every { customerRepository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { customerRepository.delete(fakeCustomer) } just runs
        //when
        customerService.delete(fakeId)
        //then
        verify(exactly = 1) { customerRepository.findById(fakeId) }
        verify(exactly = 1) { customerRepository.delete(fakeCustomer) }
    }

    companion object {
        fun buildCustomer(
            firstName: String = "Bruna",
            lastName: String = "Ariane",
            cpf: String = "11222893222",
            email: String = "brunaenaira@gmail.com",
            password: String = "12345",
            zipCode: String = "12345",
            street: String = "Rua X",
            income: BigDecimal = BigDecimal.valueOf(1000.0),
            id: Long = 1L
        ) = br.com.brunaenaira.creditapplicationsystem.entity.Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = br.com.brunaenaira.creditapplicationsystem.entity.Address(
                zipCode = zipCode,
                street = street,
            ),
            income = income,
            id = id
        )
    }
}