package ru.template.telegram.bot.kotlin.template.strategy.keyboard

import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode

interface InlineKeyboardMarkup<T: DataModel> {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun message(chatId: Long, data: T?): String

    fun inlineButtons(chatId: Long, data: T?): List<MarkupDataDto>

    fun getData(chatId: Long): T?
}
