package spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Engineering
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.data.model.ServiceModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.shared.PBUHCContentWrapper
import spacewalkerconsulting.engineering.spacewalkerhub.ui.state.DataUiState
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.GradientEnd
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.GradientStart
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.ServiceViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = koinViewModel(),
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val state by viewModel.servicesState.collectAsState()
    PBUHCContentWrapper(
        dataState = state,
        dataPopulated = {
            ServicesPopulated(
                services = (state as DataUiState.Populated).data,
                modifier = modifier,
                onNavigateToServiceDetails = onNavigateToServiceDetails,
            )
        },
        dataEmpty = {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No services available")
            }
        },
    )
}

@Composable
private fun ServicesPopulated(
    services: List<ServiceModel>,
    modifier: Modifier = Modifier,
    onNavigateToServiceDetails: (serviceId: Int) -> Unit,
) {
    val featured = services.take(4)
    val pagerState = rememberPagerState(pageCount = { featured.size })
    LaunchedEffect(featured.size) {
        while (featured.isNotEmpty()) {
            delay(3500)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % featured.size)
        }
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Text("Engineering intelligence", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Independent expertise for confident technical decisions.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(Brush.horizontalGradient(listOf(GradientStart, GradientEnd)))
                        .padding(18.dp)
                ) {
                    Column {
                        Text("NEXT AVAILABLE", color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.labelSmall)
                        Text("Today · 14:00", color = Color.White, style = MaterialTheme.typography.titleLarge)
                        Text("Remote expert consultation", color = Color.White.copy(alpha = 0.85f))
                    }
                }
            }
        }
        item {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth().height(210.dp)) { index ->
                val service = featured[index]
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 3.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateToServiceDetails(service.id) }
                ) {
                    AsyncImage(service.imageUrl, service.name, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    Box(Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)))))
                    Column(Modifier.align(Alignment.BottomStart).padding(18.dp)) {
                        Text(service.category.uppercase(), color = Color.White.copy(alpha = 0.75f), style = MaterialTheme.typography.labelSmall)
                        Text(service.name, color = Color.White, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        }
        item {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                featured.indices.forEach { index ->
                    Box(
                        Modifier.padding(3.dp).size(if (index == pagerState.currentPage) 9.dp else 7.dp)
                            .background(if (index == pagerState.currentPage) MaterialTheme.colorScheme.secondary else Color.LightGray, CircleShape)
                    )
                }
            }
        }
        item {
            Text("Expertise areas", style = MaterialTheme.typography.titleLarge)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(top = 10.dp)) {
                items(
                    listOf(
                        "Industrial" to Icons.Default.Engineering,
                        "Audits" to Icons.Default.Policy,
                        "Energy" to Icons.Default.Bolt,
                        "Materials" to Icons.Default.Science,
                    )
                ) { category ->
                    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(category.second, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(32.dp))
                            Text(category.first, style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }
        }
        item { Text("Consulting services", style = MaterialTheme.typography.titleLarge) }
        items(services, key = { it.id }) { service ->
            ServiceCard(service, onNavigateToServiceDetails)
        }
        item {
            Text("Knowledge base", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            Text("Innovation readiness · Applying systems engineering to early-stage technology", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Standards strategy · Turning compliance into a design advantage", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("Materials insight · Designing for durability and circularity", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            Text("Selected project portfolio", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            Text("Process optimisation · Asset life extension · Low-carbon retrofit", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun ServiceCard(service: ServiceModel, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(service.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(14.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                service.imageUrl,
                service.name,
                Modifier.size(96.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(Modifier.weight(1f)) {
                Text(service.name, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(service.description, maxLines = 2, overflow = TextOverflow.Ellipsis, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("From £${service.price.toInt()}", fontWeight = FontWeight.Bold)
                    Text("BOOK NOW", color = MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
