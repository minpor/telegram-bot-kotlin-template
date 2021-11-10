package ru.template.telegram.bot.kotlin.template.strategy.stepper

import ru.template.telegram.bot.kotlin.template.enums.StepCode

interface ChooseNextStep {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun getNextStep(chatId: Long): StepCode?
}
