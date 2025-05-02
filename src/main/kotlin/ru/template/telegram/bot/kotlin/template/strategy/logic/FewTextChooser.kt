package ru.template.telegram.bot.kotlin.template.strategy.logic

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.message.Message
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.repository.UsersRepository

@Component
class FewTextChooser(private val usersRepository: UsersRepository) : MessageChooser {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.USER_INFO
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun execute(chatId: Long, message: Message) {
        usersRepository.updateText(chatId, message.text)
    }
}