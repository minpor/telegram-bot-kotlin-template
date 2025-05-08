package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.ReplyMarkupDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

@Component
interface Message<T: DataModel?> {

    fun classStepCode() = this.currentStepCode("Message")

    fun message(chatId: Long, data: T? = null): String

    fun inlineButtons(chatId: Long, data: T?): List<MarkupDataDto>

    fun replyButton(chatId: Long, data: T? = null): List<ReplyMarkupDto> = emptyList()

    fun isPermitted(chatId: Long): Boolean
}
