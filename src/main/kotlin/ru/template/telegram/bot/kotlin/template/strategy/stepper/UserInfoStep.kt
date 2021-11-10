package ru.template.telegram.bot.kotlin.template.strategy.stepper

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class UserInfoStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.USER_INFO
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.BUTTON_REQUEST
    }

}