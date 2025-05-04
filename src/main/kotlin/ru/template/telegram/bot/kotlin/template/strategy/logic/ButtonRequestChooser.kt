package ru.template.telegram.bot.kotlin.template.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class ButtonRequestChooser(private val usersRepository: UsersRepository) : CallbackChooser {

    override fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        usersRepository.updateAccept(chatId, callbackQuery.data)
        return ExecuteStatus.FINAL
    }
}