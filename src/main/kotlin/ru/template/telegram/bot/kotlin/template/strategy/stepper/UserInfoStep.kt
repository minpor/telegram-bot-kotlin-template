package ru.template.telegram.bot.kotlin.template.strategy.stepper

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class UserInfoStep : Step {

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.BUTTON_REQUEST
    }

}