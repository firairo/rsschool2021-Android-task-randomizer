package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.rsschool.android2021.R
import com.rsschool.android2021.interfaces.FirstFragmentListener


class FirstFragment : Fragment() {
    private lateinit var firstFragmentListener: FirstFragmentListener
    private lateinit var generateButton: Button
    private lateinit var previousResult: TextView
    private var min = ""
    private var max = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            firstFragmentListener = context as FirstFragmentListener
        } catch (e: Exception) {
            throw RuntimeException("$context must implement FirstFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)


        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult.text = "Previous result: ${result.toString()}"

        view.findViewById<EditText>(R.id.min_value).addTextChangedListener {
            min = view.findViewById<EditText>(R.id.min_value).text.toString()

        }

        view.findViewById<EditText>(R.id.max_value).addTextChangedListener {
            max = view.findViewById<EditText>(R.id.max_value).text.toString()

        }

       /* view.findViewById<EditText>(R.id.max_value)
            .setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    if (checkValues()) {
                        firstFragmentListener.secondFragment(min.toInt(), max.toInt())
                    }
                    return@OnKeyListener true
                }
                false
            }) Обрабатываем нажатие кнопки Enter на клавиатуре*/

        generateButton.setOnClickListener {
            if (checkValues() == false) {
                checkValues()
            }
            else{
            firstFragmentListener.secondFragment(min.toInt(), max.toInt())
        }}
    }

    private fun checkValues(): Boolean {
        return when {
            min.isBlank() || max.isBlank() -> {
                Toast.makeText(activity, "Please, input your values", Toast.LENGTH_SHORT).show()
                false
            }
            max.toInt() < min.toInt() -> {
                Toast.makeText(activity, "Max less than min", Toast.LENGTH_SHORT).show()
                false
            }
            max.toInt() == min.toInt() -> {
                Toast.makeText(activity, "Max equals min", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
    

    companion object {
        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}