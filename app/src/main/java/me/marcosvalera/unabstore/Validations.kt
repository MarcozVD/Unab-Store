package me.marcosvalera.unabstore

import android.util.Patterns
import androidx.compose.ui.graphics.vector.Path

fun validationEmail(email:String):Pair<Boolean,String> {
    return when{
        email.isEmpty() ->Pair(false,"El correo es requerido.")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()->Pair(false,"El correo es invalido")
        !email.endsWith("@test.com")-> Pair(false,"Ese email no es corporativo")
        else -> Pair(true,"")
    }

}
fun validatePassword(password:String): Pair <Boolean,String>{
    return when{
        password.isEmpty() -> Pair(false,"La contraseña es requerida")
        password.length<6 -> Pair(false,"La contraseña debe tener minimo 6 caracteres")
        !password.any{it.isDigit()}->Pair(false,"La contraseña debe tener al menos un número ")
        else ->Pair(true,"")
    }
}
fun validationName(name: String):Pair<Boolean, String>{
    return when{
        name.isEmpty() -> Pair(false,"El nombre es requerido")
        name.length < 3-> Pair(false,"El nombre debe tener al menos 3 caracteres")
        else -> Pair(true,"")
    }
}
fun validateConfirmPassword(passw:String,confirmPassw:String):Pair<Boolean, String>{
    return when{
        confirmPassw.isEmpty() -> Pair(false,"La contraseña es requerida")
        confirmPassw !=passw -> Pair(false,"Las contraseñas no coinciden")
        else -> Pair(true,"")
    }
}