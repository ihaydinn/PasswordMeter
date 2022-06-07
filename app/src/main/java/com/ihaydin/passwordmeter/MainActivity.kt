package com.ihaydin.passwordmeter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val strengthView = findViewById<PasswordMeterView>(R.id.cvPasswordMeter)
        val etContent = findViewById<EditText>(R.id.etContent)

        strengthView.apply {
            setBarsTintColor(color = android.R.color.darker_gray)
            setCountSize(size = 5)
            setBarRadius(radius = 20)
            setBarWidth(value = 10)
            setBarSpace(value = 6)
            setTextHeight(value = 14)
            setTextPaddingStart(value = 8f)
            setBarHeight(value = 3f)
            setTextFontStyle(font = "fonts/regular.ttf")
        }

        val states = listOf(
            State(
                text = "Weak",
                color = ContextCompat.getColor(this, android.R.color.holo_red_dark),
                tintSize = 1
            ),
            State(
                text = "Medium",
                color = ContextCompat.getColor(this, android.R.color.holo_orange_light),
                tintSize = 3
            ),
            State(
                text = "Strong",
                color = ContextCompat.getColor(this, android.R.color.holo_green_dark),
                tintSize = 5
            )
        )

        strengthView.setList(states)

        etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(value: Editable?) {
                value.let {
                    when {
                        it.toString().contains("weak") -> {
                            strengthView.apply {
                                setSelectedIndex(state = 0)
                            }
                        }
                        it.toString().contains("medium") -> {
                            strengthView.apply {
                                setSelectedIndex(state = 1)
                            }
                        }
                        it.toString().contains("strong") -> {
                            strengthView.apply {
                                setSelectedIndex(state = 2)
                            }
                        }
                        else -> {
                            strengthView.apply {
                                setSelectedIndex(state = 0)
                            }
                        }
                    }
                }
            }
        })
    }
}