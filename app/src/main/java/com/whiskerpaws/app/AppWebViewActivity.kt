package com.whiskerpaws.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AppWebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private var mInterstitialAd: InterstitialAd? = null
    private var currentUrl: String = ""
    private var currentTitle: String = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        webView = WebView(this)
        setContentView(webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.webViewClient = WebViewClient()

        currentUrl = intent.getStringExtra("TARGET_URL") ?: "https://google.com"
        currentTitle = intent.getStringExtra("PAGE_TITLE") ?: "Portal View"
        
        supportActionBar?.title = currentTitle
        webView.loadUrl(currentUrl)

        loadInterstitialAd()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.add(Menu.NONE, 99, Menu.NONE, "Save to Profile")
        item?.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        item?.setIcon(android.R.drawable.btn_star_big_on)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == 99) {
            val urlToSave = webView.url ?: currentUrl
            val prefs = getSharedPreferences("SavedPortalsPrefs", Context.MODE_PRIVATE)
            prefs.edit().putString(currentTitle, urlToSave).apply()
            Toast.makeText(this, "📌 Pinned '$currentTitle' to your Dashboard Profile!", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
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
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            }
            super.onBackPressed()
        }
    }
}
