package com.baxramov.domain

class GetDateUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getDate()
}