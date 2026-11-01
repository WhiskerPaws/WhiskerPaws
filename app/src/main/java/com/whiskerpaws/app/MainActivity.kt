package com.whiskerpaws.app

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF8FAFC)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        
                        // 1. TOP HEADER LAYER: Hospitality Greeting
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "Mabuhay! Welcome back to WhiskerPaws. 🐾",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "Thank you so much for opening our hub and choosing to stay with us today! Tap a portal below to explore.",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }

                        // 2. MIDDLE LAYER: Categorized Scrollable Portals
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 14.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            // Category A: AI Portal Hub
                            PortalCategoryCard("🤖 ARTIFICIAL INTELLIGENCE HUB", Color(0xFF2563EB)) {
                                PortalButton("🚀 Launch ChatGPT", "https://chatgpt.com")
                                PortalButton("✨ Launch Google Gemini", "https://google.com")
                                PortalButton("🧠 Launch Claude AI", "https://claude.ai")
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Category B: Fast Food Portals
                            PortalCategoryCard("🍔 FOOD DELIVERY PLACES", Color(0xFFEA580C)) {
                                PortalButton("🐝 Jollibee Food Delivery", "https://jollibeedelivery.com")
                                PortalButton("🍔 McDonald's McDelivery", "https://mcdelivery.com.ph")
                                PortalButton("🐼 Foodpanda Portal", "https://foodpanda.ph")
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Category C: Online Shopping
                            PortalCategoryCard("🛍️ ONLINE SHOPPING PLACES", Color(0xFFDB2777)) {
                                PortalButton("🧡 Lazada Philippines", "https://lazada.com.ph")
                                PortalButton("❤️ Shopee PH Shopping", "https://shopee.ph")
                            }
                            
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // 3. BOTTOM LAYER: Modern AdMob Banner Container
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            AndroidView(
                                factory = { context ->
                                    AdView(context).apply {
                                        adUnitId = "ca-app-pub-3940256099942544/6300978111"
                                        setAdSize(AdSize.BANNER)
                                        loadAd(AdRequest.Builder().build())
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PortalCategoryCard(title: String, themeColor: Color, content: @Composable ColumnScope.() -> Unit) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = themeColor)
                Spacer(modifier = Modifier.height(8.dp))
                content()
            }
        }
    }

    @Composable
    fun PortalButton(label: String, targetUrl: String) {
        Button(
            onClick = {
                val customTabsIntent = CustomTabsIntent.Builder().apply {
                    setShowTitle(true)
                    setInstantAppsEnabled(true)
                }.build()
                customTabsIntent.launchUrl(this@MainActivity, Uri.parse(targetUrl))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
        ) {
            Text(text = label, color = Color.White)
        }
    }
}
