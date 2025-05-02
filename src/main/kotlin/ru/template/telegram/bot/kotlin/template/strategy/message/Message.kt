package ru.template.telegram.bot.kotlin.template.strategy.message

import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.checkCurrentStep

interface Message {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.checkCurrentStep(stepCode, "Message")
    }

    fun getMessage(chatId: Long): String
}
