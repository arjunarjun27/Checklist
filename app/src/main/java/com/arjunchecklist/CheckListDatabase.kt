package com.arjunchecklist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CheckList::class), version = 1, exportSchema = false)
public abstract class CheckListDatabase : RoomDatabase() {

    abstract fun checkListDao(): CheckListDao

    companion object {
        @Volatile
        private var INSTANCE: CheckListDatabase? = null

        fun getDatabase(context: Context): CheckListDatabase {

            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CheckListDatabase::class.java,
                    "checklist_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }

}