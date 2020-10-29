package open.zgdump.simplefinance.presentation.global

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import open.zgdump.simplefinance.util.pattern.observer.Observable
import open.zgdump.simplefinance.util.pattern.observer.Observer
import timber.log.Timber

open class DefaultObservable :
    Observable,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    override val observers: ArrayList<Observer> = ArrayList()

    fun updated(from: Observer) {
        observers.forEach {
            launch {
                if (it !== from) {
                    it.observableUpdated()
                } else {
                    Timber.d("Ignored")
                }
            }
        }
    }
}