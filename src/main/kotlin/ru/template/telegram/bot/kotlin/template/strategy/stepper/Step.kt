package ru.template.telegram.bot.kotlin.template.strategy.stepper

import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

interface Step {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.currentStepCode( "Step") == stepCode
    }

    fun getNextStep(chatId: Long): StepCode?
}
