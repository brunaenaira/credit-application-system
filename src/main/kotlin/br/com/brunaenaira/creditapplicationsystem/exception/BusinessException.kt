package br.com.brunaenaira.creditapplicationsystem.exception

data class BusinessException(override val message: String?) : RuntimeException(message)