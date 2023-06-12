package com.project.bmicalculator.bmiapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import com.project.bmicalculator.R
import java.text.DecimalFormat
import kotlin.math.roundToInt

class BmiAppActivity : AppCompatActivity() {

    private var maleSelected:Boolean = false
    private var femaleSelect:Boolean = false
    private lateinit var maleCard:CardView
    private lateinit var femaleCard:CardView
    private lateinit var textHeight:TextView
    private lateinit var heightBar:RangeSlider
    private lateinit var weightText:TextView
    private lateinit var ageText:TextView
    private lateinit var decreaseWeightBtn:FloatingActionButton
    private lateinit var increaseWeightBtn:FloatingActionButton
    private lateinit var decreaseAgeBtn:FloatingActionButton
    private lateinit var increaseAgeBtn:FloatingActionButton
    private lateinit var calculateBtn:Button
    private var currentAge:Int = 25
    private var currentWeight:Int = 70
    private var currentHeight:Int = 100

    companion object{
        const val BMI_KEY = "BMI_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_app)
        initComponents()
        initListeners()
        initUI()
    }

    private fun initComponents() {
        maleCard = findViewById(R.id.maleCard)
        femaleCard = findViewById(R.id.femaleCard)
        textHeight = findViewById(R.id.textHeight)
        heightBar = findViewById(R.id.heightBar)
        weightText = findViewById(R.id.weightText)
        ageText = findViewById(R.id.ageText)
        decreaseWeightBtn = findViewById(R.id.decreaseWeightBtn)
        increaseWeightBtn = findViewById(R.id.increaseWeightBtn)
        decreaseAgeBtn = findViewById(R.id.decreaseAgeBtn)
        increaseAgeBtn = findViewById(R.id.increaseAgeBtn)
        calculateBtn = findViewById(R.id.calculateBtn)
    }

    private fun initListeners() {
        maleCard.setOnClickListener{
            maleSelected = true
            femaleSelect = false
            setCardColor() }
        femaleCard.setOnClickListener {
            maleSelected = false
            femaleSelect = true
            setCardColor() }
        heightBar.addOnChangeListener { slider, value, fromUser ->
            currentHeight = value.roundToInt()
            textHeight.text = "${value.roundToInt().toString()} cm"
        }
        increaseWeightBtn.setOnClickListener {
            setWeight("I")
        }
        decreaseWeightBtn.setOnClickListener {
            setWeight("D")
        }
        increaseAgeBtn.setOnClickListener {
            setAge("I")
        }
        decreaseAgeBtn.setOnClickListener {
            setAge("D")
        }
        calculateBtn.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {

        if(maleSelected || femaleSelect){

            val currentHeightMts:Double = currentHeight.toDouble()/100
            var bmi = currentWeight / ((currentHeightMts * currentHeightMts))

            val df = DecimalFormat("#.#")
            bmi = df.format(bmi).toDouble()

            val intent = Intent(this,BmiResultActivity::class.java)
            intent.putExtra(BMI_KEY,bmi)
            startActivity(intent)
        }else{
            createAlertMessage()
        }


    }

    private fun setWeight(stateBtn:String){

        if(stateBtn == "I"){

            currentWeight++
            weightText.text = "$currentWeight Kg"

        }else if(stateBtn == "D"){

            if(currentWeight>0){
                currentWeight--
                weightText.text = "$currentWeight Kg"
            }else{
                weightText.text = "0 Kg"
            }
        }else if(stateBtn == "S"){
            weightText.text = "$currentWeight Kg"
        }
    }

    private fun setAge(stateBtn:String){
        if(stateBtn == "I"){

            currentAge++
            ageText.text = "$currentAge"

        }else if(stateBtn == "D"){

            if(currentAge>0){
                currentAge--
                ageText.text = "$currentAge"
            }else{
                ageText.text = "0"
            }
        }else if(stateBtn == "S"){
            ageText.text = "$currentAge"
        }
    }

    private fun setCardColor(){
        maleCard.setCardBackgroundColor(getBackgroundColor(maleSelected))
        femaleCard.setCardBackgroundColor(getBackgroundColor(femaleSelect))
    }

    private fun getBackgroundColor(isSelectedComponent:Boolean):Int{

        val colorNumber = if (isSelectedComponent){
            R.color.background_component_selected
        }else{
            R.color.background_component
        }

        return ContextCompat.getColor(this,colorNumber)
    }

    private fun createAlertMessage(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titleAlert))
        builder.setMessage(getString(R.string.messageAlert))
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->  })
        builder.setCancelable(false)
        builder.show()
    }

    private fun initUI() {
        setCardColor()
        setWeight("S")
        setAge("S")
    }

}