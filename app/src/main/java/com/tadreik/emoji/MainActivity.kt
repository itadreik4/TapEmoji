package com.tadreik.emoji

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.muddzdev.styleabletoast.StyleableToast
import com.tadreik.emoji.MyVariables.clickValue
import com.tadreik.emoji.MyVariables.maxXp
import com.tadreik.emoji.MyVariables.multiplier
import com.tadreik.emoji.MyVariables.multiplier2
import com.tadreik.emoji.MyVariables.player
import com.tadreik.emoji.MyVariables.xp
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {

    private fun hideSystemUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideSystemUi()

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = sharedPref.edit()
        val moneyView = findViewById<TextView>(R.id.money_view)
        val multipliertext = findViewById<TextView>(R.id.multipliertext)
        val faceImage = findViewById<ImageView>(R.id.faceImage)
        val xpBar = findViewById<ProgressBar>(R.id.xpBar)
        val levelText = findViewById<TextView>(R.id.leveltext)
        val medalImage = findViewById<ImageView>(R.id.medalImage)

        val multiplier2Name = findViewById<TextView>(R.id.name_multiplier2)
        val multiplier2Price = findViewById<TextView>(R.id.price_multiplier2)
        val multiplier2Item = findViewById<CardView>(R.id.item_multiplier2)

        val levelUp1Name = findViewById<TextView>(R.id.name_levelup1)
        val levelUp1Price = findViewById<TextView>(R.id.price_levelup1)
        val levelUp1Item = findViewById<CardView>(R.id.item_levelup1)

        val mgr = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mgr.hideSoftInputFromWindow(cheatbox.windowToken, 0)


        constraintLayout1.setOnClickListener {
            mgr.hideSoftInputFromWindow(cheatbox.windowToken, 0)
        }


        fun save() {
            editor.putInt("money", player.money)
            editor.putInt("level", player.level)
            editor.putInt("moneyspent", player.moneySpent)
            editor.putInt("moneyearned", player.moneyEarned)
            editor.putInt("numberofpurchases", player.numberOfPurchases)
            editor.putInt("totalclicks", player.totalClicks)
            editor.putFloat("multiplier", multiplier)
            editor.putFloat("clickvalue", clickValue)
            editor.putInt("xp", xp)
            editor.apply()
        }

        fun loadStats() {
            val savedMoney = sharedPref.getInt("money", player.money)
            val savedLevel = sharedPref.getInt("level", player.level)
            val savedMoneySpent = sharedPref.getInt("moneyspent", player.moneySpent)
            val savedMoneyEarned = sharedPref.getInt("moneyearned",player.moneyEarned)
            val savedPurchases = sharedPref.getInt("numberofpurchases",player.numberOfPurchases)
            val savedTotalClicks = sharedPref.getInt("totalclicks",player.totalClicks)
            val savedMultiplier = sharedPref.getFloat("multiplier", multiplier)
            val savedClickValue = sharedPref.getFloat("clickvalue", clickValue)
            val savedXp = sharedPref.getInt("xp", xp)
            player.money = savedMoney
            player.level = savedLevel
            player.moneySpent = savedMoneySpent
            player.moneyEarned = savedMoneyEarned
            player.numberOfPurchases = savedPurchases
            player.totalClicks = savedTotalClicks
            xp = savedXp
            multiplier = savedMultiplier
            clickValue = savedClickValue
        }

        fun checkOnLevel() {
            if (player.level >= 10) { medalImage.setImageDrawable(resources.getDrawable(R.drawable.third_place_medal, applicationContext.theme)) }
            if (player.level >= 50) { medalImage.setImageDrawable(resources.getDrawable(R.drawable.third_place_medal, applicationContext.theme)) }
        }

        fun checkOnMoney() {
            when (player.money) {
                in 101..130 -> { faceImage.setImageDrawable(resources.getDrawable(R.drawable.smiling_face_with_heart_shaped_eyes, applicationContext.theme)) }
                in 400..600 -> { faceImage.setImageDrawable(resources.getDrawable(R.drawable.smiling_face_with_heart_shaped_eyes, applicationContext.theme)) }
                in 1500..2000 -> { faceImage.setImageDrawable(resources.getDrawable(R.drawable.smiling_face_with_heart_shaped_eyes, applicationContext.theme)) }
            }
        }


        fun updateText() {
            multipliertext.text = "Multiplier: ${multiplier}x"
            moneyView.text = "${player.money}"
            levelText.text = "Level: ${player.level}"

            multiplier2Name.text = MyVariables.multiplier2.name
            multiplier2Price.text = "${MyVariables.multiplier2.price}"

            levelUp1Name.text = MyVariables.levelUp1.name
            levelUp1Price.text = "${MyVariables.levelUp1.price}"

            checkOnMoney()
        }

        fun levelUpComplete() {
            xp = 0
            xpBar.progress = xp
            player.level += 1
            StyleableToast.makeText(this, "Level Up!", R.style.mytoast).show()
        }

        fun checkOnXp() {
            if (xp < maxXp) {
                xp += 1
                xpBar.progress = xp
            }
            if (xp == maxXp) {
                xpBar.progress = xp
                levelUpComplete()
            }
        }

        fun reset() {
            player.money = 0
            player.moneyEarned = 0
            player.moneySpent = 0
            player.level = 1
            player.numberOfPurchases = 0
            player.totalClicks = 0
            multiplier2.price = 500
            multiplier = 1.0f
            clickValue = 1.0f
            xp = 0
            xpBar!!.progress = xp
            updateText()
            checkOnLevel()
            checkOnMoney()
            save()
        }

        settingsbutton.setOnClickListener {
            reset()
        }

        cheatbutton.setOnClickListener {
            if(cheatbox.length() > 1) {
                val value = cheatbox.text.toString()
                val finalVal = Integer.parseInt(value)
                player.money += finalVal
                updateText()
                checkOnMoney()
            } else {
                StyleableToast.makeText(this, "Too small", R.style.mytoast).show()
            }

        }


        fun onTouchEmoji() {
            player.money += (clickValue * multiplier).toInt()
            player.moneyEarned += (clickValue * multiplier).toInt()
            player.totalClicks += 1
            checkOnXp()
            updateText()
            checkOnMoney()
            save()
        }

        multiplier2Item.setOnClickListener {
            if (player.money >= MyVariables.multiplier2.price && player.level > 1) {
                multiplier += .2f
                player.moneySpent += MyVariables.multiplier2.price
                player.money -= MyVariables.multiplier2.price
                MyVariables.multiplier2.price += 500
                player.numberOfPurchases += 1
                save()
                updateText()
            } else if (player.level < 2) {
                StyleableToast.makeText(this, "Level ${MyVariables.multiplier2.levelRequired} Required", R.style.mytoast).show()
            } else {
                StyleableToast.makeText(this, "Not enough money", R.style.mytoast).show()
            }
        }

        levelUp1Item.setOnClickListener {
            if (player.money >= MyVariables.levelUp1.price && player.level > 9) {
                player.moneySpent += MyVariables.levelUp1.price
                player.money -= MyVariables.levelUp1.price
                MyVariables.levelUp1.price += 5000
                player.numberOfPurchases += 1
                player.level += 1
                save()
                updateText()
            } else if (player.level < 10) {
                StyleableToast.makeText(this, "Level ${MyVariables.levelUp1.levelRequired} Required", R.style.mytoast).show()
            } else {
                StyleableToast.makeText(this, "Not enough money", R.style.mytoast).show()
            }
        }

        faceImage.setOnClickListener {
            onTouchEmoji()
        }
        loadStats()
        updateText()
        checkOnLevel()
        xpBar!!.progress = xp
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
    }


    override fun onRestart() {
        super.onRestart()
        hideSystemUi()
    }

    override fun onPause() {
        super.onPause()
        hideSystemUi()
    }
}
  