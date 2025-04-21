package ie.setu.homefit.ui.screens.profile

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ie.setu.homefit.R
import ie.setu.homefit.ui.components.general.HeadingTextComponent
import ie.setu.homefit.ui.components.general.MyTextFieldComponent
import ie.setu.homefit.ui.components.general.ShowPhotoPicker
import ie.setu.homefit.ui.screens.login.LoginViewModel
import ie.setu.homefit.ui.screens.register.RegisterViewModel
import ie.setu.homefit.ui.theme.HomeFitTheme

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit = {},
    profileViewModel: ProfileViewModel = hiltViewModel(),
//    loginViewModel: LoginViewModel = hiltViewModel(),
//    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    var photoUri: Uri? by remember { mutableStateOf(profileViewModel.photoUri) }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var targetCalories by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HeadingTextComponent(value = stringResource(id = R.string.account_settings))
        Spacer(modifier = Modifier.height(10.dp))

        if(photoUri.toString().isNotEmpty())
            ProfileContent(
                photoUri = photoUri,
                displayName = profileViewModel.displayName,
                email = profileViewModel.email
            )
        ShowPhotoPicker(
            onPhotoUriChanged = {
                photoUri = it
                profileViewModel.updatePhotoUri(photoUri!!)
            }
        )
        TextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = targetCalories,
            onValueChange = { targetCalories = it },
            label = { Text("Target Calories/week") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                profileViewModel.saveUserProfile(
                    height.toIntOrNull() ?: 0,
                    weight.toIntOrNull() ?: 0,
                    targetCalories.toIntOrNull() ?: 0,
                    dob
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
        ) {
            Text(text = "Save Profile")
        }

        Button(
            onClick = {
                profileViewModel.signOut()
                onSignOut()
//                loginViewModel.resetLoginFlow()
//                registerViewModel.resetRegisterFlow()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
        ) {
            Text(text = "Logout")
        }
    }
}