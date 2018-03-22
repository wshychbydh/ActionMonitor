package com.heshidai.plugin.monitor

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.heshidai.plugin.monitor.db.helper.DataFactory
import com.heshidai.plugin.monitor.db.helper.DataHelper
import com.heshidai.plugin.monitor.db.model.EventAction
import com.heshidai.plugin.monitor.db.model.PageAction
import com.heshidai.plugin.monitor.db.model.RequestBody
import com.heshidai.plugin.monitor.net.Api
import com.heshidai.plugin.monitor.net.ObserverWrapper
import com.heshidai.plugin.monitor.util.LogUtils
import com.heshidai.plugin.monitor.util.RxUtils
import com.heshidai.plugin.monitor.util.SystemUtils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * Created by cool on 2018/3/19.
 */
class SyncService : Service() {

    companion object {
        const val TYPE_PAGE = 0
        const val TYPE_SYNC = 1
        const val TYPE_EVENT = 2
        const val TYPE = "start_type"
        const val ACTION = "action"
        const val EVENT = "event"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
                if (MonitorSdk.isSdkInited) {
                    LogUtils.d("同步数据===>>")
                    syncData()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun savePageAction(actions: List<PageAction>) {
        Observable.create(ObservableOnSubscribe<Boolean> { e ->
            val body = DataFactory.composePageActionBody(this, actions)
            uploadTrack(body, {
                if (it) {
                    LogUtils.d("上传行为轨迹数据成功，尝试同步数据===>>")
                    e.onNext(it)
                } else {
                    LogUtils.d("上传行为轨迹数据失败，将数据保存至本地===>>")
                    saveActionToDb(body)
                }
            })

        }).compose(RxUtils.applySingleScheduler()).subscribe {
            if (it) {
                //尝试上传本地缓存的数据
                syncData()
            }
        }
    }

    private fun saveEventAction(action: EventAction) {
        Observable.create(ObservableOnSubscribe<Boolean> { e ->
            val body = DataFactory.composeEventActionBody(this, action)
            uploadTrack(body, {
                if (it) {
                    LogUtils.d("上传用户事件数据成功，尝试同步数据===>>")
                    e.onNext(it)
                } else {
                    LogUtils.d("上传用户事件数据失败，将数据保存至本地===>>")
                    saveActionToDb(body)
                }
            })

        }).compose(RxUtils.applySingleScheduler()).subscribe {
            if (it) {
                //尝试上传本地缓存的数据
                syncData()
            }
        }
    }

    private fun uploadTrack(body: RequestBody, callback: (result: Boolean) -> Unit) {
        if (!SystemUtils.isWifiConnect(this)) {
            callback.invoke(false)
            return
        }
        Api.service.upload(body)
                .compose(RxUtils.applySingleScheduler())
                .subscribe(object : ObserverWrapper<String>() {
                    override fun onError(e: Throwable?) {
                        super.onError(e)
                        callback.invoke(false)
                    }

                    override fun onComplete() {
                        super.onComplete()
                        callback.invoke(true)
                    }
                })
    }

    private fun saveActionToDb(body: RequestBody) {
        DataHelper.saveBody(body)
//        val realm = Realm.getDefaultInstance()
//        realm.beginTransaction()
//        val newBody = realm.createObject(RealmBody::class.java)
//        if (body.pageActions != null && body.pageActions!!.isNotEmpty()) {
//            val pageActions = RealmList<RealmPageAction>()
//            for (temp in body.pageActions!!) {
//                val action = realm.createObject(RealmPageAction::class.java)
//                action.copy(temp)
//                pageActions.add(action)
//            }
//            newBody.pageActions = pageActions
//        }
//        if (body.eventAction != null) {
//            val action = realm.createObject(RealmEventAction::class.java)
//            action.copy(body.eventAction!!)
//            newBody.eventAction = action
//        }
//        val networkInfo = realm.createObject(RealmNetworkInfo::class.java)
//        networkInfo.encode(body.networkInfo)
//        newBody.networkInfo = networkInfo
//
//        realm.commitTransaction()
    }

    private fun syncData() {
        var syncAble = false
        Observable.create<Boolean> {
            Observable.create(ObservableOnSubscribe<RequestBody> { e ->
                val all = DataHelper.getBody()
                LogUtils.d("有${all?.size ?: 0}条数据需要同步==${Thread.currentThread().name}==>>")
                if (all == null || all.isEmpty()) {
                    e.onComplete()
                } else {
                    syncAble = true
                    for (body in all) {
                        e.onNext(body)
                    }
                }
            }).takeUntil {
                syncAble && SystemUtils.isWifiConnect(this) }
                    .subscribe {
                        Api.service.upload(it)
                                .compose(RxUtils.applySingleScheduler())
                                .subscribe(object : ObserverWrapper<String>() {

                                    override fun onError(e: Throwable?) {
                                        super.onError(e)
                                        LogUtils.d("同步失败==${Thread.currentThread().name}==>>${e?.message} , $syncAble")
                                        syncAble = false
                                    }

                                    override fun onComplete() {
                                        super.onComplete()
                                        LogUtils.d("同步数据成功，该数据将从数据删除==${Thread.currentThread().name}==>>$it")
                                        DataHelper.deleteBody(it)
                                    }
                                })
                    }
        }.compose(RxUtils.applySingleScheduler()).subscribe()

//        Observable.create<Boolean> {
//            val realm = Realm.getDefaultInstance()
//            Observable.create(ObservableOnSubscribe<RealmBody> { e ->
//                val all = realm.where(RealmBody::class.java).findAll()
//                for (body in all) {
//                    e.onNext(body)
//                }
//            }).takeWhile { SystemUtils.isWifiConnect(this) }
//                    .subscribe {
//                        Api.service.upload(it.decode())
//                                .compose(RxUtils.applySingleScheduler())
//                                .subscribe(object : ObserverWrapper<String>() {
//                                    override fun onComplete() {
//                                        super.onComplete()
//                                        LogUtils.d("同步数据成功，该数据将从数据删除===>>$it")
//                                        realm.beginTransaction()
//                                        it.deleteFromRealm()
//                                        realm.commitTransaction()
//                                    }
//                                })
//                    }
//        }.compose(RxUtils.applySingleScheduler()).subscribe()
    }
}