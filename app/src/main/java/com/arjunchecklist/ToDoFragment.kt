package com.arjunchecklist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import kotlinx.android.synthetic.main.fragment_to_do.view.*
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_to_do.*
import androidx.lifecycle.ViewModelProvider
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationChannel
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context.ALARM_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.content.Context.ALARM_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context.ALARM_SERVICE
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"


class ToDoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: Int? = null

    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0
    private var c: Calendar? = null
    private lateinit var wordViewModel: ViewModel

    private val NOTIFICATION_ID = 0
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    protected lateinit var mNotificationManager: NotificationManager;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getInt(ARG_PARAM4)


        }
        mYear = Calendar.getInstance().get(Calendar.YEAR);
        mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        mHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        mMinute = Calendar.getInstance().get(Calendar.MINUTE);
        wordViewModel = ViewModelProvider(this).get(ViewModel::class.java)

        retainInstance = true
        mNotificationManager =
            context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager


    }


    override fun onResume() {
        super.onResume()
        hideFab();
    }

    fun hideFab() {
        (activity as MainActivity).hideFab()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_to_do, container, false)
        view.editText_title.setText(param1)
        view.edit_datepicker.setText(param2)
        view.edit_text_timer.setText(param3)
        if (param1 != null)
            view.button_done.setText(getString(R.string.update))


        view.edit_datepicker.setOnTouchListener(OnTouchListener { v, event ->
            if (MotionEvent.ACTION_UP == event.action) {
                show_Datepicker();
            }
            false
        })

        view.edit_text_timer.setOnTouchListener { view, motionEvent ->
            if (MotionEvent.ACTION_UP == motionEvent.action) {
                show_Timepicker();
            }
            false
        }

        val buttonText = view.button_done.text.toString();

        view.button_done.setOnClickListener {

            if (view.editText_title.text.toString().equals("") && view.edit_datepicker.text.toString().equals(
                    ""
                ) && view.edit_text_timer.text.toString().equals("")
            ) {
                Toast.makeText(context, getString(R.string.enter_all_fields), Toast.LENGTH_SHORT).show()
            } else {
                if (buttonText.equals(getString(R.string.done))) {
                    var list = CheckList(
                        view.editText_title.text.toString(),
                        view.edit_datepicker.text.toString(),
                        view.edit_text_timer.text.toString()
                    )//read only, fix-size
                    wordViewModel.insert(list)
                    activity?.onBackPressed();

                } else {
                    wordViewModel.updateAChecklist(
                        param4!!,
                        view.editText_title.text.toString(),
                        view.edit_datepicker.text.toString(),
                        view.edit_text_timer.text.toString()

                    )
                    activity?.onBackPressed();

                }
            }
        }
        return view;
    }


    private fun show_Datepicker() {
        c = Calendar.getInstance()
        val mYearParam = mYear
        val mMonthParam = mMonth - 1
        val mDayParam = mDay

        val datePickerDialog = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                mMonth = monthOfYear + 1
                mYear = year
                mDay = dayOfMonth
                edit_datepicker.setText("${mDay.toString()}-${mMonth.toString()}- ${mYear.toString()}")

            }, mYearParam, mMonthParam, mDayParam
        )



        datePickerDialog.show()
    }

    private fun show_Timepicker() {

        val timePickerDialog = TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { view, pHour, pMinute ->
                mHour = pHour
                mMinute = pMinute
                edit_text_timer.setText("${mHour.toString()} : ${mMinute.toString()}");
            }, mHour, mMinute, true
        )

        timePickerDialog.show()
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String, param4: Int) =
            ToDoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                    putInt(ARG_PARAM4, param4)

                }
            }
    }
}
