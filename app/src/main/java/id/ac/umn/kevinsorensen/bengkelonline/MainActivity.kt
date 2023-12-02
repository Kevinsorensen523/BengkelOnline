package id.ac.umn.kevinsorensen.bengkelonline

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import id.ac.umn.kevinsorensen.bengkelonline.viewmodels.LoginState
import id.ac.umn.kevinsorensen.bengkelonline.viewmodels.LoginViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val loginState = LoginState()
            val loginViewModel = LoginViewModel()
            LoginActivity(loginState = loginState, loginViewModel = loginViewModel)
        }
    }

    companion object {
        private const val TAG = "MainActivity";
    }
}

