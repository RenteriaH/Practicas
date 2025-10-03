
package com.cusosandroid.practicas.view

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.cusosandroid.practicas.R
import com.cusosandroid.practicas.components.ImageGallery
import com.cusosandroid.practicas.components.KeyStatsRow
import com.cusosandroid.practicas.components.SectionCard
import com.cusosandroid.practicas.components.SectionTitle
import com.cusosandroid.practicas.components.YouTubeVideosRow

// --- ESTRUCTURA DE DATOS ACTUALIZADA ---
data class DivisionInfo(
    val title: String,
    val sections: Map<String, String>,
    val images: List<Int> = emptyList(),
    val keyStats: List<Pair<String, String>> = emptyList(),
    val youtubeVideoIds: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTextApi::class)
@Composable
fun DetailsView(navController: NavHostController, team: String, division: String) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // --- Colores (sin cambios) ---
    val lakersPrimary = Color(0xFF552583);
    val lakersSecondary = Color(0xFFFFB81C);
    val lakersTextOnSecondaryDetails = Color.Black
    val knicksPrimary = Color(0xFF006BB6);
    val knicksSecondary = Color(0xFFF58426);
    val knicksTextOnSecondaryDetails = Color.White
    val textOnPrimary = Color.White
    val defaultPrimary = Color(0xFF212121);
    val defaultSecondary = Color(0xFF424242);
    val defaultTextOnSecondaryDetails = Color.White
    val currentTeamPrimaryColor: Color;
    val currentTeamSecondaryColor: Color;
    val currentTextOnSecondaryColorDetails: Color
    val shortTeamName = when {
        team.contains("Lakers", ignoreCase = true) -> "Lakers"
        team.contains("Knicks", ignoreCase = true) -> "Knicks"
        else -> "Default"
    }
    when (shortTeamName) {
        "Lakers" -> {
            currentTeamPrimaryColor = lakersPrimary; currentTeamSecondaryColor =
                lakersSecondary; currentTextOnSecondaryColorDetails = lakersTextOnSecondaryDetails
        }

        "Knicks" -> {
            currentTeamPrimaryColor = knicksPrimary; currentTeamSecondaryColor =
                knicksSecondary; currentTextOnSecondaryColorDetails = knicksTextOnSecondaryDetails
        }

        else -> {
            currentTeamPrimaryColor = defaultPrimary; currentTeamSecondaryColor =
                defaultSecondary; currentTextOnSecondaryColorDetails = defaultTextOnSecondaryDetails
        }
    }

    val divisionInfo = getTeamDivisionInfo(team, division)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "$team - $division", color = textOnPrimary, maxLines = 1) },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver",
                                tint = textOnPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = currentTeamPrimaryColor
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(currentTeamPrimaryColor)
                .verticalScroll(scrollState), // El scroll principal
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- TÍTULO (Aparece al instante) ---
            Spacer(modifier = Modifier.height(24.dp))
            val gradientBrush = Brush.verticalGradient(colors = listOf(currentTeamSecondaryColor, textOnPrimary))
            Text(
                text = divisionInfo.title,
                style = TextStyle(brush = gradientBrush, fontSize = 28.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(text = "🏀", fontSize = 32.sp, modifier = Modifier.padding(vertical = 8.dp))

            // --- CONTENEDOR ANIMADO PARA TODO EL CONTENIDO ---
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(initialAlpha = 0.3f) + slideInVertically(initialOffsetY = { it / 5 })
            ) {
                // Columna para todo el contenido que aparece de forma animada
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // 1. GALERÍA DE IMÁGENES
                    if (divisionInfo.images.isNotEmpty()) {
                        ImageGallery(images = divisionInfo.images)
                    }

                    // 2. TARJETAS DE ESTADÍSTICAS
                    if (divisionInfo.keyStats.isNotEmpty()) {
                        KeyStatsRow(
                            stats = divisionInfo.keyStats,
                            cardColor = currentTeamSecondaryColor,
                            textColor = currentTextOnSecondaryColorDetails
                        )
                    }

                    // ... dentro del Column animado ...

// 3. SECCIONES DE TEXTO Y VIDEOS
                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                        // --- Definimos el degradado para los subtítulos ---
                        val subtitleBrush = Brush.verticalGradient(
                            colors = listOf(Color.White, currentTeamSecondaryColor.copy(alpha = 0.8f))
                        )

                        // TARJETAS DE INFORMACIÓN
                        divisionInfo.sections.forEach { (sectionTitle, sectionContent) ->
                            SectionTitle(sectionTitle, brush = subtitleBrush) // <-- Usamos el nuevo brush
                            SectionCard(
                                content = sectionContent,
                                cardColor = currentTeamSecondaryColor.copy(alpha = 0.85f),
                                textColor = currentTextOnSecondaryColorDetails
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // VIDEOS DE YOUTUBE
                        if (divisionInfo.youtubeVideoIds.isNotEmpty()) {
                            SectionTitle("Highlights en YouTube", brush = subtitleBrush) // <-- Usamos el mismo brush
                            YouTubeVideosRow(
                                videoIds = divisionInfo.youtubeVideoIds,
                                onVideoClick = { videoId ->
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://www.youtube.com/watch?v=$videoId")
                                    )
                                    context.startActivity(intent)
                                }
                            )
                        }
                    }

                }

            }
        }
    }
}

    // --- FUNCIÓN DE DATOS (AQUÍ DEBES AÑADIR TUS ESTADÍSTICAS Y VIDEOS) ---
    fun getTeamDivisionInfo(team: String, division: String): DivisionInfo {
        val shortTeamNameForLogic = when {
            team.contains("Lakers", ignoreCase = true) -> "Lakers"
            team.contains("Knicks", ignoreCase = true) -> "Knicks"
            else -> team
        }

        // !! IMPORTANTE: REEMPLAZA R.drawable.placeholder POR TUS IMÁGENES !!
        val placeholder = R.drawable.lakerss // Una imagen por defecto si no tienes una específica

        return when ("$shortTeamNameForLogic-$division") {
            // --- LOS ANGELES LAKERS ---
            "Lakers-Pacifico" -> DivisionInfo(
                title = "LOS ANGELES LAKERS - DIVISIÓN PACÍFICO",
                sections = mapOf(
                    "Sobre la División Pacífico" to """
                    Considerada una de las divisiones más competitivas y glamurosas de la NBA. Hogar de múltiples campeones y estrellas.
                    Equipos Principales: Los Angeles Lakers, Los Angeles Clippers, Golden State Warriors, Phoenix Suns, Sacramento Kings.
                """.trimIndent(),
                    "Historia de los Lakers en la División" to """
                    - Era "Showtime" (80s): Magic Johnson, Kareem Abdul-Jabbar, James Worthy. Dominio absoluto y múltiples campeonatos.
                    - Era Shaq & Kobe (2000s): Tres campeonatos consecutivos, reafirmando su lugar en la cima.
                    - Era LeBron & AD (2020s): Campeonato en la burbuja, manteniendo la tradición ganadora.
                """.trimIndent(),
                    "Rivales Divisionales Clave" to """
                    - Golden State Warriors: Rivalidad que creció con la dinastía de Stephen Curry.
                    - Los Angeles Clippers: La "batalla de Los Ángeles", ahora con dos contendientes.
                    - Phoenix Suns y Sacramento Kings: Rivalidades intensas en diferentes épocas.
                """.trimIndent(),
                    "Contexto Actual" to "Una división de élite. Lakers, Warriors, Clippers y Suns aspiran al título. Los Kings son un equipo joven y emocionante."
                ),
                images = listOf(
                    R.drawable.lakers_pacifico1,
                    R.drawable.lakers_pacifico2,
                    R.drawable.lakers_pacifico3,
                    R.drawable.lakers_pacifico4,
                    R.drawable.lakers_pacifico5,
                    R.drawable.lakers_pacifico6
                ),
                keyStats = listOf(
                    "Títulos Div." to "24",
                    "vs Warriors" to "290-172",
                    "vs Clippers" to "151-82"
                ),
                youtubeVideoIds = listOf("XJ89PFCzAeE", "WjR5go5y8wk")
            )

            "Lakers-Noroeste" -> DivisionInfo(
                title = "LAKERS vs. DIVISIÓN NOROESTE",
                sections = mapOf(
                    "Sobre la División Noroeste" to """
                    Caracterizada por equipos físicos, mercados apasionados y el hogar del campeón, Denver Nuggets. Ha producido MVPs y equipos muy competitivos.
                    Equipos Principales: Denver Nuggets, Minnesota Timberwolves, Oklahoma City Thunder, Portland Trail Blazers, Utah Jazz.
                """.trimIndent(),
                    "Interacciones Históricas" to """
                    - Denver Nuggets: Múltiples series de playoffs. La rivalidad se ha intensificado con Nikola Jokić.
                    - Utah Jazz: Duelos duros en playoffs, especialmente contra los Jazz de Stockton y Malone en los 90.
                    - Portland Trail Blazers y OKC Thunder: Rivalidades de la costa oeste y enfrentamientos notables en postemporada.
                """.trimIndent(),
                    "Jugadores Destacados de la División" to "Nikola Jokić (Nuggets), Karl Malone y John Stockton (Jazz), Damian Lillard (Blazers), Kevin Durant y Russell Westbrook (cuando en OKC), Anthony Edwards (Timberwolves)."
                ),
                images = listOf(
                    R.drawable.lakers_noroeste1,
                    R.drawable.lakers_noroeste2,
                    R.drawable.lakers_noroeste3,
                    R.drawable.lakers_noroeste4,
                    R.drawable.lakers_noroeste5
                ),
                keyStats = listOf(
                    "vs Nuggets" to "116-83",
                    "vs Jazz" to "114-87",
                    "Duelo Clave" to "Jokic vs AD"
                ),
                youtubeVideoIds = listOf("1rHsp8ftQbI","9cEYGS8rc3I")
            )

            "Lakers-Suroeste" -> DivisionInfo(
                title = "LAKERS vs. DIVISIÓN SUROESTE",
                sections = mapOf(
                    "Sobre la División Suroeste" to """
                    Conocida por su rica historia de campeonatos (Spurs, Rockets) y por tener algunos de los talentos internacionales más impactantes de la liga.
                    Equipos Principales: Dallas Mavericks, Houston Rockets, Memphis Grizzlies, New Orleans Pelicans, San Antonio Spurs.
                """.trimIndent(),
                    "Interacciones Históricas" to """
                    - San Antonio Spurs: Una de las grandes rivalidades de los 2000s, definiendo a menudo al representante del Oeste en las Finales.
                    - Dallas Mavericks: Duelos con Dirk Nowitzki y ahora con Luka Dončić.
                    - Houston Rockets: Desde Kareem vs. Moses Malone, hasta Kobe vs. Yao Ming y LeBron vs. Harden.
                    - Memphis Grizzlies: Una rivalidad más reciente pero intensa.
                """.trimIndent(),
                    "Jugadores Destacados de la División" to "Tim Duncan (Spurs), Dirk Nowitzki (Mavericks), Hakeem Olajuwon (Rockets), James Harden (Rockets), Luka Dončić (Mavericks), Ja Morant (Grizzlies)."
                ),
                images = listOf(
                    R.drawable.lakers_suroeste1,
                    R.drawable.lakers_suroeste3,
                    R.drawable.lakers_suroeste5
                ),
                keyStats = listOf(
                    "vs Spurs" to "92-90",
                    "vs Mavs" to "119-51",
                    "vs Rockets" to "141-99"
                ),
                youtubeVideoIds = listOf("ZTk0fwsJ6qo","S0callNZ8sU")
            )

            "Lakers-Atlantico" -> DivisionInfo(
                title = "LAKERS vs. DIVISIÓN ATLÁNTICO",
                sections = mapOf(
                    "Sobre la División Atlántico" to """
                    Hogar de la rivalidad más icónica para los Lakers: los Boston Celtics. Una división con mercados grandes y mucha historia.
                    Equipos Principales: Boston Celtics, Philadelphia 76ers, New York Knicks, Brooklyn Nets, Toronto Raptors.
                """.trimIndent(),
                    "La Rivalidad Lakers-Celtics" to "Es la rivalidad por excelencia de la NBA. Han disputado el mayor número de Finales entre sí, definiendo eras enteras de la liga: Russell vs. Chamberlain, Magic vs. Bird, Kobe vs. Pierce.",
                    "Otras Interacciones" to """
                    - Philadelphia 76ers: Finales en los 80 (vs. Dr. J) y 2001 (vs. Iverson).
                    - New York Knicks: Choque de grandes mercados con historia en las Finales de los 70.
                """.trimIndent()
                ),
                images = listOf(
                    R.drawable.lakers_atlantico1,
                    R.drawable.lakers_atlantico2,
                    R.drawable.lakers_atlantico3,
                    R.drawable.lakers_atlantico4,
                    R.drawable.lakers_atlantico5
                ),
                keyStats = listOf(
                    "Títulos NBA" to "17 - 17",
                    "Finales" to "12",
                    "vs Celtics" to "164-135"
                ),
                youtubeVideoIds = listOf("1fjhIWJSxfw", "3VlMSo7AXow")
            )

            "Lakers-Central" -> DivisionInfo(
                title = "LAKERS vs. DIVISIÓN CENTRAL",
                sections = mapOf(
                    "Sobre la División Central" to """
                    Hogar de la dinastía de los Bulls de Michael Jordan y de los "Bad Boys" de Detroit. Equipos conocidos por su dureza y defensa.
                    Equipos Principales: Chicago Bulls, Cleveland Cavaliers, Detroit Pistons, Indiana Pacers, Milwaukee Bucks.
                """.trimIndent(),
                    "Interacciones Históricas" to """
                    - Chicago Bulls: Las Finales de 1991 marcaron el ascenso de Michael Jordan y el fin de la era Showtime.
                    - Detroit Pistons: Finales en 1988, 1989 y 2004. Duelos muy físicos.
                    - Cleveland Cavaliers: La historia de LeBron James conecta a ambas franquicias.
                """.trimIndent(),
                    "Jugadores Destacados de la División" to "Michael Jordan (Bulls), Isiah Thomas (Pistons), LeBron James (Cavs), Giannis Antetokounmpo (Bucks), Reggie Miller (Pacers)."
                ),
                images = listOf(
                    R.drawable.lakers_central1,
                    R.drawable.lakers_central2,
                    R.drawable.lakers_central3,
                    R.drawable.lakers_central5
                ),
                keyStats = listOf(
                    "vs Bulls" to "87-68",
                    "vs Pistons" to "143-138",
                    "Finales '91" to "CHI 4-1"
                ),
                youtubeVideoIds = listOf("9lP95Qo-I0","fEui_r4X1PE")
            )

            "Lakers-Sudeste" -> DivisionInfo(
                title = "LAKERS vs. DIVISIÓN SUDESTE",
                sections = mapOf(
                    "Sobre la División Sudeste" to """
                    Una división que ha visto campeones recientes (Miami Heat) y equipos con jóvenes estrellas emergentes.
                    Equipos Principales: Miami Heat, Orlando Magic, Atlanta Hawks, Washington Wizards, Charlotte Hornets.
                """.trimIndent(),
                    "Interacciones Históricas" to """
                    - Miami Heat: Las Finales de 2020 en la burbuja. También la era del "Big Three" de Miami (LeBron, Wade, Bosh) generó partidos de alto voltaje.
                    - Orlando Magic: Finales de 2009 (Lakers vs. Dwight Howard). También el Magic de Shaq y Penny en los 90.
                """.trimIndent(),
                    "Jugadores Destacados de la División" to "Dwyane Wade, LeBron James (Heat), Shaquille O'Neal (Magic/Heat), Dwight Howard (Magic), Trae Young (Hawks)."
                ),
                images = listOf(
                    R.drawable.lakers_sudeste1,
                    R.drawable.lakers_sudeste2,
                    R.drawable.lakers_sudeste3,
                    R.drawable.lakers_sudeste4,
                    R.drawable.lakers_sudeste5),
                keyStats = listOf(
                    "vs Heat" to "39-32",
                    "vs Magic" to "36-31",
                    "Finales '20" to "LAL 4-2"
                ),
                youtubeVideoIds = listOf("prvjNFHSOj0","ZTVz_SFGt_o")
            )

            // --- NEW YORK KNICKS ---
            "Knicks-Atlantico" -> DivisionInfo(
                title = "NEW YORK KNICKS - DIVISIÓN ATLÁNTICO",
                sections = mapOf(
                    "Sobre la División Atlántico" to """
                    ¡Esta es la casa de los Knicks! Una de las divisiones más históricas y con mayor rivalidad. Cada partido divisional tiene un significado especial.
                    Equipos Principales: New York Knicks, Boston Celtics, Philadelphia 76ers, Brooklyn Nets, Toronto Raptors.
                """.trimIndent(),
                    "Historia de los Knicks en la División" to """
                    - Años de Campeonato (70s): Liderados por Willis Reed, Walt Frazier y Dave DeBusschere, los Knicks ganaron dos campeonatos (1970, 1973).
                    - Era Ewing (90s): Con Patrick Ewing como pilar, fueron contendientes perennes, conocidos por su defensa y tenacidad. Llegaron a las Finales en 1994 y 1999.
                    - Resurgimiento Reciente: Después de años difíciles, los Knicks han vuelto a ser un equipo competitivo, devolviendo la ilusión al Madison Square Garden.
                """.trimIndent(),
                    "Rivales Divisionales Clave" to """
                    - Boston Celtics: La rivalidad más antigua y una de las más enconadas de la NBA.
                    - Philadelphia 76ers: Rivalidad de ciudades cercanas, con muchos duelos memorables.
                    - Brooklyn Nets: La "Batalla de los Boroughs", una rivalidad moderna que ha crecido en intensidad.
                    - Toronto Raptors: Un rival divisional duro, especialmente desde su campeonato en 2019.
                """.trimIndent()
                ),
                images = listOf(R.drawable.knicks_atlantico1, R.drawable.knicks_atlantico3, R.drawable.knicks_atlantico4,R.drawable.knicks_atlantico5),
                keyStats = listOf(
                    "Títulos Div." to "5",
                    "vs Celtics" to "208-299",
                    "vs 76ers" to "209-260"
                ),
                youtubeVideoIds = listOf("YgI01l-t0L4","L3WenRwlMHI")
            )

            "Knicks-Central" -> DivisionInfo(
                title = "KNICKS vs. DIVISIÓN CENTRAL",
                sections = mapOf(
                    "Sobre la División Central" to """
                    Esta división ha sido escenario de algunas de las rivalidades más feroces para los Knicks, especialmente en los playoffs de los 90.
                    Equipos Principales: Chicago Bulls, Cleveland Cavaliers, Detroit Pistons, Indiana Pacers, Milwaukee Bucks.
                """.trimIndent(),
                    "Duelos Históricos" to """
                    - Indiana Pacers: Quizás la rivalidad más intensa de los Knicks en los 90 fuera de su división. Las series contra los Pacers de Reggie Miller fueron batallas épicas.
                    - Chicago Bulls: Enfrentar a los Bulls de Michael Jordan fue el mayor obstáculo para los Knicks de Ewing.
                    - Detroit Pistons: Los "Bad Boys" de Detroit también fueron un rival duro en los 80 y principios de los 90.
                """.trimIndent()
                ),
                images = listOf(R.drawable.knicks_central1, R.drawable.knicks_central2, R.drawable.knicks_central3,R.drawable.knicks_central4,R.drawable.knicks_central5,R.drawable.knicks_central6),
                keyStats = listOf(
                    "Rivalidad 90s" to "Pacers",
                    "Obstáculo" to "Bulls de MJ",
                    "Dureza" to "Bad Boys"
                ),
                youtubeVideoIds = listOf("aL0L--UCCOM", "ghmUCp0E0R8")
            )

            "Knicks-Sudeste" -> DivisionInfo(
                title = "KNICKS vs. DIVISIÓN SUDESTE",
                sections = mapOf(
                    "Sobre la División Sudeste" to """
                    La rivalidad con el Miami Heat, especialmente marcada por la figura del entrenador Pat Riley, es lo más destacado para los Knicks en esta división.
                    Equipos Principales: Miami Heat, Orlando Magic, Atlanta Hawks, Washington Wizards, Charlotte Hornets.
                """.trimIndent(),
                    "La Rivalidad con Miami Heat" to "Una rivalidad que explotó en los 90 cuando Pat Riley se fue a Miami. Las series de playoffs entre Knicks y Heat en esa década fueron increíblemente físicas y hostiles.",
                    "Otras Interacciones" to "Duelos interesantes contra el Magic de Shaq/Penny en los 90. Partidos de conferencia importantes contra Hawks, Wizards y Hornets."
                ),
                images = listOf(R.drawable.knicks_sudeste3, R.drawable.knicks_sudeste4,R.drawable.knicks_sudeste5,R.drawable.knicks_sudeste6),
                keyStats = listOf(
                    "vs Heat" to "70-66",
                    "Playoffs 90s" to "Batallas",
                    "Pat Riley" to "Factor Clave"
                ),
                youtubeVideoIds = listOf("p5CbIIGR79g")
            )

            "Knicks-Pacifico" -> DivisionInfo(
                title = "KNICKS vs. DIVISIÓN PACÍFICO",
                sections = mapOf(
                    "Sobre la División Pacífico" to """Enfrentar a la División Pacífico significa medirse contra algunos de los equipos más laureados, con mayor poder estelar y mercados más grandes de la liga. Para los Knicks, esta gira por el Oeste es siempre un barómetro para medir su nivel contra la élite de la NBA.
                    Equipos Principales: Los Angeles Lakers, Los Angeles Clippers, Golden State Warriors, Phoenix Suns, Sacramento Kings.
                """.trimIndent(),
                    "Duelos Relevantes y Narrativas" to """
                    - Los Angeles Lakers: Un choque de titanes mediáticos. La rivalidad entre las dos ciudades más grandes de EE.UU. siempre genera expectación. Tienen historia en las Finales de los años 70, y cualquier partido en el Madison Square Garden o el Crypto.com Arena es un evento.

                    - Golden State Warriors: Enfrentamientos contra la dinastía de Stephen Curry han servido como pruebas de fuego. El estilo de juego rápido y de perímetro de los Warriors contrasta a menudo con la identidad más física y defensiva que los Knicks han buscado en sus mejores años.

                    - Phoenix Suns & LA Clippers: Estos equipos, a menudo contendientes, presentan desafíos tácticos significativos. Los duelos contra jugadores estrella como Kevin Durant (Suns) o Kawhi Leonard (Clippers) son siempre destacados.
                """.trimIndent(),
                    "Perspectiva Histórica Adicional" to """
                    Históricamente, los equipos de la División del Pacífico han sido un obstáculo para las aspiraciones de los Knicks en duelos interconferencia. La potencia de fuego ofensiva y la concentración de talento en esta división a menudo exponen cualquier debilidad defensiva. Sin embargo, para los Knicks, obtener victorias en esta gira por la costa oeste es una gran inyección de moral y una declaración de intenciones para el resto de la liga.
                """.trimIndent(),
                    "Claves Tácticas del Enfrentamiento" to """
                    Generalmente, para competir contra la División Pacífico, los Knicks deben imponer su ritmo, controlar la pintura y ser extremadamente disciplinados en la defensa del perímetro. Evitar que equipos como los Warriors o los Suns entren en racha de triples es fundamental, al igual que contener las transiciones rápidas de equipos como los Kings.
                """.trimIndent()
                ),
                images = listOf(R.drawable.knicks_pacifico2, R.drawable.knicks_pacifico3,R.drawable.knicks_pacifico4,R.drawable.knicks_pacifico5,R.drawable.knicks_pacifico6),
                keyStats = listOf(
                    "vs Lakers" to "147-150",
                    "Finales '73" to "NYK 4-1",
                    "NY vs LA" to "Mercados"
                ),
                youtubeVideoIds = listOf("cgpv8ShAoaU","yIcz-oSwTgs")
            )

            "Knicks-Noroeste" -> DivisionInfo(
                title = "KNICKS vs. DIVISIÓN NOROESTE",
                sections = mapOf(
                    "Sobre la División Noroeste" to """
                    Esta división presenta una mezcla de desafíos únicos: la altitud de Denver y Salt Lake City, aficiones muy ruidosas y leales, y una combinación de potencias establecidas y equipos jóvenes en ascenso.
                    Equipos Principales: Denver Nuggets, Minnesota Timberwolves, Oklahoma City Thunder, Portland Trail Blazers, Utah Jazz.
                """.trimIndent(),
                    "Duelos Relevantes y Narrativas" to """
                    - Denver Nuggets: La conexión con Carmelo Anthony, quien fue una estrella en Denver antes de ser traspasado a los Knicks, siempre añade una capa de historia a estos duelos. Más recientemente, enfrentar al dos veces MVP Nikola Jokić es uno de los mayores desafíos individuales y colectivos para cualquier equipo de la liga.

                    - Utah Jazz: La conexión con Donovan Mitchell, nativo del área de Nueva York, siempre generó rumores y expectación cuando jugaba en Utah. Estos partidos eran vistos como una especie de "audición" por los fans, haciendo los duelos más interesantes.

                    - Timberwolves & Thunder: Estos dos equipos representan el futuro de la Conferencia Oeste, con plantillas jóvenes y explosivas lideradas por Anthony Edwards (Timberwolves) y Shai Gilgeous-Alexander (Thunder). Para los Knicks, son enfrentamientos que miden su capacidad para competir con la próxima generación de estrellas.
                """.trimIndent(),
                    "Desafíos Logísticos y Ambientales" to """
                    La gira por la División Noroeste es logísticamente complicada. Jugar a casi 1.600 metros de altitud en Denver y a unos 1.300 metros en Salt Lake City supone un reto físico real que afecta a la resistencia de los jugadores. Los equipos deben planificar cuidadosamente los viajes y la aclimatación para no verse en desventaja. Además, las aficiones de Portland y Oklahoma City son conocidas por crear ambientes muy hostiles para los equipos visitantes.
                """.trimIndent()
                ),
                images = listOf(R.drawable.knicks_noroeste2, R.drawable.knicks_noroeste3,R.drawable.knicks_noroeste4,R.drawable.knicks_noroeste6),
                keyStats = listOf(
                    "Carmelo" to "NYK & DEN",
                    "Mitchell" to "Nativo de NY",
                    "Altitud" to "Factor Físico"
                ),
                youtubeVideoIds = listOf("C7pzb7GLA-E","dFurc38n-Xk")
            )

            "Knicks-Suroeste" -> DivisionInfo(
                title = "KNICKS vs. DIVISIÓN SUROESTE",
                sections = mapOf(
                    "Sobre la División Suroeste" to """
                    Históricamente una de las divisiones más duras y exitosas del Oeste. Ha sido el hogar de dinastías legendarias como los San Antonio Spurs y de algunos de los talentos internacionales más revolucionarios que han llegado a la NBA.
                    Equipos Principales: San Antonio Spurs, Dallas Mavericks, Houston Rockets, Memphis Grizzlies, New Orleans Pelicans.
                """.trimIndent(),
                    "Duelos Históricos y Narrativas" to """
                    - San Antonio Spurs: El recuerdo más doloroso para los Knicks. Perdieron las Finales de 1999 contra los Spurs de un joven Tim Duncan y el Almirante David Robinson. Esa serie marcó el primer campeonato de la era dorada de San Antonio y fue la última aparición de los Knicks en las Finales. Los duelos contra los equipos de Gregg Popovich siempre han sido una lección de baloncesto fundamental.

                    - Dallas Mavericks: La narrativa con los Mavs se centró durante un tiempo en Kristaps Porziņģis, el ex-unicornio de los Knicks que fue traspasado a Dallas. Esto añadía un extra de morbo a cada partido. Ahora, el desafío principal es intentar contener a Luka Dončić, uno de los jugadores ofensivos más imparables de la liga.

                    - Houston Rockets: Durante la era de James Harden, los Rockets presentaron un desafío matemático con su ofensiva de triples y aclarados. Los Knicks, a menudo con una identidad más tradicional, tenían que adaptarse a un estilo de juego completamente diferente.
                """.trimIndent(),
                    "Nuevas Generaciones y Desafíos" to """
                    - Memphis Grizzlies: En los últimos años, los Grizzlies emergieron como un equipo joven, atlético y con una mentalidad dura, muy similar a la que los Knicks aspiran. Los enfrentamientos contra Ja Morant se caracterizan por su explosividad y alto ritmo.

                    - New Orleans Pelicans: Con jugadores como Zion Williamson, los Pelicans presentan un desafío físico único en la pintura. Para los Knicks, es una prueba para su defensa interior y su capacidad para controlar los rebotes contra uno de los jugadores más potentes de la liga.
                """.trimIndent()
                ),
                images = listOf(R.drawable.knicks_suroeste1, R.drawable.knicks_suroeste2,R.drawable.knicks_suroeste3,R.drawable.knicks_suroeste4,R.drawable.knicks_suroeste5,R.drawable.knicks_suroeste6,R.drawable.knicks_suroeste7),
                keyStats = listOf(
                    "Finales '99" to "SAS 4-1",
                    "vs Mavs" to "Porziņģis",
                    "vs Rockets" to "Harden Era"
                ),
                youtubeVideoIds = listOf("Drs6QE9Ae4s","I1elUJ0B2jA")
            )

            // --- CASO POR DEFECTO ---
            else -> DivisionInfo(
                title = "Información no Disponible",
                sections = mapOf(
                    "Contenido no encontrado" to "Actualmente no tenemos información detallada para $team en la división $division. Por favor, revisa más tarde."
                )
            )
        }
    }



