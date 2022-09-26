package com.example.movieapp.widgets

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.movieapp.model.Movie
import com.example.movieapp.model.getMovies

@Preview
@Composable
fun MovieRow(movie: Movie= getMovies()[0], onItemClick:(String) -> Unit ={}){
    var expanded by remember{
        mutableStateOf(false)
    }
    Card(modifier  = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        //.height(130.dp)
        .clickable {
            onItemClick(movie.imdbId)
        },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 5.dp)
    {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start)
        {
            Surface(modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = RectangleShape,
                elevation = 5.dp)
            {
                //coil is lib used to get image by url
//                Image(painter = , contentDescription ="Movie Poster" )
                Log.d("TAG", "MovieRow: "+movie.images[0])
                AsyncImage(model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.images[0])
                    .crossfade(true)
                    .transformations(CircleCropTransformation())
                    .build(),contentDescription ="Movie Poster")
//                Icon(imageVector = Icons.Default.AccountBox, contentDescription ="MovieImage" )
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = movie.title, style = MaterialTheme.typography.h6)
                Text(text = "Director:${movie.director}",style = MaterialTheme.typography.caption)
                Text(text = "Released: ${movie.released}",style = MaterialTheme.typography.caption)

                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text(buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)){
                                append("Plot: ")
                            }
                            withStyle(style = SpanStyle(color = Color.White, fontSize = 13.sp)){
                                append(movie.plot)
                            }
                        }, modifier = Modifier.padding(6.dp))
                        Divider(modifier = Modifier.padding(6.dp))
                        Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.caption)
                        Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.caption)
                        Text(text = "Rating: ${movie.imdbRating}", style = MaterialTheme.typography.caption)

                    }
                }

                Icon(imageVector = if(expanded) Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown
                    , contentDescription ="Down arrow",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded = !expanded
                        }, tint = Color.DarkGray)
            }

        }

    }
}