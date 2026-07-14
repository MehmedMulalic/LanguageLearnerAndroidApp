package com.mmulalic.languagelearner.notifications

import org.junit.Assert.*
import org.junit.Test

class ReminderSchedulerTest {
    @Test
    fun `next trigger time is in the future`() {
        val result = ReminderScheduler.getNextTriggerTime(8, 0)
        assertTrue(result > System.currentTimeMillis())
    }

    @Test
    fun `next trigger time is at most 24 hours away`() {
        val result = ReminderScheduler.getNextTriggerTime(8, 0)
        val maxExpected = System.currentTimeMillis() + 24 * 60 * 60 * 1000
        assertTrue(result <= maxExpected)
    }
}