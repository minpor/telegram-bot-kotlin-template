package ru.template.telegram.bot.kotlin.template.api

import org.springframework.stereotype.Component
import org.telegram.telegrambots.longpolling.BotSession
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.Update
import ru.template.telegram.bot.kotlin.template.command.TelegramCommand
import ru.template.telegram.bot.kotlin.template.properties.BotProperty
import ru.template.telegram.bot.kotlin.template.service.ReceiverService


@Component
class TelegramConsumer(
    private val botProperty: BotProperty,
    private val commands: List<TelegramCommand>,
    private val receiverService: ReceiverService
) : SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {


    override fun getBotToken()= botProperty.token

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer {
        return this
    }

    override fun consume(update: Update) {
        commands
            .firstOrNull {
                it.command == update.message?.text
            }
            ?.execute(update.message.chat)
            ?: receiverService.execute(update)
    }

    @AfterBotRegistration
    fun afterRegistration(botSession: BotSession) {
        println("Registered bot running state is: " + botSession.isRunning)
    }

}