package ru.template.telegram.bot.kotlin.template.strategy.command

import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.chat.Chat
import org.telegram.telegrambots.meta.generics.TelegramClient

interface BotCommands {

    fun prepare(user: User, chat: Chat, arguments: Array<out String>) = Unit

    fun execute(telegramClient: TelegramClient, user: User, chat: Chat, arguments: Array<out String>)
}