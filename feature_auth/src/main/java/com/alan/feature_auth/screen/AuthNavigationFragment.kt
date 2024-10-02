package com.alan.feature_auth.screen

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alan.core.navigation.AuthNavigationHandler
import com.alan.feature_auth.R

class AuthNavigationFragment : Fragment() {

    private var authNavigationHandler: AuthNavigationHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authNavigationHandler = requireActivity() as? AuthNavigationHandler

        val hasEntered = authNavigationHandler?.isUserLoggedIn() ?: false

        if (hasEntered) {
            findNavController().navigate(Uri.parse("app://com.alan.find/search"))
        } else {
            findNavController().navigate(Uri.parse("app://com.alan.test/auth"))
        }
    }
}
