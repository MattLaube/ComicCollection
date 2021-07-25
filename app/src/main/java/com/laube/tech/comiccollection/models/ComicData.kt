package com.laube.tech.comiccollection.models

import com.laube.tech.comiccollection.models.response.MarvelResponse


class ComicData() {
    var coverLink: String = ""
    var issueTitle: String = ""
    var issueLength: Long = 0
    var coverImageName: String = ""
    var issueDescription: String = ""

    constructor(newData: MarvelResponse) : this() {
        coverLink = fetchCoverLink(newData)
        issueTitle = fetchIssueTitle(newData)
        issueLength = fetchIssueLength(newData)
        coverImageName = fetchCoverImageName(newData)
        issueDescription = fetchIssueDescription(newData)
    }

    private fun fetchCoverLink(newData: MarvelResponse): String {
        return newData.data.results.firstOrNull()?.images?.firstOrNull()?.path ?: ""
    }

    private fun fetchIssueTitle(newData: MarvelResponse): String {
        return newData.data.results.firstOrNull()?.title ?: ""
    }

    private fun fetchIssueLength(newData: MarvelResponse): Long {
        return newData.data.results.firstOrNull()?.pageCount ?: 0
    }

    private fun fetchCoverImageName(newData: MarvelResponse): String {
        return newData.data.results.firstOrNull()?.id.toString() ?: "0"
    }

    private fun fetchIssueDescription(newData: MarvelResponse): String {
        return newData.data.results.firstOrNull()?.description ?: ""
    }

    override fun toString(): String {
        return "$issueTitle $issueLength Pages"
    }

    fun getCoverLink(size: String, extension: String): String? {
        val updatedUrl: String
        if (coverLink.startsWith("http:", true)) {
            updatedUrl = coverLink.replace("http:", "https:")
        } else {
            updatedUrl = coverLink
        }
        return "$updatedUrl/$size.$extension"
    }


}