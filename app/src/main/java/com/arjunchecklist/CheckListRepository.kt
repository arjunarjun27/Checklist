package com.arjunchecklist

import androidx.lifecycle.LiveData

class CheckListRepository(private val checkListDao: CheckListDao){


     val allWords: LiveData<List<CheckList>> = checkListDao.getAlphabetizedWords()

     suspend fun insert(word: CheckList) {
         checkListDao.insert(word)
     }

    suspend fun delete(id:Int) {
        checkListDao.delete(id)
    }

    suspend fun update(id:Int,title:String,date:String,time:String) {
        checkListDao.update(id,title,date,time)
    }

    suspend fun getCount(){
        checkListDao.getCount();
    }
 }