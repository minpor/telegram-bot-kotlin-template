package ru.template.telegram.bot.kotlin.template.strategy.message

import ru.template.telegram.bot.kotlin.template.enums.StepCode

interface Message {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun getMessage(chatId: Long): String
}
