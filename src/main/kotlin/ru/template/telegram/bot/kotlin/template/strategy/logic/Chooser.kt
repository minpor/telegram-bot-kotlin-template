package ru.template.telegram.bot.kotlin.template.strategy.logic

import ru.template.telegram.bot.kotlin.template.enums.StepCode

interface Chooser {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun isPermitted(chatId: Long): Boolean

}
