package com.frost.el_ch.ui.home.ui.qr

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.el_ch.repositories.QRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val api: QRRepository
) : ViewModel() {

    var urlLiveData = MutableLiveData<String?>()

    var qrUrl : String? = null
    private set

    fun onCreate(name: String){
        api.getQR(name)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {urlLiveData.postValue(it.qrCode) },
                { urlLiveData.postValue(null) }
            )
    }
}