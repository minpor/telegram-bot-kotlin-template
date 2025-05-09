package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

@Component
class StartMessage(messageWriter: MessageWriter) : AbstractSendMessage<DataModel>(messageWriter)