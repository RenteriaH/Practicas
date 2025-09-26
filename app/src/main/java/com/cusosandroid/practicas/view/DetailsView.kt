package com.cusosandroid.practicas.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.components.ActionButton
import com.cusosandroid.practicas.components.TitleBar


@Composable
fun DetailsView(navController: NavHostController, team: String, division: String) {

    val scrollState = rememberScrollState()

    // 🔵 Fondo dinámico por equipo
    val backgroundColor = when (team) {
        "Los Angeles Lakers" -> Color(0xFF552583) // púrpura Lakers
        "New York Knicks" -> Color(0xFF006BB6)    // azul Knicks
        else -> Color(0xFF121212)                 // default oscuro
    }

    // Colores según equipo
    val teamColor = when (team) {
        "Los Angeles Lakers" -> Color(0xFFFFD700) // amarillo dorado
        "New York Knicks" -> Color(0xFF1D428A)   // azul Knicks
        else -> Color(0xFF444444)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)  // ← dinámico por equipo
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título principal
        TitleBar("$team - $division")

        Spacer(modifier = Modifier.height(20.dp))

        // Información dentro de un cuadro de color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(teamColor.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {

            val info = when ("$team-$division") {
                "Los Angeles Lakers-Pacifico" -> """
                Conferencia Oeste

                🏀
                Equipos: Los Angeles Lakers, Los Angeles Clippers, Golden State Warriors, Phoenix Suns, Sacramento Kings

                Historia:
                Los Lakers fueron fundados en 1947 como los Minneapolis Lakers y se mudaron a Los Ángeles en 1960.
                Han ganado 17 campeonatos de la NBA, empatando con los Boston Celtics como la franquicia más exitosa en la historia de la liga.
                La era "Showtime" en los años 80, liderada por Magic Johnson, Kareem Abdul-Jabbar y James Worthy, marcó una época dorada para el equipo.
                En la década de 2000, con Shaquille O'Neal y Kobe Bryant, los Lakers dominaron la liga, ganando tres campeonatos consecutivos (2000-2002).
                En 2020, con LeBron James y Anthony Davis, el equipo ganó su último campeonato en la burbuja de Orlando.

                Rivalidades:
                Los Angeles Clippers, Boston Celtics, Golden State Warriors

                Estadísticas recientes:
                Temporada 2024-25: 50 victorias y 32 derrotas, eliminados en la primera ronda de playoffs.
                Temporada 2023-24: 47 victorias y 35 derrotas, llegaron a las finales de la Conferencia Oeste.

                Contexto actual:
                Los Lakers continúan siendo contendientes en la Conferencia Oeste, con una mezcla de veteranos y jóvenes talentos.
                La franquicia ha experimentado una transición en la propiedad, con la familia Buss vendiendo una participación mayoritaria al empresario Mark Walter por 10.000 millones de dólares en 2025.
            """.trimIndent()

                "Los Angeles Lakers-Noroeste" -> """
                División Noroeste

                Equipos: Denver Nuggets, Minnesota Timberwolves, Oklahoma City Thunder, Portland Trail Blazers, Utah Jazz

                Historia:
                Esta división ha sido históricamente menos competitiva que la División Pacífico, pero equipos como los Nuggets y los Thunder han sido contendientes recientes.

                Rivalidades:
                Los Lakers han tenido enfrentamientos clave con los Nuggets y los Thunder en los últimos años, especialmente en las finales de la Conferencia Oeste.

                Estadísticas recientes:
                Los Nuggets ganaron el campeonato de la NBA en 2023.
                Los Thunder, con jugadores como Shai Gilgeous-Alexander, han sido un equipo en ascenso en la Conferencia Oeste.
            """.trimIndent()

                "Los Angeles Lakers-Suroeste" -> """
                 Conferencia Oeste

                🏀
	        	Equipos: Dallas Mavericks, Houston Rockets, Memphis Grizzlies, New Orleans Pelicans, San Antonio Spurs
		        Historia:
		        Los Spurs dominaron la división durante dos décadas bajo la dirección de Tim Duncan, Tony Parker y Manu Ginóbili.
    	        Los Mavericks ganaron el campeonato en 2011, liderados por Dirk Nowitzki.
		        Rivalidades:
		        Los Lakers han tenido enfrentamientos significativos con los Mavericks y los Grizzlies en los playoffs recientes.
		        Estadísticas recientes:
		        Los Grizzlies han sido un equipo joven prometedor en los últimos años, con Ja Morant como su estrella principal.
		        Los Mavericks continúan siendo contendientes con Luka Dončić liderando el equipo.

            """.trimIndent()

                "Los Angeles Lakers-Atlantico" -> """
                Conferencia Este
                
                🏀
                La División Atlántico está compuesta por los New York Knicks, Boston Celtics, Philadelphia 76ers, Brooklyn Nets y Toronto Raptors.  
                Es una de las divisiones más históricas de la NBA, con una gran tradición de títulos y rivalidades intensas.  

                Boston Celtics: Máximos campeones de la NBA junto con Lakers (17 títulos), dominando varias épocas.  
                Philadelphia 76ers: Con Julius Erving, Allen Iverson y Joel Embiid han tenido momentos legendarios.  
                Brooklyn Nets: Recientemente formaron un superteam con Durant, Harden e Irving.  
                Toronto Raptors: Campeones en 2019 con Kawhi Leonard.  
                Knicks: equipo fundacional con gran peso histórico.  

                Aunque los Lakers no pertenecen a esta división, sus duelos contra equipos del Atlántico, en especial los Celtics, han marcado la historia de la NBA en Finales legendarias. 

                
            """.trimIndent()

                "Los Angeles Lakers-Central" -> """
                Conferencia Este
                
                🏀
                La División Central está integrada por: Chicago Bulls, Cleveland Cavaliers, Detroit Pistons, Indiana Pacers y Milwaukee Bucks.  
                Ha sido escenario de equipos con épocas dominantes y jugadores históricos.  

                Chicago Bulls: Dominio absoluto en los 90 con Michael Jordan y Scottie Pippen (6 títulos).  
                Cleveland Cavaliers: Campeones en 2016 liderados por LeBron James.  
                Detroit Pistons: "Bad Boys" en los 80 y campeones de 2004 con un equipo muy defensivo.  
                Indiana Pacers: Rivalidad histórica contra los Knicks y años dorados con Reggie Miller.  
                Milwaukee Bucks: actuales campeones (2021) con Giannis Antetokounmpo.  

                Los Lakers han tenido duelos memorables contra estos equipos en Finales de la NBA, como ante Bulls de Jordan y Pistons de los 80 y 2004.  


             """.trimIndent()

                "Los Angeles Lakers-Sudeste" -> """
                 Conferencia Este
                 
                 🏀
                Equipos: Miami Heat, Orlando Magic, Atlanta Hawks, Washington Wizards y Charlotte Hornets.  

                Miami Heat: Una de las franquicias más exitosas de los últimos 20 años (3 campeonatos). Fueron rivales fuertes en la era de LeBron con los Lakers.  
                Orlando Magic: Recordados por la dupla Shaquille O’Neal y Penny Hardaway en los 90, y Dwight Howard en los 2000.  
                Atlanta Hawks: equipo histórico con buena base de fanáticos, figuras recientes como Trae Young.  
                Washington Wizards: conocidos por Michael Jordan en su regreso y la dupla Arenas-Jamison.  
                Charlotte Hornets: franquicia de Michael Jordan como dueño, equipo joven con LaMelo Ball.  

                Los Lakers suelen enfrentarse a estos equipos en temporada regular, pero los Heat han sido su rival más grande del Sudeste, especialmente en las Finales 2020.  


            """.trimIndent()

                "New York Knicks-Pacifico" -> """
                Conferencia Oeste 
                
                🏀
                Equipos: Los Angeles Lakers, Los Angeles Clippers, Golden State Warriors, Phoenix Suns y Sacramento Kings.  

                Es una de las divisiones más poderosas de la NBA por historia y por la actualidad:  
                Lakers: 17 campeonatos y una de las franquicias más importantes del deporte.  
                Warriors: dinastía reciente con Curry, 4 anillos entre 2015 y 2022.  
                Clippers: ascenso en los últimos años con Kawhi Leonard y Paul George.  
                Suns: Finales de 2021 y grandes figuras históricas como Steve Nash y Charles Barkley.  
                Kings: renacieron en 2023 con De’Aaron Fox y Domantas Sabonis tras 16 años fuera de playoffs.  

                Los Knicks, aunque son de la Conferencia Este, se cruzan con estos equipos en temporada regular y han protagonizado choques llamativos contra Lakers y Warriors.  


            """.trimIndent()

                "New York Knicks-Noroeste" -> """
                Conferencia Oeste 

                🏀
                Equipos: Denver Nuggets, Utah Jazz, Portland Trail Blazers, Minnesota Timberwolves y Oklahoma City Thunder.  

                Denver Nuggets: campeones en 2023 con Nikola Jokić, actual referente de la NBA.  
                Utah Jazz: potencia de los 90 con Stockton y Malone, grandes duelos contra Bulls.  
                Portland Trail Blazers: recordados por Clyde Drexler y más tarde Damian Lillard.  
                Minnesota Timberwolves: mejores años con Kevin Garnett, ahora liderados por Anthony Edwards.  
                Oklahoma City Thunder: finalistas en 2012 con Durant, Westbrook y Harden.  

                Knicks no forman parte de esta división, pero han enfrentado históricamente a varios equipos, especialmente a Denver y Portland, en intensos partidos de temporada regular.  
             
             """.trimIndent()

                "New York Knicks-Suroeste" -> """"
                Conferencia Oeste 
                
                🏀
                Equipos: San Antonio Spurs, Dallas Mavericks, Houston Rockets, Memphis Grizzlies y New Orleans Pelicans.  

                Spurs: una de las dinastías más grandes, 5 campeonatos entre 1999 y 2014 con Tim Duncan, Parker y Ginóbili.  
                Mavericks: campeones en 2011 con Dirk Nowitzki; hoy liderados por Luka Dončić.  
                Rockets: 2 campeonatos con Olajuwon (1994 y 1995), más tarde con Harden fueron potencia.  
                Grizzlies: estilo físico en los 2010 con Marc Gasol y Tony Allen, actualmente con Ja Morant.  
                Pelicans: equipo joven con Zion Williamson e Ingram como referentes.  

                Los Knicks han enfrentado a los Spurs en Finales de 1999 (donde San Antonio ganó su primer título), y han jugado partidos memorables contra Rockets y Mavericks.  

            """.trimIndent()

                "New York Knicks-Atlántico" -> """
                Conferencia Este
                
                🏀
                Equipos: New York Knicks, Boston Celtics, Brooklyn Nets, Philadelphia 76ers, Toronto Raptors

                Historia:
                Los Knicks fueron fundados en 1946 y son una de las franquicias más antiguas de la NBA.
                Han ganado 2 campeonatos de la NBA, en 1970 y 1973, bajo la dirección del entrenador Red Holzman y con jugadores como Willis Reed y Walt Frazier.

                Rivalidades:
                Boston Celtics, Brooklyn Nets, Philadelphia 76ers

                Estadísticas recientes:
                Temporada 2024-25: 51 victorias y 31 derrotas, llegaron a las finales de la Conferencia Este antes de ser eliminados por los Indiana Pacers.

                Contexto actual:
                Los Knicks han mostrado mejoras en las últimas temporadas, con jugadores como Jalen Brunson y Julius Randle liderando el equipo.
            """.trimIndent()

                "New York Knicks-Central" -> """
                 Conferencia Este

                🏀
	       	    Equipos: Chicago Bulls, Cleveland Cavaliers, Detroit Pistons, Indiana Pacers, Milwaukee Bucks
		        Historia:
  	            Los Bulls dominaron la división en la década de 1990 con Michael Jordan, ganando seis campeonatos.
		        Los Bucks ganaron el campeonato en 2021, liderados por Giannis Antetokounmpo.
	         	Rivalidades:
 	            Los Knicks han tenido enfrentamientos clave con los Pacers en los playoffs, especialmente en 2025 cuando llegaron a las finales de la Conferencia Este.
	         	Estadísticas recientes:
	         	Los Pacers han sido un equipo competitivo en la Conferencia Este en los últimos años, con jugadores como Tyrese Haliburton.
	        	Los Bucks continúan siendo contendientes con Giannis como su estrella principal.


            """.trimIndent()
                "New York Knicks-Sudeste" -> """             
                Conferencia Este

                🏀
	        	Equipos: Atlanta Hawks, Charlotte Hornets, Miami Heat, Orlando Magic, Washington Wizards
	        	Historia:
	        	Los Heat han sido el equipo más exitoso de la división en las últimas dos décadas, ganando tres campeonatos (2006, 2012, 2013).
	        	Rivalidades:
	        	Los Knicks han tenido enfrentamientos significativos con los Heat en los playoffs, especialmente en la década de 1990.
	        	Estadísticas recientes:
	        	Los Hawks, con Trae Young, han sido un equipo competitivo en la Conferencia Este.
	        	Los Wizards han estado en un proceso de reconstrucción en los últimos años.

            """.trimIndent()


                else -> "No hay información disponible."
            }
           // Column {
           //     Text(
              //      text = "Equipo: $team\nDivisión: $division",
              //      fontSize = 24.sp,
          //          color = Color.Black
        //        )

                Spacer(modifier = Modifier.height(28.dp))

                // Texto con info
                Text(
                    text = info,
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
                // Botón estilizado para volver
                //  Button(
                //    onClick = { navController.popBackStack() },
                //  colors = ButtonDefaults.buttonColors(containerColor = teamColor),
                //shape = RoundedCornerShape(10.dp),
                //modifier = Modifier
                //  .fillMaxWidth(0.5f) // botón más pequeño y centrado
                // .height(50.dp)
                //) {
                //  Text(
                //    text = "Volver",
                //  color = Color.White,
                //fontSize = 18.sp,
                //fontWeight = FontWeight.Bold
                //)
                //}
            //}
        }
    }
}