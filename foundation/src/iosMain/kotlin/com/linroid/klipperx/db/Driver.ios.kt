package com.linroid.klipperx.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual fun createSqlDriver(name: String): SqlDriver {
    return NativeSqliteDriver(Database.Schema, "klipperx.db")
}