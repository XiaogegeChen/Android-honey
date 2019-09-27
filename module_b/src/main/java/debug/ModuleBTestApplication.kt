package debug

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class ModuleBTestApplication:Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}