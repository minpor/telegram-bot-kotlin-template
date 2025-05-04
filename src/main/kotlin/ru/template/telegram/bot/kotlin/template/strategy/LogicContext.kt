package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.logic.CallbackChooser
import ru.template.telegram.bot.kotlin.template.strategy.logic.Chooser
import ru.template.telegram.bot.kotlin.template.strategy.logic.MessageChooser

@Component
class LogicContext(private val chooser: List<Chooser>) {

    fun execute(chatId: Long, message: Message, stepCode: StepCode) {
        chooser
            .filter { it.isAvailableForCurrentStep(stepCode) }
            .forEach {
                (it as MessageChooser).execute(chatId = chatId, message = message)
            }
    }

    fun execute(chatId: Long, callbackQuery: CallbackQuery, stepCode: StepCode): ExecuteStatus {
        return chooser
            .filter { it.isAvailableForCurrentStep(stepCode) }
            .map { (it as CallbackChooser).execute(chatId = chatId, callbackQuery = callbackQuery) }
            .first()
    }
}
