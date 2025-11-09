package SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thesparkinstituteapp.R
import kotlin.jvm.java
import com.example.thesparkinstituteapp.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

        // Handler to start MainActivity after splash timeout
        Handler(Looper.getMainLooper()).postDelayed({
            // Start MainActivity
            val intent = Intent(this,  MainActivity::class.java )
            startActivity(intent)

            // Close splash activity
            finish()
        }, 3000)
    }

    override fun onBackPressed() {
        // Disable back button on splash screen
        super.onBackPressed()
    }


    }
