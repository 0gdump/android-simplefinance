package open.zgdump.simplefinance.util.android

fun <T> MutableList<T>.swap(fromPosition: Int, toPosition: Int) {
    val item = this[fromPosition]
    removeAt(fromPosition)
    add(toPosition, item)
}