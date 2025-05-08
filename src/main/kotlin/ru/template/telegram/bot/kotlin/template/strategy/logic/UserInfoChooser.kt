package ru.template.telegram.bot.kotlin.template.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class UserInfoChooser(private val usersRepository: UsersRepository) : MessageChooser {

    override fun execute(chatId: Long, message: Message) {
        usersRepository.updateText(chatId, message.text)
    }
}