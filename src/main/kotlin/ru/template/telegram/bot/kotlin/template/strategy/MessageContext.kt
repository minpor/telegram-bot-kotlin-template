package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.InlineKeyboardMarkupDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.data.AbstractRepository
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.strategy.message.Button
import ru.template.telegram.bot.kotlin.template.strategy.message.Message
import ru.template.telegram.bot.kotlin.template.strategy.message.Photo

@Component
class MessageContext<T : DataModel>(
    private val message: Map<StepCode, Message<T>>,
    private val button: Map<StepCode, Button<T>>,
    private val photo: Map<StepCode, Photo<T>>,
    private val abstractRepository: List<AbstractRepository<T>>
) {

    fun getMessage(chatId: Long, stepCode: StepCode): String {
        return message[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
            ?.message(chatId, getData(chatId, stepCode)) ?: "Доступ запрещён"
    }

    fun getPhotoMessage(chatId: Long, stepCode: StepCode): InlineKeyboardMarkupDto? {
        return photo[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
            ?.let {
                val data = getData(chatId, stepCode)
                InlineKeyboardMarkupDto(
                    message = it.message(chatId, data),
                    inlineButtons = it.inlineButtons(chatId, data),
                    file = it.file(data) ?: throw IllegalArgumentException("file data is empty")
                )
            }
    }

    fun getInlineKeyboardMarkupDto(
        chatId: Long,
        stepCode: StepCode
    ): InlineKeyboardMarkupDto? {
        return button[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
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
