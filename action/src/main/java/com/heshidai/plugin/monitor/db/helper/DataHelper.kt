package com.heshidai.plugin.monitor.db.helper

import android.content.ContentValues
import android.content.Context
import com.google.gson.Gson
import com.heshidai.plugin.monitor.db.model.RequestBody

/**
 * Created by cool on 2018/3/22.
 */
internal object DataHelper {

    fun savePhone(context: Context, userInfo: String) {
        val shared = context.getSharedPreferences("INFO", Context.MODE_PRIVATE)
        shared.edit().putString("phone", userInfo).apply()
    }

    fun getPhone(context: Context): String {
        return context.getSharedPreferences("INFO", Context.MODE_PRIVATE).getString("phone", "")
    }

    fun saveBody(body: RequestBody) {
        val database = SqliteHelper.get().writableDatabase
        val contentValue = ContentValues()
        contentValue.put(SqliteHelper.BODY, Gson().toJson(body))
        database.insert(SqliteHelper.TABLE_NAME, null, contentValue)
    }

    fun getBody(): List<RequestBody>? {
        val database = SqliteHelper.get().readableDatabase
        val cursor = database.query(SqliteHelper.TABLE_NAME, null, null, null, null, null, null)
        try {
            if (cursor?.count ?: 0 > 0) {
                val gson = Gson()
                val bodyList = ArrayList<RequestBody>()
                while (cursor.moveToNext()) {
                    bodyList.add(gson.fromJson(cursor.getString(cursor.getColumnIndex(SqliteHelper.BODY)), RequestBody::class.java))
                }
                return bodyList
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

    fun deleteBody(body: RequestBody) {
        val database = SqliteHelper.get().writableDatabase
        database.delete(SqliteHelper.TABLE_NAME, "${SqliteHelper.BODY}=?", arrayOf(body.toString()))
    }
}