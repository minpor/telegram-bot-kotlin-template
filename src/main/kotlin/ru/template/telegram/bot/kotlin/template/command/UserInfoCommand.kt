package ru.template.telegram.bot.kotlin.template.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import ru.template.telegram.bot.kotlin.template.enums.CommandCode
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class UserInfoCommand(
    private val usersRepository: UsersRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : TelegramCommand(CommandCode.USER_INFO.command, CommandCode.USER_INFO.desc) {

    companion object {
        private val USER_INFO = StepCode.USER_INFO
    }

    override fun execute(chat: Chat) {
        val chatId = chat.id

        usersRepository.updateUserStep(chatId, USER_INFO)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = USER_INFO)
        )
    }

}
