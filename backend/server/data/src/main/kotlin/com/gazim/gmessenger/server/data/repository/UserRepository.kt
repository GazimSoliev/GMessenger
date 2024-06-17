package com.gazim.gmessenger.server.data.repository

import com.gazim.gmessenger.server.data.database.GMessengerDatabase.dbQuery
import com.gazim.gmessenger.server.data.database.model.AccountEntity
import com.gazim.gmessenger.server.data.database.model.ProfilePhotoEntity
import com.gazim.gmessenger.server.data.database.model.TokenEntity
import com.gazim.gmessenger.server.data.database.table.AccountTable
import com.gazim.gmessenger.server.data.extensions.nowInUTC
import com.gazim.gmessenger.server.data.mapper.toAccountEntity
import com.gazim.gmessenger.server.data.mapper.toImageEntity
import com.gazim.gmessenger.server.data.mapper.toUser
import com.gazim.gmessenger.server.domain.model.Image
import com.gazim.gmessenger.server.domain.model.ProfileForm
import com.gazim.gmessenger.server.domain.model.User
import com.gazim.gmessenger.server.domain.repository.IUserRepository
import java.util.*

class UserRepository : IUserRepository {
//    override suspend fun insert(user: User): Boolean {
//        dbQuery {
//            AccountEntity.new {
//                nickname = user.nickname
//                username = user.username
//                createdAt = LocalDateTime.now(ZoneOffset.UTC)
//            }
//        }
//        return true
//    }

    override suspend fun findByUsername(
        username: String,
        limit: Int,
    ): List<User> =
        dbQuery {
            AccountEntity
                .find {
                    AccountTable.username like "%$username%"
                }.limit(limit)
                .map(AccountEntity::toUser)
        }

    override suspend fun getUser(tokenId: UUID): User =
        dbQuery {
            TokenEntity[tokenId].account.toUser()
        }

    override suspend fun editProfile(
        user: User,
        profileForm: ProfileForm,
    ) = dbQuery {
        val account = AccountEntity[user.id]
        account.nickname = profileForm.nickname
        account.username = profileForm.username
    }

    override suspend fun setProfilePhoto(
        user: User,
        image: Image,
    ) = dbQuery {
        val accountEntity = user.toAccountEntity()
        val imageEntity = image.toImageEntity()
        ProfilePhotoEntity.new {
            this.account = accountEntity
            this.image = imageEntity
            createdAt = nowInUTC()
        }
        Unit
    }
}
