package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

@Component
class AccessMessage : Message<DataModel> {

    override fun message(
        chatId: Long,
        data: DataModel?
    ): String {
        return "" // not use, this is only for test isPermitted
    }

    override fun inlineButtons(
        chatId: Long,
        data: DataModel?
    ): List<MarkupDataDto> {
        return emptyList()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return false
    }
}