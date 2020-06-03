package com.example.myintelligence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.main_login.*
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.Request
import com.android.volley.Response

class MainLogin : AppCompatActivity() {

    val url_api = "http://192.168.1.68"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_login)
    }

    fun login(view: View) {
        if (textControl.text.toString().isEmpty() || textPassword.text.toString().isEmpty()) {
            Toast.makeText(this, "Algun Campo se Encuentra Vacio", Toast.LENGTH_LONG).show()
        } else {
            val control = textControl.text.toString()
            val contrasena = textPassword.text.toString()
            val wsURL = url_api + "/DAMI/Users/loginIntelligent.php"
            var jsonEntrada = JSONObject()
            jsonEntrada.put("num_user", control)
            jsonEntrada.put("pas_user", contrasena)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, wsURL, jsonEntrada,
                Response.Listener { response ->
                    val succ = response["success"]
                    val msg = response["message"]
                    if (succ == 200) {
                        val sensadoJson = response.getJSONArray("user")
                        val num_user = sensadoJson.getJSONObject(0).getString("num_user")
                        val pas_user = sensadoJson.getJSONObject(0).getString("pas_user")

                        val actHome = Intent(this, MainActivity::class.java)
                        actHome.putExtra(MainActivity.EXTRA_CONTROL, num_user)
                        actHome.putExtra(MainActivity.EXTRA_CONTRASENA, pas_user)
                        startActivity(actHome)
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this,
                        "Error en el Usuario : " + error.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("Intelligent ", error.message.toString())
                }
            )
            VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }
    }

    fun registrar(view: View) {
        val actReg: Intent = Intent(this, MainRegister::class.java)
        startActivity(actReg)
    }
}
