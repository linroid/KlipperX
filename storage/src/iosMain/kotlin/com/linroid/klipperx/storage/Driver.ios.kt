package com.linroid.klipperx.storage

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.linroid.klipperx.db.Database

internal actual fun createSqlDriver(name: String): SqlDriver {
    return NativeSqliteDriver(Database.Schema, "klipperx.db")
}