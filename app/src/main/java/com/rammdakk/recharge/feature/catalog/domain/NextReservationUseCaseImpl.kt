package com.rammdakk.recharge.feature.catalog.domain

import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel

class NextReservationUseCaseImpl(
    private val catalogRepository: CatalogRepository
) : NextReservationUseCase {
    override suspend fun getNextActivityInfo(): Result<NextActivityDataModel> =
        catalogRepository.getNextActivityInfo()

}