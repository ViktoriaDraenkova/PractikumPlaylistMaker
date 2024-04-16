package com.practicum.appplaylistmaker.domain.sharing.impl

import android.provider.Settings.System.getString
import com.practicum.appplaylistmaker.R
import com.practicum.appplaylistmaker.data.sharing.ExternalNavigation
import com.practicum.appplaylistmaker.domain.sharing.SharingInteractor

class SharingInteractorImpl(val externalNavigation: ExternalNavigation) : SharingInteractor {
    override fun shareApp() {
        externalNavigation.shareLink(
            externalNavigation.getCourseLink()
        )
    }

    override fun openTerms() {
        externalNavigation.openLink(externalNavigation.getUserAgreementLink())
    }

    override fun openSupport() {
        val suppEmailInfo = externalNavigation.getSupportEmailInfo()
        externalNavigation.sendEmail(
            suppEmailInfo.emailAddress,
            suppEmailInfo.messageTitle,
            suppEmailInfo.emailMessage
        )
    }

}