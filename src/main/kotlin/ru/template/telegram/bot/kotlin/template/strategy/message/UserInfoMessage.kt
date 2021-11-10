package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.UserInfoDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class UserInfoMessage(
    private val usersRepository: UsersRepository,
    private val messageWriter: MessageWriter
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.USER_INFO.toString()
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.USER_INFO, UserInfoDto(chatId))
    }
}