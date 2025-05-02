package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.message.Message

@Component
class MessageContext(private val message: List<Message>) {

    fun getMessage(chatId: Long, stepCode: StepCode): String {
        return message
            .filter { it.isAvailableForCurrentStep(stepCode) }
            .map { it.getMessage(chatId) }
            .first()
    }
}
