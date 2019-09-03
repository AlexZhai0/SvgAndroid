package com.alex.svgandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alex.svgandroid.base.fragment.BaseFragment
import com.alex.svgandroid.base.listener.AdapterItemClick
import com.alex.svgandroid.utils.CheckPermission
import com.alex.svgandroid.utils.FileSVG
import com.alex.svgandroid.utils.checkPermission
import com.alex.svgandroid.utils.log
import kotlinx.android.synthetic.main.fragment_layout_svg_list.*

/**
 * 使用 WebView 打开 SVG 文件
 *
 * 1、查找手机上所有的 SVG 文件，并生成列表
 * 2、点击列表中的某个 SVG 文件就用 Fragment 打开
 * 3、尝试使用 ViewModel，旋转屏幕可以查看
 * 4、优化 WebView 显示
 */
class SvgListFragment : BaseFragment(), AdapterItemClick {

  override val layoutId = R.layout.fragment_layout_svg_list

  private val listFileSVG = ArrayList<FileSVG>()

  private val viewModel by lazy {
    ViewModelProviders.of(this)[SVGViewModel::class.java]
  }

  private val svgAdapter by lazy {
    SVGAdapter(context, listFileSVG, this)
  }

  companion object {
    const val NAME_SVG = "SVG"
    const val FILE_BEGIN = "file://"
    const val FILE_PATH = "file_path"
    const val FILE_NAME = "file_name"
  }

  override fun setToolbarContent(toolbar: Toolbar) {
    super.setToolbarContent(toolbar)
    toolbar.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
    val toolbarTitle = toolbar.findViewById<TextView>(R.id.toolbarTitle)
    toolbar.findViewById<ImageView>(R.id.toolbarBack).visibility = View.GONE
    toolbarTitle.text = NAME_SVG
    toolbarTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestPermission()
  }

  @SuppressLint("SetJavaScriptEnabled")
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recyclerViewSVG.layoutManager = LinearLayoutManager(context)
    recyclerViewSVG.adapter = svgAdapter
  }

  private fun requestPermission() {
    checkPermission(context, CheckPermission.storage,
      {
        initData()
      }, {
        requestPermission()
      })
  }

  private fun initData() {
    viewModel.searchSVGFile(context, scopeProvider)
    log(mCurrentClassName,"ViewModel init data")
    viewModel.fileSVGList.observe(this, Observer {
      listFileSVG.clear()
      listFileSVG.addAll(it)
      log(mCurrentClassName, "add data completed")
      svgAdapter.notifyDataSetChanged()
    })
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T> itemClick(holder: RecyclerView.ViewHolder, list: List<T>) {
    val data = list as ArrayList<FileSVG>
    val fileSVG = data[holder.adapterPosition]
    val bundle = Bundle()
    bundle.putString(FILE_PATH, FILE_BEGIN + fileSVG.path)
    bundle.putString(FILE_NAME, fileSVG.fileName)

    NavHostFragment.findNavController(this).navigate(R.id.fragment_action_svg_list, bundle)

//    Navigation.createNavigateOnClickListener(R.id.fragment_action_svg_list, bundle)

//    Navigation.findNavController(holder.itemView).navigate(R.id.fragment_action_svg_list, bundle)
  }
}



