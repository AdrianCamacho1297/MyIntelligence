package com.example.myintelligence

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main_answer.*
import org.json.JSONObject

class MainAnswer : AppCompatActivity() {

    val url_api = "http://192.168.1.68"

    companion object {
        val TEST_ACTUAL = "Test"
        val QUESTION_ACTUAL = "Question"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_answer)

        var listAnswer: ArrayList<dataAnswer>? = null

        val res = intent
        val questionActualString = res.getStringExtra(MainAnswer.QUESTION_ACTUAL)
        val gson = Gson()
        val questionActual = gson.fromJson(questionActualString, dataQuestions::class.java)

        val testActual = res.getStringExtra(MainAnswer.TEST_ACTUAL)
        val id_question = questionActual.idQuestion

        val textPregunta = questionActual.question
        pregunta.setText(textPregunta)

        val wsURL = url_api + "/DAMI/Tests/getTestQuestionAnswers.php"
        var jsonEntrada = JSONObject()
        jsonEntrada.put("id_test", testActual)
        jsonEntrada.put("id_question", id_question)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL, jsonEntrada,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200) {
                    val sensadoJson = response.getJSONArray("answers")
                    for (i in 0 until sensadoJson.length()) {
                        val id_answer = sensadoJson.getJSONObject(i).getString("id_answer")
                        val answer = sensadoJson.getJSONObject(i).getString("answer")
                        val status_answer = sensadoJson.getJSONObject(i).getString("status_answer")
                        val id_question = sensadoJson.getJSONObject(i).getString("id_question")
                        listAnswer?.add(dataAnswer(id_answer, answer, status_answer, id_question))
                        if( i == 0 ) btnAnswer_one.text = "${answer}"
                        if( i == 1 ) btnAnswer_two.text = "${answer}"
                        if( i == 2 ) btnAnswer_three.text = "${answer}"
                        if( i == 3 ) btnAnswer_four.text = "${answer}"
                    }
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error : " + error.message.toString(), Toast.LENGTH_LONG).show();
                Log.d("Error : ", error.message.toString())
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun resUno(view: View) {

    }
}
