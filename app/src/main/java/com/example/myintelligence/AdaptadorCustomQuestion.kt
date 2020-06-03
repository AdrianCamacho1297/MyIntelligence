package com.example.myintelligence

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdaptadorCustomQuestion(items: ArrayList<dataQuestions>, var listener: ClickListenerQuestion) :
    RecyclerView.Adapter<AdaptadorCustomQuestion.ViewHolder>() {

    var items: ArrayList<dataQuestions>? = null

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorCustomQuestion.ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.element_question, parent, false)
        val viewHolder = ViewHolder(vista, listener)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.question?.text = item?.question
    }

    class ViewHolder(view: View, listener: ClickListenerQuestion) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var view = view
        var question: TextView? = null
        var listener: ClickListenerQuestion? = null

        init {
            question = view.findViewById(R.id.titleQuestion)
            this.listener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }

}