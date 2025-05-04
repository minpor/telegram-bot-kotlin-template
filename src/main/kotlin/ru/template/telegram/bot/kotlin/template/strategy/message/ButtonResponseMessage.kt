package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.dto.ButtonResponseDto

@Component
class ButtonResponseMessage(
    private val messageWriter: MessageWriter
) : Message<ButtonResponseDto> {

    override fun message(chatId: Long, data: ButtonResponseDto?): String {
        return messageWriter.process(StepCode.BUTTON_RESPONSE, data)
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}