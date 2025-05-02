package ru.template.telegram.bot.kotlin.template.utils

import ru.template.telegram.bot.kotlin.template.enums.StepCode

object CommonUtils {

    val REGEXP = "(?<=.)[A-Z]".toRegex()

    fun Any.checkCurrentStep(stepCode: StepCode, removeSuffix: String): Boolean {
        val stepCodeName = this
            .javaClass
            .simpleName
            .removeSuffix(removeSuffix)
            .replace(REGEXP, "_$0")
            .uppercase()

        return StepCode.valueOf(stepCodeName) == stepCode
    }
}