package ru.ggproject.bluenote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ru.ggproject.bluenote.model.Item
import ru.ggproject.bluenote.model.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ItemRepository(application.applicationContext,
        viewModelScope)

    val allItems = repository.allItems


    fun insert (item : Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(item)
    }

    fun delete (item : Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(item)
    }

    fun update (item : Item) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(item)
    }



}