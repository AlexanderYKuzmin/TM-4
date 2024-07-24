package com.kuzmin.tm_4.feature.site_creation.extensions

fun Int.isSectionId(): Boolean {
    return this in 100..130
}

fun Int.getOriginalId(parentId: Int): Int {
    return this - parentId * 3
}

fun Int.transformId(parentId: Int): Int {
    return this + parentId * 3
}