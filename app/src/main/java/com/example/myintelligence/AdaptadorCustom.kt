package com.example.myintelligence

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCustom(items: ArrayList<dataTests>, var listener: ClickListenerTest) :
    RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items: ArrayList<dataTests>? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustom.ViewHolder {
        val vista =
            LayoutInflater.from(parent?.context).inflate(R.layout.element_test, parent, false)
        val viewHolder = ViewHolder(vista, listener)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: AdaptadorCustom.ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.idTest?.text = item?.idTest
        holder.nombreTest?.text = item?.nomTest
        holder.tiempoTest?.text = "Duración : " + item?.timeTest + " Minutos"
        holder.descripTest?.text = "Descripción : " + item?.descTest
    }

    class ViewHolder(view: View, listener: ClickListenerTest) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var vista = view
        var idTest: TextView? = null
        var nombreTest: TextView? = null
        var tiempoTest: TextView? = null
        var descripTest: TextView? = null
        var listener: ClickListenerTest? = null

        init {
            idTest = vista.findViewById(R.id.numberTest)
            nombreTest = vista.findViewById(R.id.titleTest)
            tiempoTest = vista.findViewById(R.id.sexoUsuario)
            descripTest = vista.findViewById(R.id.descTest)
            this.listener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }
}