package com.example.lifttracker.exerciseSelection

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.lifttracker.exerciseDatabase.ExerciseDatabase
import com.example.lifttracker.R
import com.example.lifttracker.databinding.FragmentSelectionBinding


class SelectionFragment : Fragment() {
    var list = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSelectionBinding>(
                inflater, R.layout.fragment_selection, container, false)

        //view model linking
        val application = requireNotNull(this.activity).application
        val dataSource = ExerciseDatabase.getInstance(application).exerciseDatabaseDao
        val viewModelFactory = SelectionViewModelFactory(dataSource, application)
        val selectionViewModel = ViewModelProvider(this, viewModelFactory).get(SelectionViewModel::class.java)
        binding.selectionViewModel = selectionViewModel

        val adapter = SelectExerciseAdapter(SelectExerciseListener {
            exerciseTitle -> Toast.makeText(context, "${exerciseTitle}", Toast.LENGTH_SHORT).show()
        })
        binding.exerciseList.adapter = adapter
        selectionViewModel.allExercises.observe(viewLifecycleOwner, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })


        binding.lifecycleOwner = this

        binding.createExerciseFAB.setOnClickListener { view: View ->
            view.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToAddExerciseFragment())
        }

        binding.clearButton.setOnClickListener{view: View ->
            selectionViewModel.onClear()
        }

        binding.addSelectedButton.setOnClickListener{view : View ->
            list = adapter.mutableList
            view.findNavController().navigate(SelectionFragmentDirections.actionSelectionFragmentToBuilderFragment())
        }

//        FILTERING ATTEMPT
//        binding.exerciseFilter.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.e("TAG", "====>${newText}")
//                selectionViewModel.onFilter(newText)
//                return true
//            }
//        })
        return binding.root
    }

}