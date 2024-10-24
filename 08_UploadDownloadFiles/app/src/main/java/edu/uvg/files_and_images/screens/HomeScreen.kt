package edu.uvg.files_and_images.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.math.log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    // Estado para el campo de texto
    var textState by remember { mutableStateOf(TextFieldValue()) }

    // Estados para la imagen y archivo
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var fileUri by remember { mutableStateOf<Uri?>(null) }

    // Estados para manejar el estado de carga
    var isUploading by remember { mutableStateOf(false) }
    var uploadError by remember { mutableStateOf<String?>(null) }

    // CoroutineScope para manejar la ejecución de corrutinas
    val coroutineScope = rememberCoroutineScope()

    // Funciones para seleccionar imagen y archivo
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    val fileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> fileUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Escribe tu publicación") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { imageLauncher.launch("image/*") }) {
            Text("Seleccionar imagen")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar imagen seleccionada
        imageUri?.let {
            val bitmap = remember { android.graphics.BitmapFactory.decodeStream(context.contentResolver.openInputStream(it)) }
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null, modifier = Modifier.size(128.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { fileLauncher.launch("*/*") }) {
            Text("Seleccionar archivo")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (textState.text.isNotEmpty()) {
                    isUploading = true
                    uploadError = null

                    // Ejecutar operación de subida en una corrutina
                    coroutineScope.launch {
                        Log.d("test", "before upload")
                        val result = uploadPostToFirebase(textState.text, imageUri, fileUri)
                        isUploading = false
                        if (result != null) {
                            uploadError = result
                            Log.d("test","Inside upload")
                        }
                        Log.d("test","Outside upload")
                    }
                } else {
                    // Mostrar mensaje de error
                    uploadError = "El texto es obligatorio."
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isUploading
        ) {
            Text("Publicar")
        }

        // Mostrar estado de carga
        if (isUploading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        // Mostrar mensaje de error
        uploadError?.let { error ->
            Text(text = error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        }
    }
}

// Función para subir la publicación a Firebase (sin @Composable)
suspend fun uploadPostToFirebase(text: String, imageUri: Uri?, fileUri: Uri?): String? {
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance().reference

    var imageUrl: String? = null
    var fileUrl: String? = null

    return try {
        // Subir imagen si existe
        if (imageUri != null) {
            val imageRef = storage.child("images/${System.currentTimeMillis()}.jpg")
            imageRef.putFile(imageUri).await()
            imageUrl = imageRef.downloadUrl.await().toString()
            Log.d("Image URL", imageUrl.toString())
        }

        // Subir archivo si existe
        if (fileUri != null) {
            val fileRef = storage.child("files/${System.currentTimeMillis()}")
            fileUrl = fileRef.putFile(fileUri).await().storage.downloadUrl.await().toString()
        }

        // Guardar los datos en Firestore
        val post = hashMapOf(
            "text" to text,
            "imageUrl" to imageUrl,
            "fileUrl" to fileUrl,
            "timestamp" to System.currentTimeMillis()
        )

        firestore.collection("posts").add(post).await()

        null
    } catch (e: Exception) {
        // Devuelve el mensaje de error
        Log.d("Ocurrio error: ", e.message.toString())
        e.localizedMessage ?: "Error al subir la publicación"

    }
}
