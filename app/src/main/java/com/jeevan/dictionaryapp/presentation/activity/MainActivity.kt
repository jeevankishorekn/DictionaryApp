package com.jeevan.dictionaryapp.presentation.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.dictionaryapp.databinding.ActivityMainBinding
import com.example.dictionaryapp.databinding.WordDetailViewBinding
import com.jeevan.dictionaryapp.domain.model.DictionaryItem
import com.jeevan.dictionaryapp.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataObserver: Observer<DictionaryItem>
    private lateinit var isLoadingObserver: Observer<Boolean>
    private lateinit var isErrorObserver: Observer<Boolean>
    private val TAG = "MainActivity"
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            resultLauncher =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == RESULT_OK) {
                        var result = it.data?.getStringArrayListExtra(
                            RecognizerIntent.EXTRA_RESULTS
                        )
                        Log.d(TAG, "onCreate: ${result?.get(0)}")
                        binding.wordText.setText(result?.get(0))
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this, " " + e.message, Toast.LENGTH_SHORT).show()
        }

        isErrorObserver = Observer { error ->
            if (error) {
                AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Error")
                    .setMessage("Oops something went wrong! Please try again.")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .create().show()
            }
        }

        dataObserver = Observer { item ->
            binding.itemContainer.removeAllViews()
            binding.word.text = item.word
            if (item.meanings.isNotEmpty()) {
                item.meanings.forEach {
                    Log.d(TAG, "onCreate: ${it.definitions.size}")
                    it.definitions.forEach { definitionItem ->
                        val wordBinding = WordDetailViewBinding.inflate(layoutInflater)
                        if (definitionItem.definition == "") {
                            wordBinding.definition.visibility = View.GONE
                            wordBinding.definitionText.visibility = View.GONE
                        }
                        if (definitionItem.example == "") {
                            wordBinding.example.visibility = View.GONE
                            wordBinding.exampleText.visibility = View.GONE
                        }
                        if (definitionItem.synonyms.isEmpty()) {
                            wordBinding.synonyms.visibility = View.GONE
                            wordBinding.synonymsText.visibility = View.GONE
                        }
                        wordBinding.definition.text = definitionItem.definition
                        wordBinding.example.text = definitionItem.example
                        var synonyms = ""
                        definitionItem.synonyms.forEach { syn ->
                            Log.d(TAG, "onCreate: $syn")
                            synonyms += "$syn, "
                        }
                        if (synonyms != "") synonyms = synonyms.substring(0, synonyms.length - 2)
                        wordBinding.synonyms.text = synonyms

                        val layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        layoutParams.setMargins(0, 20, 0, 20)


                        when (applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                            Configuration.UI_MODE_NIGHT_YES -> wordBinding.cardViewLayout.setBackgroundColor(
                                Color.parseColor(viewModel.getRandomDarkColor())
                            )

                            Configuration.UI_MODE_NIGHT_NO -> wordBinding.cardViewLayout.setBackgroundColor(
                                Color.parseColor(viewModel.getRandomLightColor())
                            )

                            Configuration.UI_MODE_NIGHT_UNDEFINED -> wordBinding.cardViewLayout.setBackgroundColor(
                                Color.parseColor(viewModel.getRandomDarkColor())
                            )
                        }
                        binding.itemContainer.addView(wordBinding.root, layoutParams)
                    }

                }
            }
        }

        isLoadingObserver = Observer {
            if (it) binding.progressBar.visibility = View.VISIBLE
            else binding.progressBar.visibility = View.GONE
        }
        viewModel.data.observe(this, dataObserver)
        viewModel.isLoading.observe(this, isLoadingObserver)
        viewModel.isError.observe(this, isErrorObserver)

        binding.searchButton.setOnClickListener {
            viewModel.getDictionaryItem(binding.wordText.text.toString())
            closeKeyboard()
        }

        binding.speechToTextBtn.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now")
            resultLauncher.launch(intent)


        }
    }


    private fun closeKeyboard() {
        val view = this.currentFocus

        if (view != null) {

            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.data.removeObserver(dataObserver)
        viewModel.isLoading.removeObserver(isLoadingObserver)
        viewModel.isError.removeObserver(isErrorObserver)
    }
}