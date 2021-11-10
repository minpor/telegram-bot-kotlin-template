package ru.template.telegram.bot.kotlin.template.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.domain.Tables
import ru.template.telegram.bot.kotlin.template.domain.tables.pojos.Users
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Repository
class UsersRepository(private val dslContext: DSLContext) {

    private val users = Tables.USERS

    fun isUserExist(chatId: Long): Boolean {
        return dslContext.selectCount().from(users).where(users.ID.eq(chatId)).fetchOneInto(Int::class.java) == 1
    }

    fun createUser(chatId: Long): Users {
        val record = dslContext.newRecord(users, Users().apply {
            id = chatId
            stepCode = StepCode.START.toString()
        })
        record.store()
        return record.into(Users::class.java)
    }

    fun getUser(chatId: Long) =
        dslContext.selectFrom(users).where(users.ID.eq(chatId)).fetchOneInto(Users::class.java)

    fun updateUserStep(chatId: Long, stepCode: StepCode): Users =
        dslContext.update(users)
            .set(users.STEP_CODE, stepCode.toString())
            .where(users.ID.eq(chatId)).returning().fetchOne()!!.into(Users::class.java)

    fun updateText(chatId: Long, text: String) {
        dslContext.update(users)
            .set(users.TEXT, text)
            .where(users.ID.eq(chatId)).execute()
    }

    fun updateAccept(chatId: Long, accept: String) {
        dslContext.update(users)
            .set(users.ACCEPT, accept)
            .where(users.ID.eq(chatId)).execute()
    }
}