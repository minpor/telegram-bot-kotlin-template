package ru.template.telegram.bot.kotlin.template.strategy.message

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.strategy.dto.UserInfoDto

@Component
class UserInfoMessage(messageWriter: MessageWriter) : SendMessage<UserInfoDto>(messageWriter)