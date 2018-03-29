package com.fruits

import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.IllegalStateException

open class BasePresenter {
    private val disposables = CompositeDisposable()

    @CallSuper
    fun unregister() {
        disposables.clear()
    }

    @CallSuper
    fun addToUnsubscribe(disposable: Disposable) {
        disposables.add(disposable)
    }
}

