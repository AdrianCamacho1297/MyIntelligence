package com.example.myintelligence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.element_question.*
import org.json.JSONObject

class MainQuestion : AppCompatActivity() {

    val url_api = "http://192.168.1.68"

    companion object {
        val TEST_ACTUAL = "Test"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_question)

        var listQuestion: ArrayList<dataQuestions>? = null
        val listQ = ArrayList<dataQuestions>()
        //
        var lista: RecyclerView? = null
        var layoutManager: RecyclerView.LayoutManager? = null
        //
        val res = intent
        val testActualString = res.getStringExtra(TEST_ACTUAL)
        val gson = Gson()
        val testActual = gson.fromJson(testActualString, dataTests::class.java)
        //
        val id_test = testActual.idTest
        //
        val wsURL = url_api + "/DAMI/Tests/getTestQuestions.php"
        var jsonEntrada = JSONObject()
        jsonEntrada.put("id_test", id_test)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEntrada,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200) {
                    val sensadoJson = response.getJSONArray("questions")
                    for (i in 0 until sensadoJson.length()) {
                        val id_question = sensadoJson.getJSONObject(i).getString("id_question")
                        val question = sensadoJson.getJSONObject(i).getString("question")
                        val id_test = sensadoJson.getJSONObject(i).getString("id_test")
                        //Add ArrayList
                        listQuestion?.add(dataQuestions(id_question, question, id_test))
                        //Add RecyclerView
                        listQ.add(dataQuestions(id_question, question, id_test))
                    }
                    lista = findViewById(R.id.listaQuestions)
                    lista?.setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this)
                    lista?.layoutManager = layoutManager
                    var adaptador = AdaptadorCustomQuestion(listQ, object: ClickListenerQuestion{
                        override fun onClick(view: View, index: Int) {
                            val testToJson = Gson()
                            val questionActualString = testToJson.toJson(listQ.get(index))
                            val intent = Intent(applicationContext, MainAnswer::class.java)
                            intent.putExtra(MainAnswer.TEST_ACTUAL, id_test)
                            intent.putExtra(MainAnswer.QUESTION_ACTUAL, questionActualString)
                            startActivity(intent)
                        }
                    })
                    lista?.adapter = adaptador
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error : " + error.message.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error : ", error.message.toString())
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
