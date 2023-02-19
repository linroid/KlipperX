package com.linroid.klipperx.db

import app.cash.sqldelight.db.SqlDriver

expect fun createSqlDriver(name: String = "KlipperX.db"): SqlDriver

fun query() {
    val driver = createSqlDriver()
    val database = Database(driver)
    database.moonrakerServerQueries.selectAll()
}
