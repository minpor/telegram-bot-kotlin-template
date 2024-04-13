package ru.template.telegram.bot.kotlin.template.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class FewTextChooser(private val usersRepository: UsersRepository) : MessageChooser {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return usersRepository.getUser(chatId)!!.stepCode == StepCode.USER_INFO.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun execute(chatId: Long, message: Message) {
        usersRepository.updateText(chatId, message.text)
    }
}