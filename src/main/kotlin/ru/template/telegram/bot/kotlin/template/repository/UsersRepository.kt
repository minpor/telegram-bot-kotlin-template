package ru.template.telegram.bot.kotlin.template.repository

import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import ru.template.telegram.bot.kotlin.template.domain.tables.pojos.Users
import ru.template.telegram.bot.kotlin.template.domain.tables.references.USERS
import ru.template.telegram.bot.kotlin.template.enums.StepCode

@Repository
class UsersRepository(private val dslContext: DSLContext) {


    fun isUserExist(chatId: Long): Boolean {
        return dslContext.selectCount().from(USERS).where(USERS.ID.eq(chatId)).fetchOneInto(Int::class.java) == 1
    }

    fun createUser(chatId: Long): Users {
        val record = dslContext.newRecord(USERS, Users(
            id = chatId,
            stepCode = StepCode.START.toString()
        ))
        record.store()
        return record.into(Users::class.java)
    }

    fun getUser(chatId: Long) =
        dslContext.selectFrom(USERS).where(USERS.ID.eq(chatId)).fetchOneInto(Users::class.java)

    fun updateUserStep(chatId: Long, stepCode: StepCode): Users =
        dslContext.update(USERS)
            .set(USERS.STEP_CODE, stepCode.toString())
            .where(USERS.ID.eq(chatId)).returning().fetchOne()!!.into(Users::class.java)

    fun updateText(chatId: Long, text: String) {
        dslContext.update(USERS)
            .set(USERS.TEXT, text)
            .where(USERS.ID.eq(chatId)).execute()
    }

    fun updateAccept(chatId: Long, accept: String) {
        dslContext.update(USERS)
            .set(USERS.ACCEPT, accept)
            .where(USERS.ID.eq(chatId)).execute()
    }
}