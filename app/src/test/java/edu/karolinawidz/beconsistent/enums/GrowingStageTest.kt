package edu.karolinawidz.beconsistent.enums

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GrowingStageTest {

    @Test
    fun `when streak equal 0, return stage1`() {
        val result = GrowingStage.matchStreak(0)
        assertEquals(GrowingStage.STAGE_1, result)
    }

    @Test
    fun `when streak equal 4, return stage1`() {
        val result = GrowingStage.matchStreak(4)
        assertEquals(GrowingStage.STAGE_1, result)
    }

    @Test
    fun `when streak equal 5, return stage2`() {
        val result = GrowingStage.matchStreak(5)
        assertEquals(GrowingStage.STAGE_2, result)
    }

    @Test
    fun `when streak equal 9, return stage2`() {
        val result = GrowingStage.matchStreak(9)
        assertEquals(GrowingStage.STAGE_2, result)
    }

    @Test
    fun `when streak equal 10, return stage3`() {
        val result = GrowingStage.matchStreak(10)
        assertEquals(GrowingStage.STAGE_3, result)
    }

    @Test
    fun `when streak equal 14, return stage3`() {
        val result = GrowingStage.matchStreak(14)
        assertEquals(GrowingStage.STAGE_3, result)
    }

    @Test
    fun `when streak equal 15, return stage4`() {
        val result = GrowingStage.matchStreak(15)
        assertEquals(GrowingStage.STAGE_4, result)
    }

    @Test
    fun `when streak equal 20, return stage4`() {
        val result = GrowingStage.matchStreak(20)
        assertEquals(GrowingStage.STAGE_4, result)
    }

    @Test
    fun `when streak equal 21, return stage4`() {
        val result = GrowingStage.matchStreak(21)
        assertEquals(GrowingStage.STAGE_5, result)
    }

    @Test
    fun `when streak is negative, return stage error`() {
        val result = GrowingStage.matchStreak(-1)
        assertEquals(GrowingStage.STAGE_ERROR, result)
    }

    @Suppress("INTEGER_OVERFLOW")
    @Test
    fun `when streak is greater than int max, return stage error`() {
        val result = GrowingStage.matchStreak(Int.MAX_VALUE + 1)
        assertEquals(GrowingStage.STAGE_ERROR, result)
    }
}