package com.whiskerpaws.app

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Ads
        MobileAds.initialize(this) {}
        val adContainer = findViewById<FrameLayout>(R.id.admobBannerContainer)
        adView = AdView(this).apply {
            adUnitId = "ca-app-pub-3940256099942544/6300978111"
            setAdSize(AdSize.BANNER)
        }
        adContainer.addView(adView)
        adView.loadAd(AdRequest.Builder().build())

        // Setup Portal Routing Hooks
        linkButton(R.id.btnChatGPT, "https://chatgpt.com")
        linkButton(R.id.btnGemini, "https://google.com")
        linkButton(R.id.btnClaude, "https://claude.ai")
        linkButton(R.id.btnJollibee, "https://jollibeedelivery.com")
        linkButton(R.id.btnMcDo, "https://mcdelivery.com.ph")
        linkButton(R.id.btnFoodpanda, "https://foodpanda.ph")
        linkButton(R.id.btnLazada, "https://lazada.com.ph")
        linkButton(R.id.btnShopee, "https://shopee.ph")
    }

    private fun linkButton(buttonId: Int, targetUrl: String) {
        findViewById<Button>(buttonId).setOnClickListener {
            val customTabsIntent = CustomTabsIntent.Builder().apply {
                setShowTitle(true)
                setInstantAppsEnabled(true)
            }.build()
            customTabsIntent.launchUrl(this, Uri.parse(targetUrl))
        }
    }

    override fun onPause() { adView.pause(); super.onPause() }
    override fun onResume() { super.onResume(); adView.resume() }
    override fun onDestroy() { adView.destroy(); super.onDestroy() }
}
