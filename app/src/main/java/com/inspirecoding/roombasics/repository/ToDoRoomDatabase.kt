package com.inspirecoding.roombasics.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inspirecoding.roombasics.model.ToDo

@Database(entities = [ToDo::class], version = 1)
abstract class ToDoRoomDatabase: RoomDatabase()
{
    abstract fun toDoDao(): ToDoDao

    companion object
    {
        /** Singleton prevents multiple instances of database opening at the  same time **/
        @Volatile
        private var INSTANCE: ToDoRoomDatabase? = null

        fun getDatabase(context: Context): ToDoRoomDatabase
        {
            val tempInstance = INSTANCE

            if(tempInstance != null)
            {
                return tempInstance
            }
            synchronized(ToDoRoomDatabase::class) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoRoomDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}