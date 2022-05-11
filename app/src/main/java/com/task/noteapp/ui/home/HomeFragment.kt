package com.task.noteapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentHomeBinding
import com.task.noteapp.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var requestManager: RequestManager

    private val viewModel: MainViewModel by hiltNavGraphViewModels(R.id.main_nav_graph)
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        binding.addNoteBtn.setOnClickListener {
            viewModel.setSelectedNote(null)
            navigateToDetailFragment()
        }
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        binding.noteRecycler.addItemDecoration(dividerItemDecoration)
        viewModel.loadNotes()
        viewModel.notesLiveData.observe(this, { list ->
            binding.emptyIcon.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            noteAdapter = NoteAdapter(list, requestManager) {
                viewModel.setSelectedNote(it)
                navigateToDetailFragment()
            }
            binding.noteRecycler.adapter = noteAdapter
        })
        return binding.root
    }

    private fun navigateToDetailFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
    }

}