package open.zgdump.simplefinance.util.pattern.observer

interface Observable {

    val observers: ArrayList<Observer>

    fun add(observer: Observer) {
        observers.add(observer)
    }

    fun remove(observer: Observer) {
        observers.remove(observer)
    }
}