package ru.template.telegram.bot.kotlin.template.strategy.message

import java.io.ByteArrayInputStream
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

abstract class SendPhoto<T: DataModel>(messageWriter: MessageWriter): SendMessage<T>(messageWriter) {

    abstract fun file(data: T?): ByteArrayInputStream?

}