package com.taetae98.diary.data.response

import com.taetae98.diary.data.converter.GeoTrans
import com.taetae98.diary.data.converter.GeoTransPoint
import com.taetae98.diary.domain.model.place.PlaceEntity
import kotlinx.serialization.Serializable

@Serializable
data class NaverPlaceSearchResponse(
    val items: List<NaverPlace>
) {
    @Serializable
    data class NaverPlace(
        val title: String,
        val link: String,
        val category: String,
        val description: String,
        val address: String,
        val roadAddress: String,
        val mapx: Int,
        val mapy: Int
    ) {
        fun toEntity(): PlaceEntity {
            val title = title.replace("<b>", "").replace("</b>", "")
            val geo = GeoTrans.convert(
                GeoTrans.KATEC,
                GeoTrans.GEO,
                GeoTransPoint(x = mapx.toDouble(), y = mapy.toDouble())
            )
            return PlaceEntity(
                title = title,
                address = roadAddress.takeIf { it.isNotBlank() } ?: address,
                link = link.takeIf { it.isNotBlank() } ?: "https://search.naver.com/search.naver?query=$title",
                description = description.takeIf { it.isNotBlank() } ?: category,
                latitude = geo.y,
                longitude = geo.x
            )
        }
    }
}