package com.whiskerpaws.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainDashboardActivity : AppCompatActivity() {
    private lateinit var rewardManager: RewardManager
    private lateinit var txtCoinBalance: TextView
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dashboard)

        // Initialize Google AdMob Core
        MobileAds.initialize(this) {}
        setupBottomBannerAd()

        rewardManager = RewardManager(this)
        txtCoinBalance = findViewById(R.id.txtCoinBalance)
        updateBalanceDisplay()

        // Bind AI Model Navigation Elements
        findViewById<Button>(R.id.btnChatGPT).setOnClickListener { handleInteraction("https://chatgpt.com", "ChatGPT Workspace", "AI_CHAT", 12, 5) }
        findViewById<Button>(R.id.btnGemini).setOnClickListener { handleInteraction("https://google.com", "Google Gemini Workspace", "AI_GEMINI", 12, 5) }
        findViewById<Button>(R.id.btnClaude).setOnClickListener { handleInteraction("https://claude.ai", "Claude AI Workspace", "AI_CLAUDE", 12, 5) }
        findViewById<Button>(R.id.btnGrok).setOnClickListener { handleInteraction("https://x.com", "Grok Workspace", "AI_GROK", 12, 5) }
        findViewById<Button>(R.id.btnPerplexity).setOnClickListener { handleInteraction("https://perplexity.ai", "Perplexity AI Workspace", "AI_PERPLEX", 12, 5) }

        // Bind Utilities & Networks Core Matrix
        findViewById<Button>(R.id.btnSocial).setOnClickListener { handleInteraction("https://facebook.com", "Social Network Hub", "SOCIAL_NET", 5, 5) }
        findViewById<Button>(R.id.btnGovernment).setOnClickListener { handleInteraction("https://bworldonline.com", "News & Business Portal", "NEWS_BIZ", 10, 3) }
        findViewById<Button>(R.id.btnShopping).setOnClickListener { handleInteraction("https://shopee.ph", "Shopping Marketplace", "SHOPPING", 5, 5) }
        findViewById<Button>(R.id.btnEntertainment).setOnClickListener { handleInteraction("https://youtube.com", "Entertainment & Music Hub", "ENTERTAINMENT", 15, 5) }
        findViewById<Button>(R.id.btnHealth).setOnClickListener { handleInteraction("https://philhealth.gov.ph", "Health & Wellness", "HEALTH", 10, 2) }
        findViewById<Button>(R.id.btnFinance).setOnClickListener { handleInteraction("https://okx.com", "Crypto & Financial Management", "CRYPTO_FIN", 10, 2) }
        findViewById<Button>(R.id.btnUtilities).setOnClickListener { handleInteraction("https://meralco.com.ph", "Utility Bills Payment", "UTILITIES", 5, 3) }
        findViewById<Button>(R.id.btnTransportation).setOnClickListener { handleInteraction("https://foodpanda.ph", "Logistics & Food Delivery", "TRANSPORTATION", 5, 5) }
    }

    private fun setupBottomBannerAd() {
        val bannerContainer = findViewById<LinearLayout>(R.id.admobBannerContainer)
        adView = AdView(this).apply {
            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Official Google Test Banner ID
            setAdSize(AdSize.BANNER)
        }
        bannerContainer.addView(adView)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }

    private fun handleInteraction(url: String, title: String, categoryKey: String, awardAmount: Int, dailyCap: Int) {
        if (rewardManager.canEarnToday(categoryKey, dailyCap)) {
            rewardManager.addCoins(awardAmount)
            rewardManager.trackClick(categoryKey)
            Toast.makeText(this, "Earned +$awardAmount pawcoins!", Toast.LENGTH_SHORT).show()
            updateBalanceDisplay()
        } else {
            Toast.makeText(this, "Daily reward cap reached for this section!", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, AppWebViewActivity::class.java).apply {
            putExtra("TARGET_URL", url)
            putExtra("PAGE_TITLE", title)
        }
        startActivity(intent)
    }

    private fun updateBalanceDisplay() {
        txtCoinBalance.text = "🪙 ${rewardManager.getBalance()}"
    }

    override fun onResume() {
        super.onResume()
        updateBalanceDisplay()
        adView?.resume()
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }
}
