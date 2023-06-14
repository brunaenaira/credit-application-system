package br.com.brunaenaira.creditapplicationsystem.service

import br.com.brunaenaira.creditapplicationsystem.entity.Customer

interface ICustomerService {
    fun save(customer: br.com.brunaenaira.creditapplicationsystem.entity.Customer): br.com.brunaenaira.creditapplicationsystem.entity.Customer
    fun findById(id: Long): br.com.brunaenaira.creditapplicationsystem.entity.Customer
    fun delete(id: Long)
}