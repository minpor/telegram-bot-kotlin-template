package ru.template.telegram.bot.kotlin.template.strategy.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.chat.Chat
import ru.template.telegram.bot.kotlin.template.enums.CommandCode
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class StartCommand(
    private val usersRepository: UsersRepository,
    applicationEventPublisher: ApplicationEventPublisher
) : AbstractCommand(CommandCode.START, usersRepository, applicationEventPublisher) {

    override fun prepare(user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id
        if (usersRepository.isUserExist(chatId)) {
            usersRepository.updateUserStep(chatId, StepCode.START)
        } else usersRepository.createUser(chatId)
    }


}
