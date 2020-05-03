package com.szlachta.medialibrary.ui

enum class ItemStatusEnum(val position: Int, val key: String) {
    PLANNED(0, "planned"),
    IN_PROGRESS(1, "in_progress"),
    FINISHED(2, "finished"),
    ;

    companion object {
        const val ARG = "item-status"

        fun getByPosition(position: Int): ItemStatusEnum {
            return values()[position]
        }
    }
}