package ru.template.telegram.bot.kotlin.template.strategy.logic

interface Chooser {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun isPermitted(chatId: Long): Boolean

}
