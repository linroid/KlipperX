package com.linroid.klipperx.storage

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.linroid.klipperx.db.Database
import org.koin.java.KoinJavaComponent.inject

internal actual fun createSqlDriver(name: String): SqlDriver {
    val context: Context by inject(Context::class.java)
    return AndroidSqliteDriver(Database.Schema, context, name)
}