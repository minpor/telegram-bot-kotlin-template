package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.InlineKeyboardMarkupDto
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.data.AbstractRepository
import ru.template.telegram.bot.kotlin.template.strategy.message.Button
import ru.template.telegram.bot.kotlin.template.strategy.message.Message

@Component
class MessageContext<T : DataModel>(
    private val message: List<Message<T>>,
    private val button: List<Button<T>>,
    private val abstractRepository: List<AbstractRepository<T>>
) {

    fun getMessage(chatId: Long, stepCode: StepCode): String {
        return message
            .filter { it.isAvailableForCurrentStep(stepCode) }
            .filter { it.isPermitted(chatId) }
            .map {
                it.message(chatId, getData(chatId,  stepCode))
            }
            .firstOrNull() ?: "Доступ запрещён"
    }

    fun getInlineKeyboardMarkupDto(
        chatId: Long,
        stepCode: StepCode
    ): InlineKeyboardMarkupDto? {
        return button
            .filter { it.isPermitted(chatId) }
            .firstOrNull { it.isAvailableForCurrentStep(stepCode) }
            ?.let {
                val data = getData(chatId, stepCode)
                InlineKeyboardMarkupDto(
                    it.message(chatId, data),
                    it.inlineButtons(chatId, data)
                )
            }
    }

    private fun getData(chatId: Long, stepCode: StepCode): T? {
        return abstractRepository.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getData(chatId)
    }
}
