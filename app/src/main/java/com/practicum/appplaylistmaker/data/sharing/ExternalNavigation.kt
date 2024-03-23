package com.practicum.appplaylistmaker.data.sharing

data class SupportEmailInfo(
    var emailMessage: String,
    var messageTitle: String,
    var emailAddress: String
)

interface ExternalNavigation {
    fun shareLink(link: String)
    fun openLink(url: String)
    fun sendEmail(
        recipientEmail: String,
        subject: String,
        body: String
    )

    fun getSupportEmailInfo(): SupportEmailInfo

    fun getCourseLink(): String

    fun getUserAgreementLink(): String


}