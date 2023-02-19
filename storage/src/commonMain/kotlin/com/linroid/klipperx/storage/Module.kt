package com.linroid.klipperx.storage

import com.linroid.klipperx.storage.db.Database
import org.koin.core.module.Module
import org.koin.dsl.module

fun storageModule(): Module {
    return module {
        single {
            Database(createSqlDriver())
        }
        single {
            createSettings()
        }
    }
}