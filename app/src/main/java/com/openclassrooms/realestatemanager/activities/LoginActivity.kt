package com.openclassrooms.realestatemanager.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Lionel JOFFRAY - on 06/08/2019.
 */

class LoginActivity : AppCompatActivity(){
   lateinit var textViewMain:TextView
    lateinit var textViewQuantity:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_btn.setOnClickListener {
            val intent = Intent(baseContext, DescriptionActivity::class.java)
            startActivity(intent)
        }
      /**  textViewMain = activity_main_activity_text_view_main    //  Fix the ID from second activity to main activity.
        textViewQuantity = activity_main_activity_text_view_quantity
        configureTextViewMain()
        configureTextViewQuantity()
      */
    }

    /**
     *
    private fun configureTextViewMain() {
        textViewMain.textSize = 15f
        textViewMain.text = "Le premier bien immobilier enregistré vaut "
    }

    private fun configureTextViewQuantity() {
        val quantity = Utils.convertDollarToEuro(200)
        textViewQuantity.textSize = 20f
        textViewQuantity.text = quantity.toString()   //  Fix the variable, convert from int to string.
    }

     */
}
