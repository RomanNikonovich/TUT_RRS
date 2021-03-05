package com.example.presentation.screens.main.rv_adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.model.entity.Article
import com.example.presentation.R
import kotlinx.android.synthetic.main.item_article.view.*
import java.util.*

class AdaptRV : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list: MutableList<Article> = arrayListOf()
    private var listener: OnArticleClickListener? = null

    fun setArticles(list: List<Article>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun setListener(listener: OnArticleClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
                LayoutInflater.from(parent?.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (list[position].isClicked){
            holder.itemView.setBackgroundColor(Color.parseColor("#00ff00"))
        }

        holder.itemView.setOnClickListener { listener?.let { it.onClick(list[position], position) } }
        holder.itemView.title.text = list[position].title
        holder.itemView.author.text = list[position].author
        val calendar = Calendar.getInstance().apply { timeInMillis = list[position].date }
        holder.itemView.date.text = "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${calendar.get(Calendar.YEAR)}"

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var author: TextView
        var date: TextView

        init {
            title = view.findViewById(R.id.title)
            author = view.findViewById(R.id.author)
            date = view.findViewById(R.id.date)
        }
    }
}

interface OnArticleClickListener {
    fun onClick(article: Article, position: Int)
}