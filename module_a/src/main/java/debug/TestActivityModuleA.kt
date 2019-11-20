package debug

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.xiaogegechen.module_a.R
import com.github.xiaogegechen.module_a.view.impl.FragmentA

class TestActivityModuleA: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_a_activity_test)
        supportFragmentManager.beginTransaction().replace(R.id.module_a_test,
            FragmentA()
        ).commit()
    }
}