package com.linroid.klipperx.db

import org.koin.dsl.module

fun storageModule() {
    module {
        single {
            Database(createSqlDriver())
        }
    }

}