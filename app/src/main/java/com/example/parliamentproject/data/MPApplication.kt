package com.example.parliamentproject.data

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * An subclass of Application superclass, for instantiating the MemberDatabase
 * and the MemberRepository. this will be used in the AndroidManifest xml file.
 */
class MPApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // Database and repository will be instantiated when they are first needed,
    // in this case, when the application is built.
    val database by lazy { MemberDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MemberRepository(database.memberDao())}
}