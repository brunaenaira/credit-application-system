package br.com.brunaenaira.creditapplicationsystem.repository

import br.com.brunaenaira.creditapplicationsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository: JpaRepository<br.com.brunaenaira.creditapplicationsystem.entity.Customer, Long>