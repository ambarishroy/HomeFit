package ie.setu.homefit.ui.screens.profile

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.homefit.R
import ie.setu.homefit.ui.components.general.HeadingTextComponent
import ie.setu.homefit.ui.components.general.MyTextFieldComponent
import ie.setu.homefit.ui.components.general.ShowPhotoPicker
import ie.setu.homefit.ui.screens.login.LoginViewModel
import ie.setu.homefit.ui.screens.register.RegisterViewModel
import ie.setu.homefit.ui.theme.HomeFitTheme
import androidx.compose.foundation.layout.Row

@Composable
fun ProfileScreen(
    onSignOut: () -> Unit = {},
    profileViewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
//    loginViewModel: LoginViewModel = hiltViewModel(),
//    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    var photoUri: Uri? by remember { mutableStateOf(profileViewModel.photoUri) }
    val height by profileViewModel.height
    val weight by profileViewModel.weight
    val targetCalories by profileViewModel.targetCalories
    val dob by profileViewModel.dob
    val isProfileSaved by profileViewModel.isProfileSaved

    LaunchedEffect(isProfileSaved) {
        if (isProfileSaved) {
            navController.navigate("home") {
                popUpTo("profile") { inclusive = true }
            }
            profileViewModel.isProfileSaved.value = false
        }
    }
    LaunchedEffect(Unit) {
        profileViewModel.loadUserProfile()
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,

    ) {
        //HeadingTextComponent(value = stringResource(id = R.string.account_settings))
        //Spacer(modifier = Modifier.height(10.dp))
        //Spacer(modifier = Modifier.height(1.dp))
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(1.dp))
                TextField(
                    value = height,
                    onValueChange = { profileViewModel.height.value = it },
                    label = { Text("Height (cm)") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = weight,
                    onValueChange = { profileViewModel.weight.value = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = targetCalories,
                    onValueChange = { profileViewModel.targetCalories.value = it },
                    label = { Text("Target Calories/week") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = dob,
                    onValueChange = { profileViewModel.dob.value = it },
                    label = { Text("Date of Birth") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        profileViewModel.saveUserProfile()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(text = "Save Profile")
                }
            }
        }




    }
}