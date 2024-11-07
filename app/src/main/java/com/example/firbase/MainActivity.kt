package com.example.firbase
import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Инициализация Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("users")

        setContent {
            MainScreen(onAddData = { userId, name, email ->
                addDataToDatabase(userId, name, email)
            })
        }
    }

    private fun addDataToDatabase(userId: String, name: String, email: String) {
        val user = User(name, email)
        mDatabase.child(userId).setValue(user)
            .addOnSuccessListener {
                // Успешное добавление
            }
            .addOnFailureListener {
                // Ошибка при добавлении
            }
    }
}

data class User(val name: String = "", val email: String = "")
