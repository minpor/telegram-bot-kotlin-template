package ru.template.telegram.bot.kotlin.template.command

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Chat
import ru.template.telegram.bot.kotlin.template.enums.CommandCode
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.event.TelegramStepMessageEvent
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class ButtonRequestCommand(
    private val usersRepository: UsersRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : TelegramCommand(CommandCode.BUTTON.command, CommandCode.BUTTON.desc) {

    companion object {
        private val BUTTON_REQUEST = StepCode.BUTTON_REQUEST
    }

    override fun execute(chat: Chat) {
        val chatId = chat.id

        usersRepository.updateUserStep(chatId, BUTTON_REQUEST)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = BUTTON_REQUEST)
        )
    }

}
