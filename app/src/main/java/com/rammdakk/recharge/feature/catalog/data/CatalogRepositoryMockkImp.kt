package com.rammdakk.recharge.feature.catalog.data

import com.rammdakk.recharge.feature.catalog.data.model.ActivityRecommendationDataModel
import com.rammdakk.recharge.feature.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.feature.catalog.data.model.Coordinates
import com.rammdakk.recharge.feature.catalog.data.model.NextActivityDataModel
import com.rammdakk.recharge.feature.catalog.data.model.ProfileDataModel
import com.rammdakk.recharge.feature.catalog.domain.CatalogRepository

class CatalogRepositoryMockkImp : CatalogRepository {


    override suspend fun getProfileInfo(): Result<ProfileDataModel> =
        Result.success(
            ProfileDataModel(
                photoPath = "https://fiverr-res.cloudinary.com/videos/t_main1,q_auto,f_auto/ayao3nn9ntatngxnuaf7/create-you-an-3d-animoji-avatar.png",
                name = "Анна"
            )
        )


    override suspend fun getNextActivityInfo(): Result<NextActivityDataModel> =
        Result.success(
            NextActivityDataModel(
                id = 3567,
                imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
                name = "2-ух часовое занятие с тренером очень интереснг длиныый текст 2-ух часовое занятие с тренером очень интереснг длиныый текст",
                time = System.currentTimeMillis() + 24 * 60 * 60 * 1000 * 10,
                address = "м.Курская, Земляной вал, 33",
                organizationName = "Бассейн Чайка",
                coordinates = Coordinates(55.757114f, 37.65905f)
            )
        )

    override suspend fun getCategories(): Result<List<CategoryDataModel>> {
        return Result.success(
            listOf(
                CategoryDataModel(
                    imagePath = "https://cdn-icons-png.flaticon.com/128/185/185590.png",
                    id = 1,
                    name = ""
                ),
                CategoryDataModel(
                    imagePath = "https://cdn-icons-png.flaticon.com/128/185/185615.png",
                    id = 2,
                    name = ""
                ),
                CategoryDataModel(
                    imagePath = "https://cdn-icons-png.flaticon.com/128/185/185616.png",
                    id = 3,
                    name = ""
                ),
                CategoryDataModel(
                    imagePath = "https://cdn-icons-png.flaticon.com/128/185/185617.png",
                    id = 4,
                    name = ""
                ),
                CategoryDataModel(
                    imagePath = "https://cdn-icons-png.flaticon.com/128/185/185618.png",
                    id = 5,
                    name = ""
                )
            )
        )
    }

    override suspend fun updateCatalog(selectedCategoryId: Int?): Result<List<ActivityRecommendationDataModel>> {
        val activity = ActivityRecommendationDataModel(
            id = 3567,
            imagePath = "https://lrhotel.ru/upload/resize_cache/iblock/688/767_500_2/hys84kpkx508fkmxh77gm3s1ve3afbky.JPG",
            name = "2-ух часовое занятие с тренером очень интереснг длиныый текст 2-ух часовое занятие с тренером очень интереснг длиныый текст",
            startPrice = 2000f,
            address = "м.Курская, Земляной вал, 33",
            organizationName = "Бассейн Чайка",
        )
        return Result.success(
            listOf(
                activity,
                activity.copy(name = "testu"),
                activity,
                activity,
                activity,
                activity,
                activity,
                activity,
                activity,
                activity,
                activity, activity, activity, activity,
            )
        )
    }
}