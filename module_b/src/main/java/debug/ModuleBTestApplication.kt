package debug

import android.app.Application
import com.github.xiaogegechen.module_b.Constants
import com.github.xiaogegechen.network.Network

class ModuleBTestApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        Network.init(Constants.sMap)
    }
}