package com.plugin.monitor.db.helper

import android.content.ContentValues
import android.content.Context
import com.plugin.monitor.db.model.RequestBody
import com.plugin.monitor.util.Utils

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
        contentValue.put(SqliteHelper.BODY, Utils.getGson().toJson(body))
        contentValue.put(SqliteHelper.ID, body.hashCode())
        database.insert(SqliteHelper.TABLE_NAME, null, contentValue)
    }

    fun getBody(): List<RequestBody>? {
        val database = SqliteHelper.get().readableDatabase
        val cursor = database.query(SqliteHelper.TABLE_NAME, null, null, null, null, null, null)
        try {
            if (cursor?.count ?: 0 > 0) {
                val gson = Utils.getGson()
                val bodyList = ArrayList<RequestBody>()
                while (cursor.moveToNext()) {
                    val body = gson.fromJson(cursor.getString(cursor.getColumnIndex(SqliteHelper.BODY)), RequestBody::class.java)
                    body.id = cursor.getInt(cursor.getColumnIndex(SqliteHelper.ID))
                    bodyList.add(body)
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

    fun deleteBody(body: RequestBody): Int {
        return SqliteHelper.get().writableDatabase.delete(SqliteHelper.TABLE_NAME,
                "${SqliteHelper.ID}=${body.id}", null)
    }
}