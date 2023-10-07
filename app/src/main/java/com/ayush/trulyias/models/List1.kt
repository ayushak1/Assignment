package com.ayush.trulyias.models

data class List1(

    val ChapterName: String,
    val childItemList : ArrayList<List2>,
    var isExpandale: Boolean = false

)