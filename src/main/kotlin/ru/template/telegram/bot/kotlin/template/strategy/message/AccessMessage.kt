package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

@Component
class AccessMessage(messageWriter: MessageWriter) : AbstractSendMessage<DataModel>(messageWriter) {

    override fun message(data: DataModel?): String {
        return "" // not use, this is only for test isPermitted
    }

    override fun isPermitted(chatId: Long): Boolean {
        return false
    }
}