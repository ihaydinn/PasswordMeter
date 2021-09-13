package com.ihaydin.passwordmeter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val view = findViewById<PasswordMeterView>(R.id.cvPasswordMeter)
        val etContent = findViewById<EditText>(R.id.etContent)

        view.setStrengthBarsTintColor(android.R.color.darker_gray)
        view.setStrengthCountSize(5)
        view.setStrengthBarRadius(20)
        view.setStrengthBarWidth(10)
        view.setStrengthBarSpacer(6)
        view.setStrengthTextHeight(14)
        view.setTextPaddingStart(8f)
        view.setStrengthBarHeight(3f)
        view.setTextFontStyle("fonts/regular.ttf")

        val states = listOf(
            PasswordMeterView.State(
                "Weak",
                ContextCompat.getColor(this, android.R.color.holo_red_dark),
                1
            ),
            PasswordMeterView.State(
                "Medium",
                ContextCompat.getColor(this, android.R.color.holo_orange_light),
                3
            ),
            PasswordMeterView.State(
                "Strong",
                ContextCompat.getColor(this, android.R.color.holo_green_dark),
                5
            )
        )

        view.setList(states)

        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(value: Editable?) {
                value.let {
                    when {
                        it.toString().contains("weak") -> {
                            view.setStrengthSelectedIndex(0)
                        }
                        it.toString().contains("medium") -> {
                            view.setStrengthSelectedIndex(1)
                        }
                        it.toString().contains("strong") -> {
                            view.setStrengthSelectedIndex(2)
                        }
                        else -> {
                            view.setStrengthSelectedIndex(0)
                        }
                    }
                }


            }
        })

    }
}