package com.example.myintelligence

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.main_login.*
import org.json.JSONObject


class FragmentUser : Fragment() {

    val url_api = "http://192.168.1.68"
    var botonCerrar: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val preferences =
            this.activity!!.getSharedPreferences(Preferencias.DATOS, Context.MODE_PRIVATE)
        val cont = preferences.getString(Preferencias.CONTROL, "No Disponible")

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        val wsURL = url_api + "/DAMI/Users/getUsers.php"
        var jsonEntrada = JSONObject()
        jsonEntrada.put("num_user", cont)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEntrada,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200) {
                    val sensadoJson = response.getJSONArray("user")
                    val num_user = sensadoJson.getJSONObject(0).getString("num_user")
                    val pas_user = sensadoJson.getJSONObject(0).getString("pas_user")
                    val name_user = sensadoJson.getJSONObject(0).getString("name_user")
                    val esp_user = sensadoJson.getJSONObject(0).getString("esp_user")
                    val sem_user = sensadoJson.getJSONObject(0).getString("sem_user")
                    val age_user = sensadoJson.getJSONObject(0).getString("age_user")
                    val sex_user = sensadoJson.getJSONObject(0).getString("sex_user")

                    nomUsuario.setText(name_user)
                    noConUsuario.setText("No. Control : " + num_user)
                    edadUsuario.setText("Edad : " + age_user + " Años   ")
                    semUsuario.setText("Semestre : " + sem_user)
                    espUsuario.setText("Carrera : " + esp_user + "  ")
                    sexoUsuario.setText("Sexo : " + sex_user)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    view.context,
                    "Error en el Usuario : " + error.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
                Log.d("Intelligent ", error.message.toString())
            }
        )
        VolleySingleton.getInstance(view.context).addToRequestQueue(jsonObjectRequest)

        botonCerrar = view.findViewById(R.id.cerrarSesionUsuario)
        botonCerrar?.setOnClickListener{
            Toast.makeText(view.context,"Cerrar Sesión", Toast.LENGTH_LONG).show()
        }

        return view
    }
}
