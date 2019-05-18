package debug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.xiaogegechen.network.Network
import com.github.xiaogegechen.network.R
import kotlinx.android.synthetic.main.network_activity_test.*

class NetworkTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.network_activity_test)

        network_test_button.setOnClickListener{
            Network.query()
                .create(Api::class.java)
                .testGet()
                .compose(Network.changeThread())
                .subscribe {
                    Log.d(TAG, it.toString())
                    Toast.makeText(this@NetworkTestActivity, it.toString(), Toast.LENGTH_LONG).show()
                }
        }

    }

    companion object{
        const val TAG = "NetworkTestActivity"
    }
}
