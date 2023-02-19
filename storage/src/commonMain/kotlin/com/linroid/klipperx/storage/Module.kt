package com.linroid.klipperx.storage

import com.linroid.klipperx.storage.db.Database
import org.koin.dsl.module

fun storageModule() {
    module {
        single {
            Database(createSqlDriver())
        }
        single {
            createSettings()
        }
    }
}