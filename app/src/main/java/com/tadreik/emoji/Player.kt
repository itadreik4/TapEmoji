package com.tadreik.emoji

class Player(var level: Int = 1,
             var money: Int = 0,
             var credit: Int = 0,
             var moneySpent: Int = 0,
             var moneyEarned: Int = 0,
             var numberOfPurchases: Int = 0,
             val maxCash: Int = 2000000000,
             var creditLimit: Int = 0,
             var totalClicks: Int = 0)