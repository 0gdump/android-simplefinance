package open.zgdump.simplefinance.util.kotlin

fun <T> List<T>.prepend(item: T): List<T> {
    val list = ArrayList<T>(1 + size)

    list.add(item)
    list.addAll(this)

    return list
}