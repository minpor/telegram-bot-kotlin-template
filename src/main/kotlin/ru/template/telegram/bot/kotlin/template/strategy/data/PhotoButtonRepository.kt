package ru.template.telegram.bot.kotlin.template.strategy.data

import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.strategy.dto.PhotoButtonDto

@Repository
class PhotoButtonRepository : AbstractRepository<PhotoButtonDto>() {

    override fun getData(chatId: Long): PhotoButtonDto {
        return PhotoButtonDto(
            chatId,
            "https://api.dub.co/qr?url=google.com",
            listOf("test1", "test2")
        )
    }
}