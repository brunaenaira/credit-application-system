package br.com.brunaenaira.creditapplicationsystem.entity

import jakarta.persistence.*
import br.com.brunaenaira.creditapplicationsystem.enummeration.Status
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
//@Table(name = "Credito")
data class Credit (
    @Column(nullable = false, unique = true) var creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false) val creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) val dayFirstInstallment: LocalDate,
    @Column(nullable = false) val numberOfInstallments: Int = 0,
    @Enumerated val status: br.com.brunaenaira.creditapplicationsystem.enummeration.Status = br.com.brunaenaira.creditapplicationsystem.enummeration.Status.IN_PROGRESS,
    @ManyToOne var customer: br.com.brunaenaira.creditapplicationsystem.entity.Customer? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null)
