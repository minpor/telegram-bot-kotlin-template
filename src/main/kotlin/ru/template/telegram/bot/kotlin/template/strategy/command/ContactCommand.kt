package ru.template.telegram.bot.kotlin.template.strategy.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.enums.CommandCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class ContactCommand(
    usersRepository: UsersRepository,
    applicationEventPublisher: ApplicationEventPublisher
) : AbstractCommand(CommandCode.CONTACT, usersRepository, applicationEventPublisher)