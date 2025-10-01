package com.cusosandroid.practicas.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Para el ícono de retroceso
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// import androidx.compose.ui.draw.clip // Ya está implícito con shape en Background
import androidx.compose.ui.geometry.Offset // No se usa actualmente
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow // No se usa actualmente
// import androidx.compose.ui.graphics.vector.ImageVector // Icon ya lo maneja
// import androidx.compose.ui.layout.ContentScale // No hay Image aquí
// import androidx.compose.ui.res.painterResource // No hay Image aquí
import androidx.compose.ui.text.TextStyle // No se usa directamente para Text básicos
import androidx.compose.ui.text.font.FontWeight // Si usas FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview // Para el Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController // Para el Preview
// import com.cusosandroid.practicas.R // No parece usarse aquí directamente
// import com.cusosandroid.practicas.components.TitleBar // Comentado en el original

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(navController: NavHostController, team: String, division: String) { // team aquí es el nombre completo

    val scrollState = rememberScrollState()

    // --- Definición de Colores del Equipo ---
    val lakersPrimary = Color(0xFF552583)
    val lakersSecondary = Color(0xFFFFB81C)
    val lakersTextOnSecondaryDetails = Color.Black // Texto negro sobre amarillo para detalles

    val knicksPrimary = Color(0xFF006BB6)
    val knicksSecondary = Color(0xFFF58426)
    val knicksTextOnSecondaryDetails = Color.White // Texto blanco sobre naranja para detalles

    val textOnPrimary = Color.White // Para TopAppBar y texto principal sobre fondo primario

    val defaultPrimary = Color(0xFF212121) // Un gris oscuro para fondo por defecto
    val defaultSecondary = Color(0xFF424242) // Un gris medio para el cuadro por defecto
    val defaultTextOnSecondaryDetails = Color.White


    val currentTeamPrimaryColor: Color
    val currentTeamSecondaryColor: Color
    val currentTextOnSecondaryColorDetails: Color

    // Derivamos el nombre corto del equipo para la lógica de color
    val shortTeamName = when {
        team.contains("Lakers", ignoreCase = true) -> "Lakers"
        team.contains("Knicks", ignoreCase = true) -> "Knicks"
        else -> "Default"
    }

    when (shortTeamName) {
        "Lakers" -> {
            currentTeamPrimaryColor = lakersPrimary
            currentTeamSecondaryColor = lakersSecondary
            currentTextOnSecondaryColorDetails = lakersTextOnSecondaryDetails
        }
        "Knicks" -> {
            currentTeamPrimaryColor = knicksPrimary
            currentTeamSecondaryColor = knicksSecondary
            currentTextOnSecondaryColorDetails = knicksTextOnSecondaryDetails
        }
        else -> {
            currentTeamPrimaryColor = defaultPrimary
            currentTeamSecondaryColor = defaultSecondary
            currentTextOnSecondaryColorDetails = defaultTextOnSecondaryDetails
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$team - $division", color = textOnPrimary, maxLines = 1) }, // Asegurar que el título no sea muy largo
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = textOnPrimary // Color del icono
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = currentTeamPrimaryColor, // Color primario del equipo
                    titleContentColor = textOnPrimary,
                    navigationIconContentColor = textOnPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(currentTeamPrimaryColor) // Fondo de la columna con el color primario
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // El TitleBar original podría ser redundante si el título ya está en la TopAppBar.
            // Puedes decidir si mantenerlo, quitarlo, o modificarlo.
            // Por ahora, lo comentaré para evitar duplicidad de título.
            // TitleBar("$team - $division") // Comentado o eliminado

            Spacer(modifier = Modifier.height(16.dp)) // Espacio después de la TopAppBar

            // Información dentro de un cuadro de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // Usamos el color secundario con alfa para el fondo del cuadro
                    .background(currentTeamSecondaryColor.copy(alpha = 0.85f), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                // El texto dentro de este Box usará currentTextOnSecondaryColorDetails
                Text(
                    text = getTeamDivisionInfo(team, division), // Función para obtener la información
                    color = currentTextOnSecondaryColorDetails, // Color del texto sobre el fondo secundario
                    fontSize = 15.sp, // Ajusta el tamaño según necesites
                    lineHeight = 22.sp // Para mejor legibilidad del párrafo largo
                )
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espacio al final antes de que termine el scroll
        }
    }
}



fun getTeamDivisionInfo(team: String, division: String): String {
    // Convertimos el nombre completo del equipo a un nombre corto para la lógica interna si es necesario
    val shortTeamNameForLogic = when {
        team.contains("Lakers", ignoreCase = true) -> "Lakers"
        team.contains("Knicks", ignoreCase = true) -> "Knicks"
        else -> team // Si no es Lakers o Knicks, usamos el nombre completo
    }

    return when ("$shortTeamNameForLogic-$division") { // Usamos el nombre corto para el matching interno

        // --- LOS ANGELES LAKERS ---
        "Lakers-Pacifico" -> """
            LOS ANGELES LAKERS - DIVISIÓN PACÍFICO (CONFERENCIA OESTE)
            🏀
            Equipos Principales: Los Angeles Lakers, Los Angeles Clippers, Golden State Warriors, Phoenix Suns, Sacramento Kings.

            Sobre la División Pacífico:
            Considerada una de las divisiones más competitivas y glamurosas de la NBA. Hogar de múltiples campeones y algunas de las mayores estrellas de la liga. La rivalidad Lakers-Celtics trasciende las divisiones, pero dentro del Pacífico, los duelos Lakers-Warriors y Lakers-Clippers han ganado mucha tracción recientemente.

            Historia de los Lakers en la División:
            Los Lakers han sido la franquicia dominante en esta división durante gran parte de su historia.
            - Era "Showtime" (80s): Magic Johnson, Kareem Abdul-Jabbar, James Worthy. Dominio absoluto y múltiples campeonatos.
            - Era Shaq & Kobe (2000s): Tres campeonatos consecutivos, reafirmando su lugar en la cima.
            - Era LeBron & AD (2020s): Campeonato en la burbuja, manteniendo la tradición ganadora.

            Rivales Divisionales Clave:
            - Golden State Warriors: Una rivalidad que creció enormemente con la dinastía de los Warriors de Stephen Curry. Partidos de alta anotación y expectación.
            - Los Angeles Clippers: La "batalla de Los Ángeles". Aunque históricamente los Lakers han sido superiores, los Clippers se han convertido en un contendiente serio, haciendo los derbis muy emocionantes.
            - Phoenix Suns: Han tenido épocas de gran rivalidad, especialmente en playoffs, como en los 2000s con Steve Nash.
            - Sacramento Kings: Rivalidad intensa a principios de los 2000s, con series de playoffs memorables.

            Contexto Actual de la División (Ejemplo General):
            La División Pacífico sigue siendo un hervidero de talento. Los Warriors buscan mantenerse relevantes, los Clippers aspiran al título, los Suns son contendientes, y los Kings han resurgido como un equipo joven y emocionante. Los Lakers siempre están en la conversación por el campeonato.
        """.trimIndent()

        "Lakers-Noroeste" -> """
            LOS ANGELES LAKERS - ENFRENTANDO A LA DIVISIÓN NOROESTE (CONFERENCIA OESTE)
            🏀
            Equipos Principales de la Noroeste: Denver Nuggets, Minnesota Timberwolves, Oklahoma City Thunder, Portland Trail Blazers, Utah Jazz.

            Sobre la División Noroeste:
            Una división caracterizada por equipos físicos, mercados apasionados y, recientemente, hogar del actual campeón (Denver Nuggets). Ha producido equipos muy competitivos y MVP's.

            Interacciones Históricas Lakers vs. Noroeste:
            Aunque no es su división, los Lakers han tenido importantes enfrentamientos en playoffs contra equipos de la Noroeste.
            - Denver Nuggets: Múltiples series de playoffs, incluyendo Finales de Conferencia. La rivalidad se ha intensificado con el ascenso de Nikola Jokić.
            - Utah Jazz: Históricamente, duelos duros en playoffs, especialmente contra los Jazz de Stockton y Malone en los 90.
            - Portland Trail Blazers: Rivalidad geográfica en la costa oeste, con varias series de playoffs a lo largo de los años.
            - Oklahoma City Thunder: Enfrentamientos en playoffs cuando el Thunder tenía a Durant, Westbrook y Harden.

            Jugadores Destacados de la Noroeste que han enfrentado a Lakers:
            Nikola Jokić (Nuggets), Karl Malone y John Stockton (Jazz), Damian Lillard (Blazers), Kevin Durant y Russell Westbrook (cuando estaban en OKC), Anthony Edwards (Timberwolves).

            Contexto Actual de la División (Ejemplo General):
            Los Denver Nuggets son una potencia. Timberwolves y Thunder son equipos jóvenes en ascenso con mucho futuro. Jazz y Blazers están en fases de reconstrucción o buscando redefinirse.
        """.trimIndent()

        "Lakers-Suroeste" -> """
            LOS ANGELES LAKERS - ENFRENTANDO A LA DIVISIÓN SUROESTE (CONFERENCIA OESTE)
            🏀
            Equipos Principales de la Suroeste: Dallas Mavericks, Houston Rockets, Memphis Grizzlies, New Orleans Pelicans, San Antonio Spurs.

            Sobre la División Suroeste:
            Conocida por su rica historia de campeonatos (especialmente los Spurs y Rockets) y por tener algunos de los talentos internacionales más impactantes de la liga.

            Interacciones Históricas Lakers vs. Suroeste:
            Los Lakers han tenido batallas legendarias en playoffs contra equipos de esta división.
            - San Antonio Spurs: Una de las grandes rivalidades de los 2000s. Lakers y Spurs se enfrentaron múltiples veces en postemporada, definiendo a menudo al representante del Oeste en las Finales.
            - Dallas Mavericks: Duelos con Dirk Nowitzki, incluyendo una dolorosa barrida en los playoffs de 2011 para los Lakers. Ahora, con Luka Dončić, los Mavs son siempre un rival peligroso.
            - Houston Rockets: Históricamente, desde los tiempos de Kareem vs. Moses Malone, hasta Kobe vs. Yao Ming/T-Mac, y más recientemente LeBron vs. Harden.
            - Memphis Grizzlies: Una rivalidad más reciente pero intensa, especialmente en los playoffs de 2023.

            Jugadores Destacados de la Suroeste que han enfrentado a Lakers:
            Tim Duncan (Spurs), Dirk Nowitzki (Mavericks), Hakeem Olajuwon (Rockets), James Harden (Rockets), Luka Dončić (Mavericks), Ja Morant (Grizzlies).

            Contexto Actual de la División (Ejemplo General):
            Los Mavericks con Dončić son contendientes. Los Grizzlies son un equipo joven y atlético. Pelicans tienen talento pero buscan consistencia. Rockets y Spurs están en reconstrucción con jóvenes promesas.
        """.trimIndent()

        "Lakers-Atlantico" -> """
            LOS ANGELES LAKERS - ENFRENTANDO A LA DIVISIÓN ATLÁNTICO (CONFERENCIA ESTE)
            🏀
            Equipos Principales de la Atlántico: Boston Celtics, Philadelphia 76ers, New York Knicks, Brooklyn Nets, Toronto Raptors.

            Sobre la División Atlántico:
            Posiblemente la división con la rivalidad más icónica para los Lakers: los Boston Celtics. Es una división con mercados grandes y mucha historia.

            La Rivalidad Lakers-Celtics:
            Es la rivalidad por excelencia de la NBA. Han disputado el mayor número de Finales entre sí, definiendo eras enteras de la liga. Desde Russell vs. Chamberlain/West, pasando por Magic vs. Bird, hasta Kobe vs. Pierce/Garnett/Allen. Cada partido, incluso en temporada regular, tiene un peso especial.

            Otras Interacciones Lakers vs. Atlántico:
            - Philadelphia 76ers: Finales en los 80 (Lakers vs. Dr. J) y principios de los 2000 (Lakers vs. Iverson).
            - New York Knicks: Aunque en conferencias opuestas, los partidos entre estos dos equipos de mercados gigantes siempre generan interés. Finales en los 70.
            - Brooklyn Nets: Con la reciente formación de "superteams" en Brooklyn, los enfrentamientos han ganado expectación.
            - Toronto Raptors: Campeones en 2019, siempre un equipo duro, especialmente en su casa.

            Jugadores Destacados de la Atlántico que han impactado duelos vs. Lakers:
            Larry Bird, Bill Russell, Paul Pierce (Celtics), Julius Erving, Allen Iverson, Joel Embiid (76ers), Patrick Ewing (Knicks).

            Contexto Actual de la División (Ejemplo General):
            Celtics y 76ers suelen ser contendientes al título del Este. Knicks y Nets buscan consolidarse. Los Raptors son un equipo competitivo y bien dirigido.
        """.trimIndent()

        "Lakers-Central" -> """
            LOS ANGELES LAKERS - ENFRENTANDO A LA DIVISIÓN CENTRAL (CONFERENCIA ESTE)
            🏀
            Equipos Principales de la Central: Chicago Bulls, Cleveland Cavaliers, Detroit Pistons, Indiana Pacers, Milwaukee Bucks.

            Sobre la División Central:
            Hogar de la dinastía de los Bulls de Michael Jordan y de los "Bad Boys" de Detroit. Equipos conocidos por su dureza y defensa.

            Interacciones Históricas Lakers vs. Central:
            - Chicago Bulls: Las Finales de 1991 marcaron el ascenso de Michael Jordan y el fin de la era Showtime de los Lakers.
            - Detroit Pistons: Finales en 1988, 1989 (ganaron los "Bad Boys") y 2004 (ganaron los Pistons de nuevo). Duelos muy físicos.
            - Cleveland Cavaliers: La historia de LeBron James conecta a ambas franquicias. Sus enfrentamientos cuando LeBron estaba en Cleveland, y luego cuando se unió a los Lakers, siempre tuvieron morbo.
            - Milwaukee Bucks: Campeones recientes con Giannis Antetokounmpo. Han tenido duelos interesantes.
            - Indiana Pacers: Aunque menos directos en postemporada, siempre un rival respetable del Este.

            Jugadores Destacados de la Central que han tenido grandes duelos vs. Lakers:
            Michael Jordan (Bulls), Isiah Thomas (Pistons), LeBron James (cuando en Cavs), Giannis Antetokounmpo (Bucks), Reggie Miller (Pacers).

            Contexto Actual de la División (Ejemplo General):
            Los Bucks son perennes contendientes. Los Cavaliers tienen un núcleo joven y talentoso. Bulls, Pistons y Pacers están en diferentes etapas de construcción o búsqueda de identidad.
        """.trimIndent()

        "Lakers-Sudeste" -> """
            LOS ANGELES LAKERS - ENFRENTANDO A LA DIVISIÓN SUDESTE (CONFERENCIA ESTE)
            🏀
            Equipos Principales de la Sudeste: Miami Heat, Orlando Magic, Atlanta Hawks, Washington Wizards, Charlotte Hornets.

            Sobre la División Sudeste:
            Una división que ha visto campeones recientes (Miami Heat) y equipos con jóvenes estrellas emergentes.

            Interacciones Históricas Lakers vs. Sudeste:
            - Miami Heat: Las Finales de 2020 en la burbuja (Lakers ganaron). También la era del "Big Three" de Miami (LeBron, Wade, Bosh) generó partidos de alto voltaje contra los Lakers de Kobe.
            - Orlando Magic: Finales de 2009 (Lakers ganaron contra el Magic de Dwight Howard). También en los 90, el Magic de Shaq y Penny fue un rival emocionante.
            - Atlanta Hawks: Equipos competitivos en diferentes épocas, pero sin una rivalidad directa profunda en postemporada.
            - Washington Wizards: Partidos interesantes, especialmente cuando Michael Jordan jugó para los Wizards.

            Jugadores Destacados de la Sudeste que han sido relevantes vs. Lakers:
            Dwyane Wade, LeBron James (cuando en Heat), Shaquille O'Neal (cuando en Magic y Heat), Dwight Howard (cuando en Magic), Trae Young (Hawks).

            Contexto Actual de la División (Ejemplo General):
            Miami Heat siempre es un equipo duro y competitivo gracias a su cultura. Atlanta y Orlando tienen talento joven. Wizards y Hornets buscan reconstruir y encontrar estabilidad.
        """.trimIndent()

        // --- NEW YORK KNICKS ---
        "Knicks-Pacifico" -> """
            NEW YORK KNICKS - ENFRENTANDO A LA DIVISIÓN PACÍFICO (CONFERENCIA OESTE)
            🏀
            Equipos Principales de la Pacífico: Los Angeles Lakers, Los Angeles Clippers, Golden State Warriors, Phoenix Suns, Sacramento Kings.

            Sobre la División Pacífico para los Knicks:
            Enfrentar a la División Pacífico significa medirse contra algunos de los equipos más laureados y con mayor poder estelar de la liga. Aunque son de conferencias diferentes, estos partidos suelen tener mucha atención mediática.

            Duelos Knicks vs. Pacífico:
            - Los Angeles Lakers: Un choque de titanes de grandes mercados. Aunque no es una rivalidad divisional, los partidos Knicks-Lakers en el Madison Square Garden o en Los Ángeles siempre son especiales. Tienen historia en Finales (años 70).
            - Golden State Warriors: Enfrentamientos contra la dinastía reciente de Curry han sido pruebas de fuego.
            - Los Angeles Clippers: Un rival duro del Oeste.
            - Phoenix Suns & Sacramento Kings: Equipos con talento que ofrecen partidos competitivos.

            Contexto Actual de la División (Ejemplo General):
            La Pacífico es una potencia. Lakers, Warriors, Clippers y Suns suelen estar en la lucha por los playoffs y el campeonato. Los Kings han emergido como un equipo vibrante. Para los Knicks, cada partido contra estos equipos es una prueba importante.
        """.trimIndent()

        "Knicks-Noroeste" -> """
            NEW YORK KNICKS - ENFRENTANDO A LA DIVISIÓN NOROESTE (CONFERENCIA OESTE)
            🏀
            Equipos Principales de la Noroeste: Denver Nuggets, Minnesota Timberwolves, Oklahoma City Thunder, Portland Trail Blazers, Utah Jazz.

            Sobre la División Noroeste para los Knicks:
            Esta división presenta desafíos con equipos de gran altitud (Denver, Utah) y mercados con aficiones muy leales. Ha ganado prominencia con los Nuggets como campeones.

            Duelos Knicks vs. Noroeste:
            - Denver Nuggets: Carmelo Anthony, ex estrella de los Knicks, también jugó en Denver, lo que añadió un ángulo a estos duelos. Recientemente, enfrentar a Jokic es un gran reto.
            - Utah Jazz: Donovan Mitchell, nativo de Nueva York, tuvo una etapa destacada en Utah, y siempre hubo rumores sobre su posible llegada a los Knicks, lo que hacía interesantes estos partidos.
            - Portland Trail Blazers, Minnesota Timberwolves, Oklahoma City Thunder: Partidos interconferencia que sirven para medir fuerzas.

            Contexto Actual de la División (Ejemplo General):
            Los Nuggets son la élite. Timberwolves y Thunder son equipos jóvenes con un futuro brillante. Jazz y Blazers están en transición. Para los Knicks, estos partidos son oportunidades para sumar victorias importantes fuera de su conferencia.
        """.trimIndent()

        "Knicks-Suroeste" -> """
            NEW YORK KNICKS - ENFRENTANDO A LA DIVISIÓN SUROESTE (CONFERENCIA OESTE)
            🏀
            Equipos Principales de la Suroeste: Dallas Mavericks, Houston Rockets, Memphis Grizzlies, New Orleans Pelicans, San Antonio Spurs.

            Sobre la División Suroeste para los Knicks:
            Esta división ha sido hogar de algunas de las dinastías más importantes (Spurs) y de grandes estrellas internacionales.

            Duelos Knicks vs. Suroeste:
            - San Antonio Spurs: Los Knicks perdieron las Finales de 1999 contra los Spurs de Duncan y Robinson. Esa serie marcó el primer campeonato de la era dorada de San Antonio.
            - Dallas Mavericks: El ex Knick Kristaps Porziņģis jugó en Dallas, lo que generó interés en esos enfrentamientos. Ahora, enfrentar a Luka Dončić es un desafío mayúsculo.
            - Houston Rockets: Históricamente, partidos competitivos.
            - Memphis Grizzlies y New Orleans Pelicans: Equipos con jóvenes estrellas que ofrecen duelos atléticos.

            Contexto Actual de la División (Ejemplo General):
            Mavericks son un contendiente. Grizzlies buscan volver a serlo. Pelicans tienen potencial. Rockets y Spurs están reconstruyendo con talento joven.
        """.trimIndent()

        "Knicks-Atlantico" -> """
            NEW YORK KNICKS - DIVISIÓN ATLÁNTICO (CONFERENCIA ESTE - PROPIA DIVISIÓN)
            🏀
            Equipos Principales: New York Knicks, Boston Celtics, Philadelphia 76ers, Brooklyn Nets, Toronto Raptors.

            Sobre la División Atlántico para los Knicks:
            ¡Esta es la casa de los Knicks! Una de las divisiones más históricas y con mayor rivalidad de toda la NBA. Cada partido divisional tiene un significado especial y grandes implicaciones para los playoffs.

            Historia de los Knicks en la División:
            - Fundación y Primeros Años: Los Knicks son uno de los miembros fundadores de la BAA (que se convirtió en la NBA).
            - Años de Campeonato (70s): Liderados por Willis Reed, Walt Frazier, Dave DeBusschere, Bill Bradley y Earl Monroe, los Knicks ganaron dos campeonatos (1970, 1973), jugando un baloncesto de equipo ejemplar.
            - Era Ewing (90s): Con Patrick Ewing como pilar, los Knicks fueron contendientes perennes en el Este, conocidos por su defensa y tenacidad. Llegaron a las Finales en 1994 y 1999. Las batallas contra los Bulls, Pacers y Heat de esa época son legendarias.
            - Resurgimiento Reciente: Después de años de dificultades, los Knicks han vuelto a ser un equipo competitivo, devolviendo la ilusión al Madison Square Garden.

            Rivales Divisionales Clave:
            - Boston Celtics: La rivalidad más antigua y una de las más enconadas de la NBA. Partidos llenos de historia y pasión.
            - Philadelphia 76ers: Rivalidad de ciudades cercanas, con muchos duelos memorables en temporada regular y playoffs.
            - Brooklyn Nets: La "Batalla de los Boroughs". Una rivalidad más moderna pero que ha crecido rápidamente en intensidad con ambos equipos buscando dominar Nueva York.
            - Toronto Raptors: Un rival divisional duro, especialmente desde que ganaron el campeonato en 2019.

            Contexto Actual de la División (Ejemplo General):
            La División Atlántico es extremadamente competitiva. Celtics y 76ers son potencias. Los Knicks están en ascenso. Los Nets buscan redefinirse. Los Raptors siempre son peligrosos. Ganar esta división es un logro significativo.
        """.trimIndent()

        "Knicks-Central" -> """
            NEW YORK KNICKS - ENFRENTANDO A LA DIVISIÓN CENTRAL (CONFERENCIA ESTE)
            🏀
            Equipos Principales de la Central: Chicago Bulls, Cleveland Cavaliers, Detroit Pistons, Indiana Pacers, Milwaukee Bucks.

            Sobre la División Central para los Knicks:
            Esta división ha sido escenario de algunas de las rivalidades más feroces para los Knicks, especialmente en los playoffs de los 90.

            Duelos Knicks vs. Central:
            - Indiana Pacers: Quizás la rivalidad más intensa de los Knicks en los 90 fuera de su división. Las series de playoffs contra los Pacers de Reggie Miller fueron batallas épicas, llenas de momentos dramáticos y controversia.
            - Chicago Bulls: Enfrentar a los Bulls de Michael Jordan fue el mayor obstáculo para los Knicks de Ewing en su búsqueda del campeonato. Partidos muy físicos y competitivos.
            - Detroit Pistons: Los "Bad Boys" de Detroit también fueron un rival duro en los 80 y principios de los 90.
            - Milwaukee Bucks y Cleveland Cavaliers: Rivales de conferencia que siempre presentan desafíos.

            Contexto Actual de la División (Ejemplo General):
            Bucks y Cavaliers son equipos fuertes en el Este. Bulls, Pistons y Pacers están en diferentes fases, pero siempre pueden dar sorpresas, especialmente en casa.
        """.trimIndent()

        "Knicks-Sudeste" -> """
            NEW YORK KNICKS - ENFRENTANDO A LA DIVISIÓN SUDESTE (CONFERENCIA ESTE)
            🏀
            Equipos Principales de la Sudeste: Miami Heat, Orlando Magic, Atlanta Hawks, Washington Wizards, Charlotte Hornets.

            Sobre la División Sudeste para los Knicks:
            La rivalidad con el Miami Heat, especialmente marcada por la figura de Pat Riley, es lo más destacado para los Knicks en esta división.

            Duelos Knicks vs. Sudeste:
            - Miami Heat: Una rivalidad que explotó en los 90 cuando Pat Riley, ex-entrenador campeón con los Knicks (como asistente) y luego entrenador principal exitoso, se fue a Miami. Las series de playoffs entre Knicks y Heat en esa década fueron increíblemente físicas y hostiles.
            - Orlando Magic: Duelos interesantes, especialmente cuando el Magic tenía jóvenes estrellas.
            - Atlanta Hawks, Washington Wizards, Charlotte Hornets: Rivales de conferencia que, aunque sin la misma carga histórica que el Heat, siempre son importantes para la clasificación.

            Contexto Actual de la División (Ejemplo General):
            Miami siempre es competitivo. Atlanta y Orlando tienen talento joven. Wizards y Hornets buscan mejorar y desarrollar sus proyectos.
        """.trimIndent()

        // --- CASO DEFAULT ---
        // Puedes añadir aquí un texto genérico aún más largo si quieres asegurar el scroll
        // para cualquier combinación no explícitamente definida.
        else -> """
            INFORMACIÓN DETALLADA PARA $team - $division
            🏀
            Actualmente, la información específica para esta combinación de equipo y división está siendo recopilada.
            
            Mientras tanto, podemos hablar en términos generales sobre cómo los equipos de la NBA se preparan para enfrentar a diferentes divisiones:
            
            Análisis del Rival: Los cuerpos técnicos dedican horas a estudiar videos, estadísticas y tendencias de los equipos de la división a la que se van a enfrentar. Se identifican fortalezas, debilidades y jugadores clave.
            
            Ajustes Tácticos: Dependiendo de las características de los oponentes divisionales, los entrenadores pueden realizar ajustes en sus esquemas ofensivos y defensivos. Por ejemplo, contra una división con muchos tiradores, se enfatizará la defensa perimetral. Contra una división con postes dominantes, se buscarán estrategias para contener la pintura.
            
            Viajes y Logística: Enfrentar a equipos de diferentes divisiones, especialmente en conferencias opuestas, implica una planificación logística considerable en cuanto a viajes, horarios de descanso y adaptación a diferentes zonas horarias.
            
            Importancia de los Partidos: Aunque todos los partidos cuentan, los duelos divisionales suelen tener un peso extra en los desempates para la clasificación a playoffs. Ganar la propia división es un objetivo importante.
            
            Desarrollo de Jugadores Jóvenes: Los partidos contra equipos de diferentes características son valiosos para el desarrollo de jugadores jóvenes, ya que los expone a diversos estilos de juego y niveles de competencia.
            
            Impacto de las Lesiones: Las lesiones de jugadores estrella, tanto propios como rivales, pueden alterar drásticamente la dinámica competitiva dentro de una división y en los enfrentamientos interdivisionales.
            
            El Mercado de Traspasos y Agencia Libre: Los movimientos de jugadores pueden cambiar el balance de poder entre divisiones de una temporada a otra, haciendo que algunas se vuelvan más fuertes o más débiles.
            
            Este deporte es dinámico y cada temporada trae nuevas narrativas y desafíos.
            Pronto tendremos más detalles específicos para $team en la división $division.
            
            (Este es un texto de relleno considerablemente largo para asegurar el scroll. Puedes hacerlo aún más extenso si es necesario).
            Párrafo adicional de relleno.
            Otro párrafo para aumentar la longitud.
            Y uno más para estar seguros.
            Continuamos añadiendo texto.
            Un poco más de contenido.
            Casi llegamos al final del relleno.
            Última línea de relleno.
        """.trimIndent()
    }
}

// ... (El resto de DetailsView.kt, previews, etc., se mantienen igual que en la respuesta anterior)
// ... (Las funciones getIconicPlayersInfo y getStadiumInfo también se mantienen)
