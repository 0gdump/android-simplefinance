package open.zgdump.simplefinance.util.kotlin

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}