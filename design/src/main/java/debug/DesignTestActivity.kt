package debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.xiaogegechen.design.R

class DesignTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.design_activity_test)
    }

    companion object{
        private const val TAG = "DesignTestActivity"
    }
}
