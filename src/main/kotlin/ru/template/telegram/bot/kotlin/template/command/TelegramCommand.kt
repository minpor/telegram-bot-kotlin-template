package ru.template.telegram.bot.kotlin.template.command

import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

abstract class TelegramCommand(name: String, desc: String): BotCommand(name, desc) {
    abstract fun execute(chat: Chat)
}