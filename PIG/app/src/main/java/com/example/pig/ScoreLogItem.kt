package com.example.pig

// Single log item string
class ScoreLogItem( var winner:String, var score: String, var date: String) {

    fun toCSV(): String {
        return "$winner, $score, $date"
    }

    override fun toString(): String{
        return "$winner     : $score : $date"
    }
}