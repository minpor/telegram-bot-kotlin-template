package ru.template.telegram.bot.kotlin.template.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import ru.template.telegram.bot.kotlin.template.enums.CommandCode
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class StartCommand(
    private val usersRepository: UsersRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : TelegramCommand(CommandCode.START.command, CommandCode.START.desc) {

    companion object {
        private val START_CODE = StepCode.START
    }

    override fun execute(chat: Chat) {
        val chatId = chat.id

        if (usersRepository.isUserExist(chatId)) {
            usersRepository.updateUserStep(chatId, START_CODE)
        } else usersRepository.createUser(chatId)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = START_CODE)
        )
    }


}
