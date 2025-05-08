package ru.template.telegram.bot.kotlin.template.strategy.message

import java.io.ByteArrayInputStream
import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.dto.MarkupDataDto
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.service.FileService
import ru.template.telegram.bot.kotlin.template.strategy.dto.PhotoDto

@Component
class PhotoMessage(
    private val messageWriter: MessageWriter,
    private val fileService: FileService
): Photo<PhotoDto> {

    override fun message(
        chatId: Long,
        data: PhotoDto?
    ): String {
        return messageWriter.process(StepCode.PHOTO, data)
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun file(data: PhotoDto?): ByteArrayInputStream? {
        return data?.url?.let { fileService.getFileFromUrl(data.url) }
    }

    override fun inlineButtons(
        chatId: Long,
        data: PhotoDto?
    ): List<MarkupDataDto> {
        return emptyList()
    }
}