package  com.heshidai.plugin.monitor.net

import com.heshidai.plugin.monitor.db.model.RequestBody
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by cool on 2018/3/8.
 */
interface MonitorService {
    @POST("api/collect/info")
    fun upload(@Body request: RequestBody): Observable<String>
}