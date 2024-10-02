package com.alan.test.search.presentation.screen.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.alan.feature_find_vacancy.databinding.BottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(private val text: String? = null) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (text != null) binding.coverLetter.setText(text)

        if (text != null) {
            binding.addCovverLetter.isVisible = false
            binding.coverLetter.isVisible = true
        }

        binding.addCovverLetter.setOnClickListener {
            binding.addCovverLetter.isVisible = false
            binding.coverLetter.isVisible = true
        }

        binding.applyButton.setOnClickListener {
            dismiss()
        }

    }
}