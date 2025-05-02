package ru.template.telegram.bot.kotlin.template.strategy.stepper

import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.checkCurrentStep

interface ChooseNextStep {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.checkCurrentStep(stepCode, "Step")
    }

    fun getNextStep(chatId: Long): StepCode?
}
