package com.alex.svgandroid.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

fun queryFiles(context: Context?, fileSuffixName: String): ArrayList<FileSVG> {

  val list = ArrayList<FileSVG>()

  val projection = arrayOf(
    MediaStore.Files.FileColumns._ID,
    MediaStore.Files.FileColumns.DATA,
    MediaStore.Files.FileColumns.SIZE
  )
  val cursor = context?.contentResolver?.query(
    Uri.parse("content://media/external/file"),
    projection,
    MediaStore.Files.FileColumns.DATA + " like ?",
    arrayOf("%.$fileSuffixName"),
    null
  )
  if (cursor != null) {
    if (cursor.moveToFirst()) {
      do {
        val id = cursor.getString(
          cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
        )
        val path = cursor.getString(
          cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
        )
        val size = cursor.getString(
          cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE)
        )
        val fileName = path.substring(path.lastIndexOf("/") + 1)
        val fileSVG = FileSVG(id, path, fileName, size)
        list.add(fileSVG)
      } while (cursor.moveToNext())
    }
  }
  cursor?.close()
  return list
}

data class FileSVG(
  val id: String,
  val path: String,
  val fileName: String,
  val size: String
)