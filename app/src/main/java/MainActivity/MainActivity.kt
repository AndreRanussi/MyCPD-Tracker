package MainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.course.mycpdtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Action back button display
//        setSupportActionBar(binding?.toolbarMainActivity)
//        if(supportActionBar != null) {
//            supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        }
    }
}