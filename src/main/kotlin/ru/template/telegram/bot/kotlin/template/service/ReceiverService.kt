package ru.template.telegram.bot.kotlin.template.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedCallbackEvent
import ru.template.telegram.bot.kotlin.template.event.TelegramReceivedMessageEvent
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Service
class ReceiverService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val usersRepository: UsersRepository
) {

    fun execute(update: Update) {
        if (update.hasCallbackQuery()) {
            callbackExecute(update.callbackQuery)
        } else if (update.hasMessage()) {
            messageExecute(update.message)
        } else {
            throw IllegalStateException("Not yet supported")
        }
    }

    private fun messageExecute(message: Message) {
        val chatId = message.chatId
        val stepCode = usersRepository.getUser(chatId)!!.stepCode!!
        applicationEventPublisher.publishEvent(
            TelegramReceivedMessageEvent(
                chatId = chatId,
                stepCode = StepCode.valueOf(stepCode),
                message = message
            )
        )
    }

    private fun callbackExecute(callback: CallbackQuery) {
        val chatId = callback.from.id
        val stepCode = usersRepository.getUser(chatId)!!.stepCode!!
        applicationEventPublisher.publishEvent(
            TelegramReceivedCallbackEvent(chatId = chatId, stepCode = StepCode.valueOf(stepCode), callback = callback)
        )
    }
}