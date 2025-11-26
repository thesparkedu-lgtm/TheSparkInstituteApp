package com.example.thesparkinstituteapp.Courses_Classes

import com.example.thesparkinstituteapp.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// Use an abstract class to ensure subclasses implement required methods
abstract class MyTranslationFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    // --- Core Properties ---
    private var btnHindi: Button? = null
    private var btnBangla: Button? = null

    // Abstract property to be implemented by subclasses: which TextViews to translate
    abstract val contentTextViewIds: List<Int>

    // State Management
    protected var currentLanguage: String = TranslateLanguage.ENGLISH
    protected val originalTextMap = mutableMapOf<Int, String>()
    private var translator: Translator? = null

    // Constants
    protected val HINDI_CODE = TranslateLanguage.HINDI
    protected val BANGLA_CODE = TranslateLanguage.BENGALI
    protected val ENGLISH_CODE = TranslateLanguage.ENGLISH

    // Abstract property for the container ID holding the buttons (optional, but good practice)
    abstract val buttonContainerId: Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Initialize Buttons from the layout
        val buttonContainer = view.findViewById<View>(buttonContainerId)
        btnHindi = buttonContainer?.findViewById(R.id.btn_translate_hindi)
        btnBangla = buttonContainer?.findViewById(R.id.btn_translate_bangla)

        // 2. Map TextViews and Store Original Text
        contentTextViewIds.forEach { id ->
            view.findViewById<TextView>(id)?.let { textView ->
                originalTextMap[id] = textView.text.toString()
            }
        }

        // 3. Set Click Listeners
        btnHindi?.setOnClickListener {
            val target = if (currentLanguage == HINDI_CODE) ENGLISH_CODE else HINDI_CODE
            handleTranslationClick(target)
        }

        btnBangla?.setOnClickListener {
            val target = if (currentLanguage == BANGLA_CODE) ENGLISH_CODE else BANGLA_CODE
            handleTranslationClick(target)
        }

        // 4. Set initial appearance
        updateButtonAppearance()
    }

    // --- Core Logic ---

    private fun handleTranslationClick(targetLangCode: String) {
        if (targetLangCode == ENGLISH_CODE) {
            resetToOriginalLanguage()
        } else {
            translateContent(targetLangCode)
        }
    }

    private fun resetToOriginalLanguage() {
        contentTextViewIds.forEach { id ->
            (view?.findViewById<TextView>(id))?.text = originalTextMap[id]
        }
        currentLanguage = ENGLISH_CODE
        translator?.close()
        translator = null
        updateButtonAppearance()
        Toast.makeText(context, "Content reset to English.", Toast.LENGTH_SHORT).show()
    }

    private fun translateContent(targetLangCode: String) {
        // ... (Translation logic remains the same as previous answer)
        // ... (See implementation details below)
        // ...
        val targetLangName = getLanguageName(targetLangCode)
        setButtonsEnabled(false)
        showLoadingState(targetLangCode)

        lifecycleScope.launch {
            try {
                // 1. Get/Create Translator and Check/Download Model
                val currentTranslator = getOrCreateTranslator(ENGLISH_CODE, targetLangCode)

                // Try non-blocking check first
                val isModelDownloaded = try {
                    currentTranslator.downloadModelIfNeeded(DownloadConditions.Builder().build()).await()
                    true
                } catch (e: Exception) {
                    false
                }

                if (!isModelDownloaded) {
                    promptModelDownload(requireContext(), currentTranslator, targetLangCode)
                    return@launch // Exit if a prompt is shown, translation resumes after download
                }

                // 2. Translate All TextViews
                contentTextViewIds.forEach { id ->
                    val originalText = originalTextMap[id] ?: return@forEach
                    val translatedText = currentTranslator.translate(originalText).await()
                    view?.findViewById<TextView>(id)?.text = translatedText
                }

                // 3. Update State
                currentLanguage = targetLangCode
                Toast.makeText(context, "Successfully translated to $targetLangName!", Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                Log.e("MLKit", "Translation failed for $targetLangCode: ${e.message}")
                Toast.makeText(context, "Translation failed. Check connection/model.", Toast.LENGTH_LONG).show()
            } finally {
                setButtonsEnabled(true)
                updateButtonAppearance()
            }
        }
    }

    // --- Utility Methods (Same as previous answer, but inside the Base Class) ---

    private fun getOrCreateTranslator(sourceLang: String, targetLang: String): Translator {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(sourceLang)
            .setTargetLanguage(targetLang)
            .build()

        // Close old translator if exists
        if (translator != null) {
            translator?.close()
            translator = null
        }

        translator = Translation.getClient(options)
        lifecycle.addObserver(translator!!)
        return translator!!
    }


    private fun promptModelDownload(context: Context, translator: Translator, targetLangCode: String) {
        setButtonsEnabled(true)
        AlertDialog.Builder(context)
            .setTitle("Download Model for ${getLanguageName(targetLangCode)}")
            .setMessage("This requires a one-time download (approx. 30MB) of the language model. Proceed with your current data connection?")
            .setPositiveButton("Download Now") { _, _ ->
                lifecycleScope.launch {
                    val conditions = DownloadConditions.Builder().build() // No restrictions
                    try {
                        translator.downloadModelIfNeeded(conditions).await()
                        Toast.makeText(context, "Model downloaded successfully.", Toast.LENGTH_SHORT).show()
                        translateContent(targetLangCode) // Rerun translation
                    } catch (e: Exception) {
                        Toast.makeText(context, "Model download failed.", Toast.LENGTH_LONG).show()
                        setButtonsEnabled(true)
                        updateButtonAppearance()
                    }
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                setButtonsEnabled(true)
                updateButtonAppearance()
                Toast.makeText(context, "Translation cancelled.", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun setButtonsEnabled(isEnabled: Boolean) {
        btnHindi?.isEnabled = isEnabled
        btnBangla?.isEnabled = isEnabled
    }

    private fun showLoadingState(targetLangCode: String) {
        if (targetLangCode == HINDI_CODE) {
            btnHindi?.text = "..."
        } else if (targetLangCode == BANGLA_CODE) {
            btnBangla?.text = "..."
        }
    }

    private fun updateButtonAppearance() {
        val hindiName = "हिन्"
        val banglaName = "বাং"

        btnHindi?.text = if (currentLanguage == HINDI_CODE) "EN" else hindiName
        btnBangla?.text = if (currentLanguage == BANGLA_CODE) "EN" else banglaName

        // Note: You should have a separate drawable for the 'active' state
        // if you want highlighting. Using the default selector here.
        val defaultDrawable = R.drawable.button_normal_shape

        btnHindi?.setBackgroundResource(if (currentLanguage == HINDI_CODE) R.drawable.button_pressed_shape else defaultDrawable)
        btnBangla?.setBackgroundResource(if (currentLanguage == BANGLA_CODE) R.drawable.button_pressed_shape else defaultDrawable)
    }

    private fun getLanguageName(code: String): String = when (code) {
        HINDI_CODE -> "Hindi"
        BANGLA_CODE -> "Bangla"
        else -> "English"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        translator?.close()
        translator = null
        // Nullify views to prevent memory leaks
        btnHindi = null
        btnBangla = null
    }
}