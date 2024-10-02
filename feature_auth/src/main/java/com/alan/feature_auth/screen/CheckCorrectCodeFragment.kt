package com.alan.feature_auth.screen

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alan.core.navigation.AuthNavigationHandler
import com.alan.feature_auth.databinding.FragmentCheckCodeBinding

class CheckCorrectCodeFragment : Fragment() {

    private lateinit var binding: FragmentCheckCodeBinding
    private val args by navArgs<CheckCorrectCodeFragmentArgs>()
    private var authNavigationHandler: AuthNavigationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckCodeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authNavigationHandler = requireActivity() as? AuthNavigationHandler

        binding.email.text = getString(com.alan.core.R.string.email, args.email)

        binding.otpTest.setOnDoneListener {
            binding.otpTest.setIsCorrect(true)
            binding.otpTest.text?.clear()

            authNavigationHandler?.onOtpVerified()

            val deepLink = Uri.parse("app://com.alan.find/search")
            findNavController().navigate(deepLink)
        }
    }
}
