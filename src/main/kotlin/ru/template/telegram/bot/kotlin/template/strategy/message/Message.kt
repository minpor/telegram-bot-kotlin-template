package ru.template.telegram.bot.kotlin.template.strategy.message

interface Message {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun getMessage(chatId: Long): String
}
