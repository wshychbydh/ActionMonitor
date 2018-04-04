package com.plugin.monitor.db.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.plugin.monitor.MonitorSdk.context

/**
 * Created by cool on 2018/3/22.
 */
internal class SqliteHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    private val CREATE_TABLE = ("create table $TABLE_NAME ("
            + "$ID integer,"
            + "$BODY text)")

    override fun onCreate(db: SQLiteDatabase?) {
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //FIXME
    }

    private fun createTable(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    companion object {
        private val DATABASE_NAME = "monitor.db"
        private val DATABASE_VERSION = 1
        const val TABLE_NAME = "ACTIONS"
        const val BODY = "body"
        const val ID = "id"

        private var instance: SQLiteOpenHelper? = null

        fun get(): SQLiteOpenHelper {
            if (instance == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (instance == null) {
                        instance = SqliteHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
                    }
                }
            }
            return instance!!
        }
    }
}