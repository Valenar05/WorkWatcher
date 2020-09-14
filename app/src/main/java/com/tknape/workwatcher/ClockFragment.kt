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

        viewModel.formattedTimeLeftInMillis.observe(this, clockObserver)

        val progressObserver = Observer<Float> { progressPercentage ->
            circularProgressBar.progress = progressPercentage
        }

        viewModel.timerProgressInPercents.observe(this, progressObserver)

        val sessionTypeObserver = Observer<String> { currrentSessionType ->
            sessionName.text = currrentSessionType
        }

        viewModel.currentSessionType.observe(this, sessionTypeObserver)

        val infoToNextBigBreakObserver = Observer<String> { nextBigBreak ->
            workSessionsLeftToBigBreakInfo.text = nextBigBreak
        }

        viewModel.workSessionsUntilBigBreak.observe(this, infoToNextBigBreakObserver)

        return inflater.inflate(R.layout.content_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_button.setOnClickListener {

            if (viewModel.isTimerRunning()) {
                start_button.setImageResource(R.drawable.ic_play)
            }
            else if (!viewModel.isTimerRunning()) {
                start_button.setImageResource(R.drawable.ic_pause)
            }

            viewModel.startPauseClock()
        }

        stop_button.setOnClickListener {
            viewModel.stopClock()
            start_button.setImageResource(R.drawable.ic_play)
        }

        skip_to_next_button.setOnClickListener {
            viewModel.skipToNextSession()
            start_button.setImageResource(R.drawable.ic_play)
        }
    }
}