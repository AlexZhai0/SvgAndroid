package com.alex.svgandroid.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun queryFiles(context: Context?, fileSuffixName: String): ArrayList<FileSVG> {

  val list = ArrayList<FileSVG>()

  val id = MediaStore.Files.FileColumns._ID
  val path = MediaStore.Files.FileColumns.DATA
  val size = MediaStore.Files.FileColumns.SIZE

  val cursor = context?.contentResolver?.query(
    Uri.parse("content://media/external/file"),
    arrayOf(id, path, size), "$path like ?",
    arrayOf("%.$fileSuffixName"), null
  )

  cursor?.apply {
    if (moveToFirst()) {
      do {
        val pathName = getString(getColumnIndex(path))
        val fileSVG = FileSVG(
          getString(getColumnIndex(id)),
          pathName,
          pathName.substring(pathName.lastIndexOf("/") + 1),
          getString(getColumnIndex(size))
        )
        list.add(fileSVG)
      } while (cursor.moveToNext())
    }
    close()
  }
  return list
}

data class FileSVG(
  val id: String,
  val path: String,
  val fileName: String,
  val size: String
)