package br.com.brunaenaira.creditapplicationsystem.controller

import br.com.brunaenaira.creditapplicationsystem.service.impl.CreditService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/credits")
class CreditResource(
    private val creditService: CreditService
) {

    @PostMapping
    fun saveCredit(@RequestBody @Valid creditDto: br.com.brunaenaira.creditapplicationsystem.dto.request.CreditDto): ResponseEntity<String> {
        val credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit = this.creditService.save(creditDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Credit ${credit.creditCode} - Customer ${credit.customer?.email} saved!")
    }

    @GetMapping
    fun findAllByCustomerId(@RequestParam(value = "customerId") customerId: Long):
            ResponseEntity<List<br.com.brunaenaira.creditapplicationsystem.dto.response.CreditViewList>> {
        val creditViewList: List<br.com.brunaenaira.creditapplicationsystem.dto.response.CreditViewList> =
            this.creditService.findAllByCustomer(customerId)
                .stream()
                .map { credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit ->
                    br.com.brunaenaira.creditapplicationsystem.dto.response.CreditViewList(
                        credit
                    )
                }
                .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(creditViewList)
    }

    @GetMapping("/{creditCode}")
    fun findByCreditCode(
        @RequestParam(value = "customerId") customerId: Long,
        @PathVariable creditCode: UUID
    ): ResponseEntity<br.com.brunaenaira.creditapplicationsystem.dto.response.CreditView> {
        val credit: br.com.brunaenaira.creditapplicationsystem.entity.Credit =
            this.creditService.findByCreditCode(customerId, creditCode)
        return ResponseEntity.status(HttpStatus.OK).body(
            br.com.brunaenaira.creditapplicationsystem.dto.response.CreditView(
                credit
            )
        )
    }
}