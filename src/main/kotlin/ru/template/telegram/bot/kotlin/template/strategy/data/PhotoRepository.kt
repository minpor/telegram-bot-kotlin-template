package ru.template.telegram.bot.kotlin.template.strategy.data

import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.strategy.dto.PhotoDto

@Repository
class PhotoRepository: AbstractRepository<PhotoDto>() {

    override fun getData(chatId: Long): PhotoDto {
        return PhotoDto(chatId, "https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=https://google.com")
    }
}