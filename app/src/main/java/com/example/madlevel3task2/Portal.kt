package com.example.madlevel3task2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Portal(val title:String, var url:String) : Parcelable