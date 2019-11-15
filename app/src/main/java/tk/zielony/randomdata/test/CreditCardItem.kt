package tk.zielony.randomdata.test

import android.graphics.drawable.Drawable

import java.io.Serializable

data class CreditCardItem(
        var name: String,
        var number: String,
        var amount: String,
        var image: Drawable,
        var validity: Validity,
        var testDouble: Double
) : Serializable
