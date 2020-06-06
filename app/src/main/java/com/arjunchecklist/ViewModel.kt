package com.arjunchecklist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CheckListRepository

    val allWords: LiveData<List<CheckList>>

    init {
        val wordsDao = CheckListDatabase.getDatabase(application).checkListDao()
        repository = CheckListRepository(wordsDao)
        allWords = repository.allWords
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: CheckList) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(word)
    }

    fun deleteATitle(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(id)
    }

    fun updateAChecklist(id:Int,title: String,date:String,time:String) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(id,title,date,time)
    }

    fun  getRowCount() = viewModelScope.launch(Dispatchers.IO){
        repository.getCount();
    }

}