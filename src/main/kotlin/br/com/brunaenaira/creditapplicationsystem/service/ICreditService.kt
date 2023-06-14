package br.com.brunaenaira.creditapplicationsystem.service

import br.com.brunaenaira.creditapplicationsystem.entity.Credit
import java.util.*

interface ICreditService {
    fun save(credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit): br.com.brunaenaira.creditapplicationsystem.entity.Credit
    fun findAllByCustomer(customerId: Long): List<br.com.brunaenaira.creditapplicationsystem.entity.Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID): br.com.brunaenaira.creditapplicationsystem.entity.Credit
}