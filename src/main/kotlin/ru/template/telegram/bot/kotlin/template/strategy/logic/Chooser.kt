package ru.template.telegram.bot.kotlin.template.strategy.logic

import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.checkCurrentStep

interface Chooser {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.checkCurrentStep(stepCode, "Chooser")
    }

    fun isPermitted(chatId: Long): Boolean

}
