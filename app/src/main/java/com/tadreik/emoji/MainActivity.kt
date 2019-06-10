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
import kotlinx.android.synthetic.main.activity_main.*
import android.view.inputmethod.InputMethodManager
import com.tadreik.emoji.MyVariables.levelUp1
import com.tadreik.emoji.MyVariables.multiplier2
import com.tadreik.emoji.MyVariables.player


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
        val cashView = findViewById<TextView>(R.id.cash_view)
        val creditView = findViewById<TextView>(R.id.cash_view)
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
            editor.putInt("credit", player.credit)
            editor.putInt("level", player.level)
            editor.putInt("moneyspent", player.moneySpent)
            editor.putInt("moneyearned", player.moneyEarned)
            editor.putInt("numberofpurchases", player.numberOfPurchases)
            editor.putInt("totalclicks", player.totalClicks)
            editor.putInt("creditlimit", player.creditLimit)
            editor.putInt("multiplier", player.multiplier)
            editor.putInt("clickvalue", player.clickValue)
            editor.putInt("multiplierprice", multiplier2.price)
            editor.putInt("xp", player.xp)
            editor.apply()
        }

        fun loadStats() {
            val savedMoney = sharedPref.getInt("money", player.money)
            val savedCredit = sharedPref.getInt("credit", player.credit)
            val savedLevel = sharedPref.getInt("level", player.level)
            val savedMoneySpent = sharedPref.getInt("moneyspent", player.moneySpent)
            val savedMoneyEarned = sharedPref.getInt("moneyearned",player.moneyEarned)
            val savedPurchases = sharedPref.getInt("numberofpurchases",player.numberOfPurchases)
            val savedTotalClicks = sharedPref.getInt("totalclicks",player.totalClicks)
            val savedMultiplier = sharedPref.getInt("multiplier", player.multiplier)
            val savedClickValue = sharedPref.getInt("clickvalue", player.clickValue)
            val savedMultiplierPrice = sharedPref.getInt("multiplierprice", multiplier2.price)
            val savedCreditLimit = sharedPref.getInt("creditlimit", player.creditLimit)
            val savedXp = sharedPref.getInt("xp", player.xp)
            player.money = savedMoney
            player.credit = savedCredit
            player.level = savedLevel
            player.moneySpent = savedMoneySpent
            player.moneyEarned = savedMoneyEarned
            player.numberOfPurchases = savedPurchases
            player.totalClicks = savedTotalClicks
            multiplier2.price = savedMultiplierPrice
            player.creditLimit = savedCreditLimit
            player.xp = savedXp
            player.multiplier = savedMultiplier
            player.clickValue = savedClickValue
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
            multipliertext.text = "Multiplier: ${player.multiplier}x"
            cashView.text = "${player.money}"
            levelText.text = "Level: ${player.level}"

            multiplier2Name.text = multiplier2.name
            multiplier2Price.text = "${multiplier2.price}"

            levelUp1Name.text = levelUp1.name
            levelUp1Price.text = "${levelUp1.price}"

            checkOnMoney()
        }

        fun levelUpComplete() {
            player.xp = 0
            xpBar.progress = player.xp
            player.level += 1
            StyleableToast.makeText(this, "Level Up!", R.style.mytoast).show()
        }

        fun checkOnXp() {
            if (player.xp < player.maxXp) {
                player.xp += 1
                xpBar.progress = player.xp
            }
            if (player.xp == player.maxXp) {
                xpBar.progress = player.xp
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
            player.multiplier = 1
            player.clickValue = 1
            player.xp = 0
            xpBar!!.progress = player.xp
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
                mgr.hideSoftInputFromWindow(cheatbox.windowToken, 0)
            } else {
                StyleableToast.makeText(this, "Too small", R.style.mytoast).show()
                mgr.hideSoftInputFromWindow(cheatbox.windowToken, 0)
            }

        }


        fun onTouchEmoji() {
            player.money += (player.clickValue * player.multiplier)
            player.moneyEarned += (player.clickValue * player.multiplier)
            player.totalClicks += 1
            checkOnXp()
            updateText()
            checkOnMoney()
            save()
        }

        multiplier2Item.setOnClickListener {
            if (player.money >= multiplier2.price && player.level > 1) {
                player.multiplier += 1
                player.moneySpent += multiplier2.price
                player.money -= multiplier2.price
                multiplier2.price += 500
                player.numberOfPurchases += 1
                save()
                updateText()
            } else if (player.level < 2) {
                StyleableToast.makeText(this, "Level ${multiplier2.levelRequired} Required", R.style.mytoast).show()
            } else {
                StyleableToast.makeText(this, "Not enough money", R.style.mytoast).show()
            }
        }

        levelUp1Item.setOnClickListener {
            if (player.money >= levelUp1.price && player.level > 9) {
                player.moneySpent += levelUp1.price
                player.money -= levelUp1.price
                levelUp1.price += 5000
                player.numberOfPurchases += 1
                player.level += 1
                save()
                updateText()
            } else if (player.level < 10) {
                StyleableToast.makeText(this, "Level ${levelUp1.levelRequired} Required", R.style.mytoast).show()
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
        xpBar!!.progress = player.xp
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
  