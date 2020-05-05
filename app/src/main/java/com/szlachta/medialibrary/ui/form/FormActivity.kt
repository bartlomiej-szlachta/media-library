package com.szlachta.medialibrary.ui.form

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.viewmodel.DatabaseViewModel
import kotlinx.android.synthetic.main.activity_form.*
import java.util.Calendar

class FormActivity : AppCompatActivity() {

    private val viewModel: DatabaseViewModel by lazy {
        ViewModelProvider(this).get(DatabaseViewModel::class.java)
    }

    private lateinit var mode: FormModeEnum
    private lateinit var itemType: ItemTypeEnum

    private var isTitleTouched: Boolean = false
    private var isYearTouched: Boolean = false

    private val onTitleChangedListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (isTitleTouched) {
                checkIfTitleCorrect(s.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private val onTitleFocusChangedListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            checkIfTitleCorrect((v as EditText).text.toString())
        }
    }

    private val onYearChangedListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (isYearTouched) {
                checkIfYearCorrect(s.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private val onYearFocusChangedListener = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) {
            checkIfYearCorrect((v as EditText).text.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContentView(R.layout.activity_form)

        mode = intent.getSerializableExtra(FormModeEnum.ARG) as FormModeEnum
        itemType = intent.getSerializableExtra(ItemTypeEnum.ARG) as ItemTypeEnum

        setSupportActionBar(action_bar_form)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_clear)
        supportActionBar?.title = createActionBarTitle()

        input_title.addTextChangedListener(onTitleChangedListener)
        input_title.onFocusChangeListener = onTitleFocusChangedListener
        input_year.addTextChangedListener(onYearChangedListener)
        input_year.onFocusChangeListener = onYearFocusChangedListener
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                val isTitleCorrect = checkIfTitleCorrect(input_title.text.toString())
                val isYearCorrect = checkIfYearCorrect(input_year.text.toString())

                if (isTitleCorrect && isYearCorrect) {
                    val year = if (input_year.text.isNullOrEmpty()) {
                        null
                    } else {
                        input_year.text.toString().toInt()
                    }
                    val newItem = Item(
                        title = input_title.text.toString(),
                        year = year
                    )
                    saveData(newItem)
                }

                true
            }
            else -> false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun createActionBarTitle(): String {
        val actionName: String = when (mode) {
            FormModeEnum.CREATE -> getString(R.string.title_form_create)
            FormModeEnum.EDIT -> getString(R.string.title_form_edit)
        }
        val itemTypeName: String = when (itemType) {
            ItemTypeEnum.GAMES -> getString(R.string.title_form_game)
            ItemTypeEnum.MOVIES -> getString(R.string.title_form_movie)
            ItemTypeEnum.BOOKS -> getString(R.string.title_form_book)
        }
        return "$actionName $itemTypeName"
    }

    private fun checkIfTitleCorrect(title: String): Boolean {
        isTitleTouched = true
        if (title.isEmpty()) {
            input_title_layout.error = getString(R.string.error_title_required)
            return false
        }

        input_title_layout.error = null
        return true
    }

    private fun checkIfYearCorrect(year: String): Boolean {
        isYearTouched = true

        if (year.isEmpty()) {
            input_year_layout.error = null
            return true
        }

        if (year.toInt() < 1450 || year.toInt() > Calendar.getInstance().get(Calendar.YEAR)) {
            input_year_layout.error = getString(R.string.error_year_invalid)
            return false
        }

        input_year_layout.error = null
        return true
    }

    private fun saveData(item: Item) {
        viewModel.saveItem(item, itemType).observe(this, Observer {
            if (it.success) {
                Toast.makeText(this, "Item created", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Operation rejected", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
