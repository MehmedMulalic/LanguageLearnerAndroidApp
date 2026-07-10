package com.mmulalic.languagelearner.data.repository

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class SessionManagerTest {
    @Test
    fun notifySessionExpired() = runTest {
        val fakeSessionManager = SessionManager()

        fakeSessionManager.sessionEvents.test {
            fakeSessionManager.notifySessionExpired()

            assertEquals(SessionEvent.Expired, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }
}