package com.example.cw2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class League(
    @PrimaryKey(autoGenerate = false) var idLeague: Int = 0,
    val strLeague: String,
    val strSport: String,
    val strLeagueAlternate: String
)

@Entity
data class LeagueTwo(
    @PrimaryKey(autoGenerate = false)
    var idTeam: String,
    var name: String?,
    var strTeamShort:String?,
    var strLeague: String?,
    var strAlternate: String?,
    var intFormedYear: String?,
    var idLeague: String?,
    var strStadium: String?,
    var strKeywords: String?,
    var strStadiumLocation: String?,
    var strStadiumThumb:String?,
    var intStadiumCapacity: String?,
    var strTeamJersey: String?,
    var strTeamLogo: String?,
    var strWebsite:String?
)

data class ImageAndName(
    var name:String,
    var strTeamLogo: String
)

data class JerseyAndName(
    var name:String,
    var strTeamJersey: String?
)