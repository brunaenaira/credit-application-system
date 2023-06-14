package br.com.brunaenaira.creditapplicationsystem.repository

import br.com.brunaenaira.creditapplicationsystem.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CreditRepository: JpaRepository<br.com.brunaenaira.creditapplicationsystem.entity.Credit, Long> {
  fun findByCreditCode(creditCode: UUID) : br.com.brunaenaira.creditapplicationsystem.entity.Credit?

  @Query(value = "SELECT * FROM CREDIT WHERE CUSTOMER_ID = ?1", nativeQuery = true)
  fun findAllByCustomerId(customerId: Long): List<br.com.brunaenaira.creditapplicationsystem.entity.Credit>
}