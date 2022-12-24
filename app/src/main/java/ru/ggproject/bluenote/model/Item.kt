package ru.ggproject.bluenote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item_table_bluenote")
class Item (
    val description : String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}