package com.buzzware.callcabdriver.ui.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import com.buzzware.callcabdriver.R
import java.util.Locale

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val langCode = sharedPref.getString("language", "en") ?: "en"

        UserSession.userLanguage = langCode
        setAppLocale(langCode)
    }

    private fun setAppLocale(language: String) {
        val parts = language.split("-")
        val locale = if (parts.size == 2) Locale(parts[0], parts[1]) else Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "YOUR_CHANNEL_ID"
            val channelName = "Your Channel Name"
            val importance = NotificationManager.IMPORTANCE_HIGH

           // val soundUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.driver_accept_notification)
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

            val channel = NotificationChannel(channelId, channelName, importance).apply {
               // setSound(soundUri, attributes)
                enableLights(true)
                lightColor = Color.BLUE
                enableVibration(true)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}