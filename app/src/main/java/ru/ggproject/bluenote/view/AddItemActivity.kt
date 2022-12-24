package ru.ggproject.bluenote.view

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.ContextCompat
import ru.ggproject.bluenote.R
import ru.ggproject.bluenote.databinding.ActivityAddItemBinding
import ru.ggproject.bluenote.model.Item
import io.github.muddz.styleabletoast.StyleableToast

class AddItemActivity : AppCompatActivity() {

    private lateinit var descriptionEditText: EditText
    private val binding : ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //removing the status/tool/actionbar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this@AddItemActivity, R.color.statusBarColor)

        descriptionEditText =findViewById(R.id.descriptionEditText)
        //adding a font
        descriptionEditText.typeface = Typeface.createFromAsset(
            this.assets,
            "SimplehandwrittingRegular-ggDP.ttf")

        val item = intent.getSerializableExtra(ITEM_EXTRA_KEY) as? Item
        if (item != null) {
            descriptionEditText.setText(item.description)
        }

        binding.buttonSave.setOnClickListener{
            saveItem()
        }


    }

    private fun saveItem () {

        val description = descriptionEditText.text.toString()
        if (description.isBlank()) {
            showToastEnterNote()
            return
        }
        val result = Item(description)
        val id = (intent.getSerializableExtra(ITEM_EXTRA_KEY) as? Item)?.id
        if (id != null) {
            result.id = id
        }
        val data = Intent()
        data.putExtra(ITEM_EXTRA_KEY, result)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    //toast message
    private fun showToastEnterNote() {
        StyleableToast.makeText(
            this,
            "Пожалуйста напишите заметку...",
            R.style.enterToast
        ).show()
    }

    companion object {
        const val ITEM_EXTRA_KEY = "ru.ggproject.bluenote"
    }


}