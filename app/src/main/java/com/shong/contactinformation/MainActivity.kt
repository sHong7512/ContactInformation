package com.shong.contactinformation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val telText = findViewById<EditText>(R.id.phoneNumText)
        val emailText = findViewById<EditText>(R.id.emailText)
        val outLookPackage = "com.microsoft.office.outlook"
        val teamsPackage = "com.microsoft.teams"

        findViewById<Button>(R.id.goTelButton).setOnClickListener{
            val tel = "tel:" + telText.text
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(tel))
            startActivity(intent)
        }

        findViewById<Button>(R.id.goSMSButton).setOnClickListener {
            val defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(applicationContext)
            val intent = Intent(Intent.ACTION_SEND, Uri.parse("smsto:")).apply {
                putExtra("address", telText.text.toString())
                setPackage(defaultSmsPackage)
            }
            startActivity(intent)
        }


        findViewById<Button>(R.id.goOutlookButton).setOnClickListener {
            val checkOutLookIntent: Intent? =
                packageManager.getLaunchIntentForPackage(outLookPackage)
            if (checkOutLookIntent == null) {
                val outLookURL =
                    "https://play.google.com/store/apps/details?id=com.microsoft.office.outlook"
                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(outLookURL))
                startActivity(urlIntent)
            } else {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    setPackage(outLookPackage)
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(emailText.text))
                    type = "text/plain"
                }

//                startActivity(Intent.createChooser(intent, "Select your Email app"))
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.goTeamsButton).setOnClickListener {
            val checkOutLookIntent: Intent? = packageManager.getLaunchIntentForPackage(teamsPackage)
            if (checkOutLookIntent == null) {
                val outLookURL = "https://play.google.com/store/apps/details?id=com.microsoft.teams"
                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(outLookURL))
                startActivity(urlIntent)
            } else {
                val teamsURL = "msteams://teams.microsoft.com/l/chat/0/0?users=${emailText.text}"
                val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(teamsURL))
                startActivity(urlIntent)
            }
        }
    }
}