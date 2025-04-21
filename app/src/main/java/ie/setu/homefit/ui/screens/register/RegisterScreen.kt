package ie.setu.homefit.ui.screens.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ie.setu.homefit.firebase.auth.Response
import ie.setu.homefit.navigation.Home
import ie.setu.homefit.navigation.Register
import ie.setu.homefit.ui.components.general.ButtonComponent
import ie.setu.homefit.ui.components.general.CheckboxComponent
import ie.setu.homefit.ui.components.general.ClickableLoginTextComponent
import ie.setu.homefit.ui.components.general.DividerTextComponent
import ie.setu.homefit.ui.components.general.HeadingLogoComponent
import ie.setu.homefit.ui.components.general.HeadingTextComponent
import ie.setu.homefit.ui.components.general.MyTextFieldComponent
import ie.setu.homefit.ui.components.general.NormalTextComponent
import ie.setu.homefit.ui.components.general.PasswordTextFieldComponent
import ie.setu.homefit.ui.components.general.ShowLoader
import ie.setu.homefit.R
import ie.setu.homefit.navigation.Profile

@Composable
fun RegisterScreen(
    onRegister: () -> Unit = {},
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel()) {

    val registerFlow = registerViewModel.signupFlow.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                HeadingLogoComponent()
                Spacer(modifier = Modifier.height(20.dp))
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.FirstNameChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.firstNameError
                )
                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextChanged = {
                        registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.emailError
                )
                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                    },
                    errorStatus = registerViewModel.registrationUIState.value.passwordError
                )
                CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {},
                    onCheckedChange = {
                        registerViewModel.onEvent(RegisterUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                        onRegister()
                    },
                    isEnabled = registerViewModel.allValidationsPassed.value
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        if(registerViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }

    registerFlow.value?.let {
        when (it) {
            is Response.Failure -> {
                val context = LocalContext.current
                Toast.makeText(context, it.e.message, Toast.LENGTH_LONG).show()
                navController.popBackStack()
                navController.navigate(Register.route)
            }
            is Response.Loading -> {
                ShowLoader(message = "Please Wait...")
            }
            is Response.Success -> {
                LaunchedEffect(Unit) {
                    navController.navigate(Profile.route) {
                        popUpTo(Register.route) { inclusive = true }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {
    PreviewRegisterScreen()
}

@Composable
fun PreviewRegisterScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                NormalTextComponent(value = stringResource(id = R.string.register))
                HeadingTextComponent(value = stringResource(id = R.string.create_account))
                Spacer(modifier = Modifier.height(20.dp))
                HeadingLogoComponent()
                Spacer(modifier = Modifier.height(20.dp))

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource(id = R.drawable.profile),
                    onTextChanged = {
                        //  registerViewModel.onEvent(RegisterUIEvent.FirstNameChanged(it))
                    },
                    //errorStatus = registerViewModel.registrationUIState.value.firstNameError
                    errorStatus = true
                )

                MyTextFieldComponent(
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.message),
                    onTextChanged = {
                        //registerViewModel.onEvent(RegisterUIEvent.EmailChanged(it))
                    },
                    errorStatus = true
                )

                PasswordTextFieldComponent(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        //   registerViewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
                    },
                    // errorStatus = registerViewModel.registrationUIState.value.passwordError
                    errorStatus = true
                )

                CheckboxComponent(value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {
                        //  PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                    },
                    onCheckedChange = {
                        //    registerViewModel.onEvent(RegisterUIEvent.PrivacyPolicyCheckBoxClicked(it))
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                ButtonComponent(
                    value = stringResource(id = R.string.register),
                    onButtonClicked = {
                        //   registerViewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
                    },
                    //    isEnabled = registerViewModel.allValidationsPassed.value
                    isEnabled = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                DividerTextComponent()

                ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                    //AuthAppRouter.navigateTo(Screen.LoginScreen)
                })
            }
        }
    }

}