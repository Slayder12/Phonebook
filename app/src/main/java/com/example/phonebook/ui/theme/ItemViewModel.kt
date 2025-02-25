package com.example.phonebook.ui.theme

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    val itemTextState = mutableStateOf("")
    val itemListState = mutableStateListOf<String>()
    val item = mutableStateOf("")



    fun addItem(){
        if(itemTextState.value.isNotEmpty()){
            itemListState.add(itemTextState.value)
            itemTextState.value = ""
        }
    }

    fun removeItem(item: String){
        itemListState.remove(item)
    }
}