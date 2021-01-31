package com.famous.track.dao

import androidx.room.*
import com.famous.track.entites.Notes

@Dao
interface NoteDao {

    @get: Query("SELECT * FROM notes ORDER BY id DESC")
    val allNotes : List<Notes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: Notes)

    @Delete
    fun deleteNote(notes: Notes)
}