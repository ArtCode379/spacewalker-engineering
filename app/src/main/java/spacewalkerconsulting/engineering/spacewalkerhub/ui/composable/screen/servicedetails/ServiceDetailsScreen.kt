package spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.screen.servicedetails

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import spacewalkerconsulting.engineering.spacewalkerhub.data.model.ServiceModel
import spacewalkerconsulting.engineering.spacewalkerhub.ui.composable.shared.PBUHCContentWrapper
import spacewalkerconsulting.engineering.spacewalkerhub.ui.state.DataUiState
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.ChipBackground
import spacewalkerconsulting.engineering.spacewalkerhub.ui.theme.ChipContent
import spacewalkerconsulting.engineering.spacewalkerhub.ui.viewmodel.ServiceDetailsViewModel

@Composable
fun ServiceDetailsScreen(
    serviceId: Int,
    modifier: Modifier = Modifier,
    viewModel: ServiceDetailsViewModel = koinViewModel(),
    onNavigateToCheckout: (serviceId: Int) -> Unit,
) {
    val state by viewModel.serviceState.collectAsState()
    LaunchedEffect(serviceId) { viewModel.observeServiceById(serviceId) }
    PBUHCContentWrapper(
        dataState = state,
        dataPopulated = {
            ServiceDetails((state as DataUiState.Populated).data, modifier, onNavigateToCheckout)
        },
        dataEmpty = { Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Service not found") } },
    )
}

@Composable
private fun ServiceDetails(service: ServiceModel, modifier: Modifier, onBook: (Int) -> Unit) {
    LazyColumn(modifier.fillMaxSize()) {
        item {
            AsyncImage(
                service.imageUrl,
                service.name,
                Modifier.fillMaxWidth().height(280.dp).clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                contentScale = ContentScale.Crop,
            )
        }
        item {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(service.name, style = MaterialTheme.typography.headlineMedium)
                Text(
                    service.category,
                    color = ChipContent,
                    modifier = Modifier.background(ChipBackground, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 6.dp),
                )
                Text("From £${service.price.toInt()} · ${service.durationMinutes} min", fontWeight = FontWeight.Bold)
                Text(service.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("What is included", style = MaterialTheme.typography.titleLarge)
                service.features.forEach { feature ->
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(20.dp))
                        Text(feature)
                    }
                }
                Text("Available consultation times", style = MaterialTheme.typography.titleLarge)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(service.availableTime.orEmpty()) { time ->
                        Card(Modifier.clickable { onBook(service.id) }) {
                            Text(time.toString(), Modifier.padding(horizontal = 14.dp, vertical = 9.dp))
                        }
                    }
                }
                Button(onClick = { onBook(service.id) }, modifier = Modifier.fillMaxWidth().height(54.dp)) {
                    Text("Book Consultation")
                }
            }
        }
    }
}
