package com.frost.el_ch.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.frost.el_ch.R
import com.frost.el_ch.databinding.ActivityHomeBinding
import com.frost.el_ch.extensions.getEmailPref
import com.frost.el_ch.extensions.showToast
import com.frost.el_ch.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<MainViewModel>()

    companion object{
        fun start(activity: Activity){
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_cards, R.id.navigation_qr)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getEmailPref()?.let { viewModel.onCreate(it) }
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.updatedLiveData.observe(this) { handleResponse(it) }
    }

    private fun handleResponse(update: Unit?) {
        update
            ?.let { showToast(this, "Tarjeta agregada") }
            ?:run { showToast(this, getString(R.string.error)) }
    }

    @Deprecated("Deprecated in Java")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            val scanResult = getData(data)

            val card = scanResult?.let { arrayListOf<String>(
                it.redactedCardNumber,
                "${it.expiryMonth}/${it.expiryYear}") }

            val cardString = card?.joinToString(separator = ",")?:""
            viewModel.updateCard(cardString)
        } else {
            showToast(this, "Scan was canceled.")
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getData(data: Intent?) =
        if (Build.VERSION.CODENAME == Build.VERSION_CODES.TIRAMISU.toString())
            data?.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT, CreditCard::class.java)
        else data?.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)
}