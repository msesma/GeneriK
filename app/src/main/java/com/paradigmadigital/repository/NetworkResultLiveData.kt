package com.paradigmadigital.repository


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class NetworkResultLiveData
@Inject constructor() : LiveData<NetworkResult>() {

    private val pending = AtomicBoolean(false)

    fun setNetworkResult(value: NetworkResult) {
        launch(UI) {
            suspendSet(value)
        }
    }

    suspend private fun suspendSet(value: NetworkResult) {
        pending.set(true)
        super.setValue(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NetworkResult>) =
            super.observe(owner, Observer<NetworkResult> { value ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(value)
                }
            })
}