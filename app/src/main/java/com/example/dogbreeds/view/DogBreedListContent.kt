package com.example.dogbreeds.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dogbreeds.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedListContent(
    dogBreeds: List<String>,
    filteredDogBreeds: List<String>,
    breedOnClick: (breed: String) -> Unit = {},
    filterOnType: (filteredBreed: String) -> Unit = {},
    loadingStatus: Boolean? = false
) {
    var filterDogBreedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        loadingStatus?.let { errorStatus ->
            if (errorStatus) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    text = stringResource(R.string.error_loading_dogs),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        text = stringResource(R.string.dog_breeds),
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp
                    )
                    if (filterDogBreedText.isEmpty()) {
                        dogBreeds.forEach { breed ->
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        breedOnClick(breed.lowercase())
                                    },
                                text = breed,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            )
                        }
                    } else {
                        if (filteredDogBreeds.isEmpty()) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = stringResource(R.string.no_dogs_breeds_found),
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp
                            )
                        } else {
                            filteredDogBreeds.forEach { filteredBreed ->
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            breedOnClick(filteredBreed.lowercase())
                                        },
                                    text = filteredBreed,
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    TextField(
                        value = filterDogBreedText,
                        onValueChange = {
                            filterDogBreedText = it
                            filterOnType(filterDogBreedText)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.enter_breed_to_filter)
                            )
                        },
                        singleLine = true
                    )
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