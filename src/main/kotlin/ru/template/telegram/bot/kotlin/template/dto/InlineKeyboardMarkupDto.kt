package ru.template.telegram.bot.kotlin.template.dto

data class InlineKeyboardMarkupDto(
    /** Сообщение */
    val message: String,
    /** Кнопки под сообщением */
    val inlineButtons: List<MarkupDataDto>
)
