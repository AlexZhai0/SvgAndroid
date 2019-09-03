package com.alex.svgandroid.base.listener

import androidx.recyclerview.widget.RecyclerView

interface AdapterItemClick {

  fun <T> itemClick(holder: RecyclerView.ViewHolder, list: List<T>)

}