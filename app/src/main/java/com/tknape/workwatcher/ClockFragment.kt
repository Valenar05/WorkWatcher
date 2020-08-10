package com.tknape.workwatcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.content_main.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ClockFragment : Fragment() {

    private lateinit var viewModel: ClockViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        viewModel = ClockViewModel()

        val clockObserver = Observer<Long> { timer ->
            clock.text = timer.toString()
        }

        viewModel.timeLeftInMillis.observe(this, clockObserver)
        
        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_button.setOnClickListener {
            viewModel.startStop()
        }

        clock.text = "25:00"

    }

}