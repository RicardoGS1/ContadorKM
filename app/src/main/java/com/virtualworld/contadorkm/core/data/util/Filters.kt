package com.virtualworld.contadorkm.core.data.util

enum class RunSortOrder {
    DATE,
    DURATION,
    AVG_SPEED,
    DISTANCE;

    override fun toString(): String {
        return super.toString()
            .lowercase()
            .replace('_', ' ')
            .replaceFirstChar { it.uppercase() }
    }
}
