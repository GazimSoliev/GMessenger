package com.gazim.gmessenger.presentation.features.chat

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gazim.gmessenger.domain.model.IChat
import com.gazim.gmessenger.domain.model.IMessage
import com.gazim.gmessenger.domain.model.MessagePageKey
import com.gazim.gmessenger.domain.usecase.GetMessagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class MessagePagerSource(
    private val chatId: Uuid,
    private val getMessages: GetMessagesUseCase,
    private val errors: FlowCollector<Throwable>,
) : PagingSource<MessagePageKey, IMessage>() {
    override fun getRefreshKey(state: PagingState<MessagePageKey, IMessage>): MessagePageKey? = null

    override suspend fun load(params: LoadParams<MessagePageKey>): LoadResult<MessagePageKey, IMessage> =
        withContext(Dispatchers.IO) {
            runCatching {
                val currentKey = params.key
                val page = getMessages(chatId, currentKey).getOrThrow()
                LoadResult.Page<MessagePageKey, IMessage>(
                    data = page.data,
                    prevKey = page.prev,
                    nextKey = page.next,
                )
            }.getOrElse {
                errors.emit(it)
                LoadResult.Error(it)
            }
        }
}
