package com.example.lifttracker.builder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lifttracker.exerciseDatabase.ExerciseDatabase
import com.example.lifttracker.R
import com.example.lifttracker.currentWorkout.CurrentWorkoutDatabase
import com.example.lifttracker.databinding.FragmentBuilderBinding
import com.example.lifttracker.exerciseDatabase.NewExercise
import com.example.lifttracker.exerciseSelection.SelectionFragment
import com.example.lifttracker.exerciseSelection.SelectionViewModel

class BuilderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentBuilderBinding>(
            inflater, R.layout.fragment_builder, container, false)

        //view model linking
        val application = requireNotNull(this.activity).application
        val dataSource = CurrentWorkoutDatabase.getInstance(application).currentWorkoutDao
        val viewModelFactory = BuilderViewModelFactory(dataSource, application)
        val builderViewModel = ViewModelProvider(this, viewModelFactory).get(BuilderViewModel::class.java)
        binding.builderViewModel = builderViewModel

        val adapter = BuilderAdapter(BuilderListener {
            exercise ->  builderViewModel.onDelete(exercise)
//            exerciseId ->  Toast.makeText(context, "$exerciseId", Toast.LENGTH_SHORT).show()
        })

        binding.workoutList.adapter = adapter

        binding.lifecycleOwner = this

       builderViewModel.workoutSummary.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.addExerciseFAB.setOnClickListener { view: View ->
            view.findNavController().navigate(BuilderFragmentDirections.actionBuilderFragmentToSelectionFragment())
        }

        binding.resetButton.setOnClickListener{view: View ->
            builderViewModel.onClear()
        }



        return binding.root
    }
}
