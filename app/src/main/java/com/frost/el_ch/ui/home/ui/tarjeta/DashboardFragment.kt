package com.frost.el_ch.ui.home.ui.tarjeta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.frost.el_ch.databinding.FragmentDashboardBinding
import com.frost.el_ch.extensions.getEmailPref
import com.frost.el_ch.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import io.card.payment.CardIOActivity

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    companion object{
        const val MY_SCAN_REQUEST_CODE = 200
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addCardButton.setOnClickListener { onScanPress(binding.root) }
    }

    private fun onScanPress(v: View?) {
        val scanIntent = Intent(requireContext(), CardIOActivity::class.java)

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true) // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE)
    }

}