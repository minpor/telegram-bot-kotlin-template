package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus
import ru.template.telegram.bot.kotlin.template.strategy.logic.CallbackChooser
import ru.template.telegram.bot.kotlin.template.strategy.logic.Chooser
import ru.template.telegram.bot.kotlin.template.strategy.logic.MessageChooser

@Component
class LogicContext(private val chooser: List<Chooser>) {

    fun execute(chatId: Long, message: Message) {
        chooser.filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .forEach {
                (it as MessageChooser).execute(chatId = chatId, message = message)
            }
    }

    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        return chooser
            .filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .map { (it as CallbackChooser).execute(chatId = chatId, callbackQuery = callbackQuery) }
            .first()
    }
}
