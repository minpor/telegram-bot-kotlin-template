package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.ButtonRequestDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class ButtonRequestMessage<T : DataModel>(
    private val messageWriter: MessageWriter
) : Button<ButtonRequestDto> {

    override fun message(chatId: Long, data: ButtonRequestDto?): String {
        return messageWriter.process(StepCode.BUTTON_REQUEST)
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun inlineButtons(chatId: Long, data: ButtonRequestDto?): List<MarkupDataDto> {
        val accept = data!!.accept
        return listOf(
            MarkupDataDto(0, accept.first()),
            MarkupDataDto(1, accept.last()),
            MarkupDataDto(1, accept.last())
        )
    }

}