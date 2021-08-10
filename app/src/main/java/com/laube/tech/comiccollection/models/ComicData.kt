package com.laube.tech.comiccollection.models

import android.os.Parcel
import android.os.Parcelable
import com.laube.tech.comiccollection.models.response.MarvelResponse


class ComicData() : Parcelable {
    var coverLink: String? = ""
    var issueTitle: String? = ""
    var issueLength: Long = 0
    var coverImageName: String? = ""
    var issueDescription: String? = ""

    constructor(parcel: Parcel) : this() {
        coverLink = parcel.readString()
        issueTitle = parcel.readString()
        issueLength = parcel.readLong()
        coverImageName = parcel.readString()
        issueDescription = parcel.readString()
    }

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


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(coverLink)
        parcel.writeString(issueTitle)
        parcel.writeLong(issueLength)
        parcel.writeString(coverImageName)
        parcel.writeString(issueDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicData> {
        override fun createFromParcel(parcel: Parcel): ComicData {
            return ComicData(parcel)
        }

        override fun newArray(size: Int): Array<ComicData?> {
            return arrayOfNulls(size)
        }
    }


}