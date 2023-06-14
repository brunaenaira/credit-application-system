package br.com.brunaenaira.creditapplicationsystem.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import br.com.brunaenaira.creditapplicationsystem.entity.Address
import br.com.brunaenaira.creditapplicationsystem.entity.Customer
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
  @field:NotEmpty(message = "Invalid input") val firstName: String,
  @field:NotEmpty(message = "Invalid input") val lastName: String,
  @field:NotEmpty(message = "Invalid input")
  @field:CPF(message = "This invalid CPF") val cpf: String,
  @field:NotNull(message = "Invalid input") val income: BigDecimal,
  @field:Email(message = "Invalid email")
  @field:NotEmpty(message = "Invalid input") val email: String,
  @field:NotEmpty(message = "Invalid input") val password: String,
  @field:NotEmpty(message = "Invalid input") val zipCode: String,
  @field:NotEmpty(message = "Invalid input") val street: String
) {

  fun toEntity(): br.com.brunaenaira.creditapplicationsystem.entity.Customer =
      br.com.brunaenaira.creditapplicationsystem.entity.Customer(
          firstName = this.firstName,
          lastName = this.lastName,
          cpf = this.cpf,
          income = this.income,
          email = this.email,
          password = this.password,
          address = br.com.brunaenaira.creditapplicationsystem.entity.Address(
              zipCode = this.zipCode,
              street = this.street
          )
      )
}
