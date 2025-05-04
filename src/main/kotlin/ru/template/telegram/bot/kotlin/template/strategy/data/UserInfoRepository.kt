package ru.template.telegram.bot.kotlin.template.strategy.data

import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.strategy.dto.UserInfoDto

@Repository
class UserInfoRepository: AbstractRepository<UserInfoDto>() {

    override fun getData(chatId: Long): UserInfoDto {
        return UserInfoDto(chatId)
    }
}