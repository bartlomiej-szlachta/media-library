package com.szlachta.medialibrary.ui.itemoptions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.szlachta.medialibrary.R
import com.szlachta.medialibrary.model.Item
import com.szlachta.medialibrary.model.ItemStatusEnum
import com.szlachta.medialibrary.model.ItemTypeEnum
import com.szlachta.medialibrary.ui.form.FormActivity
import com.szlachta.medialibrary.ui.form.FormModeEnum
import com.szlachta.medialibrary.viewmodel.DatabaseViewModel
import kotlinx.android.synthetic.main.fragment_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG_ITEM = "tag-item-to-bottom-sheet"
        const val TAG_SHEET = "item-options-bottom-sheet"
    }

    private val viewModel: DatabaseViewModel by lazy {
        ViewModelProvider(this).get(DatabaseViewModel::class.java)
    }

    private lateinit var item: Item
    private lateinit var itemType: ItemTypeEnum

    // TODO: bug: app uses light mode instead of dark mode
    //  when going back from the background
    //  if the dialog was opened at the moment app goes to the background

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments
            ?.takeIf { it.containsKey(ItemTypeEnum.TAG) }
            ?.apply {
                itemType = getSerializable(ItemTypeEnum.TAG) as ItemTypeEnum
            }
            ?.takeIf { it.containsKey(TAG_ITEM) }
            ?.apply {
                item = getSerializable(TAG_ITEM) as Item
            }

        initializeHeader()
        initializeSetPlannedOption()
        initializeSetInProgressOption()
        initializeSetFinishedOption()
        initializeSearchItemOption()
        initializeEditItemOption()
        initializeRemoveItemOption()
    }

    private fun initializeHeader() {
        current_item_title_text.text = item.title
        current_item_year_text.text = item.year?.toString()
        Glide.with(activity!!).load(item.imageUrl).into(current_item_image)
    }

    private fun initializeSetPlannedOption() {
        if (item.status != ItemStatusEnum.PLANNED) {
            item_option_set_planned.setOnClickListener {
                viewModel.updateStatus(item, itemType, ItemStatusEnum.PLANNED)
                    .observe(activity!!, Observer {
                        if (it.success) {
                            Toast.makeText(activity!!, "Marked as planned", Toast.LENGTH_SHORT)
                                .show()
                                dismiss()
                        } else {
                            Toast.makeText(activity!!, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        } else {
            item_option_set_planned.visibility = View.GONE
        }
    }

    private fun initializeSetInProgressOption() {
        if (item.status != ItemStatusEnum.IN_PROGRESS) {
            item_option_set_in_progress.setOnClickListener {
                viewModel.updateStatus(item, itemType, ItemStatusEnum.IN_PROGRESS)
                    .observe(activity!!, Observer {
                        if (it.success) {
                            Toast.makeText(activity!!, "Marked as started", Toast.LENGTH_SHORT)
                                .show()
                            dismiss()
                        } else {
                            Toast.makeText(activity!!, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        } else {
            item_option_set_in_progress.visibility = View.GONE
        }
    }

    private fun initializeSetFinishedOption() {
        if (item.status != ItemStatusEnum.FINISHED) {
            item_option_set_finished.setOnClickListener {
                viewModel.updateStatus(item, itemType, ItemStatusEnum.FINISHED)
                    .observe(activity!!, Observer {
                        if (it.success) {
                            Toast.makeText(activity!!, "Marked as finished", Toast.LENGTH_SHORT)
                                .show()
                            dismiss()
                        } else {
                            Toast.makeText(activity!!, "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        } else {
            item_option_set_finished.visibility = View.GONE
        }
    }

    private fun initializeSearchItemOption() {
        if (item.remoteId == null) {
            item_option_search.setOnClickListener {
                Toast.makeText(activity, "Search in web clicked", Toast.LENGTH_SHORT).show()
            }
        } else {
            item_option_search.visibility = View.GONE
        }
    }

    private fun initializeEditItemOption() {
        if (item.status != null) {
            item_option_edit.setOnClickListener {
                val intent = Intent(activity!!, FormActivity::class.java)
                    .putExtra(FormModeEnum.TAG, FormModeEnum.EDIT)
                    .putExtra(ItemTypeEnum.TAG, itemType)
                    .putExtra(TAG_ITEM, item)
                startActivity(intent)
                dismiss()
            }
        } else {
            item_option_edit.visibility = View.GONE
        }
    }

    private fun initializeRemoveItemOption() {
        if (item.status != null) {
            item_option_remove.setOnClickListener {
                Toast.makeText(activity, "Remove clicked", Toast.LENGTH_SHORT).show()
            }
        } else {
            item_option_remove.visibility = View.GONE
        }
    }
}