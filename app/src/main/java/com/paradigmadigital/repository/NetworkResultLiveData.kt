package com.paradigmadigital.repository


import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class NetworkResultLiveData
@Inject
constructor(
        private val executor: MainThreadExecutor
) : LiveData<NetworkResult>() {

    private val pending = AtomicBoolean(false)

    fun setNetworkResult(value: NetworkResult) {
        executor.execute {
            pending.set(true)
            super.setValue(value)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NetworkResult>) =
            super.observe(owner, Observer<NetworkResult> { value ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(value)
                }
            })
}