package open.zgdump.simplefinance.entity

import open.zgdump.simplefinance.R

enum class TransactionType(val value: Int) {
    Income(R.string.income),
    Expense(R.string.expense)
}