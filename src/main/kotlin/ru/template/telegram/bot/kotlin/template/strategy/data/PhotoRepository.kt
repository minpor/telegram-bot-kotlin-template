package ru.template.telegram.bot.kotlin.template.strategy.data

import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.strategy.dto.PhotoDto

@Repository
class PhotoRepository: AbstractRepository<PhotoDto>() {

    override fun getData(chatId: Long): PhotoDto {
        return PhotoDto(chatId, "https://api.dub.co/qr?url=google.com")
    }
}