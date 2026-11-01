package com.whiskerpaws.app

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds

class MainDashboardActivity : AppCompatActivity() {
    private lateinit var rewardManager: RewardManager
    private lateinit var containerSavedPortals: LinearLayout
    private lateinit var txtSavedTitle: TextView
    private lateinit var scrollSavedPortals: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        MobileAds.initialize(this) {}
        rewardManager = RewardManager(this)

        containerSavedPortals = findViewById(R.id.containerSavedPortals)
        txtSavedTitle = findViewById(R.id.txtSavedTitle)
        scrollSavedPortals = findViewById(R.id.scrollSavedPortals)

        loadSavedPortals()

        val editUtilitySearch = findViewById<EditText>(R.id.editUtilitySearch)
        findViewById<Button>(R.id.btnSearchGo).setOnClickListener {
            val query = editUtilitySearch.text.toString().trim()
            if (query.isNotEmpty()) {
                var finalUrl = query
                if (!finalUrl.startsWith("http://") && !finalUrl.startsWith("https://")) {
                    finalUrl = "https://google.com"
                }
                handleInteraction(finalUrl, "Search Result", "SEARCH_PORTAL", 5, 5)
            }
        }

        findViewById<Button>(R.id.btnLedger).setOnClickListener {
            startActivity(Intent(this, PayoutStatusActivity::class.java))
        }

        findViewById<Button>(R.id.btnJobBoard).setOnClickListener {
            startActivity(Intent(this, JobBoardActivity::class.java))
        }

        findViewById<Button>(R.id.btnDailyCheckin).setOnClickListener {
            if (rewardManager.canEarnToday("ATTENDANCE", 1)) {
                rewardManager.addCoins(25)
                rewardManager.trackClick("ATTENDANCE")
                Toast.makeText(this, "Attendance bonus claimed! +25 pawcoins", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Already claimed today!", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btnFXTracker).setOnClickListener { handleInteraction("https://google.com", "USD to PHP FX Tracker", "FX", 5, 3) }
        findViewById<Button>(R.id.btnChatGPT).setOnClickListener { handleInteraction("https://chatgpt.com", "ChatGPT", "AI_CHAT", 12, 5) }
        findViewById<Button>(R.id.btnGemini).setOnClickListener { handleInteraction("https://google.com", "Gemini", "AI_GEMINI", 12, 5) }
        findViewById<Button>(R.id.btnClaude).setOnClickListener { handleInteraction("https://claude.ai", "Claude AI", "AI_CLAUDE", 12, 5) }
        findViewById<Button>(R.id.btnGrok).setOnClickListener { handleInteraction("https://x.com", "Grok", "AI_GROK", 12, 5) }
        findViewById<Button>(R.id.btnPerplexity).setOnClickListener { handleInteraction("https://perplexity.ai", "Perplexity AI", "AI_PERPLEX", 12, 5) }
        findViewById<Button>(R.id.btnSocial).setOnClickListener { handleInteraction("https://facebook.com", "Social Network", "SOCIAL", 5, 5) }
        findViewById<Button>(R.id.btnGovernment).setOnClickListener { handleInteraction("https://sss.gov.ph", "Government Services", "GOVT", 10, 3) }
        findViewById<Button>(R.id.btnShopping).setOnClickListener { handleInteraction("https://shopee.ph", "Shopping", "SHOP", 5, 5) }
        findViewById<Button>(R.id.btnEntertainment).setOnClickListener { handleInteraction("https://youtube.com", "Entertainment", "ENTERTAIN", 15, 5) }
        findViewById<Button>(R.id.btnHealth).setOnClickListener { handleInteraction("https://philhealth.gov.ph", "Health Portal", "HEALTH", 10, 2) }
        findViewById<Button>(R.id.btnFinance).setOnClickListener { handleInteraction("https://okx.com", "Crypto Assets", "FIN", 10, 2) }
        findViewById<Button>(R.id.btnUtilities).setOnClickListener { handleInteraction("https://meralco.com.ph", "Utilities Payment", "UTIL", 5, 3) }
        findViewById<Button>(R.id.btnTransportation).setOnClickListener { handleInteraction("https://foodpanda.ph", "Transportation Logistics", "TRANS", 5, 5) }
    }

    private fun handleInteraction(url: String, title: String, categoryKey: String, awardAmount: Int, dailyCap: Int) {
        if (rewardManager.canEarnToday(categoryKey, dailyCap)) {
            rewardManager.addCoins(awardAmount)
            rewardManager.trackClick(categoryKey)
            Toast.makeText(this, "Earned +$awardAmount pawcoins!", Toast.LENGTH_SHORT).show()
        }
        val intent = Intent(this, AppWebViewActivity::class.java).apply {
            putExtra("TARGET_URL", url)
            putExtra("PAGE_TITLE", title)
        }
        startActivity(intent)
    }

    private fun loadSavedPortals() {
        containerSavedPortals.removeAllViews()
        val prefs = getSharedPreferences("SavedPortalsPrefs", Context.MODE_PRIVATE)
        val allSaved = prefs.all

        if (allSaved.isNotEmpty()) {
            txtSavedTitle.visibility = View.VISIBLE
            scrollSavedPortals.visibility = View.VISIBLE
            
            for ((title, url) in allSaved) {
                val btn = Button(this).apply {
                    text = "📌 $title"
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply { setMargins(0, 0, 8, 0) }
                    backgroundTintList = ColorStateList.valueOf(resources.getColor(android.R.color.white, theme))
                    setTextColor(resources.getColor(android.R.color.black, theme))
                    setOnClickListener {
                        handleInteraction(url.toString(), title, "SAVED_LINK", 5, 5)
                    }
                }
                containerSavedPortals.addView(btn)
            }
        } else {
            txtSavedTitle.visibility = View.GONE
            scrollSavedPortals.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        loadSavedPortals()
    }
}
