package com.linroid.klipperx.storage

import app.cash.sqldelight.db.SqlDriver

internal expect fun createSqlDriver(name: String = "KlipperX.db"): SqlDriver
