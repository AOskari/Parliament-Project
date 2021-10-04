package com.example.parliamentproject.data

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.parliamentproject.data.dao.MemberDao
import com.example.parliamentproject.data.dao.ReviewDao
import com.example.parliamentproject.data.dao.SettingsDao
import com.example.parliamentproject.data.data_classes.Member
import com.example.parliamentproject.data.data_classes.Review
import com.example.parliamentproject.data.data_classes.Settings
import com.example.parliamentproject.network.MembersApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

/** A Singleton class, used for getting the instance of the Database.
 * When the singleton is called the first time, the Database will get populated. */
@Database(entities = [Member::class, Settings::class, Review::class],
    version = 5, autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5)
    ])
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao(): MemberDao
    abstract fun settingsDao() : SettingsDao
    abstract fun reviewDao() : ReviewDao

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
                    populateDB(database.memberDao(), database.settingsDao())
                }
            }
        }

        // Fetches the JSON data and populates the Database with the parsed data, as well adds a settings object.
        suspend fun populateDB(memberDao: MemberDao, settingsDao: SettingsDao) {
            val settings = Settings()
            try {
                settingsDao.addSettings(settings)
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