package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.strategy.dto.UserInfoDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Component
class UserInfoMessage(
    private val messageWriter: MessageWriter
) : Message<DataModel> {

    override fun message(chatId: Long, data: DataModel?): String {
        return messageWriter.process(StepCode.USER_INFO, UserInfoDto(chatId))
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}