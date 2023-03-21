package com.frost.el_ch.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatEditText
import com.frost.el_ch.R
import com.frost.el_ch.databinding.ActivityMainBinding
import com.frost.el_ch.extensions.saveEmail
import com.frost.el_ch.extensions.showAlert
import com.frost.el_ch.extensions.showToast
import com.frost.el_ch.model.User
import com.frost.el_ch.ui.home.MainActivity
import com.frost.el_ch.ui.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()
    private var loadingDialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.onCreate()
        setButtons()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.registerLiveData.observe(this) { handleRegister(it) }
    }

    private fun handleRegister(user: Unit?) {
        user
            ?.let { saveAndContinue() }
            ?:run { showToast(this, "Error") }
    }

    private fun saveAndContinue(){
        saveEmail(binding.usernameEditText.text.toString())
        loadingDialog.dismiss()
        MainActivity.start(this)
        finish()
    }

    private fun setButtons() {
        with(binding){
            loginButton.setOnClickListener { validateAndContinue() }
            registerButton.setOnClickListener { validateAndRegister() }
        }
    }

    private fun validateAndRegister() {
        loadingDialog.show(supportFragmentManager)
        if (validate()){
            val user = User(
                null,
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                250.00F
            )
            viewModel.registerUser(user)
        }
    }

    private fun validateAndContinue() {
        if (validate()){
            loadingDialog.show(supportFragmentManager)
            if (viewModel.users.any {
                    it.username == binding.usernameEditText.text.toString()
                            && it.password == binding.passwordEditText.text.toString()}){
                saveAndContinue()
            } else {
                loadingDialog.dismiss()
                showAlert()
            }
        }
    }

    private fun validate(): Boolean{
        return when{
            binding.usernameEditText.text.isNullOrBlank() -> { showHint(binding.usernameEditText)}
            binding.passwordEditText.text.isNullOrBlank() -> { showHint(binding.usernameEditText) }
            else -> true
        }
    }

    private fun showHint(editText: AppCompatEditText): Boolean {
        editText.hint = getString(R.string.empty)
        return false
    }
}