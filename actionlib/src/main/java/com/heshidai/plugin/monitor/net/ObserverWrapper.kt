package  com.heshidai.plugin.monitor.net

import android.util.Log

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by cool on 2018/3/1.
 */

open class ObserverWrapper<T> : Observer<T> {
    /**
     * Provides the Observer with the means of cancelling (disposing) the
     * connection (channel) with the Observable in both
     * synchronous (from within [.onNext]) and asynchronous manner.
     *
     * @param d the Disposable instance whose [Disposable.dispose] can
     * be called anytime to cancel the connection
     * @since 2.0
     */
    override fun onSubscribe(d: Disposable) {
        Log.i("observer", "onSubscribe")
    }

    /**
     * Provides the Observer with a new item to observe.
     *
     *
     * The [Observable] may call this method 0 or more times.
     *
     *
     * The `Observable` will not call this method again after it calls either [.onComplete] or
     * [.onError].
     *
     * @param t the item emitted by the Observable
     */
    override fun onNext(data: T) {
        Log.i("observer", "onNext")
    }

    /**
     * Notifies the Observer that the [Observable] has experienced an error condition.
     *
     *
     * If the [Observable] calls this method, it will not thereafter call [.onNext] or
     * [.onComplete].
     *
     * @param e the exception encountered by the Observable
     */
    override fun onError(e: Throwable?) {
        Log.i("observer", "onError : " + e?.message)
    }

    /**
     * Notifies the Observer that the [Observable] has finished sending push-based notifications.
     *
     *
     * The [Observable] will not call this method if it calls [.onError].
     */
    override fun onComplete() {
        Log.i("observer", "onComplete")
    }
}
