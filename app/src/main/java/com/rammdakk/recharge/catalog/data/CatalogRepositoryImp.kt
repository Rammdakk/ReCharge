package com.rammdakk.recharge.catalog.data

import android.util.Log
import com.rammdakk.recharge.catalog.data.model.ActivityDataModel
import com.rammdakk.recharge.catalog.data.model.CategoryDataModel
import com.rammdakk.recharge.catalog.data.model.Coordinates
import com.rammdakk.recharge.catalog.data.model.ProfileDataModel
import com.rammdakk.recharge.catalog.domain.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class CatalogRepositoryImp(
    private val dispatchers: Dispatchers,
) : CatalogRepository {
    override suspend fun getProfileInfo(): ProfileDataModel {
        Log.d("Ramil", "profile start")
        delay(1000L)
        Log.d("Ramil", "profile end")
        return ProfileDataModel(
            photoPath = "https://fiverr-res.cloudinary.com/videos/t_main1,q_auto,f_auto/ayao3nn9ntatngxnuaf7/create-you-an-3d-animoji-avatar.png",
            name = "Анна"
        )
    }

    override suspend fun getNextActivityInfo(): ActivityDataModel {
        Log.d("Ramil", "getNextActivityInfo start")
        delay(1000L)
        Log.d("Ramil", "getNextActivityInfo end")
        return ActivityDataModel(
            id = 3567,
            imagePath = "https://riamo.ru/files/image/04/54/86/gallery!alf.jpg",
            name = "ysefugadhius",
            time = System.currentTimeMillis(),
            startPrice = 2000f,
            address = "м.Курская, Земляной вал, 33",
            organizationName = "Бассейн Чайка",
            duration = 637,
            coordinates = Coordinates(55.757113f, 37.659049f)
        )
    }

    override suspend fun getCategories(): List<CategoryDataModel> {
        delay(1000L)
        return listOf(
            CategoryDataModel(
                imagePath = "https://cdn-icons-png.flaticon.com/128/185/185590.png",
                id = "",
                name = ""
            ),
            CategoryDataModel(
                imagePath = "https://cdn-icons-png.flaticon.com/128/185/185615.png",
                id = "",
                name = ""
            ),
            CategoryDataModel(
                imagePath = "https://cdn-icons-png.flaticon.com/128/185/185616.png",
                id = "",
                name = ""
            ),
            CategoryDataModel(
                imagePath = "https://cdn-icons-png.flaticon.com/128/185/185617.png",
                id = "",
                name = ""
            ),
            CategoryDataModel(
                imagePath = "https://cdn-icons-png.flaticon.com/128/185/185618.png",
                id = "",
                name = ""
            )
        )
    }

    override suspend fun updateCatalog(selectedCategoryId: String?): List<ActivityDataModel> {
        delay(1000L)
        val activity = ActivityDataModel(
            id = 3567,
            imagePath = "https://riamo.ru/files/image/04/54/86/gallery!alf.jpg",
            name = "ysefugadhius",
            time = System.currentTimeMillis(),
            startPrice = 2000f,
            address = "м.Курская, Земляной вал, 33",
            organizationName = "Бассейн Чайка",
            duration = 637,
            coordinates = Coordinates(55.76435f, 45.4766f)
        )
        return listOf(
            activity,
            activity,
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
    }
}