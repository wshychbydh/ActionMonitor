package com.plugin.monitor.db.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.plugin.monitor.db.model.RequestBody
import com.plugin.monitor.util.Utils

/**
 * Created by cool on 2018/3/22.
 */
internal object DataHelper {

    fun saveChannelKey(context: Context, channel: String) {
        val shared = context.getSharedPreferences("INFO", Context.MODE_PRIVATE)
        shared.edit().putString("channelKey", channel).apply()
    }

    fun getChannelKey(context: Context): String {
        return context.getSharedPreferences("INFO", Context.MODE_PRIVATE).getString("channelKey", "channel")
    }

    fun saveChannel(context: Context, channel: String) {
        val shared = context.getSharedPreferences("INFO", Context.MODE_PRIVATE)
        shared.edit().putString("channel", channel).apply()
    }

    fun getChannel(context: Context): String {
        return context.getSharedPreferences("INFO", Context.MODE_PRIVATE).getString("channel", "")
    }

    fun savePhone(context: Context, userInfo: String) {
        val shared = context.getSharedPreferences("INFO", Context.MODE_PRIVATE)
        shared.edit().putString("phone", userInfo).apply()
    }

    fun getPhone(context: Context): String {
        return context.getSharedPreferences("INFO", Context.MODE_PRIVATE).getString("phone", "")
    }

    fun saveBody(body: RequestBody) {
        try {
            val database = SqliteHelper.get().writableDatabase
            val contentValue = ContentValues()
            contentValue.put(SqliteHelper.BODY, Utils.getGson().toJson(body))
            contentValue.put(SqliteHelper.ID, body.hashCode())
            database.insert(SqliteHelper.TABLE_NAME, null, contentValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBody(): List<RequestBody>? {
        var cursor: Cursor? = null
        try {
            val database = SqliteHelper.get().readableDatabase
            cursor = database.query(SqliteHelper.TABLE_NAME, null, null, null, null, null, null)
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