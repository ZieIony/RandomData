package tk.zielony.randomdata.test

import android.graphics.drawable.Drawable

import java.io.Serializable

data class CreditCardItem(
        val name: String,
        val number: String,
        val amount: String,
        val image: Drawable,
        val validity: Validity,
        val testDouble: Double
) : Serializable