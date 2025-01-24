package com.example.pokemontcgpcollect.data

import android.util.Log
import androidx.compose.ui.res.stringResource
import com.example.pokemontcgpcollect.data.datamodel.BoosterEntry
import com.example.pokemontcgpcollect.data.datamodel.DexEntry
import kotlin.math.absoluteValue

private const val TAG = "StatisticFunctions"

fun calcCompletion(dex: Dex, packId: Int): Float {
    return 1f
}

fun calcNewCardInBooster(dex: List<DexEntry>, boosterEntry: BoosterEntry): Double {
    val newCardProbability = calcProbabilitiesBooster(dex = dex, boosterEntry = boosterEntry)
    val noNewCardProbability = newCardProbability.map { (100 - it).absoluteValue }

    val noNewCardAtAll = noNewCardProbability.fold(1.0) {acc, value -> acc * value / 100.0}
    return 100.0 - ( noNewCardAtAll * 100.0 )
}


fun calcProbabilitiesBooster(dex: List<DexEntry>, boosterEntry: BoosterEntry): List<Double> {

    var pullRateBooster = emptyList<Double>()
    var pullRateBoosterGod = emptyList<Double>()

    for (card in dex) {
        if (boosterEntry.NameId in card.boosterId && card.numberPossession < 1) {
            //Log.d(TAG, (boosterEntry.NameId).toString())

            val pullRates = card.pullRates.getValue(boosterEntry.NameId).pullRate
            val pullRatesGP = card.pullRates.getValue(boosterEntry.NameId).pullRateGodPack

            pullRateBooster = addLists(pullRateBooster, pullRates)
            pullRateBoosterGod = addLists(pullRateBoosterGod, pullRatesGP)
        }
    }

    pullRateBooster = pullRateBooster.map { it * (1 - boosterEntry.probabilityGodPack / 100.0) }
    pullRateBoosterGod = pullRateBoosterGod.map { it * (boosterEntry.probabilityGodPack / 100.0) }

    Log.d(TAG, addLists(pullRateBooster, pullRateBoosterGod).toString())

    return addLists(pullRateBooster, pullRateBoosterGod)
}

fun multiplyLists(list1: List<Double>, list2:List<Double>): List<Double> {
    if (list1.isEmpty()) {
        return list2
    }
    else if (list2.isEmpty()) {
        return list1
    }
    return list1.zip(list2) { a, b -> a * b }
}

fun addLists(list1: List<Double>, list2:List<Double>): List<Double> {
    if (list1.isEmpty()) {
        return list2
    }
    else if (list2.isEmpty()) {
        return list1
    }
    return list1.zip(list2) { a, b -> a + b }
}