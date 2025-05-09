package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.strategy.dto.ButtonResponseDto

@Component
class ButtonResponseMessage(messageWriter: MessageWriter) : AbstractSendMessage<ButtonResponseDto>(messageWriter)