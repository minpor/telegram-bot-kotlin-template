package ru.template.telegram.bot.kotlin.template.strategy.logic

import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus

interface CallbackChooser : Chooser {
    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus
}