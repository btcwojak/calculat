package com.spudg.calculat

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.spudg.calculat.databinding.ActivityAboutBinding
import com.spudg.calculat.databinding.DialogTermsOfUseBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var bindingAbout: ActivityAboutBinding
    private lateinit var bindingTermsOfUse: DialogTermsOfUseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAbout = ActivityAboutBinding.inflate(layoutInflater)
        val view = bindingAbout.root
        setContentView(view)

        val version = packageManager.getPackageInfo(packageName, 0).versionName
        bindingAbout.calculatDesc.text = getString(R.string.version_by_ss, version.toString())


        bindingAbout.backToCalcListFromAbout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        bindingAbout.privacyPolicy.setOnClickListener {
            privacyPolicy()
        }

        bindingAbout.termsOfUse.setOnClickListener {
            termsOfUse()
        }

        bindingAbout.rateBtn.setOnClickListener {
            rate()
        }

        bindingAbout.emailBtn.setOnClickListener {
            email()
        }

    }

    private fun privacyPolicy() {
        val url =
                "https://docs.google.com/document/d/1PeOCyN7mB3VeqWVQJtaF1v_44hyH1lOuZ6lMFmkZh5E"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun termsOfUse() {
        val termsOfUseDialog = Dialog(this, R.style.Theme_Dialog)
        termsOfUseDialog.setCancelable(false)
        bindingTermsOfUse = DialogTermsOfUseBinding.inflate(layoutInflater)
        val view = bindingTermsOfUse.root
        termsOfUseDialog.setContentView(view)
        termsOfUseDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingTermsOfUse.tvDoneTU.setOnClickListener {
            termsOfUseDialog.dismiss()
        }

        termsOfUseDialog.show()

    }

    private fun email() {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("spudgstudios@gmail.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Calculat - Suggestion / bug report")

        try {
            startActivity(Intent.createChooser(intent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                    this,
                    "There are no email clients installed.",
                    Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun rate() {
        val url =
                "https://play.google.com/store/apps/details?id=com.spudg.calculat"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}