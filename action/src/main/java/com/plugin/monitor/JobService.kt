package com.plugin.monitor

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import com.plugin.monitor.db.helper.DataFactory
import com.plugin.monitor.db.helper.DataHelper
import com.plugin.monitor.db.model.EventAction
import com.plugin.monitor.db.model.PageAction
import com.plugin.monitor.db.model.RequestBody
import com.plugin.monitor.net.Api
import com.plugin.monitor.util.LogUtils
import com.plugin.monitor.util.SystemUtils
import com.plugin.monitor.util.ThreadUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by cool on 2018/3/19.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal class JobService : JobService() {

    companion object {
        const val TYPE_PAGE = 0
        const val TYPE_SYNC = 1
        const val TYPE_EVENT = 2
        const val TYPE = "start_type"
        const val ACTION = "action"
        const val EVENT = "event"
    }


    override fun onStartJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ThreadUtils.executeOnService {
            when (intent?.getIntExtra(TYPE, TYPE_SYNC)) {
                TYPE_PAGE -> {
                    LogUtils.d("保存轨迹===>>")
                    val actions = intent.getParcelableArrayListExtra<PageAction>(ACTION)
                    if (actions != null && actions.isNotEmpty()) {
                        savePageAction(actions)
                    }
                }
                TYPE_EVENT -> {
                    LogUtils.d("保存事件===>>")
                    val action = intent.getParcelableExtra<EventAction>(EVENT)
                    if (action != null) {
                        saveEventAction(action)
                    }
                }
                TYPE_SYNC -> {
                    LogUtils.d("同步数据===>>")
                    syncData()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun savePageAction(actions: List<PageAction>) {
        val body = DataFactory.composePageActionBody(this, actions)
        uploadTrack(body) {
            if (it) {
                LogUtils.d("上传行为轨迹数据成功，尝试同步数据===>>")
                //尝试上传本地缓存的数据
                syncData()
            } else {
                LogUtils.d("上传行为轨迹数据失败，将数据保存至本地===>>")
                saveActionToDb(body)
            }
        }
    }

    private fun saveEventAction(action: EventAction) {
        val body = DataFactory.composeEventActionBody(this, action)
        uploadTrack(body) {
            if (it) {
                LogUtils.d("上传用户事件数据成功，尝试同步数据===>>")
                //尝试上传本地缓存的数据
                syncData()
            } else {
                LogUtils.d("上传用户事件数据失败，将数据保存至本地===>>")
                saveActionToDb(body)
            }
        }
    }

    private fun uploadTrack(body: RequestBody, callback: (result: Boolean) -> Unit) {
        if (!SystemUtils.isWifiConnect(this)) {
            callback.invoke(false)
            return
        }
        LogUtils.d("上传数据==>>$body")
        Api.service.upload(request = body)
                .enqueue(object : Callback<String?> {
                    override fun onFailure(call: Call<String?>?, t: Throwable?) {
                        LogUtils.e("上传数据失败-->>${t?.message}")
                        callback.invoke(false)
                    }

                    override fun onResponse(call: Call<String?>?, response: Response<String?>?) {
                        callback.invoke(response?.isSuccessful ?: false)
                    }
                })
    }

    private fun saveActionToDb(body: RequestBody) {
        DataHelper.saveBody(body)
    }

    private fun syncData() {
        var syncAble = false
        val all = DataHelper.getBody()
        LogUtils.d("有${all?.size ?: 0}条数据需要同步==>>")
        if (all != null && all.isNotEmpty() && SystemUtils.isWifiConnect(this)) {
            all.forEach {
                Api.service.upload(request = it)
                        .enqueue(object : Callback<String?> {
                            override fun onFailure(call: Call<String?>?, t: Throwable?) {
                                LogUtils.e("同步失败==$it==>>${t?.message} , $syncAble")
                                syncAble = false
                            }

                            override fun onResponse(call: Call<String?>?, response: Response<String?>?) {
                                syncAble = response?.isSuccessful ?: false
                                if (syncAble) {
                                    val index = DataHelper.deleteBody(it)
                                    LogUtils.d("同步数据成功，该数据将从数据删除==$it==>>index:$index")
                                }
                            }
                        })
            }
        }
    }
}