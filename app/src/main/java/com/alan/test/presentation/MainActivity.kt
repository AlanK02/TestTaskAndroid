package com.alan.test.presentation

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alan.core.navigation.AuthNavigationHandler
import com.alan.test.R
import com.alan.test.databinding.ActivityMainBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AuthNavigationHandler {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var hasEnteredAccount = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupNavigation()
        observeVacancyCount()
    }

    private fun setupNavigation() {
        val navFragment =
            supportFragmentManager.findFragmentById(binding.fragmentsContainer.id) as NavHostFragment
        val navController = navFragment.navController

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.alan.core.R.id.search -> {
                    navigateWithCheck(
                        hasEnteredAccount,
                        navController,
                        Uri.parse("app://com.alan.find/search")
                    )
                    true
                }

                com.alan.core.R.id.favourite -> {
                    navigateWithCheck(
                        hasEnteredAccount,
                        navController,
                        Uri.parse("app://com.alan.fav/favourite")
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun observeVacancyCount() {
        val navView: BottomNavigationView = binding.bottomNavView
        val menuItemId: Int = navView.menu.getItem(1).itemId

        viewModel.getFavourites().observe(this) { res ->
            val favouriteCount = res.count { it.isFavorite }

            if (favouriteCount > 0) {
                val badge: BadgeDrawable = navView.getOrCreateBadge(menuItemId)
                badge.backgroundColor =
                    ContextCompat.getColor(this@MainActivity, com.alan.core.R.color.red)
                badge.number = favouriteCount
            } else {
                navView.removeBadge(menuItemId)
            }
        }
    }

    private fun navigateWithCheck(
        hasEnteredAccount: Boolean,
        navController: NavController,
        deepLinkUri: Uri
    ): Boolean {
        if (hasEnteredAccount) {
            navController.navigate(deepLinkUri)
        } else {
            showLoginRequiredMessage()
            navController.navigate(Uri.parse("app://com.alan.test/auth"))
        }
        return true
    }

    private fun showLoginRequiredMessage() {
        Toast.makeText(this, "Please login to access Favourite.", Toast.LENGTH_SHORT).show()
    }

    override fun onOtpVerified() {
        hasEnteredAccount = true
        Toast.makeText(this, "Successfully logged in!", Toast.LENGTH_SHORT).show()
    }

    override fun isUserLoggedIn(): Boolean {
        return hasEnteredAccount
    }
}
