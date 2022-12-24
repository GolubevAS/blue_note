package ru.ggproject.bluenote.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.muddz.styleabletoast.StyleableToast
import ru.ggproject.bluenote.R
import ru.ggproject.bluenote.model.Item
import ru.ggproject.bluenote.viewmodel.ItemViewModel


class MainActivity : AppCompatActivity() {

    private val itemViewModel : ItemViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //removing the status/tool/actionbar
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.statusBarColor)

        val recyclerView : RecyclerView = findViewById(R.id.recycler_view)
        val adapter = ItemAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        //FAB
        val buttonAddItem : FloatingActionButton = findViewById(R.id.FAB_add_item)
        buttonAddItem.setOnClickListener{
            val intent = Intent(this, AddItemActivity::class.java)
            startActivityForResult(intent, ADD_ITEM_REQUEST)
        }


        //swipe removal
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val alertDialog = AlertDialog.Builder(this@MainActivity, R.style.AlertDialogTheme).create()
                alertDialog.setCancelable(false) // clicking past our dialog will close it, but false will not
                alertDialog.setMessage("Вы уверены, что хотите удалить заметку?")
                alertDialog.setTitle("УДАЛЕНИЕ ЗАМЕТКИ")
                alertDialog.setIcon(R.mipmap.ic_launcher)
                alertDialog.window?.setBackgroundDrawableResource(R.drawable.bg_alert_dialog)

                alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE, "ДА!"
                ) { dialog: DialogInterface?, which: Int ->
                    itemViewModel.delete(adapter.getItemAt(viewHolder.adapterPosition))
                    itemViewModel.allItems.observe(
                        this@MainActivity
                    ) { items -> adapter.setItems(items) }
                    showToastDeleteNote()
                }

                alertDialog.setButton(
                    android.app.AlertDialog.BUTTON_NEGATIVE,"НЕТ"
                ) { dialog: DialogInterface?, which: Int ->
                    itemViewModel.allItems.observe(
                        this@MainActivity
                    ) { items -> adapter.setItems(items) }
                    showToastSaveNote()
                }
                alertDialog.show()

            }
        }).attachToRecyclerView(recyclerView)


        adapter.setOnItemClickListener(object :
            ItemAdapter.OnItemClickListener {
            override fun onItemClick(item: Item?) {
                if (item != null) {
                    val intent = Intent(this@MainActivity, AddItemActivity::class.java)
                    intent.putExtra(AddItemActivity.ITEM_EXTRA_KEY, item)
                    startActivityForResult(intent, EDIT_ITEM_REQUEST)
                }
            }
        })

        itemViewModel.allItems.observe(
            this
        ) { items -> adapter.setItems(items) }


    }

    //toast message
    private fun showToastDeleteNote() {
        StyleableToast.makeText(
            this@MainActivity,
            "Ваша заметка удалена",
            R.style.deleteToast
        ).show()
    }

    //toast message
    private fun showToastSaveNote() {
        StyleableToast.makeText(
            this@MainActivity,
            "Заметка оставлена",
            R.style.saveToast
        ).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_ITEM_REQUEST &&
            resultCode == RESULT_OK &&
            data != null){
            val item =
                data.getSerializableExtra(AddItemActivity.ITEM_EXTRA_KEY) as Item
            itemViewModel.insert(item)
        } else if (requestCode === EDIT_ITEM_REQUEST &&
            resultCode === RESULT_OK &&
            data != null){
            val item =
                data.getSerializableExtra(AddItemActivity.ITEM_EXTRA_KEY) as Item
            itemViewModel.update(item)
        }

    }


    companion object {
        const val ADD_ITEM_REQUEST = 1
        const val EDIT_ITEM_REQUEST = 2
    }


}