package com.raywenderlich.tasksapp.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.raywenderlich.tasksapp.R
import com.raywenderlich.tasksapp.data.Note
import com.raywenderlich.tasksapp.databinding.FragmentUpdateBinding
import com.raywenderlich.tasksapp.viewmodels.NoteViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UpdateFragment : Fragment() {
    private lateinit var binding : FragmentUpdateBinding
    private lateinit var viewModel : NoteViewModel
    private val args by navArgs<UpdateFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater)
        binding.etTitleUpdate.setText(args.currTask.title)
        binding.etDescriptionUpdate.setText(args.currTask.description)

        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        binding.button.setOnClickListener {
            viewModel.updateData(requireContext(), findNavController(),
                binding.etTitleUpdate,binding.etDescriptionUpdate,
                DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDate.now()),args)
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_item_update)
            deleteUser()
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            viewModel.deleteNote(args.currTask)
            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToViewPagerFragment2())
        }
        builder.setNegativeButton("No"){_,_-> }
        builder.setTitle("Delete ${args.currTask.title} ?")
        builder.setMessage("are you sure you want to delete ${args.currTask.title} ?")
        builder.create().show()
    }
}