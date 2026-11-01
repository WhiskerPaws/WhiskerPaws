package com.whiskerpaws.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds

class MainDashboardActivity : AppCompatActivity() {
    private lateinit var rewardManager: RewardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        MobileAds.initialize(this) {}
        rewardManager = RewardManager(this)

        // Wallet Ledger Redirection Navigation
        findViewById<Button>(R.id.btnLedger).setOnClickListener {
            startActivity(Intent(this, PayoutStatusActivity::class.java))
        }

        // Native Modules Links
        findViewById<Button>(R.id.btnJobBoard).setOnClickListener {
            startActivity(Intent(this, JobBoardActivity::class.java))
        }

        findViewById<Button>(R.id.btnDailyCheckin).setOnClickListener {
            if (rewardManager.canEarnToday("ATTENDANCE", 1)) {
                rewardManager.addCoins(25)
                rewardManager.trackClick("ATTENDANCE")
                Toast.makeText(this, "Daily attendance claimed! +25 pawcoins", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Already claimed today!", Toast.LENGTH_SHORT).show()
            }
        }

        // Web Utility Matrix Actions
        findViewById<Button>(R.id.btnFXTracker).setOnClickListener { handleInteraction("https://google.com", "USD to PHP Live Exchange", "FX", 5, 3) }
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
}
