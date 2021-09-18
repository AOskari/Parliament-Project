package com.example.parliamentproject.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.parliamentproject.network.MembersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * A Singleton class, which is used for getting the instance of the Database.
 * When the singleton is called the first time, the Database will get populated.
 */
@Database(entities = [Member::class, Settings::class],
    version = 2, autoMigrations = [AutoMigration(from = 1, to = 2)])
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao

    // Creating a Callback subclass mainly for populating the database when it is
    // created the first time.
    private class MemberDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        // When the database is created, it is populated with the parsed JSON data.
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDB(database.memberDao())
                }
            }
        }

        // Fetches the JSON data and populates the Database with the parsed data.
        suspend fun populateDB(memberDao: MemberDao) {
            try {
                val result = MembersApi.retrofitService.getProperties()
                for (i in 1 until result.size) memberDao.addMember(result[i])
                Log.d("Success", "List size is ${result.size}")
            } catch (e: Exception) {
                Log.d("Failure", "${e.message}")
            }
        }

    }

    companion object {

        @Volatile
        private var INSTANCE: MemberDatabase? = null

        // Returns an instance of the MemberDatabase if it is not null.
        // Otherwise, it will create a new instance and return it.
        fun getDatabase(context: Context, scope: CoroutineScope): MemberDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemberDatabase::class.java,
                    "member_database"
                )
                    .addCallback(MemberDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

}