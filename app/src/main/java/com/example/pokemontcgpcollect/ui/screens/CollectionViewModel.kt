package com.example.pokemontcgpcollect.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcgpcollect.data.Dex
import com.example.pokemontcgpcollect.data.Packs
import com.example.pokemontcgpcollect.data.database.SaveStateRepo
import com.example.pokemontcgpcollect.data.datamodel.DexEntry
import com.example.pokemontcgpcollect.data.datamodel.DexUiState
import com.example.pokemontcgpcollect.data.datamodel.PackEntry
import com.example.pokemontcgpcollect.data.datamodel.SaveStateDex
import com.example.pokemontcgpcollect.data.datastore.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


private const val TAG: String = "DexScreenViewModel"

class CollectionViewModel(
    private val saveStateRepo: SaveStateRepo,
    private val settingsRepo: SettingsRepository
): ViewModel() {
    private val _uiState = MutableStateFlow((DexUiState()))
   // val uiState: StateFlow<DexUiState> = _uiState.asStateFlow()

    val uiState: StateFlow<DexUiState> =
        settingsRepo.dexWidthSetting.map { numWidth ->
            _uiState.value.copy(dexColumns = numWidth)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DexUiState(),
        )

    private fun load() {

        runBlocking {
            Log.d(TAG, "Start Settings load")
            val settingDexWidth = settingsRepo.dexWidthSetting.first()
            _uiState.update { currentState ->
                currentState.copy( dexColumns = settingDexWidth)
            }
            _uiState.value = _uiState.value.copy(dexColumns = settingDexWidth)
            Log.d(TAG, "Finished Settings load")
        }

        Log.d(TAG, "Started loading")
        viewModelScope.launch {
            Log.d(TAG, "Launched loading")
            saveStateRepo.getAllSaved()
                .collect{ savedDexEntry ->
                    val dbSavedMap = savedDexEntry.associateBy {it.cardId}

                    val updatedDex = Dex().loadDex()
                        .map { dexEntry: DexEntry ->
                            dbSavedMap[dexEntry.cardId]?.let { dbCard ->
                                dexEntry.copy(numberPossession = dbCard.inPossession)
                            } ?: dexEntry
                        }
                    _uiState.value = DexUiState(
                        packs = Packs().loadPacks().toMutableList(),
                        dex = updatedDex.toMutableList(),
                        dexColumns = 3,
                    )

                    val newDexList : MutableList<DexEntry> = mutableListOf()
                    for (dexEntry in _uiState.value.dex) {
                        //Log.d(TAG, "Starting searching Dex")
                        newDexList.add(
                            checkActive(dexEntry = dexEntry)
                        )
                    }

                    val newPackList: MutableList<PackEntry> = mutableListOf()
                    for (packEntry in _uiState.value.packs) {
                        newPackList.add(
                            calcRarity(packEntry = packEntry)
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(packs = newPackList, dex = newDexList)
                    }
                }
        }

        val newDexList : MutableList<DexEntry> = mutableListOf()
        for (dexEntry in _uiState.value.dex) {
            newDexList.add(
                checkActive(dexEntry = dexEntry)
            )
        }

        val newPackList: MutableList<PackEntry> = mutableListOf()
        for (packEntry in _uiState.value.packs) {
            newPackList.add(
                calcRarity(packEntry = packEntry)
            )
        }
        _uiState.update { currentState ->
            currentState.copy(packs = newPackList, dex = newDexList)
        }
        Log.d(TAG, "Finished loading")
    }

    private fun calcRarity(packEntry: PackEntry): PackEntry {
        val packId = packEntry.id
        var totalDiamonds = 0
        var collectedDiamonds = 0
        var totalStars = 0
        var collectedStars = 0
        for (card in _uiState.value.dex) {
            if (packId in card.packIds) {
                if (card.rarity >= 10) {
                    totalStars ++
                    if (card.isActive) {collectedStars ++}
                } else {
                    totalDiamonds ++
                    if (card.isActive) {collectedDiamonds ++}
                }
            }
        }
        packEntry.totalStar = totalStars
        packEntry.totalDiamond = totalDiamonds
        packEntry.collectedStar = collectedStars
        packEntry.collectedDiamond = collectedDiamonds

        return packEntry
        }

    private fun checkActive(dexEntry: DexEntry): DexEntry {
        return dexEntry.copy(isActive = dexEntry.numberPossession > 0)
    }

    fun toggleEntry(cardId: Int) {
        val newDex = _uiState.value.dex
        val cardIndex = newDex.indexOfFirst { it.cardId == cardId }  //-1 if no item was found

        if (cardIndex == -1) {
            return
        }

        val newCard = newDex[cardIndex]
        newCard.isActive = !newCard.isActive

        newDex[cardIndex] = newCard
        _uiState.update { currentState ->
            currentState.copy(dex = newDex)
        }
        Log.d(TAG, "Updated _uiState")

        val newPacks = _uiState.value.packs
        for (packId in newCard.packIds) {
            val packIndex = _uiState.value.packs.indexOfFirst { it.id == packId }
            if (packIndex == -1){
                Log.d(TAG, "Could not find Pack with ID: $packId")
            } else {
                newPacks[packIndex] = calcRarity(newPacks[packIndex])
            }
        }
        _uiState.update { currentState ->
            currentState.copy(packs = newPacks)
        }

    }

    fun updateDexWidth(newWidth: Int) {
        _uiState.update { currentState ->
            currentState.copy(dexColumns = newWidth)
        }
        viewModelScope.launch {
            settingsRepo.saveDexWidth(newWidth)
        }
    }

    suspend fun saveCard(cardId: Int){

        val indexCard = _uiState.value.dex.indexOfFirst { it.cardId == cardId }


        val newSave = SaveStateDex(
            cardId = cardId,
            inPossession = if (_uiState.value.dex[indexCard].isActive) {
                1
            } else {
                0
            }
        )

        val existingId = saveStateRepo.getSavedId(cardId)

        if (existingId == null) {
            saveStateRepo.saveState(newSave)
            Log.d(TAG, "ID was not yet included, included it now")
        } else {
            saveStateRepo.saveState(newSave)
            Log.d(TAG, "ID was already included, updated it now")
        }

        Log.d(TAG, "Saved new Entry")
    }

    init {
        load()
    }
}