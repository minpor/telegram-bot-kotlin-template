package ru.template.telegram.bot.kotlin.template.strategy.logic

import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

interface Chooser {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.currentStepCode( "Chooser") == stepCode
    }

}
