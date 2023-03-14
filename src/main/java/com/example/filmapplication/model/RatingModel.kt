package com.example.filmapplication.model

import com.google.gson.annotations.SerializedName

data class RatingModel (
    @SerializedName("Source" ) var Source : String? = null,
    @SerializedName("Value"  ) var Value  : String? = null

)
