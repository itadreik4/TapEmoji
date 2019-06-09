package com.tadreik.emoji

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tadreik.emoji.MyVariables.player

class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        val moneyEarnedText = findViewById<TextView>(R.id.moneystats)
        val moneySpentText = findViewById<TextView>(R.id.spendstats)
        val purchases = findViewById<TextView>(R.id.numberofpurchasestats)
        val clicksMade = findViewById<TextView>(R.id.clickstats)

        fun updateText() {
            moneyEarnedText.text = "Total money earned: ${player.moneyEarned}"
            moneySpentText.text = "Total money spent: ${player.moneySpent}"
            purchases.text = "Purchases made: ${player.numberOfPurchases}"
            clicksMade.text = "Total clicks: ${player.totalClicks}"
        }

        updateText()




    }
}
