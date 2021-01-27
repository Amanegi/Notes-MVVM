package com.amannegi.notes.screens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.amannegi.notes.R
import com.amannegi.notes.adapter.NotesRecyclerViewAdapter
import com.amannegi.notes.adapter.RecyclerViewItemClickListener
import com.amannegi.notes.database.NoteViewModel
import com.amannegi.notes.databinding.FragmentNotesListBinding
import com.amannegi.notes.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar


class NotesListFragment : Fragment() {

    private lateinit var binding: FragmentNotesListBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // handling received shared element transition
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            val recyclerClickListener = object : RecyclerViewItemClickListener {
                override fun onEditMenuClick(position: Int) {
                    val note = it[position]
                    // passing data in safe args
                    val action = NotesListFragmentDirections
                        .actionNotesListFragmentToBottomSheetFragment(note)
                    Navigation.findNavController(binding.root).navigate(action)
                }

                override fun onDeleteMenuClick(position: Int) {
                    val note = it[position]
                    viewModel.deleteNote(note)
                    Snackbar.make(binding.root, "Note deleted successfully.", Snackbar.LENGTH_SHORT)
                        .setAnchorView(binding.fabAddNote).show()
                }

            }
            binding.recyclerView.adapter = NotesRecyclerViewAdapter(it, recyclerClickListener)
        })

        binding.fabAddNote.setOnClickListener {
            val action = NotesListFragmentDirections
                .actionNotesListFragmentToBottomSheetFragment()
            Navigation.findNavController(binding.root).navigate(action)
        }

        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_account -> {
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_notesListFragment_to_accountInfoBottomSheetFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.menu_logout -> {
                    // prompt before logout
                    val alertDialog = AlertDialog.Builder(requireContext()).apply {
                        setTitle("Are you sure you want to logout?")
                        setIcon(R.drawable.ic_logout)
                        setCancelable(false)
                        setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                SessionManager(requireContext()).clearPrefs()
                                openLoginScreen()
                                signOutFromGoogleSignIn()
                            }
                        })
                        setNegativeButton("No", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog?.cancel()
                            }
                        })
                    }
                    alertDialog.create().show()

                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener false
        }


    }

    private fun signOutFromGoogleSignIn() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)
        mGoogleSignInClient.signOut()
    }

    private fun openLoginScreen() {
        val navController = Navigation.findNavController(binding.root)
        navController.navigate(R.id.action_notesListFragment_to_loginFragment)
    }

}