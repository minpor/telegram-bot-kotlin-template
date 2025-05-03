package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.stepper.Step

@Component
class StepContext(private val step: List<Step>) {

    fun next(chatId: Long, stepCode: StepCode): StepCode? {
        return step.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getNextStep(chatId)
    }

}
