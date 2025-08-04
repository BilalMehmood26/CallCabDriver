package com.buzzware.callcabdriver.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.buzzware.callcabdriver.R
import com.buzzware.callcabdriver.databinding.ActivityLanguageDetailBinding
import com.buzzware.callcabdriver.ui.util.UserSession

class LanguageDetailActivity : BaseActivity() {

    private val binding: ActivityLanguageDetailBinding by lazy {
        ActivityLanguageDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setViews()
        setListener()
    }

    private fun setViews() {
        val selectedLang = UserSession.userLanguage

        val blue = ContextCompat.getColor(this, R.color.black)
        val gray = ContextCompat.getColor(this, R.color.black)
        val blueCircle = ContextCompat.getDrawable(this, R.drawable.blue_circle)

        val allButtons = listOf(
            binding.english, binding.spanish
        )

        allButtons.forEach {
            it.setTextColor(gray)
            it.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }

        val selectedButton = when (selectedLang) {
            "en" -> binding.english
            "es" -> binding.spanish
            else -> null
        }

        selectedButton?.apply {
            setTextColor(blue)
            setCompoundDrawablesWithIntrinsicBounds(null, null, blueCircle, null)
            compoundDrawablePadding = 8
        }
    }

    private fun setListener() {

        binding.english.setOnClickListener {
            updateLanguage("en")
        }

        binding.spanish.setOnClickListener {
            updateLanguage("es")
        }
    }

    private fun updateLanguage(langCode: String) {
        UserSession.userLanguage = langCode
        saveLanguagePreference(this, langCode)

        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun saveLanguagePreference(context: Context, languageCode: String) {
        val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("language", languageCode)
        editor.apply()
    }
}