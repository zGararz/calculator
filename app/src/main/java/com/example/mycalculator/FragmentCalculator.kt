package com.example.mycalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calculator.*

class FragmentCalculator : Fragment(), View.OnClickListener {
    lateinit var viewF: View
    var countDot = 0
    var enableDot = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewF = inflater.inflate(R.layout.fragment_calculator, container, false)
        return viewF
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListener()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_zero,
            R.id.button_one,
            R.id.button_two,
            R.id.button_three,
            R.id.button_four,
            R.id.button_five,
            R.id.button_six,
            R.id.button_seven,
            R.id.button_eght,
            R.id.button_nine,
            R.id.button_open_parentheses,
            R.id.button_close_parentheses
            -> buttonNumberListener(v as Button)
            R.id.button_add, R.id.button_sub, R.id.button_mul, R.id.button_div
            -> buttonOperatorListener(v as Button)
            R.id.button_ac
            -> buttonAcListener()
            R.id.button_back
            -> buttonBackListener()
            R.id.button_equal
            -> buttonEqualListener()
            R.id.button_dot
            -> buttonDotListener()
        }
    }

    private fun buttonDotListener() {
        if (countDot == 0 && enableDot && !text_cal.text.equals("")) {
            text_cal.text = text_cal.text.toString() + button_dot.text.toString()
            countDot = 1
            enableDot = false
        }
    }

    private fun buttonEqualListener() {
        text_temp.text = text_cal.text.toString() + "="
        text_cal.text = Infix().getResult(text_cal.text.toString())
    }

    private fun buttonBackListener() {
        text_cal.apply {
            if (!text.equals("")) {
                if (text.get(text.length - 1).toString().equals(button_dot.text.toString())) {
                    countDot = 0
                    enableDot = true
                }
                text = text.dropLast(1)
            }
        }
    }

    private fun buttonAcListener() {
        text_cal.text = ""
        text_temp.text = ""
    }

    private fun buttonNumberListener(button: Button) {
        text_cal.text = text_cal.text.toString() + button.text.toString()

        if (enableDot && countDot == 1) countDot = 0
    }

    private fun buttonOperatorListener(button: Button) {
        text_cal.text.toString().also {
            if (!it.equals("")
                && !it.get(it.length - 1).equals('+')
                && !it.get(it.length - 1).equals('-')
                && !it.get(it.length - 1).equals('*')
                && !it.get(it.length - 1).equals('/')
            ) {
                text_cal.text = it + button.text.toString()
            }
        }

        if (!enableDot) enableDot = true
    }

    private fun addListener() {
        button_zero.setOnClickListener(this)
        button_one.setOnClickListener(this)
        button_two.setOnClickListener(this)
        button_three.setOnClickListener(this)
        button_four.setOnClickListener(this)
        button_five.setOnClickListener(this)
        button_six.setOnClickListener(this)
        button_seven.setOnClickListener(this)
        button_eght.setOnClickListener(this)
        button_nine.setOnClickListener(this)
        button_ac.setOnClickListener(this)
        button_back.setOnClickListener(this)
        button_add.setOnClickListener(this)
        button_sub.setOnClickListener(this)
        button_mul.setOnClickListener(this)
        button_div.setOnClickListener(this)
        button_equal.setOnClickListener(this)
        button_dot.setOnClickListener(this)
        button_open_parentheses.setOnClickListener(this)
        button_close_parentheses.setOnClickListener(this)

    }
}