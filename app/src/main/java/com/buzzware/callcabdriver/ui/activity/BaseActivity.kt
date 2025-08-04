package com.buzzware.callcabdriver.ui.activity

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import com.buzzware.callcabdriver.ui.util.UserSession

open class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        val langCode = UserSession.userLanguage ?: "en"
        val context = updateBaseContextLocale(newBase, langCode)
        super.attachBaseContext(context)
    }

    private fun updateBaseContextLocale(context: Context, language: String): Context {
        val parts = language.split("-")
        val locale = if (parts.size == 2) Locale(parts[0], parts[1]) else Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}