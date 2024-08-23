package com.team1.simplebank.common.utils

fun splitWordIntoOneLetter(dataInput:String):String{
    return dataInput.trim().take(1).uppercase()
}