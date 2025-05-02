package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.UserInfoDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class UserInfoMessage(
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.USER_INFO
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.USER_INFO, UserInfoDto(chatId))
    }
}