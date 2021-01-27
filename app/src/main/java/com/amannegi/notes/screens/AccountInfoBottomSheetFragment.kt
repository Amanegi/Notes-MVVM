package com.amannegi.notes.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amannegi.notes.databinding.AccountBottomSheetBinding
import com.amannegi.notes.utils.loadImageViaCoil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AccountInfoBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: AccountBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountBottomSheetBinding.inflate(inflater, container, false)

        // logged in account fetched using Google Sign In
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())

        // extension function called on imageview
        binding.accImage.loadImageViaCoil(account?.photoUrl.toString())
        binding.accName.setText(account?.displayName)
        binding.accEmail.setText(account?.email)

        return binding.root
    }

}