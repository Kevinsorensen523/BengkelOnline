package id.ac.umn.kevinsorensen.bengkelonline

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.ac.umn.kevinsorensen.bengkelonline.datamodel.User
import id.ac.umn.kevinsorensen.bengkelonline.viewmodels.LoginState
import id.ac.umn.kevinsorensen.bengkelonline.viewmodels.LoginViewModel

@Composable
fun LoginActivity(
    loginState: LoginState, loginViewModel: LoginViewModel
) {
    Column (
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .height(267.dp)
    ) {
        Image (
            painter = painterResource(id = R.drawable.main_pict),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
    }
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ){
        LoginUser(
            loginViewModel = loginViewModel,
            errorMessage = loginState.error,
            user = loginState.user,
            emailOrUsername = loginViewModel.inputEmailOrUsername,
            password = loginViewModel.inputPassword,
            passwordVisible = loginViewModel.passwordVisibility,
            onLogin = { loginViewModel.login() },
            togglePasswordVisibility = { loginViewModel.togglePasswordVisibility() },
            updateEmailOrUsername = { loginViewModel.updateEmailOrUsername(it) },
            updatePassword = { loginViewModel.updatePassword(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUser(
    loginViewModel: LoginViewModel = viewModel(),
    errorMessage: String = "",
    user: User? = null,
    emailOrUsername: String = "",
    password: String = "",
    passwordVisible: Boolean = true,
    updateEmailOrUsername: (String) -> Unit,
    updatePassword: (String) -> Unit,
    togglePasswordVisibility: () -> Unit,
    onLogin: () -> Unit
) {
    // placeholder for real error, don't use toast
    if(errorMessage.isNotEmpty()){
        Toast.makeText(LocalContext.current, "error: $errorMessage", Toast.LENGTH_LONG);
    }

    // if user defined, immediately switch activity
    if(user != null){
        val intent = Intent(LocalContext.current, HomeUser::class.java)
            .putExtra("userId", user.id)
            .putExtra("username", user.name);

        LocalContext.current.startActivity(
            intent
        )

    }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 120.dp)
    ) {
        TextField (
            value = emailOrUsername,
            onValueChange = {
                updateEmailOrUsername(it)
            },
            label = {
                Text(text = "Username")
            },
            leadingIcon = {
                Icon (
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if(emailOrUsername.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null,
                        Modifier.clickable {
                            updateEmailOrUsername("")
                        }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                focusedLeadingIconColor = Color.Blue,
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Blue,
                unfocusedLabelColor = Color.Blue,
                unfocusedLeadingIconColor = Color.Blue
            ),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = password,
            onValueChange = {
                updatePassword(it)
            },
            label = {
                Text(text="Password")
            },
            leadingIcon = {
                Icon (
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (password.isNotEmpty()) {
                    val visibilityIcon = if (passwordVisible) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    }
                    else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }
                    Icon (
                        painter = visibilityIcon,
                        contentDescription = null,
                        Modifier.clickable {
                            togglePasswordVisibility();
                        }
                    )
                }
            },
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            }
            else {
                PasswordVisualTransformation()
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                focusedLeadingIconColor = Color.Blue,
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Blue,
                unfocusedLabelColor = Color.Blue,
                unfocusedLeadingIconColor = Color.Blue
            ),
            textStyle = TextStyle (
                color = Color.Blue,
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.None
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button (
            modifier = Modifier
                .height(50.dp)
                .width(200.dp),
            onClick = {
                onLogin();
            },
            colors = ButtonDefaults.buttonColors(
                Color.Blue
            )
        ) {
            Text(
                text = "Log in",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // buat spacer
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Log in as Merchant",
            fontSize = 20.sp,
            color = Color.Blue,
            fontWeight = FontWeight.Bold
        )
    }
}

/*
@Composable
fun TabLayout(loginViewModel: LoginViewModel = viewModel()) {
    val loginState by loginViewModel.uiState.collectAsState();
    val tabs = listOf("User", "Bengkel")

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 120.dp)
            .height(300.dp)
            .background(Color.White)
    ) {
        TabRow (
            selectedTabIndex = loginState.pageIndex
        ) {
            tabs.forEachIndexed {
                index, title ->
                    Tab(text = { Text(title) },
                        selected = loginState.pageIndex == index,
                        onClick = {
                            loginViewModel.changePage(index)
                        }
                    )
            }
        }
        when (loginState.pageIndex) {
            0 -> LoginUser(
                errorMessage = loginState.error,
                user = loginState.user,
                emailOrUsername = loginViewModel.inputEmailOrUsername,
                password = loginViewModel.inputPassword,
                passwordVisible = loginViewModel.passwordVisibility,
                onLogin = { loginViewModel.login() },
                togglePasswordVisibility = { loginViewModel.togglePasswordVisibility() },
                updateEmailOrUsername = { loginViewModel.updateEmailOrUsername(it) },
                updatePassword = { loginViewModel.updatePassword(it) }
            )

            1 -> LoginMerchant()
        }
    }
}
 */

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginMerchant() {
    var showDialog by remember { mutableStateOf(false) }
    val emty by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val mContext = LocalContext.current
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        TextField (
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text(text = "Username")
            },
            leadingIcon = {
                Icon (
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if(name.isNotEmpty()) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_close_24),
                        contentDescription = null,
                        Modifier.clickable { name = emty.toString() }
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                focusedLeadingIconColor = Color.Blue,
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Blue,
                unfocusedLabelColor = Color.Blue,
                unfocusedLeadingIconColor = Color.Blue
            ),
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text="Password")
            },
            leadingIcon = {
                Icon (
                    painter = painterResource(id = R.drawable.baseline_lock_24),
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (password.isNotEmpty()) {
                    val visibilityIcon = if (passwordVisibility) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    }
                    else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    }
                    Icon (
                        painter = visibilityIcon,
                        contentDescription = null,
                        Modifier.clickable {
                            passwordVisibility =! passwordVisibility
                        }
                    )
                }
            },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            }
            else {
                PasswordVisualTransformation()
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Blue,
                focusedLabelColor = Color.Blue,
                focusedLeadingIconColor = Color.Blue,
                containerColor = Color.White,
                unfocusedIndicatorColor = Color.Blue,
                unfocusedLabelColor = Color.Blue,
                unfocusedLeadingIconColor = Color.Blue
            ),
            textStyle = TextStyle (
                color = Color.Blue,
                fontSize = 16.sp
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.None
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button (
            modifier = Modifier
                .height(50.dp)
                .width(200.dp),
            onClick = {
                mContext.startActivity(Intent(mContext, HomeMerchant::class.java))
            },
            colors = ButtonDefaults.buttonColors(
                Color.Blue
            )
        ) {
            Text(
                text = "Log in",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

 */
