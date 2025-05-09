package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.ReplyMarkupDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.ContactDto

@Component
class ContactMessage(messageWriter: MessageWriter): AbstractSendMessage<ContactDto>(messageWriter) {

    override fun replyButtons(
        chatId: Long,
        data: ContactDto?
    ): List<ReplyMarkupDto> {
        return listOf(ReplyMarkupDto(text = "Send contact info", requestContact = true))
    }
}