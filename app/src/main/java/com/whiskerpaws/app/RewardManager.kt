package com.whiskerpaws.app

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RewardManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("MoneyPawsRewards", Context.MODE_PRIVATE)

    fun getBalance(): Int = prefs.getInt("PAWCOINS_BALANCE", 0)

    fun addCoins(amount: Int) {
        val currentBalance = getBalance()
        prefs.edit().putInt("PAWCOINS_BALANCE", currentBalance + amount).apply()
    }

    fun canEarnToday(category: String, maxDailyClips: Int): Boolean {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val countKey = "${category}_${today}_count"
        val currentCount = prefs.getInt(countKey, 0)
        return currentCount < maxDailyClips
    }

    fun trackClick(category: String) {
        val today = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())
        val countKey = "${category}_${today}_count"
        val currentCount = prefs.getInt(countKey, 0)
        prefs.edit().putInt(countKey, currentCount + 1).apply()
    }
}
