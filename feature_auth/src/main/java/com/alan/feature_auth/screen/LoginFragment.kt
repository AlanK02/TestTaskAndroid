package com.alan.feature_auth.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alan.feature_auth.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var isError = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextAuth1.doOnTextChanged { _, _, _, _ ->
            if ((binding.editTextAuth1.text?.length ?: 0) >= 1) {
                binding.buttonNext.isEnabled = true
                binding.clearText.isVisible = true
            } else {
                binding.buttonNext.isEnabled = false
                binding.clearText.isVisible = false
            }
            if (isError) {
                binding.editTextParent.setBackgroundResource(com.alan.core.R.drawable.rounded_edittext_background)
                binding.enteredWrongEmail.isVisible = false
            }
        }

        binding.clearText.setOnClickListener {
            binding.editTextAuth1.text?.clear()
            if (isError) {
                binding.editTextParent.setBackgroundResource(com.alan.core.R.drawable.rounded_edittext_background)
                binding.enteredWrongEmail.isVisible = false
            }
        }

        binding.buttonNext.setOnClickListener {
            if (binding.editTextAuth1.text?.toString()?.trim()?.isNotEmpty() == true) {
                if (isValidEmail(binding.editTextAuth1.text.toString().trim())) {
                    val email = binding.editTextAuth1.text.toString().trim()

                    val action =
                        LoginFragmentDirections.actionAuthFragmentToCheckCodeFragment(email = email)
                    findNavController().navigate(action)
                } else {
                    isError = true
                    binding.editTextParent.setBackgroundResource(com.alan.core.R.drawable.rounded_edit_text_error)
                    binding.enteredWrongEmail.isVisible = true
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
