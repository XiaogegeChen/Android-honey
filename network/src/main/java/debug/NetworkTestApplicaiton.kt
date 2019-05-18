package debug

import android.app.Application
import com.github.xiaogegechen.network.Network
import com.github.xiaogegechen.network.TimeoutParam

class NetworkTestApplicaiton:Application() {

    override fun onCreate() {
        super.onCreate()

        val map: MutableMap<String, String> = HashMap()
        map.put("okhttp_head_name", "https://apis.juhe.cn/")

        Network.init(map, TimeoutParam.Builder().connectTime(100).readTime(100).writeTime(100).build())
    }

}