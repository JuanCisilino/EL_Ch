package com.frost.el_ch.ui.home.ui.qr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.frost.el_ch.R
import com.frost.el_ch.databinding.FragmentNotificationsBinding
import com.frost.el_ch.extensions.showToast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private val viewModel by viewModels<NotificationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        subscribeToLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setComponent()
    }

    private fun subscribeToLiveData() {
        viewModel.urlLiveData.observe(viewLifecycleOwner) { showToast(requireContext(), it?:"error") }
    }

    private fun setComponent() {
        binding.qrButton.setOnClickListener { validateAndContinue() }
    }

    private fun validateAndContinue() {
        if (binding.nametextView.text.isNotEmpty()) viewModel.onCreate(binding.nametextView.text.toString())
        else showToast(requireContext(), getString(R.string.empty))
        viewModel.qrUrl?.let { Picasso.get().load(it).into(binding.image) }
    }

}