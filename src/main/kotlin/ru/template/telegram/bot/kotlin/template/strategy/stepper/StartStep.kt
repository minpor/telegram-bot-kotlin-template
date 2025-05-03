package ru.template.telegram.bot.kotlin.template.strategy.stepper

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class StartStep : Step {

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.USER_INFO
    }

}