# PasswordMeter
A UI component that displays password strength on registration and change password screens.

# GIF
<img src="https://github.com/ihaydinn/PasswordMeter/blob/master/screen/screen.gif" width="156" height="275">
# Information

# Import
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.ihaydin.passwordmeter:PasswordMeter:1.0.0'
}
```

# Usage
You can set the XML view as default.
```xml
<com.ihaydin.passwordmeter.PasswordMeterView
    android:id="@+id/cvPasswordMeter"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintTop_toTopOf="parent"
    app:layout_constraintEnd_toEndOf="@id/etContent"/>

```
Then let's get the UI by setting the required default values for the UI
```kotlin
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
```
In the example, there are 3 strengths, weak, medium and strong. To add these levels, let's add them into a list.
If you want more options, you can add 4 or more options to the list.
NOTE: If you have more than 3 tabs by default, you should increase the number of strength view bars proportionally.
```kotlin
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
```
Finally, let's define the necessary actions in the listener to listen to the values entered by the user.
```kotlin
etContent.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

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
```

# License
```
Copyright (c) 2020 İsmail Hakkı AYDIN

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
