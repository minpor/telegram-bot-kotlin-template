package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.ButtonRequestDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

@Component
class ButtonRequestMessage<T : DataModel>(messageWriter: MessageWriter) : AbstractSendMessage<ButtonRequestDto>(messageWriter) {

    override fun inlineButtons(chatId: Long, data: ButtonRequestDto?): List<MarkupDataDto> {
        val accept = data!!.accept
        return listOf(
            MarkupDataDto(0, accept.first()),
            MarkupDataDto(1, accept.last()),
            MarkupDataDto(1, accept.last())
        )
    }

}