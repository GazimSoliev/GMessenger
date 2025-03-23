package com.gazim.gmessenger.presentation.features.chat

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.usecase.GetMessagesUseCase
import com.gazim.gmessenger.presentation.model.IMessageItemUI
import com.gazim.gmessenger.presentation.model.toUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext

class MessagePagerSource(
    private val chatModel: IChat,
    private val getMessages: GetMessagesUseCase,
    private val errors: FlowCollector<Throwable>,
) : PagingSource<MessagePageKey, IMessageItemUI>() {
    override fun getRefreshKey(state: PagingState<MessagePageKey, IMessageItemUI>): MessagePageKey? = null

    override suspend fun load(params: LoadParams<MessagePageKey>): LoadResult<MessagePageKey, IMessageItemUI> =
        withContext(Dispatchers.IO) {
            runCatching {
                val currentKey = params.key
                val page = getMessages(chatModel, currentKey).getOrThrow()
                PagingSourceLoadResultPage<MessagePageKey, IMessageItemUI>(
                    data = page.data.toUI(),
                    prevKey = page.prev,
                    nextKey = page.next,
                )
            }.getOrElse {
                errors.emit(it)
                PagingSourceLoadResultError(it)
            }
        }
}
