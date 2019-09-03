package com.alex.svgandroid

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.alex.svgandroid.base.listener.AdapterItemClick
import com.alex.svgandroid.utils.FileSVG
import com.alex.svgandroid.utils.dp
import com.alex.svgandroid.utils.onNoDoubleClick
import com.alex.svgandroid.utils.setViewDrawable

class SVGAdapter(
  private val context: Context?,
  private val list: ArrayList<FileSVG>,
  private val click: AdapterItemClick?
) : RecyclerView.Adapter<SVGAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(
      R.layout.item_text, parent, false
    )
    return ViewHolder(view)
  }

  override fun getItemCount() = list.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.textView?.text = list[position].fileName

    context?.let {
      holder.textView?.setViewDrawable(ContextCompat.getColor(it, R.color.white))
    }

    holder.textView?.onNoDoubleClick {
      click?.itemClick(holder, list)
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView.findViewById<TextView>(R.id.textViewItem) ?: null

    init {
      textView?.gravity = Gravity.CENTER
      textView?.setPadding(0, 25.dp, 0, 25.dp)
    }
  }
}