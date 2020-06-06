package com.arjunchecklist

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CheckListDao {

    @Query("SELECT * from checklist_table ORDER BY title ASC")
    fun getAlphabetizedWords(): LiveData<List<CheckList>>

    @Query("SELECT COUNT(title) FROM checklist_table")
    suspend fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: CheckList)


    @Query("DELETE FROM checklist_table WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("UPDATE checklist_table SET title=:title , date=:date , time=:time WHERE id=:id")
    suspend fun update(id:Int,title: String,date:String,time:String)

}