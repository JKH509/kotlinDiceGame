package com.example.pig

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var yourActive:Boolean = true
    var yourGamesWon: Int = 0
    var yourTotalScore: Int = 0
    var yourTurnTotalScore: Int = 0
    var diceRollTotal = 0

    var activeComp:Boolean = false
    var compGamesWon: Int = 0
    var compTotalScore: Int = 0
    var compTurnTotalScore: Int = 0
    var compDiceRollTotal = 0

    lateinit var  diceImage1: ImageView
    lateinit var  diceImage2: ImageView

    lateinit var yourDiceTotalPoints: TextView
    lateinit var yourTurnTotalPoints: TextView
    lateinit var TotalPoints: TextView
    lateinit var gamesWon: TextView

    lateinit var compTurnTotalPoints: TextView
    lateinit var compTotalGamePoints: TextView
    lateinit var compWins: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         diceImage1= findViewById(R.id.ivDice1)

         diceImage2 = findViewById(R.id.ivDice2)

        // Player info onLoad
        val startScore = 0

        yourDiceTotalPoints = findViewById(R.id.diceTotal)
        yourTurnTotalPoints = findViewById(R.id.playerTurnScore)
        TotalPoints = findViewById(R.id.playerScore)
        gamesWon = findViewById(R.id.playerGamesWon)

        // Comp info onLoad
         compTurnTotalPoints = findViewById(R.id.computerTurnScore)
         compTotalGamePoints = findViewById(R.id.computerTotalScore)
         compWins = findViewById(R.id.computerGamesWon)

        val rollButton: Button = findViewById(R.id.rollButton)

        yourDiceTotalPoints.text = startScore.toString()
        yourTurnTotalPoints.text = startScore.toString()
        TotalPoints.text = startScore.toString()
        gamesWon.text = startScore.toString()
        compTurnTotalPoints.text = startScore.toString()
        compTotalGamePoints.text = startScore.toString()
        compWins.text = startScore.toString()

        diceImage1.setImageResource(R.drawable.dice_2)
        diceImage2.setImageResource(R.drawable.dice_3)

        if(savedInstanceState != null){
            yourGamesWon = savedInstanceState.getInt("gamesWonOutput")
            yourTotalScore = savedInstanceState.getInt("totalScoreOutput")
            yourTurnTotalScore = savedInstanceState.getInt("turnTotalOutput")
            diceRollTotal = savedInstanceState.getInt("diceRollOutput")
//            diceRollTotal = savedInstanceState.getString("diceRollOutput")
            compGamesWon = savedInstanceState.getInt("compGamesWonOutput")
            compTotalScore = savedInstanceState.getInt("compTotalScoreOutput")
            compTurnTotalScore = savedInstanceState.getInt("compTurnTotalOutput")
            compDiceRollTotal = savedInstanceState.getInt("compDiceRollOutput")

            yourDiceTotalPoints.text = startScore.toString()
            yourTurnTotalPoints.text = startScore.toString()
            TotalPoints.text = startScore.toString()
            gamesWon.text = startScore.toString()
            compTurnTotalPoints.text = startScore.toString()
            compTotalGamePoints.text = startScore.toString()
            compWins.text = startScore.toString()

            diceImage1.setImageResource(R.drawable.dice_2)
            diceImage2.setImageResource(R.drawable.dice_3)
        }

        rollButton.setOnClickListener {
            rollDice()
        }
        val holdButton: Button = findViewById(R.id.holdButton)
        holdButton.setOnClickListener {
            totalScore()
            playerWins()
            yourTurnTotalScore = 0
            diceRollTotal = 0
            resetPoints()
            switchPlayer()
        }
    }


    private fun rollDice() {
//        yourTurnTotalScore = 0
        diceRollTotal = 0
        var textViewPlayer  : TextView = findViewById(R.id.textView)
        textViewPlayer.setBackgroundColor(Color.CYAN)
        var textViewComp  : TextView = findViewById(R.id.textView3)
        textViewComp.setBackgroundColor(Color.WHITE)
        val rollButton: Button = findViewById(R.id.rollButton)
        rollButton.isClickable = true
        rollButton.setBackgroundColor(Color.BLUE)
        rollButton.isEnabled = true

        val dice1 = Dice(6)
        val diceRoll1 = dice1.roll()
        val dice2 = Dice(6)
        val diceRoll2 = dice2.roll()

        val diceImage1: ImageView = findViewById(R.id.ivDice1)
        diceImage1.setImageResource(R.drawable.dice_2)
        val diceImage2: ImageView = findViewById(R.id.ivDice2)
        diceImage2.setImageResource(R.drawable.dice_3)

        val drawableResource = when (diceRoll1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage1.setImageResource(drawableResource)

        val drawableResource2 = when (diceRoll2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        diceImage2.setImageResource(drawableResource2)

        if ( diceRoll1 !== 1 && diceRoll2 !== 1){

            diceRollTotal = diceRoll1 + diceRoll2
            yourTurnTotalScore += diceRollTotal

            var diceTotalPoints: TextView = findViewById(R.id.diceTotal)
            diceTotalPoints.text = diceRollTotal.toString()
            var turnTotalPoints: TextView = findViewById(R.id.playerTurnScore)
            turnTotalPoints.text = yourTurnTotalScore.toString()

        } else if ( diceRoll1 == 1 || diceRoll2 == 1){
            yourTurnTotalScore = 0
            diceRollTotal = 0
            switchPlayer()
        }
    }
    fun onCompTurnClick(){

        val rollButton: Button = findViewById(R.id.rollButton)
            rollButton.isClickable = false
            rollButton.isEnabled = false
            rollButton.setBackgroundColor(Color.GRAY)

        var textViewComp  : TextView = findViewById(R.id.textView3)
        textViewComp.setBackgroundColor(Color.CYAN)
        var textViewPlayer  : TextView = findViewById(R.id.textView)
        textViewPlayer.setBackgroundColor(Color.WHITE)

        object : CountDownTimer(6000, 2000){ // Notification to ontick every second for 3 seconds

            override fun onTick(millisUntilFinshed: Long){
                Log.i("CallBack", "Inside onTick")
                val compDice1 = Dice(6)
                val compDiceRoll1 = compDice1.roll()
                val compDice2 = Dice(6)
                val compDiceRoll2 = compDice2.roll()

                // Match the images to the dice roll numbers
                val diceImage1: ImageView = findViewById(R.id.ivDice1)
                diceImage1.setImageResource(R.drawable.dice_2)
                val diceImage2: ImageView = findViewById(R.id.ivDice2)
                diceImage2.setImageResource(R.drawable.dice_3)

                // Dice one roll with images
                val drawableResource = when (compDiceRoll1) {
                    1 -> R.drawable.dice_1
                    2 -> R.drawable.dice_2
                    3 -> R.drawable.dice_3
                    4 -> R.drawable.dice_4
                    5 -> R.drawable.dice_5
                    else -> R.drawable.dice_6
                }
                diceImage1.setImageResource(drawableResource)
                // Dice two roll with images
                val drawableResource2 = when (compDiceRoll2) {
                    1 -> R.drawable.dice_1
                    2 -> R.drawable.dice_2
                    3 -> R.drawable.dice_3
                    4 -> R.drawable.dice_4
                    5 -> R.drawable.dice_5
                    else -> R.drawable.dice_6
                }
                diceImage2.setImageResource(drawableResource2)

                if ( compDiceRoll1 !== 1 && compDiceRoll2 !== 1){
                     compDiceRollTotal = compDiceRoll1 + compDiceRoll2
                     compTurnTotalScore += compDiceRollTotal

                    var compDiceTotalPoints: TextView = findViewById(R.id.diceTotal)
                        compDiceTotalPoints.text = compDiceRollTotal.toString()

                    var cputurnTotalPoints: TextView = findViewById(R.id.computerTurnScore)
                        cputurnTotalPoints.text = compTurnTotalScore.toString()

                } else if ( compDiceRoll1 == 1 || compDiceRoll2 == 1){
                    compDiceRollTotal = 0
                    compTurnTotalScore += compDiceRollTotal

                    var compDiceTotalPoints: TextView = findViewById(R.id.diceTotal)
                    compDiceTotalPoints.text = compDiceRollTotal.toString()

                    var cputurnTotalPoints: TextView = findViewById(R.id.computerTurnScore)
                    cputurnTotalPoints.text = compTurnTotalScore.toString()
                    cancel()
                    onFinish()
                }
            }
            override fun onFinish() {
                val holdButton: Button = findViewById(R.id.holdButton)
                holdButton.isClickable = true
                holdButton.setBackgroundColor(Color.BLUE)
                holdButton.isEnabled = true

                compTotalScore()
                compWins()
                compResetPoints()
                switchPlayer()
                resetPoints()
            }
        }.start()
        val holdButton: Button = findViewById(R.id.holdButton)
        holdButton.isClickable = false
        holdButton.setBackgroundColor(Color.GRAY)
        holdButton.isEnabled = false
//        Log.i("CallBack", "Reset Points called: ${resetPoints()} turn total: ${yourTurnTotalScore} total score: ${yourTotalScore}")
    }
    fun resetPoints(){

        diceRollTotal = 0
        yourTurnTotalScore = 0

        var playerDicePoints: TextView = findViewById(R.id.diceTotal)
        playerDicePoints.text = diceRollTotal.toString()
        var totalPoints: TextView = findViewById(R.id.playerTurnScore)
        totalPoints.text = yourTurnTotalScore.toString()

    }
    fun compResetPoints(){
        compDiceRollTotal = 0
        compTurnTotalScore = 0
        var compResetScore = 0

        var compDiceTotalPoints: TextView = findViewById(R.id.diceTotal)
        compDiceTotalPoints.text = compResetScore.toString()
        var computerRollScore: TextView = findViewById(R.id.computerTurnScore)
        computerRollScore.text = compResetScore.toString()
    }
    fun switchPlayer(){
        if (yourActive){
            yourActive = false
            activeComp = true
            onCompTurnClick()
            resetPoints()
        } else if (activeComp) {
            activeComp = false
            yourActive = true
            resetPoints()
            rollDice()
        }
    }
    fun totalScore(){
         yourTotalScore += yourTurnTotalScore
        var totalGamePoints: TextView = findViewById(R.id.playerScore)
        totalGamePoints.text = yourTotalScore.toString()
    }
    fun compTotalScore(){
        compTotalScore += compTurnTotalScore
        var totalGamePoints: TextView = findViewById(R.id.computerTotalScore)
        totalGamePoints.text = compTotalScore.toString()
    }
    fun hideWinnerPic(v: View ){
        val hideWinnerPic: ImageView = findViewById(R.id.winnerView)
        hideWinnerPic.setVisibility(View.INVISIBLE);



        val intent = Intent(this, ScoreLog::class.java)
        intent.putExtra("Winner", "You")
        intent.putExtra("Score", yourTotalScore)

//        var gameWonReset: TextView = findViewById(R.id.playerScore)
//        gameWonReset.text = yourTotalScore.toString()


        var gamesWonReset: TextView = findViewById(R.id.computerTotalScore)
        gamesWonReset.text = compTotalScore.toString()

//        yourTotalScore = 0
//        compTotalScore = 0
        startActivity(intent)
    }
    fun hideLoserPic(v: View ){
        val hideLoserPic: ImageView = findViewById(R.id.loserView)
        hideLoserPic.setVisibility(View.INVISIBLE);

        val intent = Intent(this, ScoreLog::class.java)


        intent.putExtra("Winner", "Computer")
        intent.putExtra("Score", compTotalScore)
        startActivity(intent)

//        yourTotalScore = 0
        var gameWonReset: TextView = findViewById(R.id.playerScore)
        gameWonReset.text = yourTotalScore.toString()

//        compTotalScore = 0
        var gamesWonReset: TextView = findViewById(R.id.computerTotalScore)
        gamesWonReset.text = compTotalScore.toString()

    }
    fun playerWins(){
        if (yourTotalScore >= 100) {
            yourGamesWon += 1
            var diceTotalPoints: TextView = findViewById(R.id.playerGamesWon)
            diceTotalPoints.text = yourGamesWon.toString()

//            yourTotalScore = 0
            var gameWonReset: TextView = findViewById(R.id.playerScore)
            gameWonReset.text = yourTotalScore.toString()

            val winner: ImageView = findViewById(R.id.winnerView)
            winner.isClickable = true
            winner.setVisibility(View.VISIBLE);

            compTotalScore = 0
            var gamesWonReset: TextView = findViewById(R.id.computerTotalScore)
            gamesWonReset.text = compTotalScore.toString()

        }
    }
    fun compWins(){
        if (compTotalScore >= 100){
            compGamesWon += 1
            var gamesWonCheck: TextView = findViewById(R.id.computerGamesWon)
            gamesWonCheck.text = compGamesWon.toString()

            compResetPoints()
//            compTotalScore = 0
            var gamesWonReset: TextView = findViewById(R.id.computerTotalScore)
            gamesWonReset.text = compTotalScore.toString()
            val loser: ImageView = findViewById(R.id.loserView)
            loser.isClickable = true
            loser.setVisibility(View.VISIBLE);

            yourTotalScore = 0
            var gameWonReset: TextView = findViewById(R.id.playerScore)
            gameWonReset.text = yourTotalScore.toString()
        }
    }


    override fun onSaveInstanceState(savedState: Bundle) {
        super.onSaveInstanceState(savedState)
        Log.i("Dice_Events", "Inside onSave")

        savedState.putInt("gamesWonOutput", yourGamesWon)
        savedState.putInt("totalScoreOutput", yourTotalScore)
        savedState.putInt("turnTotalOutput", yourTurnTotalScore)
        savedState.putInt("diceRollOutput", diceRollTotal)

        savedState.putInt("compGamesWonOutput", compGamesWon)
        savedState.putInt("compTotalScoreOutput", compTotalScore)
        savedState.putInt("compTurnTotalOutput", compTurnTotalScore)
        savedState.putInt("compDiceRollOutput", compDiceRollTotal)

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle ) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("Dice_Events", "Inside restore")

        savedInstanceState.getInt("gamesWonOutput", yourGamesWon)
        savedInstanceState.getInt("totalScoreOutput", yourTotalScore)
        savedInstanceState.getInt("turnTotalOutput", yourTurnTotalScore)
        savedInstanceState.getInt("diceRollOutput", diceRollTotal)

        savedInstanceState.getInt("compGamesWonOutput", compGamesWon)
        savedInstanceState.getInt("compTotalScoreOutput", compTotalScore)
        savedInstanceState.getInt("compTurnTotalOutput", compTurnTotalScore)
        savedInstanceState.getInt("compDiceRollOutput", compDiceRollTotal)

        gamesWon.text = yourGamesWon.toString()
        TotalPoints.text = yourTotalScore.toString()
        yourTurnTotalPoints.text = yourTurnTotalScore.toString()
        yourDiceTotalPoints.text = diceRollTotal.toString()

        compTotalGamePoints.text = compTotalScore.toString()
        compWins.text = compGamesWon.toString()

    }

    override  fun onResume() {
        super.onResume()
        Log.i("CallBack", "Inside onResume()")
    }


fun showScoreLogOnClick(v : View){
    // Intent, Just a structure that android uses to send data to another apllication
    val intent = Intent(this, ScoreLog::class.java)
    startActivity(intent)
}

    override  fun onStart() {
        super.onStart()
        Log.i("CallBack", "Inside onStart()")
    }


    override  fun onPause() {
        super.onPause()
        Log.i("CallBack", "Inside onPause()")
    }

    override  fun onStop() {
        super.onStop()
        Log.i("CallBack", "Inside onStop()")
    }

    override  fun onDestroy() {
        super.onDestroy()
        Log.i("CallBack", "Inside onDestroy()")

    }
}

class Dice( val numSides: Int) {
    fun roll():Int {
        return  (1..numSides).random()
    }

//    if(savedInstanceState != null){
//
//        Log.i("Dice_Events", "Inside if statement")
//
////            var yourTotalScore: Int = 0
//        Log.i("Dice_Events", "$turnTotalPoints")
//
//        turnTotalPoints = savedInstanceState.getInt("turn_Total")
//
//    }


}