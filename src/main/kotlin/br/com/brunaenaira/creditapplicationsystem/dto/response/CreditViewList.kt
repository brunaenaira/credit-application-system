package br.com.brunaenaira.creditapplicationsystem.dto.response

import br.com.brunaenaira.creditapplicationsystem.entity.Credit
import java.math.BigDecimal
import java.util.*

data class CreditViewList(
  val creditCode: UUID,
  val creditValue: BigDecimal,
  val numberOfInstallments: Int
) {
  constructor(credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit) : this(
    creditCode = credit.creditCode,
    creditValue = credit.creditValue,
    numberOfInstallments = credit.numberOfInstallments
  )
}
