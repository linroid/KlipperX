package com.linroid.klipperx.storage

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.linroid.klipperx.storage.db.Database
import okio.Path
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

internal actual fun createSqlDriver(name: String): SqlDriver {
    val dataDir: Path by inject(Path::class.java, named("data"))
    val dbFilePath = dataDir.div(name)
    val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$dbFilePath")
    if (!dbFilePath.toFile().exists()) {
        dbFilePath.toFile().createNewFile()
        Database.Schema.create(driver)
    }
    return driver
}

