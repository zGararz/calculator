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
            R.id.buttonCloseParentheses -> {
                val button = (v as? Button) ?: return
                buttonNumberListener(button)
            }
            R.id.buttonAdd,
            R.id.buttonSub,
            R.id.buttonMul,
            R.id.buttonDiv -> {
                val button = (v as? Button) ?: return
                buttonOperatorListener(button)
            }
            R.id.buttonAc -> buttonAcListener()
            R.id.buttonBack -> buttonBackListener()
            R.id.buttonEqual -> buttonEqualListener()
            R.id.buttonDot -> buttonDotListener()
        }
    }

    private fun buttonDotListener() {
        if (countDot == 0 && enableDot && !textCal.text.isBlank()) {
            textCal.text = textCal.text.toString() + buttonDot.text.toString()
            countDot = 1
            enableDot = false
        }
    }

    private fun buttonEqualListener() {
        textTemp.text = textCal.text.toString() + EQUAL
        textCal.text = Infix().getResult(textCal.text.toString())
    }

    private fun buttonBackListener() {
        textCal.apply {

            if (!text.isBlank()) {
                if (text.equals(NAN)) {
                    text = BLANK
                    return
                }
                if (text.takeLast(1) == buttonDot.text) {
                    countDot = 0
                    enableDot = true
                }
                text = text.dropLast(1)
            }
        }
    }

    private fun buttonAcListener() {
        textCal.text = BLANK
        textTemp.text = BLANK
    }

    private fun buttonNumberListener(button: Button) {
        textCal.text = textCal.text.toString() + button.text.toString()

        if (enableDot && countDot == 1) countDot = 0
    }

    private fun buttonOperatorListener(button: Button) {
        textCal.text.toString().also {
            if (!it.equals("")
                && it.get(it.length - 1) !in listOf<String>(ADD, SUB, MUL, DIV)
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
        buttonDiv.setOnClickListener(this)
        buttonEqual.setOnClickListener(this)
        buttonDot.setOnClickListener(this)
        buttonOpenParentheses.setOnClickListener(this)
        buttonCloseParentheses.setOnClickListener(this)
    }
}
