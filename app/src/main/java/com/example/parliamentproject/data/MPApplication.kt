package com.example.parliamentproject.data

import android.app.Application
import com.example.parliamentproject.data.repositories.MemberRepository
import com.example.parliamentproject.data.repositories.ReviewRepository
import com.example.parliamentproject.data.repositories.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * A subclass of Application superclass, for instantiating the MemberDatabase
 * and the MemberRepository. this is used in AndroidManifest.
 */
class MPApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Database and repositories will be lazy instantiated when the application is built.
    val database by lazy { MemberDatabase.getDatabase(this, applicationScope) }
    val memberRepository by lazy { MemberRepository(database.memberDao()) }
    val settingsRepository by lazy { SettingsRepository(database.settingsDao())}
    val reviewRepository by lazy { ReviewRepository(database.reviewDao()) }
}