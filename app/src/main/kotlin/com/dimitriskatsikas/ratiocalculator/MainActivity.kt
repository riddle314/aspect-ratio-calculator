package com.dimitriskatsikas.ratiocalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dimitriskatsikas.ratiocalculator.ui.theme.RatioCalcTheme
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeMobileAdds()
        enableEdgeToEdge()
        setContent {
            RatioCalcTheme {
                AppNavigation()
            }
        }
    }

    private fun initializeMobileAdds() {
        MobileAds.initialize(this) {}
    }
}
