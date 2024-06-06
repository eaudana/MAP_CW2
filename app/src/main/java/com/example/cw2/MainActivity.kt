package com.example.cw2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.launch

lateinit var db: CW2Database
lateinit var leagueDao: DAO

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            this, CW2Database::class.java,
            "CW2Database"
        ).build()
        leagueDao = db.fLeagueDao()
        setContent {
            val context = LocalContext.current
            Surface(
                modifier = Modifier
                    .fillMaxSize()

                ) {
                Column(
                    modifier = Modifier
                        .padding(50.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val scope = rememberCoroutineScope()
                    Button(
                        onClick = {
                            scope.launch {   //Hardcoded data was added tot he first database.
                                leagueDao.insertAll(
                                    League(
                                        4328,
                                        "English Premier League",
                                        "Soccer",
                                        "Premier League, EPL"
                                    ),
                                    League(
                                        4329,
                                        "English League Championship",
                                        "Soccer",
                                        "Championship"
                                    ),
                                    League(
                                        4330,
                                        "Scottish Premier League",
                                        "Soccer",
                                        "Scottish Premiership, SPFL"
                                    ),
                                    League(
                                        4331,
                                        "German Bundesliga",
                                        "Soccer",
                                        "Bundesliga, Fußball-Bundesliga"
                                    ),
                                    League(4332, "Italian Serie A", "Soccer", "Serie A"),
                                    League(4334, "French Ligue 1", "Soccer", "Ligue 1 Conforama"),
                                    League(
                                        4335,
                                        "Spanish La Liga",
                                        "Soccer",
                                        "LaLiga Santander, La Liga"
                                    ),
                                    League(4336, "Greek Superleague Greece", "Soccer", ""),
                                    League(4337, "Dutch Eredivisie", "Soccer", "Eredivisie"),
                                    League(
                                        4338,
                                        "Belgian First Division A",
                                        "Soccer",
                                        "Jupiler Pro League"
                                    ),
                                    League(4339, "Turkish Super Lig", "Soccer", "Super Lig"),
                                    League(4340, "Danish Superliga", "Soccer", ""),
                                    League(4344, "Portuguese Primeira Liga", "Soccer", "Liga NOS"),
                                    League(
                                        4346,
                                        "American Major League Soccer",
                                        "Soccer",
                                        "MLS, Major League Soccer"
                                    ),
                                    League(
                                        4347,
                                        "Swedish Allsvenskan",
                                        "Soccer",
                                        "Fotbollsallsvenskan"
                                    ),
                                    League(4350, "Mexican Primera League", "Soccer", "Liga MX"),
                                    League(4351, "Brazilian Serie A", "Soccer", ""),
                                    League(4354, "Ukrainian Premier League", "Soccer", ""),
                                    League(
                                        4355,
                                        "Russian Football Premier League",
                                        "Soccer",
                                        "Чемпионат России по футболу"
                                    ),
                                    League(4356, "Australian A-League", "Soccer", "A-League"),
                                    League(4358, "Norwegian Eliteserien", "Soccer", "Eliteserien"),
                                    League(4359, "Chinese Super League", "Soccer", "")
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .width(100.dp)
                    ) {
                        Text("Add Leagues to DB", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = {
                            var i = Intent(
                                context, SearchForClubsByLeagues
                                ::class.java
                            )
                            context.startActivity(i)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .width(100.dp)
                    ) {
                        Text("Search for Clubs By League", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = {
                            var i = Intent(
                                context, SearchForClubs
                                ::class.java
                            )
                            context.startActivity(i)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .width(100.dp)
                    ) {
                        Text("Search for Clubs", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = {
                            var i = Intent(
                                context, JerseyRetrieval
                                ::class.java
                            )
                            context.startActivity(i)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .width(100.dp)
                    ) {
                        Text("Retrieve Jerseys", fontSize = 16.sp)
                    }
                }
            }


        }
    }
}


