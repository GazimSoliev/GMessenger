package com.gazim.gmessenger.presentation.features.chat

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.model.MessagePageKey

class MessagePagerSource : PagingSource<MessagePageKey, List<IMessageModel>>() {
    override fun getRefreshKey(state: PagingState<MessagePageKey, List<IMessageModel>>): MessagePageKey? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<MessagePageKey>): LoadResult<MessagePageKey, List<IMessageModel>> {
        TODO("Not yet implemented")
    }

}
