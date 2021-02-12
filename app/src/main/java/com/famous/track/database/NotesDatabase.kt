package com.famous.track.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.famous.track.dao.NoteDao
import com.famous.track.entites.Notes

    @Database(entities = [Notes::class], version = 1, exportSchema = false)
    abstract class NotesDatabase: RoomDatabase() {

    companion object{
        var noteDatabase : NotesDatabase? = null

        @Synchronized
        fun getDatabase(context : Context) : NotesDatabase{
            if (noteDatabase == null)
            {
                noteDatabase = Room.databaseBuilder(
                        context
                        , NotesDatabase::class.java
                        , "notes.db"
                ).build()
            }
            return noteDatabase!!
        }
    }

    abstract fun noteDao():NoteDao

}