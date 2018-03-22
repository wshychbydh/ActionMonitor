package com.heshidai.plugin.monitor.db.helper

import android.content.ContentValues
import com.google.gson.Gson
import com.heshidai.plugin.monitor.db.model.RequestBody
import com.heshidai.plugin.monitor.util.LogUtils

/**
 * Created by cool on 2018/3/22.
 */
object DataHelper {

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
            LogUtils.d("查出来---》${cursor?.count}条")
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