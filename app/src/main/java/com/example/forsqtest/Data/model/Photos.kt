package com.example.forsqtest.Data.model

data class Photos(
        val count: Int,
        val groups: List<PhotosIn>
)


data class PhotosIn(
        val prefix: String,
        val suffix: String
)