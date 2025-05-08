package ru.template.telegram.bot.kotlin.template.strategy.dto

data class PhotoButtonDto(
    val chatId: Long,
    val url: String,
    val buttons: List<String>
): DataModel