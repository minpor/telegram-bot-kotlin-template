package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class StartMessage(
    private val messageWriter: MessageWriter
) : Message<DataModel> {

    override fun message(chatId: Long, data: DataModel?): String {
        return messageWriter.process(StepCode.START)
    }
}