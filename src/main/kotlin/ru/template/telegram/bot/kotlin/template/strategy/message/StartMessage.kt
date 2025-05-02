package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class StartMessage(
    private val messageWriter: MessageWriter
) : Message {

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.START)
    }
}