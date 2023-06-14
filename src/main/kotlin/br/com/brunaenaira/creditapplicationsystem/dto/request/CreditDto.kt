package br.com.brunaenaira.creditapplicationsystem.dto.request

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import br.com.brunaenaira.creditapplicationsystem.entity.Credit
import br.com.brunaenaira.creditapplicationsystem.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
  @field:NotNull(message = "Invalid input") val creditValue: BigDecimal,
  @field:Future val dayFirstOfInstallment: LocalDate,
  @field:Min(value = 1) @field:Max(value = 48) val numberOfInstallments: Int,
  @field:NotNull(message = "Invalid input") val customerId: Long
) {

  fun toEntity(): br.com.brunaenaira.creditapplicationsystem.entity.Credit =
      br.com.brunaenaira.creditapplicationsystem.entity.Credit(
          creditValue = this.creditValue,
          dayFirstInstallment = this.dayFirstOfInstallment,
          numberOfInstallments = this.numberOfInstallments,
          customer = br.com.brunaenaira.creditapplicationsystem.entity.Customer(id = this.customerId)
      )
}
