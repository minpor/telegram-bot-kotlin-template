package ru.template.telegram.bot.kotlin.template.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import ru.template.telegram.bot.kotlin.template.enums.ExecuteStatus
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class AcceptChooser(private val usersRepository: UsersRepository) : CallbackChooser {

    override fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        usersRepository.updateAccept(chatId, callbackQuery.data)
        return ExecuteStatus.FINAL
    }

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.BUTTON_REQUEST.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}