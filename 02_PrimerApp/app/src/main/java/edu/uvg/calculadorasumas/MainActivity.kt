package edu.uvg.calculadorasumas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var resultadoEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)  //Despues de esta llamada ya puedo usar los elementos

        val number1EditText = findViewById<EditText>(R.id.numero1EditText)
        val number2EditText = findViewById<EditText>(R.id.numero2EditText)
        val sumaBotton = findViewById<Button>(R.id.SumarButton)
        resultadoEditText = findViewById<EditText>(R.id.resultadoEditText)

        sumaBotton.setOnClickListener{
            val numero1 = number1EditText.text.toString().trim().toDoubleOrNull()
            val numero2 = number2EditText.text.toString().trim().toDoubleOrNull()

            if ((numero1 != null) && (numero2 != null)){
                val suma = numero1 + numero2
                resultadoEditText.setText("$suma")
            } else {
                resultadoEditText.setText("Los datos no son validos")
            }


        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}