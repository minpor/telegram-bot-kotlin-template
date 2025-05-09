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
class LogicContext(
    private val telegramCallbackChooser: Map<StepCode, CallbackChooser>,
    private val telegramMessageChooser: Map<StepCode, MessageChooser>
) {

    fun execute(chatId: Long, message: Message, stepCode: StepCode) {
        telegramMessageChooser[stepCode]?.execute(chatId = chatId, message = message)
    }

    fun execute(chatId: Long, callbackQuery: CallbackQuery, stepCode: StepCode): ExecuteStatus {
        return telegramCallbackChooser[stepCode]
            ?.execute(chatId = chatId, callbackQuery = callbackQuery)
            ?: throw IllegalStateException("Callback not found")
    }
}
