package ru.template.telegram.bot.kotlin.template.strategy.data

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.enums.StepCode
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel
import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

@Repository
abstract class AbstractRepository<T: DataModel> {

    protected lateinit var dslContext: DSLContext

    abstract fun getData(chatId: Long): T

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return this.currentStepCode("Repository") == stepCode
    }
}