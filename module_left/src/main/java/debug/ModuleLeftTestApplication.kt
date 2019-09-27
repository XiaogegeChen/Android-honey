package debug

import android.app.Application
import androidx.multidex.MultiDex

class ModuleLeftTestApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(applicationContext)
    }
}
