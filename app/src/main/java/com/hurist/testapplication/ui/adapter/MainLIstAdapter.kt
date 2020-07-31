package com.hurist.testapplication.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hurist.testapplication.R

/**
 * author: spike
 * version：1.0
 * create data：2020/7/31
 * Description：MainLIstAdapter
 */
class MainListAdapter(val context: Activity, private val datas: List<Pair<String, Any>>) :
    RecyclerView.Adapter<MainListAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_simple_text, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.textView.text = datas[position].first
        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, datas[position].second as Class<*>))
        }
    }
}