package ie.setu.homefit.data.rules

import android.util.Patterns

object Validator {


    fun validateFirstName(fName: String): ValidationResult {
        return ValidationResult(
            (fName.isNotEmpty() && fName.length >= 3)
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    fun validatePassword(password: String): ValidationResult {
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 6)
        )
    }

    fun validatePrivacyPolicyAcceptance(statusValue:Boolean): ValidationResult {
        return ValidationResult(
            statusValue
        )
    }
}

data class ValidationResult(
    val status: Boolean = false
)








