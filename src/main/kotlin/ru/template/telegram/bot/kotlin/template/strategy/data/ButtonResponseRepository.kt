package ru.template.telegram.bot.kotlin.template.strategy.data

import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository
import ru.template.telegram.bot.kotlin.template.strategy.dto.ButtonResponseDto

@Repository
class ButtonResponseRepository(private val usersRepository: UsersRepository) : AbstractRepository<ButtonResponseDto>() {

    override fun getData(chatId: Long): ButtonResponseDto {
        val user = usersRepository.getUser(chatId)!!
        return ButtonResponseDto(chatId, user.text, user.accept)
    }
}