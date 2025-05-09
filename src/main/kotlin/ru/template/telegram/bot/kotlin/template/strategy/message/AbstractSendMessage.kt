package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.ReplyMarkupDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

@Component
abstract class AbstractSendMessage<T: DataModel?>(private val messageWriter: MessageWriter) {

    fun classStepCode() = this.currentStepCode("Message")

    fun message(data: T? = null): String = messageWriter.process(classStepCode(), data)

    fun inlineButtons(chatId: Long, data: T?): List<MarkupDataDto> = emptyList()

    fun replyButtons(chatId: Long, data: T? = null): List<ReplyMarkupDto> = emptyList()

    fun isPermitted(chatId: Long): Boolean = true
}
