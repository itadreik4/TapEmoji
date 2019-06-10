package com.tadreik.emoji

class ShopItem(
    var name: String = "Shop Item",
    var price: Int = 0,
    val levelRequired: Int = 1,
    var purchaseWithCredit: Boolean = true)