package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.ButtonResponseDto
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class ButtonResponseMessage(
    private val usersRepository: UsersRepository,
    private val messageWriter: MessageWriter
) : Message<DataModel> {

    override fun message(chatId: Long, data: DataModel?): String {
        val user = usersRepository.getUser(chatId)
        return messageWriter.process(
            StepCode.BUTTON_RESPONSE,
            ButtonResponseDto(chatId = chatId, text = user?.text, accept = user?.accept)
        )
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}