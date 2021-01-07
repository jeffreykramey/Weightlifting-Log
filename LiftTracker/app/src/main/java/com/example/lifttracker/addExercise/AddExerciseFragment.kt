package com.example.lifttracker.addExercise

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lifttracker.R
import com.example.lifttracker.databinding.FragmentAddExerciseBinding
import com.example.lifttracker.exerciseDatabase.ExerciseDatabase

class AddExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentAddExerciseBinding>(
            inflater, R.layout.fragment_add_exercise, container, false
        )


        Log.i("AddExerciseFragment", "vmprovider called")
//        viewModel = ViewModelProvider(this).get(AdderViewModel::class.java)

        val application = requireNotNull(this.activity).application
        val dataSource = ExerciseDatabase.getInstance(application).exerciseDatabaseDao
        val viewModelFactory = AdderViewModelFactory(dataSource, application)
        val adderViewModel = ViewModelProvider(this, viewModelFactory).get(AdderViewModel::class.java)
        binding.adderViewModel = adderViewModel
        binding.lifecycleOwner = this

//        binding.addExercise = this

        binding.equipmentRadioGroup1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked != -1) {
                binding.equipmentRadioGroup2.clearCheck()
            }
        }
        binding.equipmentRadioGroup2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked != -1) {
                binding.equipmentRadioGroup1.clearCheck()
            }
        }

        adderViewModel.exerciseTitle.observe(viewLifecycleOwner, Observer {newTitle ->
            //pass to exercise creater
            Log.i("AddExerciseFragment", newTitle)
        })

        adderViewModel.equipment.observe(viewLifecycleOwner, Observer {newEquipment ->
            //pass to exercise creater
            Log.i("AddExerciseFragment", newEquipment)
        })

        adderViewModel.metric.observe(viewLifecycleOwner, Observer {newMetric ->
            //pass to exercise creater
            Log.i("AddExerciseFragment", newMetric)
        })

        binding.saveButton.setOnClickListener { view: View ->
            //Get title
            adderViewModel.exerciseTitle.value = binding.exerciseTitle.text.toString()

            //get equipment from one of the radio button groups
            if (binding.equipmentRadioGroup1.checkedRadioButtonId != -1) {
                adderViewModel.equipment.value =
                    adderViewModel.getRadioButtonText(binding.equipmentRadioGroup1.checkedRadioButtonId, binding)
            } else if (binding.equipmentRadioGroup2.checkedRadioButtonId != -1) {
                adderViewModel.equipment.value =
                    adderViewModel.getRadioButtonText(binding.equipmentRadioGroup2.checkedRadioButtonId, binding)
            }

            //get metric
            if (binding.metricRadioGroup.checkedRadioButtonId != -1) {
                adderViewModel.metric.value =  adderViewModel.getRadioButtonText(binding.metricRadioGroup.checkedRadioButtonId, binding)
            }


            adderViewModel.onSaveExercise()
            view.findNavController()
                .navigate(AddExerciseFragmentDirections.actionAddExerciseFragmentToSelectionFragment())

        }

        binding.cancelButton.setOnClickListener{ view: View ->
            view.findNavController().navigate(AddExerciseFragmentDirections.actionAddExerciseFragmentToSelectionFragment())
        }

        return binding.root
    }




//    private fun getRadioButtonText(
//        radioButtonId: Int,
//        binding: FragmentAddExerciseBinding
//    ): String {
//        val rb: RadioButton = binding.root.findViewById(radioButtonId)
//        return rb.text.toString()
//    }
}

