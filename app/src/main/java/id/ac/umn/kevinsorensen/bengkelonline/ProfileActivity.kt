package id.ac.umn.kevinsorensen.bengkelonline

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import id.ac.umn.kevinsorensen.bengkelonline.controller.ResourceCollector
import id.ac.umn.kevinsorensen.bengkelonline.controller.UserController
import id.ac.umn.kevinsorensen.bengkelonline.datamodel.User
import id.ac.umn.kevinsorensen.bengkelonline.views.ProfilePage
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class ProfileActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra("userId");
        Log.d(TAG, "userId: $userId");

        if(userId == null){
            // TODO show error
            return;
        }

        val db = Firebase;
        val userController = UserController(db.firestore);
        val ResourceCollector = ResourceCollector(db.storage);


        userController.getUserById(userId){
            setContent {
                var profileImgurl by remember { mutableStateOf("") }

                    Scaffold(
                        topBar = {
                            TopNavigation2(this)
                        },
                        content = { p ->
                            val navController = rememberNavController()
                            Column(modifier = Modifier.padding(p)) {
                                if (it != null) {
                                    ResourceCollector.getProfilePhoto(it.photo) {
                                        profileImgurl = it.toString();
                                    }

                                    UserContent(user = it, profileImgurl);
                                } else
                                    errorPage()
                            }
                        }
                    )
            }
        }
    }

    companion object {
        private const val TAG = "ProfileActivity";
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigation2(activity: ComponentActivity) {
    val mContext = LocalContext.current
    val contextForToast = LocalContext.current.applicationContext

    TopAppBar(
        title = {
            Text(text = "Profile")
        },
        navigationIcon = {
            IconButton(onClick = {
                // destroy activity
                activity.finish();
            }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go Back")
            }
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun UserContent(user: User, imageUrl: String){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GlideImage(
            model = imageUrl,
            contentDescription = "photo profile",
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .clip(CircleShape)
                .padding(0.dp, 10.dp)
        )
        // h1 bold
        Text(text = user.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = user.email)
        Text(text = user.phone)
    }
}

@Composable
fun errorPage(){
    Column {
        Text(text = "Error")
    }
}