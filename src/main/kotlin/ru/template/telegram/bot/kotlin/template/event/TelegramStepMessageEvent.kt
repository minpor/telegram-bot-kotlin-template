package ru.template.telegram.bot.kotlin.template.event

import ru.template.telegram.bot.kotlin.template.enums.StepCode

class TelegramStepMessageEvent(
    val chatId: Long,
    val stepCode: StepCode
)
