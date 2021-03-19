package com.example.mycalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calculator.*

class FragmentCalculator : Fragment(), View.OnClickListener {
    private var countDot = 0
    private var enableDot = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonZero,
            R.id.buttonOne,
            R.id.buttonTwo,
            R.id.buttonThree,
            R.id.buttoFour,
            R.id.buttonFive,
            R.id.buttonSix,
            R.id.buttonSeven,
            R.id.buttonEight,
            R.id.buttonNine,
            R.id.buttonOpenParentheses,
            R.id.buttonCloseParentheses -> buttonNumberListener(v as Button)
            R.id.buttonAdd,
            R.id.buttonSub,
            R.id.buttonMul,
            R.id.button_div -> buttonOperatorListener(v as Button)
            R.id.buttonAc -> buttonAcListener()
            R.id.buttonBack -> buttonBackListener()
            R.id.buttonEqual -> buttonEqualListener()
            R.id.buttonDot -> buttonDotListener()
        }
    }

    private fun buttonDotListener() {
        if (countDot == 0 && enableDot && !textCal.text.equals("")) {
            textCal.text = textCal.text.toString() + buttonDot.text.toString()
            countDot = 1
            enableDot = false
        }
    }

    private fun buttonEqualListener() {
        textTemp.text = textCal.text.toString() + "="
        textCal.text = Infix().getResult(textCal.text.toString())
    }

    private fun buttonBackListener() {
        textCal.apply {
            if (!text.equals("")) {
                if (text.get(text.length - 1).toString().equals(buttonDot.text.toString())) {
                    countDot = 0
                    enableDot = true
                }
                text = text.dropLast(1)
            }
        }
    }

    private fun buttonAcListener() {
        textCal.text = ""
        textTemp.text = ""
    }

    private fun buttonNumberListener(button: Button) {
        textCal.text = textCal.text.toString() + button.text.toString()

        if (enableDot && countDot == 1) countDot = 0
    }

    private fun buttonOperatorListener(button: Button) {
        textCal.text.toString().also {
            if (!it.equals("")
                && it.get(it.length - 1) !in listOf<Char>('+', '-', '*', '/')
            ) {
                textCal.text = it + button.text.toString()
            }
        }

        if (!enableDot) enableDot = true
    }

    private fun addListener() {
        buttonZero.setOnClickListener(this)
        buttonOne.setOnClickListener(this)
        buttonTwo.setOnClickListener(this)
        buttonThree.setOnClickListener(this)
        buttoFour.setOnClickListener(this)
        buttonFive.setOnClickListener(this)
        buttonSix.setOnClickListener(this)
        buttonSeven.setOnClickListener(this)
        buttonEight.setOnClickListener(this)
        buttonNine.setOnClickListener(this)
        buttonAc.setOnClickListener(this)
        buttonBack.setOnClickListener(this)
        buttonAdd.setOnClickListener(this)
        buttonSub.setOnClickListener(this)
        buttonMul.setOnClickListener(this)
        button_div.setOnClickListener(this)
        buttonEqual.setOnClickListener(this)
        buttonDot.setOnClickListener(this)
        buttonOpenParentheses.setOnClickListener(this)
        buttonCloseParentheses.setOnClickListener(this)

    }
}
