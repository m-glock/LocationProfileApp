package com.mglock.locationprofileapp.views.profiles.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.mglock.locationprofileapp.util.Time
import com.mglock.locationprofileapp.viewmodels.profiles.AddProfileViewModel
import java.util.Calendar

class TimePickerFragment(private val startTime: Boolean, private val viewModel: AddProfileViewModel) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        val time = Time(view.hour, view.minute)
        if(startTime){
            viewModel.setStartTime(time)
        } else {
            viewModel.setEndTime(time)
        }
    }

}