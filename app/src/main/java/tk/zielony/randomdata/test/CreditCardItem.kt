package tk.zielony.randomdata.test

import android.graphics.drawable.Drawable
import tk.zielony.randomdata.annotation.RandomValue

import java.io.Serializable

data class CreditCardItem(
        @RandomValue(name = "name")
        val name: String,
        @RandomValue(name = "number")
        val number: String,
        @RandomValue(name = "amount")
        val amount: String,
        @RandomValue(name = "image")
        val image: Drawable,
        @RandomValue(name = "validity")
        val validity: Validity
) : Serializable