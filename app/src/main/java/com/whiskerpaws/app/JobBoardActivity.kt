package com.whiskerpaws.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class JobBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_board)

        val rewardManager = RewardManager(this)
        if (rewardManager.canEarnToday("JOBS", 5)) {
            rewardManager.addCoins(10)
            rewardManager.trackClick("JOBS")
            Toast.makeText(this, "Organically browsing listings! Earned +10 pawcoins", Toast.LENGTH_SHORT).show()
        }
    }
}
