package com.whiskerpaws.app

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class CategoryActivity : AppCompatActivity() {

    private lateinit var categoryAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        // 1. NAVIGATION LAYER: Back button operation
        findViewById<ImageButton>(R.id.buttonBack).setOnClickListener {
            finish()
        }

        // 2. DATA SEGREGATION LAYER: Fetch token and match listing arrays
        val selectedCategory = intent.getStringExtra("SELECTED_CATEGORY") ?: "AI_HUB"
        val headerTitleView = findViewById<TextView>(R.id.textCategoryHeaderTitle)
        val merchantList = ArrayList<MerchantModel>()

        // Populate listings safely based on user category selection
        when (selectedCategory) {
            "AI_HUB" -> {
                headerTitleView.text = "Artificial Intelligence Hub"
                merchantList.add(MerchantModel("ChatGPT", "https://chatgpt.com"))
                merchantList.add(MerchantModel("Gemini", "https://google.com"))
                merchantList.add(MerchantModel("Claude AI", "https://claude.ai"))
                merchantList.add(MerchantModel("Grok AI", "https://grok.com"))
                merchantList.add(MerchantModel("Perplexity", "https://perplexity.ai"))
            }
            "FAST_FOOD" -> {
                headerTitleView.text = "Fast Food & Delivery"
                merchantList.add(MerchantModel("Jollibee Web", "https://jollibeedelivery.com"))
                merchantList.add(MerchantModel("McDonald's PH", "https://mcdelivery.com.ph"))
                merchantList.add(MerchantModel("KFC Delivery", "https://kfc.com.ph"))
                merchantList.add(MerchantModel("Foodpanda", "https://foodpanda.ph"))
                merchantList.add(MerchantModel("Grab PH", "https://grab.com"))
            }
            "ONLINE_SHOPPING" -> {
                headerTitleView.text = "Online Shopping"
                merchantList.add(MerchantModel("Lazada PH", "https://lazada.com.ph"))
                merchantList.add(MerchantModel("Shopee PH", "https://shopee.ph"))
                merchantList.add(MerchantModel("TikTok Shop", "https://tiktok.com"))
            }
        }

        // 3. RENDER GRID LAYER: Bind data list to a clean 2-column square layout grid
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMerchants)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = MerchantAdapter(merchantList)

        // 4. MONETIZATION LAYER: Secondary independent footer ad container load
        val adContainer = findViewById<FrameLayout>(R.id.categoryAdmobBanner)
        categoryAdView = AdView(this).apply {
            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Standard AdMob testing banner ID
            setAdSize(AdSize.BANNER)
        }
        adContainer.addView(categoryAdView)
        val adRequest = AdRequest.Builder().build()
        categoryAdView.loadAd(adRequest)
    }

    override fun onPause() {
        categoryAdView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        categoryAdView.resume()
    }

    override fun onDestroy() {
        categoryAdView.destroy()
        super.onDestroy()
    }
}
