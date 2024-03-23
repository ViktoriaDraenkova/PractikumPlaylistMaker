package com.practicum.appplaylistmaker.data.sharing.impl

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.practicum.appplaylistmaker.R

import com.practicum.appplaylistmaker.data.sharing.ExternalNavigation
import com.practicum.appplaylistmaker.data.sharing.SupportEmailInfo

class ExternalNavigationImpl(
    private var context: Context
) : ExternalNavigation {

    override fun shareLink(link: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        context.startActivity(
            Intent.createChooser(shareIntent, link).addFlags(
                FLAG_ACTIVITY_NEW_TASK
            )
        )
    }

    override fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.data = Uri.parse(url)
        context.startActivity(intent)
    }

    override fun sendEmail(recipientEmail: String, subject: String, body: String) {
        val emailIntent =
            Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", recipientEmail, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        context.startActivity(
            Intent.createChooser(
                emailIntent,
                context.getString(R.string.tosupp)
            ).addFlags(FLAG_ACTIVITY_NEW_TASK)
        )
    }

    override fun getSupportEmailInfo(): SupportEmailInfo {
        val recipientEmail = context.getString(R.string.my_email)
        val subject = context.getString(R.string.list_to_devs_pm)
        val body = context.getString(R.string.list_to_devs)
        return SupportEmailInfo(body, subject, recipientEmail)
    }


    override fun getCourseLink(): String {
        return context.getString(R.string.link_to_the_course)
    }

    override fun getUserAgreementLink(): String {
        return context.getString(R.string.offer)
    }
}