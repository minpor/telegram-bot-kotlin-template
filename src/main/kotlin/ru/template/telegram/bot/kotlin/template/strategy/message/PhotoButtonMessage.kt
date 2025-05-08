package ru.template.telegram.bot.kotlin.template.strategy.message

import java.io.ByteArrayInputStream
import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.service.FileService
import ru.template.telegram.bot.kotlin.template.strategy.dto.PhotoButtonDto

@Component
class PhotoButtonMessage(
    private val messageWriter: MessageWriter,
    private val fileService: FileService
) : Photo<PhotoButtonDto> {

    override fun message(chatId: Long, data: PhotoButtonDto?): String {
        return messageWriter.process(StepCode.PHOTO_BUTTON, data)
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun file(data: PhotoButtonDto?): ByteArrayInputStream? {
        return data?.url?.let { fileService.getFileFromUrl(it) }
    }

    override fun inlineButtons(
        chatId: Long,
        data: PhotoButtonDto?
    ): List<MarkupDataDto> {
        return data?.buttons?.map { MarkupDataDto(0, it) } ?: emptyList()
    }
}