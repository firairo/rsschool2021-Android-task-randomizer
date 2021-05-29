package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rsschool.android2021.interfaces.OnBackPressedListener
import com.rsschool.android2021.interfaces.SecondFragmentListener

class SecondFragment : Fragment(), OnBackPressedListener {
    private lateinit var secondFragmentListener: SecondFragmentListener
    private lateinit var backButton: Button
    private lateinit var result: TextView
    private var previousResult: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            secondFragmentListener = context as SecondFragmentListener
        } catch (e: Exception) {
            throw RuntimeException("$context must implement FirstFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        result.text = generate(min, max).toString()
        previousResult = result.text.toString().toInt()

        backButton.setOnClickListener {
            secondFragmentListener.firstFragment(previousResult)
        }
    }

    private fun generate(min: Int, max: Int): Int {
        return (min..max).random()
    }

    companion object {
        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            fragment.arguments = Bundle().apply {
                putInt(MIN_VALUE_KEY, min)
                putInt(MAX_VALUE_KEY, max)
            }
            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }

    override fun onBackPressed() {
        secondFragmentListener.firstFragment(previousResult)
    }
}