package com.velarttdesign.pokerv.models

import com.velarttdesign.pokerv.R

object CardResources {

    private val cardMap = mapOf(
        // Corazones (Hearts)
        "_ah" to R.drawable._ah, "_2h" to R.drawable._2h, "_3h" to R.drawable._3h,
        "_4h" to R.drawable._4h, "_5h" to R.drawable._5h, "_6h" to R.drawable._6h,
        "_7h" to R.drawable._7h, "_8h" to R.drawable._8h, "_9h" to R.drawable._9h,
        "_10h" to R.drawable._10h, "_jh" to R.drawable._jh, "_qh" to R.drawable._qh, "_kh" to R.drawable._kh,

        // Diamantes (Diamonds)
        "_ad" to R.drawable._ad, "_2d" to R.drawable._2d, "_3d" to R.drawable._3d,
        "_4d" to R.drawable._4d, "_5d" to R.drawable._5d, "_6d" to R.drawable._6d,
        "_7d" to R.drawable._7d, "_8d" to R.drawable._8d, "_9d" to R.drawable._9d,
        "_10d" to R.drawable._10d, "_jd" to R.drawable._jd, "_qd" to R.drawable._qd, "_kd" to R.drawable._kd,

        // Tréboles (Clubs)
        "_ac" to R.drawable._ac, "_2c" to R.drawable._2c, "_3c" to R.drawable._3c,
        "_4c" to R.drawable._4c, "_5c" to R.drawable._5c, "_6c" to R.drawable._6c,
        "_7c" to R.drawable._7c, "_8c" to R.drawable._8c, "_9c" to R.drawable._9c,
        "_10c" to R.drawable._10c, "_jc" to R.drawable._jc, "_qc" to R.drawable._qc, "_kc" to R.drawable._kc,

        // Picas (Spades)
        "_as" to R.drawable._as, "_2s" to R.drawable._2s, "_3s" to R.drawable._3s,
        "_4s" to R.drawable._4s, "_5s" to R.drawable._5s, "_6s" to R.drawable._6s,
        "_7s" to R.drawable._7s, "_8s" to R.drawable._8s, "_9s" to R.drawable._9s,
        "_10s" to R.drawable._10s, "_js" to R.drawable._js, "_qs" to R.drawable._qs, "_ks" to R.drawable._ks
    )

    fun getImageRes(cardName: String): Int? {
        return cardMap[cardName]
    }
}
