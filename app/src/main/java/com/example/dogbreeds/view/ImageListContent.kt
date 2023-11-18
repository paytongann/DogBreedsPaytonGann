package com.example.dogbreeds.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.dogbreeds.R

@Composable
fun ImageListContent(
    breedName: String,
    dogBreeds: List<String>,
    loadingStatus: Boolean? = false
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        loadingStatus?.let { errorStatus ->
            if (errorStatus) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = stringResource(R.string.error_loading_dogs),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()

                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        text = breedName.replaceFirstChar(Char::titlecase),
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp
                    )
                    if (dogBreeds.isEmpty()) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.no_images_found),
                            textAlign = TextAlign.Center,
                            fontSize = 32.sp
                        )
                    } else {
                        dogBreeds.forEach { imageUrl ->
                            Image(
                                painter = rememberAsyncImagePainter(imageUrl),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(128.dp),
                                alignment = Alignment.Center
                            )
                        }
                    }
                }
            }
        } ?: run {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = stringResource(R.string.loading_dogs),
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
        }
    }
}