package ru.template.telegram.bot.kotlin.template.strategy.dto

data class ButtonResponseDto(
    val chatId: Long, val text: String?, val accept: String?
): DataModel