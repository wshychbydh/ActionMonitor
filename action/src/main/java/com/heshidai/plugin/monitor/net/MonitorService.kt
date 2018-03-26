package  com.heshidai.plugin.monitor.net

import com.heshidai.plugin.monitor.db.model.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by cool on 2018/3/8.
 */
internal interface MonitorService {
    @POST("api/collect/info")
    fun upload(@Body request: RequestBody): Call<String>
}