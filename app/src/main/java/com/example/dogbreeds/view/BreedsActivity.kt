package com.example.dogbreeds.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.dogbreeds.ui.theme.DogBreedsTheme
import com.example.dogbreeds.viewmodel.BreedsViewModel

private const val DOG_BREED = "dog_breed"

class BreedsActivity : ComponentActivity() {

    private lateinit var viewModel: BreedsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BreedsViewModel::class.java]
        viewModel.errorStatus = null

        loadDogBreeds()

        setContent {
            DogBreedsTheme {
                DogBreedListContent(
                    viewModel.dogList,
                    viewModel.dogListFiltered,
                    breedClickListener,
                    breedFilterListener,
                    viewModel.errorStatus
                )
            }
        }
    }

    private val breedClickListener = fun (breed: String) {
        val intent = Intent(this@BreedsActivity, SelectedBreedActivity::class.java)
        intent.putExtra(DOG_BREED, breed)
        startActivity(intent)
    }

    private val breedFilterListener = fun (filteredBreed: String) {
        viewModel.dogListFiltered = viewModel.dogList.filter {
            it.lowercase().contains(filteredBreed.lowercase())
        }
    }

    private fun loadDogBreeds() {
        viewModel.loadDogBreeds().observe(
            this
        ) { breeds ->
            breeds.message?.let {
                viewModel.parseSubBreedsDogList(it)
            }
        }
    }
}