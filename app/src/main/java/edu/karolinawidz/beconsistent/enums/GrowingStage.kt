package edu.karolinawidz.beconsistent.enums

import edu.karolinawidz.beconsistent.R

enum class GrowingStage(val streak: IntRange, val drawableId: Int) {
    STAGE_1(0..4, R.drawable.plant1),
    STAGE_2(5..9, R.drawable.plant2),
    STAGE_3(10..14, R.drawable.plant3),
    STAGE_4(15..20, R.drawable.plant4),
    STAGE_5(21..Int.MAX_VALUE, R.drawable.plant5),
    STAGE_ERROR(Int.MIN_VALUE..-1, R.drawable.plant_error);

    companion object {
        fun matchStreak(currentStreak: Int): GrowingStage {
            return when (currentStreak) {
                in STAGE_1.streak -> STAGE_1
                in STAGE_2.streak -> STAGE_2
                in STAGE_3.streak -> STAGE_3
                in STAGE_4.streak -> STAGE_4
                in STAGE_5.streak -> STAGE_5
                else -> STAGE_ERROR
            }
        }
    }
}
