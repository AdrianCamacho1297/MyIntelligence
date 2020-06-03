package com.example.myintelligence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.main_register.*
import org.json.JSONObject

class MainRegister : AppCompatActivity() {

    val url_api = "http://192.168.1.68"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_register)
    }

    fun register(view: View) {
        if (textNoControl.text.toString().isEmpty() || textContrasena.text.toString()
                .isEmpty() || textNombre.text.toString()
                .isEmpty() || textEspecialidad.text.toString()
                .isEmpty() || textSemestre.text.toString().isEmpty() || textEdad.text.toString()
                .isEmpty() || textSexo.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "Algun Campo del Registro se Encuentra Vacio", Toast.LENGTH_SHORT)
                .show()
        } else {
            val control = textNoControl.text.toString()
            val contrasena = textContrasena.text.toString()
            val nombre = textNombre.text.toString()
            val especialidad = textEspecialidad.text.toString()
            val semestre = textSemestre.text.toString()
            val edad = textEdad.text.toString()
            val sexo = textSexo.text.toString()

            var jsonEntrada = JSONObject()
            jsonEntrada.put("num_user", control)
            jsonEntrada.put("pas_user", contrasena)
            jsonEntrada.put("name_user", nombre)
            jsonEntrada.put("esp_user", especialidad)
            jsonEntrada.put("sem_user", semestre)
            jsonEntrada.put("age_user", edad)
            jsonEntrada.put("sex_user", sexo)
            sendRequest(url_api + "/DAMI/Users/insertUsers.php", jsonEntrada)

            val actHome: Intent = Intent(this, MainActivity::class.java)
            actHome.putExtra(MainActivity.EXTRA_CONTROL, control)
            actHome.putExtra(MainActivity.EXTRA_CONTRASENA, contrasena)
            startActivity(actHome)
        }
    }

    fun cancel(view: View) {
        val actLogin: Intent = Intent(this, MainLogin::class.java)
        startActivity(actLogin)
    }

    private fun sendRequest(wsURL: String, jsonEnt: JSONObject) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200) {
                    textNoControl.setText("")
                    textContrasena.setText("")
                    textNombre.setText("")
                    textEspecialidad.setText("")
                    textSemestre.setText("")
                    textEdad.setText("")
                    textSexo.setText("")
                    textNoControl.requestFocus()
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show()
                    //Toast.makeText(this, "Success:${succ}  Message:${msg}", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show()
                Log.d(" ERROR ", "${error.message}")
                Toast.makeText(this, "Error en la URL", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
