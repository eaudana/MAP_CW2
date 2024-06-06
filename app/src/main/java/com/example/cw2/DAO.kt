package com.example.cw2

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DAO {
    @Query("select * from league")
    suspend fun getLeagues(): List<League>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg leagues: League)
}

@Dao
interface DAO2 {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll2(vararg leagues: LeagueTwo)


    @Query("select name,strTeamLogo from LeagueTwo where Name||strLeague like '%'||:query||'%'"  )
    suspend fun findLeagueWithImage(query:String):List<ImageAndName>


}

