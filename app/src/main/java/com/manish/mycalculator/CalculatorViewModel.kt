package com.manish.mycalculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable


class CalculatorViewModel: ViewModel() {
    private val _equationText = MutableLiveData(" ")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData(" 0 ")
    val resultText: LiveData<String> = _resultText


    fun ButtonOnClick(btn: String) {
        Log.i("ButtonCLick", btn)

        _equationText.value?.let{
            if (btn == "AC"){
                _equationText.value = ""
                _resultText.value = "0"
                return
            }
            if (btn == "C"){
                if(it.isNotEmpty()){
                    _equationText.value = it.dropLast(1)
                    return
                }
            }
            if (btn == "="){
                _equationText.value = _resultText.value
                return
            }

            _equationText.value = it + btn

            try{
               _resultText.value = calculation(_equationText.value.toString())
            }catch (_ : Exception){}
        }


        }

    fun calculation(equation: String): String {
        val context = Context.enter()
        context.optimizationLevel = -1
        val Scriptable : Scriptable = context.initStandardObjects()
        val FinalResult = context.evaluateString(Scriptable, equation, "javascript", 1, null).toString()
        return FinalResult


    }
}
