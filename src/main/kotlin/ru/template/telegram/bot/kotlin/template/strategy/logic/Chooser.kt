package ru.template.telegram.bot.kotlin.template.strategy.logic

import ru.template.telegram.bot.kotlin.template.utils.CommonUtils.currentStepCode

interface Chooser {

    fun classStepCode() = this.currentStepCode("Chooser")

}
