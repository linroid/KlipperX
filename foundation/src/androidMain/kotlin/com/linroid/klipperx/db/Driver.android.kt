package com.linroid.klipperx.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.linroid.klipperx.Android

actual fun createSqlDriver(name: String): SqlDriver {
    return AndroidSqliteDriver(Database.Schema, Android.context, name)
}