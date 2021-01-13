package com.example.lifttracker.lifting

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.lifttracker.R
import com.example.lifttracker.currentWorkout.CurrentWorkoutDatabase
import com.example.lifttracker.databinding.FragmentLiftingBinding
import com.example.lifttracker.logDatabase.LogDatabase
import org.w3c.dom.Text

class LiftingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentLiftingBinding>(
            inflater, R.layout.fragment_lifting, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource1 = LogDatabase.getInstance(application).logsDao
        val dataSource2 = CurrentWorkoutDatabase.getInstance(application).currentWorkoutDao
        val viewModelFactory = LiftingViewModelFactory(dataSource1, dataSource2, application)
        val liftingViewModel = ViewModelProvider(this, viewModelFactory).get(LiftingViewModel::class.java)
        binding.liftingViewModel = liftingViewModel

        val adapter = LiftingAdapter()
                binding.workingRepSetLog.adapter = adapter

        binding.lifecycleOwner = this

        liftingViewModel.allLogs.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
//            binding.exerciseTitle.text = liftingViewModel.currentExercise.exercise.exerciseTitle
        })

        binding.weight.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                try {
                    liftingViewModel.weight.value = Integer.parseInt(s.toString())
                }
                catch (e: NumberFormatException){
                    liftingViewModel.weight.value = 0
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    liftingViewModel.weight.value = Integer.parseInt(s.toString())
                }
                catch (e: NumberFormatException){
                    liftingViewModel.weight.value = 0
                }
            }

            override fun afterTextChanged(s: Editable?) {
//                    liftingViewModel.weight.value = Integer.parseInt(s.toString())
            }
        })

        binding.repCount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                try {
                    liftingViewModel.repCount.value = Integer.parseInt(s.toString())
                }
                catch (e: NumberFormatException){
                    liftingViewModel.repCount.value = 0
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                try {
                    liftingViewModel.repCount.value = Integer.parseInt(s.toString())
                }
                catch (e: NumberFormatException){
                    liftingViewModel.repCount.value = 0
                }
            }

            override fun afterTextChanged(s: Editable?) {
//                liftingViewModel.repCount.value = Integer.parseInt(s.toString())
            }
        })

        binding.saveLog.setOnClickListener{view: View ->
            liftingViewModel.onLogSet()
            binding.setNumber.text = (liftingViewModel.setNumber+ 1).toString()
        }

        binding.clearButton.setOnClickListener{view : View ->
            liftingViewModel.onClear()
            binding.setNumber.text = (liftingViewModel.setNumber+ 1).toString()
        }

        binding.removeSetButton.setOnClickListener{view: View ->
            liftingViewModel.onRemoveLast()

        }

        return binding.root
    }

//handle onsavedinstance state (when navigate out of app OR phone kill app)
    //hold list of exerciseIDs
    //on next, change title,
}