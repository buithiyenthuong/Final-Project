package com.example.life.management.model

import java.io.Serializable

data class Plan(
    var timeStart: String,
    var timeEnd: String,
    var title: String,
    var content: String
) : Serializable{
}