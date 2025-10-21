package me.marcosvalera.unabstore

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlin.collections.mutableListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalView
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onClickback :()-> Unit={},onSusccessfullResgister:()->Unit={}) {
    val auth = Firebase.auth
    val activity = LocalView.current.context as Activity

    //estados de los inputs
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var passw by remember { mutableStateOf("") }
    var passwConfirm by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwError by remember { mutableStateOf("") }
    var passwconfirmError by remember { mutableStateOf("") }

    var registerError by remember { mutableStateOf("") }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onClickback) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ícono de Usuario
            Image(
                painter = painterResource(id = R.drawable.img_icon_unab),
                contentDescription = "Usuario",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Título
            Text(
                text = "Registro de Usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de Nombre
            OutlinedTextField(
                value = name,
                onValueChange = {name=it},
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (nameError.isNotEmpty()){
                        Text(text = nameError,
                            color=Color.Red
                        )
                    }
                }

            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Correo Electrónico
            OutlinedTextField(
                value = email,
                onValueChange = {email=it},
                label = { Text("Correo Electrónico") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (emailError.isNotEmpty()){
                        Text(text = emailError,
                            color=Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Contraseña
            OutlinedTextField(
                value = passw,
                onValueChange = {passw=it},
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Contraseña")
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (passwError.isNotEmpty()){
                        Text(text = passwError,
                            color=Color.Red
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Confirmar Contraseña
            OutlinedTextField(
                value = passwConfirm,
                onValueChange = {passwConfirm=it},
                label = { Text("Confirmar Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirmar Contraseña"
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (passwconfirmError.isNotEmpty()){
                        Text(text = passwconfirmError,
                            color=Color.Red
                        )
                    }
                }
            )
            if (registerError.isNotEmpty()){
                Text(registerError ,color=Color.Red)

            }
            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Registro
            Button(
                onClick = {
                    val isvalidname=validationName(name).first
                    val isvalidemail= validationEmail(email).first
                    val isvalidpassw=validatePassword(passw).first
                    val isvalidconfrimpassw=validateConfirmPassword(passw,passwConfirm).first

                    nameError=validationName(name).second
                    emailError=validationEmail(email).second
                    passwError=validatePassword(passw).second
                    passwconfirmError=validateConfirmPassword(passw,passwConfirm).second

                    if (isvalidname && isvalidemail && isvalidpassw && isvalidconfrimpassw){
                        auth.createUserWithEmailAndPassword(email,passw).
                                addOnCompleteListener(activity) { task->
                                    if(task.isSuccessful){
                                        onSusccessfullResgister()
                                    }else{
                                        registerError= when(task.isSuccessful){
                                            is FirebaseAuthInvalidCredentialsException -> "Correo invalido"
                                            is FirebaseAuthUserCollisionException -> "Correo ya registrado"
                                            else -> "Error al registrarse"
                                        }
                                    }
                                }

                    }else{
                        registerError= "Hubo un error en el register"
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9900))
            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
