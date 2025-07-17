package org.example.poke.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.example.poke.domain.model.Pokemon

@Composable
fun PokemonRow(
    pokemon: Pokemon,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            KamelImage(
                { asyncPainterResource(data = pokemon.imageUrl) },
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray.copy(alpha = 0.5f)),
                contentScale = ContentScale.Crop,
                onLoading = {
                    CircularProgressIndicator()
                })
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = pokemon.name, style = MaterialTheme.typography.titleLarge)
                Text(text = "ID: #${pokemon.id}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (pokemon.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (pokemon.isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}
