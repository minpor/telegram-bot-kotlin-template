package ru.template.telegram.bot.kotlin.template.strategy.message

import java.io.ByteArrayInputStream
import ru.template.telegram.bot.kotlin.template.strategy.dto.DataModel

interface Photo<T: DataModel>: Button<T> {

    fun file(data: T?): ByteArrayInputStream?

}