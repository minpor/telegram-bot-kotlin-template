package ru.template.telegram.bot.kotlin.template.strategy.message

import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel

interface Button<T: DataModel>: Message<T> {

    fun inlineButtons(chatId: Long, data: T?): List<MarkupDataDto>

    fun getData(chatId: Long): T?
}