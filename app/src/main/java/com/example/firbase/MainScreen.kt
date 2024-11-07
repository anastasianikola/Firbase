import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.firbase.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun MainScreen(onAddData: (String, String, String) -> Unit) {
    var userId by remember { mutableStateOf(TextFieldValue()) }
    var name by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    val users = remember { mutableStateListOf<User>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = userId, onValueChange = { userId = it }, label = { Text("User ID") })
        TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })

        Button(
            onClick = {
                onAddData(userId.text, name.text, email.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Data")
        }

        Button(
            onClick = {
                val database = FirebaseDatabase.getInstance().getReference("users")
                database.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        users.clear()
                        for (snapshot in dataSnapshot.children) {
                            val user = snapshot.getValue(User::class.java)
                            if (user != null) users.add(user)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Обработка ошибок
                    }
                })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Fetch Data")
        }
    }
}
