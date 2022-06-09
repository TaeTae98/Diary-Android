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

        const val PLACE_SEARCH_URL = "$PLACE_URL/search"

        fun getPlaceDetailAction(id: Long = 0L) = "$PLACE_DETAIL_URL_PREFIX/$id"
    }

    object File {
        private const val FOLDER_URL = "$APP_URL/folder"
        const val FILE_URL = "$APP_URL/file"

        private const val FOLDER_DETAIL_URL_PREFIX = "$FOLDER_URL/detail"
        const val FOLDER_DETAIL_URL = "$FOLDER_DETAIL_URL_PREFIX/{${Parameter.ID}}?${Parameter.PARENT_ID}={${Parameter.PARENT_ID}}"

        fun getFolderDetailAction(id: Long = 0L, parentId: Long? = null) = "$FOLDER_DETAIL_URL_PREFIX/$id?${Parameter.PARENT_ID}=${parentId ?: 0L}"
    }

    object Setting {
        const val SETTING_URL = "$APP_URL/setting"
    }

    object Developer {
        const val DEVELOPER_URL = "$APP_URL/developer"
        const val EXCEPTION_LOG_URL = "$DEVELOPER_URL/exception/log"
    }

    object More {
        const val MORE_URL = "$APP_URL/more"
    }
}