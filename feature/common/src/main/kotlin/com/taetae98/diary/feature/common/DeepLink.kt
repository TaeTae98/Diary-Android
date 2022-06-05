package com.taetae98.diary.feature.common

object DeepLink {
    const val APP_URL = "https://diary"
    const val APP_DEEP_LINK = "app://diary"

    object Memo {
        const val MEMO_URL = "$APP_URL/memo"

        private const val MEMO_DETAIL_URL_PREFIX = "$MEMO_URL/detail"
        const val MEMO_DETAIL_URL = "$MEMO_DETAIL_URL_PREFIX/{${Parameter.ID}}"

        fun getMemoDetailAction(id: Long = 0L) = "$MEMO_DETAIL_URL_PREFIX/$id"

        const val PLACE_SELECT_URL = "$APP_URL/memo/place/select"

        fun getPlaceSelectAction() = PLACE_SELECT_URL
    }

    object Place {
        const val PLACE_URL = "$APP_URL/place"

        private const val PLACE_DETAIL_URL_PREFIX = "$PLACE_URL/detail"
        const val PLACE_DETAIL_URL = "$PLACE_DETAIL_URL_PREFIX/{${Parameter.ID}}"

        fun getPlaceDetailAction(id: Long = 0L) = "$PLACE_DETAIL_URL_PREFIX/$id"
    }
}