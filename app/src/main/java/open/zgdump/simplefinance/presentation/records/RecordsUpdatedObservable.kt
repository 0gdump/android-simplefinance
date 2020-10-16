package open.zgdump.simplefinance.presentation.records

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import open.zgdump.simplefinance.util.pattern.observer.Observable
import open.zgdump.simplefinance.util.pattern.observer.Observer

object RecordsUpdatedObservable :
    Observable,
    CoroutineScope by CoroutineScope(Dispatchers.Main) {

    override val observers: ArrayList<Observer> = ArrayList()

    fun recordsUpdated(observer: Observer) {
        observers.forEach {
            launch {
                if (it != observer) {
                    it.observableUpdated()
                }
            }
        }
    }
}