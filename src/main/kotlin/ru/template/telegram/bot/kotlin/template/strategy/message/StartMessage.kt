package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class StartMessage(
    private val messageWriter: MessageWriter
) : Message<DataModel> {

    override fun message(chatId: Long, data: DataModel?): String {
        return messageWriter.process(StepCode.START, data)
    }

    override fun inlineButtons(
        chatId: Long,
        data: DataModel?
    ): List<MarkupDataDto> {
        return emptyList()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}