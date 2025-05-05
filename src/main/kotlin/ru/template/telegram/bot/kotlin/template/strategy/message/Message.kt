package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

@Component
interface Message<T: DataModel?> {

    fun classStepCode() = this.currentStepCode("Message")

    fun message(chatId: Long, data: T? = null): String

    fun isPermitted(chatId: Long): Boolean
}
