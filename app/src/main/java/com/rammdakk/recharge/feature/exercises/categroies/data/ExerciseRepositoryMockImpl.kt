package com.rammdakk.recharge.feature.exercises.categroies.data

import com.rammdakk.recharge.feature.exercises.categroies.domain.ExerciseRepository
import com.rammdakk.recharge.feature.exercises.categroies.models.data.ExerciseTabDataModel
import com.rammdakk.recharge.feature.exercises.categroies.models.data.SportTypeDataModel

class ExerciseRepositoryMockImpl() : ExerciseRepository {
    override suspend fun getTabsCategories(): Result<List<ExerciseTabDataModel>> =
        Result.success(
            listOf(
                ExerciseTabDataModel("Тренировки", 1),
                ExerciseTabDataModel("Спа", 1),
            )
        )

    override suspend fun getSports(tabId: Int): Result<List<SportTypeDataModel>> =
        Result.success(
            listOf(
                SportTypeDataModel(
                    1,
                    "Фитнес1",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    2,
                    "Фитнес2",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    3,
                    "нетФитнес3",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    4,
                    "неФитнес4",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    5,
                    "Фитнес5",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    6,
                    "Фитнес6",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    7,
                    "Фитнес7",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    8,
                    "Фитнес8",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    9,
                    "Фитнес9",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
                SportTypeDataModel(
                    10,
                    "Фитнес10",
                    "https://world-bike.ru/wp-content/uploads/2023/11/5741.jpg"
                ),
            )
        )
}