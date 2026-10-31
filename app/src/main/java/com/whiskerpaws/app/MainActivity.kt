package com.whiskerpaws.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {

    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. MONETIZATION LAYER: Initialize AdMob Mobile Ads Engine
        MobileAds.initialize(this) {}

        // Bind and load a fresh persistent test banner into the layout base
        val adContainer = findViewById<FrameLayout>(R.id.admobBannerContainer)
        adView = AdView(this).apply {
            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Standard AdMob testing banner ID
            setAdSize(AdSize.BANNER)
        }
        adContainer.addView(adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        // 2. ROUTING LAYER: Setup Card Action Click Event Listeners
        val cardAIHub = findViewById<CardView>(R.id.cardAIHub)
        val cardFastFood = findViewById<CardView>(R.id.cardFastFood)
        val cardOnlineShopping = findViewById<CardView>(R.id.cardOnlineShopping)

        // Redirect clicks to CategoryActivity, passing along the chosen group bundle token
        cardAIHub.setOnClickListener {
            launchCategoryWindow("AI_HUB")
        }

        cardFastFood.setOnClickListener {
            launchCategoryWindow("FAST_FOOD")
        }

        cardOnlineShopping.setOnClickListener {
            launchCategoryWindow("ONLINE_SHOPPING")
        }
    }

    private fun launchCategoryWindow(categoryToken: String) {
        val intent = Intent(this, CategoryActivity::class.java).apply {
            putExtra("SELECTED_CATEGORY", categoryToken)
        }
        startActivity(intent)
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}
