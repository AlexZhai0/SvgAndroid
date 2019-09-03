package com.alex.svgandroid

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alex.svgandroid.SvgListFragment.Companion.NAME_SVG
import com.alex.svgandroid.utils.FileSVG
import com.alex.svgandroid.utils.queryFiles
import com.alex.svgandroid.utils.toast
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SVGViewModel : ViewModel() {

  val fileSVGList = MutableLiveData<ArrayList<FileSVG>>()

  fun searchSVGFile(context: Context?, scopeProvider: AndroidLifecycleScopeProvider) {
    Observable.create(ObservableOnSubscribe<ArrayList<FileSVG>> {
      it.onNext(queryFiles(context, NAME_SVG))
    }).subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .autoDisposable(scopeProvider)
      .subscribe({
        if (it.size == 0) {
          toast(context, R.string.svg_error)
          return@subscribe
        }

        fileSVGList.value = it
      }, {
        toast(context, R.string.error_02)
      })
  }

}