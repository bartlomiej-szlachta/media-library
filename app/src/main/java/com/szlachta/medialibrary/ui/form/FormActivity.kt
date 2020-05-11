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
import com.szlachta.medialibrary.model.ItemStatusEnum
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.viewmodel.DatabaseViewModel
import kotlinx.android.synthetic.main.activity_form.*
import java.util.Calendar

class FormActivity : AppCompatActivity() {
    companion object {
        const val TAG_ITEM = "tag-item-to-bottom-sheet"
    }

    private val viewModel: DatabaseViewModel by lazy {
        ViewModelProvider(this).get(DatabaseViewModel::class.java)
    }

    private lateinit var mode: FormModeEnum
    private lateinit var itemType: ItemTypeEnum

    private var item: Item? = null
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

        mode = intent.getSerializableExtra(FormModeEnum.TAG) as FormModeEnum
        itemType = intent.getSerializableExtra(ItemTypeEnum.TAG) as ItemTypeEnum

        if (mode == FormModeEnum.EDIT) {
            item = intent.getSerializableExtra(TAG_ITEM) as Item
            initializeFieldsEditMode()
        }

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
                    onSaveData()
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

    private fun initializeFieldsEditMode() {
        when (item?.status) {
            ItemStatusEnum.PLANNED -> input_chip_planned.isChecked = true
            ItemStatusEnum.IN_PROGRESS -> input_chip_in_progress.isChecked = true
            ItemStatusEnum.FINISHED -> input_chip_finished.isChecked = true
        }
        input_title.setText(item?.title)
        if (item?.year != null) {
            input_year.setText(item?.year.toString())
        }
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

    private fun onSaveData() {
        val title = input_title.text.toString()
        val year = if (input_year.text.isNullOrEmpty()) {
            null
        } else {
            input_year.text.toString().toInt()
        }
        val status = getChosenStatus()
        val newItem = when (mode) {
            FormModeEnum.CREATE -> Item(
                title = title,
                year = year,
                status = status
            )
            FormModeEnum.EDIT -> Item(
                title = title,
                firebaseId = item?.firebaseId,
                remoteId = item?.remoteId,
                year = year,
                imageUrl = item?.imageUrl,
                status = status
            )
        }

        viewModel.saveItem(newItem, itemType).observe(this, Observer {
            if (it.success) {
                Toast.makeText(this, "Item saved", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Operation rejected", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getChosenStatus(): ItemStatusEnum? {
        return when (input_status_chips.checkedChipId) {
            R.id.input_chip_planned -> ItemStatusEnum.PLANNED
            R.id.input_chip_in_progress -> ItemStatusEnum.IN_PROGRESS
            R.id.input_chip_finished -> ItemStatusEnum.FINISHED
            else -> null
        }
    }
}
