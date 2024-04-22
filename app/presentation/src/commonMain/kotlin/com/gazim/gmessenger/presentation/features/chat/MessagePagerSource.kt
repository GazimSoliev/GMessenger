package com.gazim.gmessenger.presentation.features.chat

import app.cash.paging.PagingSource
import app.cash.paging.PagingSourceLoadResultError
import app.cash.paging.PagingSourceLoadResultPage
import app.cash.paging.PagingState
import com.gazim.gmessenger.domain.model.IChatModel
import com.gazim.gmessenger.domain.model.IMessageModel
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.usecase.GetMessagesUseCase
import com.gazim.gmessenger.presentation.model.IMessageItemUI
import com.gazim.gmessenger.presentation.model.toMessageUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class MessagePagerSource(
    private val chatModel: IChatModel,
    private val getMessages: GetMessagesUseCase,
    private val ms: Channel<IMessageModel>,
    private val errors: MutableSharedFlow<Throwable>,
) : PagingSource<MessagePagerSource.Key, IMessageItemUI>() {
    override fun getRefreshKey(state: PagingState<Key, IMessageItemUI>): Key? = null

    override suspend fun load(params: LoadParams<Key>): LoadResult<Key, IMessageItemUI> =
        withContext(Dispatchers.IO) {
            runCatching {
                when (val currentKey = params.key) {
                    is LiveKey -> {
                        PagingSourceLoadResultPage<Key, IMessageItemUI>(
                            data = listOf(ms.receive().toMessageUI()),
                            nextKey = if (currentKey.index == 0) PagedKey(null) else LiveKey(currentKey.index - 1),
                            prevKey = LiveKey(currentKey.index + 1),
                        )
                    }

                    else -> {
                        val pagedKey = (currentKey as? PagedKey)
                        val page = getMessages.invoke(chatModel, pagedKey?.key)
                        PagingSourceLoadResultPage<Key, IMessageItemUI>(
                            data = page.data.map { it.toMessageUI() },
                            prevKey = if (pagedKey == null) LiveKey(0) else PagedKey(page.prev),
                            nextKey = page.next?.let(::PagedKey),
                        )
                    }
                }
            }.getOrElse {
                errors.emit(it)
                PagingSourceLoadResultError(it)
            }
        }

    sealed interface Key

    @JvmInline
    value class PagedKey(
        val key: MessagePageKey?,
    ) : Key

    @JvmInline
    value class LiveKey(
        val index: Int,
    ) : Key
}
