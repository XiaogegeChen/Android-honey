package debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.xiaogegechen.module_left.R
import com.github.xiaogegechen.module_left.view.impl.FragmentLeft

/**
 * 该模块的测试activity
 */
class ModuleLeftTestActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_left_activity_test)

        val fragmentLeft = FragmentLeft()
        supportFragmentManager.beginTransaction().replace(R.id.module_left_test, fragmentLeft).commit()
    }

}