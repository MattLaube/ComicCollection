package com.laube.tech.comiccollection.models.response

data class Series (
    val name: String,
    val resourceURI: String
){
    fun getComicId(): String?{
        val index = resourceURI.lastIndexOf( "/" )
        if (index == -1){
            return null
        }
        return resourceURI.substring(index+1)
    }
}