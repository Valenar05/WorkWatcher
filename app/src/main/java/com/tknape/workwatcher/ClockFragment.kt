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
        viewModel = ClockViewModel()

        val clockObserver = Observer<String> { timer ->
            clock.text = timer
        }

        viewModel.formattedTimeLeftInMillis.observe(viewLifecycleOwner, clockObserver)

        val progressObserver = Observer<Float> { progressPercentage ->
            circularProgressBar.progress = progressPercentage
        }

        viewModel.timerProgressInPercents.observe(viewLifecycleOwner, progressObserver)

        val sessionTypeObserver = Observer<String> { currrentSessionType ->
            sessionName.text = currrentSessionType
        }

        viewModel.currentSessionType.observe(viewLifecycleOwner, sessionTypeObserver)

        val infoToNextBigBreakObserver = Observer<String> { nextBigBreak ->
            workSessionsLeftToBigBreakInfo.text = nextBigBreak
        }

        val isTimerRunningObserver = Observer<Boolean> { isTimerRunning ->
            if (isTimerRunning) {
                setPauseIcon()
                keepScreenOn(true)
            }
            else {
                setStartIcon()
                keepScreenOn(false)
            }
        }

        viewModel.isTimerRunning.observe(viewLifecycleOwner, isTimerRunningObserver)

        viewModel.workSessionsUntilBigBreak.observe(viewLifecycleOwner, infoToNextBigBreakObserver)

        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_button.setOnClickListener {
            viewModel.startPauseClock()
        }

        stop_button.setOnClickListener {
            viewModel.stopClock()
        }

        skip_to_next_button.setOnClickListener {
            viewModel.skipToNextSession()

        }
    }

    private fun setStartIcon() {
        start_button.setImageResource(R.drawable.ic_play)
    }

    private fun setPauseIcon() {
        start_button.setImageResource(R.drawable.ic_pause)
    }

    private fun keepScreenOn(boolean: Boolean) {
        requireView().keepScreenOn = boolean
    }
}