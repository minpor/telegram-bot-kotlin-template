package ru.template.telegram.bot.kotlin.template.event

import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.template.telegram.bot.kotlin.template.enums.StepCode

class TelegramReceivedMessageEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val message: Message
)
