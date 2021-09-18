package com.example.parliamentproject.data

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * A subclass of Application superclass, for instantiating the MemberDatabase
 * and the MemberRepository. this is used in AndroidManifest.
 */
class MPApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Database and repository will be instantiated when they are first needed,
    // in this case, when the application is built.
    val database by lazy { MemberDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MemberRepository(database.memberDao())}
}