package com.project.bmicalculator.bmiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.project.bmicalculator.R
import com.project.bmicalculator.bmiapp.BmiAppActivity.Companion.BMI_KEY
import java.text.DecimalFormat

class BmiResultActivity : AppCompatActivity() {

    private var bmiResult:Double = 0.00
    private lateinit var textResultCondition:TextView
    private lateinit var textResultNumber:TextView
    private lateinit var textResultObservation:TextView
    private lateinit var reCalculateBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_result)

        bmiResult = intent.extras?.getDouble(BMI_KEY)?.toDouble() ?: 0.00

        initComponents()
        setResult()
        initListeners()
    }

    private fun initListeners(){
        reCalculateBtn.setOnClickListener {
            val intent = Intent(this,BmiAppActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents(){
        textResultCondition = findViewById(R.id.textResultCondition)
        textResultNumber = findViewById(R.id.textResultNumber)
        textResultObservation = findViewById(R.id.textResultObservation)
        reCalculateBtn = findViewById(R.id.reCalculateBtn)
    }

    private fun setResult(){

        val df = DecimalFormat("#.##")

        bmiResult = df.format(bmiResult).toDouble()

        textResultNumber.text = bmiResult.toString()

        when(bmiResult){
            in 0.0..18.5 -> {
                textResultCondition.text = getString(R.string.textResultCondition1)
                textResultCondition.setTextColor(ContextCompat.getColor(this,R.color.bmiResult1))
                textResultObservation.text = getString(R.string.resultObservation1)
            }
            in 18.5..24.9 -> {
                textResultCondition.text = getString(R.string.textResultCondition2)
                textResultCondition.setTextColor(ContextCompat.getColor(this,R.color.bmiResult2))
                textResultObservation.text = getString(R.string.resultObservation2)
            }
            in 24.9..29.9 -> {
                textResultCondition.text = getString(R.string.textResultCondition3)
                textResultCondition.setTextColor(ContextCompat.getColor(this,R.color.bmiResult3))
                textResultObservation.text = getString(R.string.resultObservation3)
            }
            in 29.9..100.0 -> {
                textResultCondition.text =getString(R.string.textResultCondition4)
                textResultCondition.setTextColor(ContextCompat.getColor(this,R.color.bmiResult4))
                textResultObservation.text = getString(R.string.resultObservation4)
            }
        }
    }
}