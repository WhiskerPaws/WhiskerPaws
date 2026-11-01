package com.whiskerpaws.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PayoutStatusActivity : AppCompatActivity() {
    private lateinit var rewardManager: RewardManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payout_status)

        rewardManager = RewardManager(this)
        
        val txtPayoutBalance = findViewById<TextView>(R.id.txtPayoutBalance)
        val btnRequestPayout = findViewById<Button>(R.id.btnRequestPayout)

        val currentCoins = rewardManager.getBalance()
        txtPayoutBalance.text = "🪙 $currentCoins pawcoins"

        btnRequestPayout.setOnClickListener {
            if (currentCoins >= 10000) {
                Toast.makeText(this, "Payout request submitted to secure queue!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Minimum withdrawal requirement is 10,000 pawcoins ($10 USD).", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
