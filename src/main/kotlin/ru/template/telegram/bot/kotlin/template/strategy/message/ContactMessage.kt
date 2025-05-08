package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.ReplyMarkupDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.dto.ContactDto

@Component
class ContactMessage(private val messageWriter: MessageWriter) : Message<ContactDto> {

    override fun message(chatId: Long, data: ContactDto?): String {
        return messageWriter.process(StepCode.CONTACT)
    }

    override fun inlineButtons(
        chatId: Long,
        data: ContactDto?
    ): List<MarkupDataDto> {
        return emptyList()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun replyButton(
        chatId: Long,
        data: ContactDto?
    ): List<ReplyMarkupDto> {
        return listOf(ReplyMarkupDto(text = "Предоставить контакт", requestContact = true))
    }
}