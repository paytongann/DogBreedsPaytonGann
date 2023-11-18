package com.example.dogbreeds.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.example.dogbreeds.ui.theme.DogBreedsTheme
import com.example.dogbreeds.viewmodel.SelectedBreedViewModel

private const val DOG_BREED = "dog_breed"

class SelectedBreedActivity : ComponentActivity() {

    private lateinit var viewModel: SelectedBreedViewModel
    private var dogBreedSelected = ""
    private var imageList: List<String> by mutableStateOf(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SelectedBreedViewModel::class.java]
        viewModel.errorStatus = null

        dogBreedSelected = intent.extras?.getString(DOG_BREED) ?: ""

        loadDogImages()

        setContent {
            DogBreedsTheme {
                ImageListContent(
                    dogBreedSelected,
                    imageList,
                    viewModel.errorStatus
                )
            }
        }
    }

    private fun loadDogImages() {
        viewModel.loadBreedImages(dogBreedSelected).observe(
            this
        ) { images ->
            imageList = images.message
        }
    }
}