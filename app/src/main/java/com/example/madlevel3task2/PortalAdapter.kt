package com.example.madlevel3task2

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_portal.view.*

class PortalAdapter (private val portals: List<Portal>, val clickListener: (Portal) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_portal, parent, false)
        return PortalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PortalViewHolder).bind(portals[position],clickListener)
    }

    override fun getItemCount(): Int {
        return portals.size
    }

    class PortalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(portal: Portal, clickListener: (Portal) -> Unit) {
            itemView.tvPortalTitle.text = portal.title
            itemView.tvPortalUrl.text = portal.url
            itemView.setOnClickListener { clickListener(portal)}
        }
    }
}