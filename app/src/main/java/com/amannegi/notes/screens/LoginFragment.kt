package com.amannegi.notes.screens

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.amannegi.notes.R
import com.amannegi.notes.databinding.FragmentLoginBinding
import com.amannegi.notes.utils.SessionManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


class LoginFragment : Fragment() {

    private val SIGN_IN_REQUEST_CODE = 200
    private val TAG = "LOGIN_FRAGMENT"
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        sessionManager = SessionManager(requireContext())

        // logo animation
        ObjectAnimator.ofFloat(binding.logo, "translationY", 300f).apply {
            duration = 1000
            start()
        }
        // app name animation
        ObjectAnimator.ofFloat(binding.appName, "alpha", 1f).apply {
            duration = 1000
            start()
        }

        binding.logo.postDelayed({
            // check for user login status
            if (sessionManager.getLoginStatus()) {
                openNotesListScreen()
            } else {
                // show google login button
                binding.signInButton.visibility = View.VISIBLE
            }
        }, 1100)

        // Google Sign In setup
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        binding.signInButton.setOnClickListener {
            // launching Google Sign In window
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, SIGN_IN_REQUEST_CODE)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // check for successful Sign In
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        // handle successful Sign In
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                sessionManager.setData(account.displayName, account.email)
                openNotesListScreen()
            }
        } catch (e: ApiException) {
            Log.d(TAG, "SignInResult: failed code=" + e.statusCode)
        }
    }

    private fun openNotesListScreen() {
        val navController = Navigation.findNavController(binding.root)
        // added extras for shared element transition
        val extras = FragmentNavigatorExtras(binding.appName to getString(R.string.transition_name))
        navController.navigate(R.id.action_loginFragment_to_notesListFragment, null, null, extras)
    }

}