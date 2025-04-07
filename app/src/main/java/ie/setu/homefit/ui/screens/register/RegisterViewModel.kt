package ie.setu.homefit.ui.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import ie.setu.homefit.data.rules.Validator
import ie.setu.homefit.firebase.auth.Response
import ie.setu.homefit.firebase.services.AuthService
import ie.setu.homefit.firebase.services.FirebaseSignInResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val authService: AuthService,
)
    : ViewModel() {

    var registrationUIState = mutableStateOf(RegisterUIState())
    var allValidationsPassed = mutableStateOf(false)
    var signUpInProgress = mutableStateOf(false)

    private val _signupFlow = MutableStateFlow<FirebaseSignInResponse?>(null)
    val signupFlow: StateFlow<FirebaseSignInResponse?> = _signupFlow

    fun signUpUser() = viewModelScope.launch {
        _signupFlow.value = Response.Loading

        val result = authService.createUser(
            name = registrationUIState.value.firstName,
            email = registrationUIState.value.email,
            password = registrationUIState.value.password
        )
        _signupFlow.value = result
    }

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.FirstNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    firstName = event.firstName
                )
            }

            is RegisterUIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email
                )
            }

            is RegisterUIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password
                )
            }

            is RegisterUIEvent.RegisterButtonClicked -> {
                signUpUser()
            }

            is RegisterUIEvent.PrivacyPolicyCheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    privacyPolicyAccepted = event.status
                )
            }
        }
        validateDataWithRules()
    }


    private fun validateDataWithRules() {
        val fNameResult = Validator.validateFirstName(
            fName = registrationUIState.value.firstName
        )

        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )

        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = registrationUIState.value.privacyPolicyAccepted
        )

        registrationUIState.value = registrationUIState.value.copy(
            firstNameError = fNameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )

        allValidationsPassed.value = fNameResult.status && emailResult.status &&
                passwordResult.status && privacyPolicyResult.status
    }

    fun resetRegisterFlow() {
        _signupFlow.value = null
    }
}
