package ru.template.telegram.bot.kotlin.template.strategy.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.CommandCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class UserInfoCommand(
    usersRepository: UsersRepository,
    applicationEventPublisher: ApplicationEventPublisher
) : AbstractCommand(CommandCode.USER_INFO, usersRepository, applicationEventPublisher)
