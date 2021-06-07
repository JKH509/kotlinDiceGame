package com.example.pig

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val SCORE_LOG_FILE = "scorelog.txt"

class ScoreLog : AppCompatActivity() {
    var scoreList = ArrayList<ScoreLogItem>()
    private lateinit var scoreListAdapter: ScoreLogAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_log)

        val recyclerView: RecyclerView = findViewById(R.id.scoreLogRecycler)
        scoreListAdapter = ScoreLogAdapter(scoreList)

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = scoreListAdapter

        val extras = intent.extras

        if (extras != null){
            val logData: String? = extras.getString("Winner")
            val totalScore = extras.getInt("Score")

            //CSV - Date, Score, playerwins, compwins

            val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
            val formattedDate = LocalDateTime.now().format(formatter)

            // Write data to a permanent log file file
            val fileOutputStream: FileOutputStream= openFileOutput(SCORE_LOG_FILE, MODE_APPEND)
            val scoreLogFile = OutputStreamWriter( fileOutputStream )

            scoreLogFile.write("$logData,$totalScore,$formattedDate\n")
            // whoever wins, score, dateInfo
                scoreLogFile.close()
        }
// read data to edit text window
        readLogData()
    }

    fun readLogData(){
        // read data to edit text window
        val file = File(filesDir, SCORE_LOG_FILE)
        if (file.exists()){
            File( filesDir, SCORE_LOG_FILE).forEachLine {
                val parts = it.split(",")
                // Add new DB item to our list
                var scoreItem = ScoreLogItem(parts[0].padEnd(5), parts[1].padEnd(2), parts[2])
                scoreList.add(scoreItem)
            }
            scoreList.reverse()
            scoreListAdapter.notifyDataSetChanged()
        }
    }

    fun showScoreCalcOnClick(v : View){
        // Intent, Just a structure that android uses to send data to another apllication
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    fun returnToGameOnClick(v : View){
        // Intent, Just a structure that android uses to send data to another apllication
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}