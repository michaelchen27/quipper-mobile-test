package com.quipper.test.cache

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.quipper.test.AppDatabase

class AndroidDatabaseDriverFactory(private val context: Context): DatabaseDriverFactory {

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "quipper.db")
    }


}