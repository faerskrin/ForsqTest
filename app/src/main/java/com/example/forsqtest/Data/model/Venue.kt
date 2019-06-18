package com.example.forsqtest.Data.model

data class Venue(
        val categories: List<Category>,
        val id: String,
        val location: Location,
        val name: String,
        val photos: Photos
)