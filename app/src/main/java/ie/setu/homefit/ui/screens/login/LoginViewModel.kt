package ie.setu.homefit.ui.screens.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import dagger.hilt.android.lifecycle.HiltViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.data.rules.Validator
import ie.setu.homefit.firebase.auth.Response
import ie.setu.homefit.firebase.services.AuthService
import ie.setu.homefit.firebase.services.FirebaseSignInResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.text.Typography.dagger

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val credentialManager: CredentialManager,
    private val credentialRequest: GetCredentialRequest
)
    : ViewModel() {

    private val _loginFlow = MutableStateFlow<FirebaseSignInResponse?>(null)
    val loginFlow: StateFlow<FirebaseSignInResponse?> = _loginFlow
    private val _resetPasswordMessage = mutableStateOf<String?>(null)
    val resetPasswordMessage = _resetPasswordMessage

    var loginUIState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)

    init {
        if (authService.currentUser != null) {
            _loginFlow.value = Response.Success(authService.currentUser!!)
        }
    }

    private fun loginUser() = viewModelScope.launch {

        val email = loginUIState.value.email
        val password = loginUIState.value.password

        _loginFlow.value = Response.Loading
        val result = authService.authenticateUser(email, password)
        _loginFlow.value = result
    }

    private fun loginGoogleUser(googleIdToken: String)
            = viewModelScope.launch {
        _loginFlow.value = Response.Loading
        val result = authService.authenticateGoogleUser(googleIdToken)
        _loginFlow.value = result
    }

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> { loginUser() }
            is LoginUIEvent.ForgotPasswordClicked -> {
                sendResetEmail()
            }

        }
        validateLoginUIDataWithRules()
    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    fun resetLoginFlow() {
        _loginFlow.value = null
    }

    fun signInWithGoogleCredentials(credentialsContext : Context) {
        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = credentialRequest,
                    context = credentialsContext,
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                // handleFailure(e)
                Timber.tag("TAG").e(e, "Get credential exception")
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential
                        .TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val googleIdToken = googleIdTokenCredential.idToken
                        loginGoogleUser(googleIdToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Timber.tag("TAG").e(e, "Received an invalid " +
                                "google id token response")
                    }
                }
            }
        }
    }
    private fun sendResetEmail() = viewModelScope.launch {
        val email = loginUIState.value.email

        if (email.isBlank()) {
            _resetPasswordMessage.value = "Please enter your email address"
            return@launch
        }

        val result = authService.sendPasswordResetEmail(email)
        when (result) {
            is Response.Success -> {
                _resetPasswordMessage.value = "Password reset email sent to $email"
            }
            is Response.Failure -> {
                _resetPasswordMessage.value = "Failed to send email: ${result.e.message}"
            }
            is Response.Loading -> {
                _resetPasswordMessage.value = "Still Loading"
            }
        }

    }

}


