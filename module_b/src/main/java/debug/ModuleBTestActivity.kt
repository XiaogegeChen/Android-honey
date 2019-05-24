package debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.xiaogegechen.module_b.R
import com.github.xiaogegechen.module_b.view.FragmentB

class ModuleBTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.module_b_activity_test)

        supportFragmentManager.beginTransaction().replace(R.id.module_b_test, FragmentB()).commit()

    }
}
