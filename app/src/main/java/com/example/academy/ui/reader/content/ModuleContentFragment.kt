package com.example.academy.ui.reader.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.academy.data.source.local.entity.ModuleEntity
import com.example.academy.databinding.FragmentModuleContentBinding
import com.example.academy.ui.reader.CourseReaderViewModel
import com.example.academy.viewmodel.ViewModelFactory
import com.example.academy.vo.Status

class ModuleContentFragment : Fragment() {

    companion object {
        val TAG = ModuleContentFragment::class.java.simpleName

        fun newInstance(): ModuleContentFragment {
            return ModuleContentFragment()
        }
    }

    private var _binding: FragmentModuleContentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CourseReaderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            viewModel.selectedModule.observe(this, { moduleEntity ->
                if (moduleEntity != null) {
                    when (moduleEntity.status) {
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> if (moduleEntity.data != null) {
                            binding.progressBar.visibility = View.GONE
                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data)
                            }
                            setButtonNextPrevState(moduleEntity.data)
                            if (!moduleEntity.data.read) {
                                viewModel.readContent(moduleEntity.data)
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                    binding.btnNext.setOnClickListener { viewModel.setNextPage() }
                    binding.btnPrev.setOnClickListener { viewModel.setPrevPage() }
                }
            })
        }
    }

    private fun populateWebView(module: ModuleEntity) {
        module.contentEntity?.content?.let { binding.webView.loadData(it, "text/html", "UTF-8") }
    }

    private fun setButtonNextPrevState(module: ModuleEntity) {
        if (activity != null) {
            when (module.position) {
                0 -> {
                    binding.btnPrev.isEnabled = false
                    binding.btnNext.isEnabled = true
                }
                viewModel.getModuleSize() - 1 -> {
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = false
                }
                else -> {
                    binding.btnPrev.isEnabled = true
                    binding.btnNext.isEnabled = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}