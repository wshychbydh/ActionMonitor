package  com.plugin.monitor.net

import com.plugin.monitor.db.model.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url


/**
 * Created by cool on 2018/3/8.
 */
internal interface MonitorService {

    @POST
    fun upload(@Url url: String = Api.url, @Body request: RequestBody): Call<String>
}