package com.tknape.workwatcher

class Commons {

    companion object {
        const val ERROR = "##Error##"
        const val STOP_TIMER_ACTION =  "WORK_WATCHER_ACTION_STOP_TIMER"
        const val START_PAUSE_TIMER_ACTION =  "WORK_WATCHER_ACTION_START_PAUSE_TIMER"
        const val SKIP_TIMER_ACTION =  "WORK_WATCHER_ACTION_SKIP_TIMER"

        fun convertMinutesAfterMidnightToHourlyTime(minutesAfterMidnight: Int): String {
            val hourString = "${if (minutesAfterMidnight / 60 < 10) {"0"} else {""}}${minutesAfterMidnight / 60}"
            val minuteString = "${if((minutesAfterMidnight % 60) < 10) {"0"} else {""}}${(minutesAfterMidnight % 60)}"

            return "${hourString}:${minuteString}"
        }

        fun convertMillisecondsToMinutelyString(time: Long): String {
            val minuteString = "${if (time / 60000 < 10) {"0"} else {""}}${time / 60000}"
            val secondString = "${if((time % 60000) / 1000 < 10) {"0"} else {""}}${(time % 60000) / 1000}"

            return "${minuteString}:${secondString}"
        }
    }
}