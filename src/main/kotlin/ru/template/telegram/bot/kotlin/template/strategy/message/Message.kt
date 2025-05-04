package ru.template.telegram.bot.kotlin.template.strategy.message

import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.checkCurrentStep

interface Message<T: DataModel?> {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.checkCurrentStep(stepCode, "Message")
    }

    fun message(chatId: Long, data: T? = null): String

    fun isPermitted(chatId: Long): Boolean
}
