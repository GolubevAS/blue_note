package ru.ggproject.bluenote.model

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao() : ItemDao

    companion object {

        @Volatile
        private var INSTANCE : ItemDatabase? = null

        //creating a database
        fun getDatabase (context: Context, scope: CoroutineScope) : ItemDatabase {

            return INSTANCE ?: synchronized(this) {
                val instanse : ItemDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "item_database_bluenote"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(ItemDatabaseCallback(scope))
                    .build()

                INSTANCE = instanse
                instanse
            }
        }

        fun populateDatabase (itemDao : ItemDao) {
            itemDao.insert(Item("NOTE"))
            itemDao.insert(Item("NOTE"))
            itemDao.insert(Item("NOTE"))
        }

        private class ItemDatabaseCallback (
            private val scope : CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate (db : SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.itemDao())
                    }
                }
            }
        }

    }






}