package ru.template.telegram.bot.kotlin.template.strategy

import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.dto.MessageModelDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.data.AbstractRepository
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.strategy.message.AbstractSendMessage
import ru.template.telegram.bot.kotlin.template.strategy.message.AbstractSendPhoto

@Component
class MessageContext<T : DataModel>(
    private val sendMessages: Map<StepCode, AbstractSendMessage<T>>,
    private val sendPhotos: Map<StepCode, AbstractSendPhoto<T>>,
    private val abstractRepository: List<AbstractRepository<T>>
) {

    fun getMessage(chatId: Long, stepCode: StepCode): MessageModelDto? {
        return sendMessages[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
            ?.let {
                val data = getData(chatId, stepCode)
                MessageModelDto(
                    message = it.message(data),
                    inlineButtons = it.inlineButtons(chatId, data),
                    replyButtons = it.replyButtons(chatId, data)
                )
            }
    }

    fun getPhotoMessage(chatId: Long, stepCode: StepCode): MessageModelDto? {
        return sendPhotos[stepCode]
            ?.takeIf { it.isPermitted(chatId) }
            ?.let {
                val data = getData(chatId, stepCode)
                MessageModelDto(
                    message = it.message(data),
                    inlineButtons = it.inlineButtons(chatId, data),
                    file = it.file(data)
                )
            }
    }

    private fun getData(chatId: Long, stepCode: StepCode): T? {
        return abstractRepository.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getData(chatId)
    }
}
