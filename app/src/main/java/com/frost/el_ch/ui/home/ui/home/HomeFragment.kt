package com.frost.el_ch.ui.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.frost.el_ch.databinding.FragmentHomeBinding
import com.frost.el_ch.extensions.getEmailPref
import com.frost.el_ch.extensions.showToast
import com.frost.el_ch.extensions.turnToCard
import com.frost.el_ch.model.Card
import com.frost.el_ch.model.User
import com.frost.el_ch.ui.utils.CardAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: CardAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        getEmailPref(requireContext())?.let { viewModel.onCreate(it) }
        subscribeToLiveData()
        return binding.root
    }

    private fun subscribeToLiveData() {
        viewModel.userLiveData.observe(viewLifecycleOwner) { handleUser(it) }
    }

    private fun handleUser(user: User?) {
        user
            ?.let { setContent(it) }
            ?:run { showToast(requireContext(), "Error") }
    }

    override fun onResume() {
        super.onResume()
        getEmailPref(requireContext())?.let { viewModel.onCreate(it) }
    }

    private fun setContent(user: User) {
        if (!user.cards.isNullOrEmpty()) splitAndSetCards(user.cards!!)
        with(binding){
            binding.numbertextView.text = "$ ${user.saldo}"
        }
    }

    private fun splitAndSetCards(cards: String) {
        val cardList = cards.split(";")
        val list = cardList.map { it.turnToCard() }
        binding.cardsrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cardsrecyclerView.adapter = CardAdapter(list)
    }
}