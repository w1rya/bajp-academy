package com.example.academy.ui.academy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy.R
import com.example.academy.databinding.FragmentAcademyBinding
import com.example.academy.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_academy.*

class AcademyFragment : Fragment() {

    private var _binding: FragmentAcademyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAcademyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]

            val academyAdapter = AcademyAdapter()

            binding.progressBar.visibility = View.VISIBLE
            viewModel.getCourses().observe(viewLifecycleOwner, { courses ->
                binding.progressBar.visibility = View.GONE
                academyAdapter.setCourses(courses)
                academyAdapter.notifyDataSetChanged()
            })

            with(binding.rvAcademy) {
                layoutManager = LinearLayoutManager(context)
                adapter = academyAdapter
                setHasFixedSize(true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}