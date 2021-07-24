package com.laube.tech.comiccollection.models.response

data class Creators (
    val available: Long,
    val collectionURI: String,
    val items: List<CreatorsItem>,
    val returned: Long
)