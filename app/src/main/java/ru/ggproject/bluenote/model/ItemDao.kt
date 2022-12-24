package ru.ggproject.bluenote.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Insert
    fun insert (item : Item)

    @Update
    fun update (item : Item)

    @Delete
    fun delete (item : Item)

    @Query("DELETE FROM item_table_bluenote")
    fun deleteAllItems()

    @Query("SELECT * FROM item_table_bluenote")
    fun allItems() : LiveData<List<Item>>

    @Delete
    fun removedItem (item : Item)

}