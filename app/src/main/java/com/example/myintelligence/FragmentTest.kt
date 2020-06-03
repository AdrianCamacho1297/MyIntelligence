package com.example.myintelligence

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson

class FragmentTest : Fragment() {

    val url_api = "http://192.168.1.68"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_test, container, false)

        // ArrayList
        var listTest: ArrayList<dataTests>? = null
        val listT = ArrayList<dataTests>()
        // RecyclerView
        var lista: RecyclerView? = null
        var layoutManager: RecyclerView.LayoutManager? = null
        //Map of Data, Test
        val wsURL = url_api + "/DAMI/Tests/getTests.php"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, wsURL, null,
            Response.Listener { response ->
                val testJson = response.getJSONArray("tests")
                listTest = ArrayList()
                for (i in 0 until testJson.length()) {
                    val id_test = testJson.getJSONObject(i).getString("id_test")
                    val name_test = testJson.getJSONObject(i).getString("name_test")
                    val time_test = testJson.getJSONObject(i).getString("time_test")
                    val desc_test = testJson.getJSONObject(i).getString("desc_test")
                    //Add ArrayList
                    listTest?.add(dataTests(id_test, name_test, time_test, desc_test))
                    //Add RecyclerView
                    listT.add(dataTests(id_test, name_test, time_test, desc_test))
                }
                //RecyclerView
                lista = view.findViewById(R.id.listaTest)
                lista?.setHasFixedSize(true)
                layoutManager = LinearLayoutManager(view.context)
                lista?.layoutManager = layoutManager
                var adaptador = AdaptadorCustom(listT, object : ClickListenerTest {
                    override fun onClick(view: View, index: Int) {
                        val testToJson = Gson()
                        val testActualString = testToJson.toJson(listT.get(index))
                        val intent = Intent(view.context, MainQuestion::class.java)
                        intent.putExtra(MainQuestion.TEST_ACTUAL, testActualString)
                        startActivity(intent)
                    }
                })
                lista?.adapter = adaptador
            },
            Response.ErrorListener { error ->
                Toast.makeText(view.context, "Error : " + error.message.toString(), Toast.LENGTH_LONG).show()
                Log.d("Error : ", error.message.toString())
            }
        )
        VolleySingleton.getInstance(view.context).addToRequestQueue(jsonObjectRequest)
        return view
    }
}
