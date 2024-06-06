package com.example.cw2

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [League::class,LeagueTwo::class], version = 1)
abstract class CW2Database : RoomDatabase() {
    abstract fun fLeagueDao(): DAO

    abstract fun fLeagueDao2(): DAO2


}