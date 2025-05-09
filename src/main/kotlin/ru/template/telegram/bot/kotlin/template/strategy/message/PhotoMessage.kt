package ru.template.telegram.bot.kotlin.template.strategy.message

import java.io.ByteArrayInputStream
import org.springframework.stereotype.Component
import ru.template.telegram.bot.kotlin.template.component.MessageWriter
import ru.template.telegram.bot.kotlin.template.service.FileService
import ru.template.telegram.bot.kotlin.template.strategy.dto.PhotoDto

@Component
class PhotoMessage(
    messageWriter: MessageWriter,
    private val fileService: FileService
) : AbstractSendPhoto<PhotoDto>(messageWriter) {

    override fun file(data: PhotoDto?): ByteArrayInputStream? {
        return data?.url?.let {
            fileService.getFileFromUrl(data.url)
        }
    }

}