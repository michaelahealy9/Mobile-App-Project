package org.wit.product.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel(var id:Long=0,
                        var title:String="",
                        var description:String="",
                        var image:String=""):Parcelable