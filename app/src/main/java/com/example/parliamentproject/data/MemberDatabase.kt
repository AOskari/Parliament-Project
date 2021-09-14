package com.example.parliamentproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A Singleton class, which is used for getting the instance of the Database.
 */
@Database(entities = [Member::class], version = 1, exportSchema = false)
abstract class MemberDatabase : RoomDatabase() {

    abstract fun memberDao() : MemberDao


    private class MemberDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.memberDao())
                }
            }
        }

        // Used for populating the database with 2 test subjects.
        suspend fun populateDatabase(memberDao: MemberDao) {
            // Delete all content here.

            memberDao.deleteAll()

            var member1 = Member(1, 1, "Subject 1", "Test", "Party 1")
            var member2 = Member(2, 2, "Subject 2", "Test", "Party 2")
            var member3 = Member(3, 3, "Subject 3", "Test", "Party 3")
            var member4 = Member(4, 4, "Subject 4", "Test", "Party 4")
            memberDao.addMember(member1)
            memberDao.addMember(member2)
            memberDao.addMember(member3)
            memberDao.addMember(member4)
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