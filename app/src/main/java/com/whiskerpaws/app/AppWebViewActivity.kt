package com.whiskerpaws.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AppWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var mInterstitialAd: InterstitialAd? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        webView = WebView(this)
        setContentView(webView)

        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true

        webView.webViewClient = WebViewClient()

        val targetUrl = intent.getStringExtra("TARGET_URL") ?: "https://google.com"
        val pageTitle = intent.getStringExtra("PAGE_TITLE") ?: "Portal View"
        
        supportActionBar?.title = pageTitle
        webView.loadUrl(targetUrl)

        // Load your Unit 3 Interstitial Gate in the background during the user session
        loadInterstitialAd()
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        // Official Google Test Interstitial ID
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // Natural Session Gate: If the ad loaded during the session, show it right when leaving
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            }
            super.onBackPressed()
        }
    }
}
