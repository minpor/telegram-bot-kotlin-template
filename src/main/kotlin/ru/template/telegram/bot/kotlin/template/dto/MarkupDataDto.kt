package ru.template.telegram.bot.kotlin.template.dto

/** Модель для InlineKeyboardMarkup() */
data class MarkupDataDto(
    /** Номер позиции кнопки под сообщением (Начиная с 0) **/
    val rowPos: Int = 0,
    /** Текст кнопки */
    val text: String
)
