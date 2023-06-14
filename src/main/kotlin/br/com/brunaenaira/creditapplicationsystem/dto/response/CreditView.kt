package br.com.brunaenaira.creditapplicationsystem.dto.response

import br.com.brunaenaira.creditapplicationsystem.entity.Credit
import br.com.brunaenaira.creditapplicationsystem.enummeration.Status
import java.math.BigDecimal
import java.util.*

data class CreditView(
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallment: Int,
    val status: br.com.brunaenaira.creditapplicationsystem.enummeration.Status,
    val emailCustomer: String?,
    val incomeCustomer: BigDecimal?
) {
  constructor(credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit) : this(
    creditCode = credit.creditCode,
    creditValue = credit.creditValue,
    numberOfInstallment = credit.numberOfInstallments,
    status = credit.status,
    emailCustomer = credit.customer?.email,
    incomeCustomer = credit.customer?.income
  )
}
