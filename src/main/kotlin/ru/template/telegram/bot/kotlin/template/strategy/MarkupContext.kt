package ru.template.telegram.bot.kotlin.template.strategy


import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.InlineKeyboardMarkupDto
import ru.template.telegram.bot.kotlin.template.dto.markup.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.keyboard.InlineKeyboardMarkup

@Component
class MarkupContext<T : DataModel>(private val inlineKeyboardMarkup: List<InlineKeyboardMarkup<T>>) {

    fun getInlineKeyboardMarkupDto(
        chatId: Long,
        stepCode: StepCode
    ): InlineKeyboardMarkupDto? {
        return inlineKeyboardMarkup
            .firstOrNull { it.isAvailableForCurrentStep(stepCode) }
            ?.let {
                val data = it.getData(chatId)
                InlineKeyboardMarkupDto(
                    it.message(chatId, data),
                    it.inlineButtons(chatId, data)
                )
            }
    }
}
