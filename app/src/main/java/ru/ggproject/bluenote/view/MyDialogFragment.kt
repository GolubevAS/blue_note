package ru.ggproject.bluenote.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.ggproject.bluenote.R



class MyDialogFragment : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context: Context = requireActivity()

        return AlertDialog.Builder(context)
            .setCancelable(false) // clicking past our dialog will close it, but false will not
            .setMessage("Вы уверены, что хотите удалить заметку?")
            .setTitle("Удаление заметки")
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(
                "ДА"
            ) { dialog: DialogInterface?, which: Int ->
                //TODO
            }

            .setNegativeButton(
                "НЕТ"
            ) { dialog: DialogInterface?, which: Int ->
                //TODO
            }
            .create()



    }


}