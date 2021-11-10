package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.stepper.ChooseNextStep

@Component
class NextStepContext(private val chooseNextStep: List<ChooseNextStep>) {

    fun next(chatId: Long, stepCode: StepCode): StepCode? {
        return chooseNextStep.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getNextStep(chatId)
    }

}
