package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.MessageModelDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.data.AbstractRepository
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.strategy.message.Message
import ru.template.telegram.bot.kotlin.template.strategy.message.Photo

@Component
class MessageContext<T : DataModel>(
    private val telegramMessage: Map<StepCode, Message<T>>,
    private val telegramPhoto: Map<StepCode, Photo<T>>,
    private val abstractRepository: List<AbstractRepository<T>>
) {

    fun getMessage(chatId: Long, stepCode: StepCode): MessageModelDto? {
        return telegramMessage[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
            ?.let {
                val data = getData(chatId, stepCode)
                MessageModelDto(
                    it.message(chatId, data),
                    it.inlineButtons(chatId, data)
                )
            }
    }

    fun getPhotoMessage(chatId: Long, stepCode: StepCode): MessageModelDto? {
        return telegramPhoto[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
            ?.let {
                val data = getData(chatId, stepCode)
                MessageModelDto(
                    message = it.message(chatId, data),
                    inlineButtons = it.inlineButtons(chatId, data),
                    file = it.file(data)
                )
            }
    }

    private fun getData(chatId: Long, stepCode: StepCode): T? {
        return abstractRepository.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getData(chatId)
    }
}
