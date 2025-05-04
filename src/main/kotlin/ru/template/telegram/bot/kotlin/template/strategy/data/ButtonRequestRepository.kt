package ru.template.telegram.bot.kotlin.template.strategy.data

import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.strategy.dto.ButtonRequestDto

@Repository
class ButtonRequestRepository:  AbstractRepository<ButtonRequestDto>() {

    override fun getData(chatId: Long): ButtonRequestDto {
        return ButtonRequestDto(listOf("YES", "NO"))
    }
}