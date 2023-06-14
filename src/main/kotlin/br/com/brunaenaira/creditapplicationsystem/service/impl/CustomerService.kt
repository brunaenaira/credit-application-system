package br.com.brunaenaira.creditapplicationsystem.service.impl

import br.com.brunaenaira.creditapplicationsystem.repository.CustomerRepository
import br.com.brunaenaira.creditapplicationsystem.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
) : ICustomerService {
    override fun save(customer: br.com.brunaenaira.creditapplicationsystem.entity.Customer): br.com.brunaenaira.creditapplicationsystem.entity.Customer =
        this.customerRepository.save(customer)

    override fun findById(id: Long): br.com.brunaenaira.creditapplicationsystem.entity.Customer =
        this.customerRepository.findById(id)
            .orElseThrow { throw br.com.brunaenaira.creditapplicationsystem.exception.BusinessException("Id $id not found") }

    override fun delete(id: Long) {
        val customer: br.com.brunaenaira.creditapplicationsystem.entity.Customer = this.findById(id)
        this.customerRepository.delete(customer)
    }
}