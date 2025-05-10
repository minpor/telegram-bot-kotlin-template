package ru.template.telegram.bot.kotlin.template.api

import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.CommandLongPollingTelegramBot
import org.telegram.telegrambots.longpolling.BotSession
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.generics.TelegramClient
import ru.template.telegram.bot.kotlin.template.strategy.command.AbstractCommand
import ru.template.telegram.bot.kotlin.template.properties.BotProperty
import ru.template.telegram.bot.kotlin.template.service.ReceiverService


@Component
class TelegramConsumer(
    private val botProperty: BotProperty,
    private val receiverService: ReceiverService,
    commands: List<AbstractCommand>,
    telegramClient: TelegramClient
) : SpringLongPollingBot, CommandLongPollingTelegramBot(telegramClient, true, { botProperty.username }) {


    init {
        registerAll(*commands.toTypedArray())
    }

    override fun getBotToken() = botProperty.token

    override fun getUpdatesConsumer(): LongPollingUpdateConsumer {
        return this
    }

    override fun processNonCommandUpdate(update: Update) {
        receiverService.execute(update)
    }

    @AfterBotRegistration
    fun afterRegistration(botSession: BotSession) {
        println("Registered bot running state is: " + botSession.isRunning)
    }

}