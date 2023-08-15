package com.baxramov.domain

class GetLocationUseCase(private val repository: Repository) {
    operator fun invoke() = repository.getLocation()
}