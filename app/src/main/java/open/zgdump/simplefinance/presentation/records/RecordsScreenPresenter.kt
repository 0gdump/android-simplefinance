package open.zgdump.simplefinance.presentation.records

import open.zgdump.simplefinance.entity.Record
import open.zgdump.simplefinance.presentation.global.paginal.PaginalPresenter

class RecordsScreenPresenter : PaginalPresenter<RecordsScreenView, Record>() {

    override fun diffItems(old: Any, new: Any): Boolean {
        return true
    }

    override suspend fun loadPage(page: Int): List<Record> {
        return listOf()
    }

    override fun fabClicked() {

    }

    override fun itemClicked(index: Int) {

    }

    override fun provideRemove(index: Int) {

    }
}