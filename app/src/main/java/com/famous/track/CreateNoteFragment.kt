package com.famous.track

import android.app.Activity.RESULT_OK
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.famous.track.database.NotesDatabase
import com.famous.track.databinding.FragmentCreateNoteBinding
import com.famous.track.databinding.FragmentHomeBinding
import com.famous.track.entites.Notes
import com.famous.track.util.NoteBottomSheetFragment
import kotlinx.android.synthetic.main.fragment_create_note.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest


class CreateNoteFragment : BaseFragment(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

//    private var _binding : FragmentCreateNoteBinding? = null
//    private val binding get() = _binding!!

    var selectedColor= "#171C26"
    var currentDate:String? = null
    private var READ_STORAGE_PERM = 123
    private var selectedImagePath = ""
    private var REQUEST_CODE_IMAGE = 456

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//        // Inflate the layout for this fragment
//        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
//
//
//        return binding.root
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_note, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                CreateNoteFragment().apply {
                    arguments = Bundle().apply {

                    }

                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
                BroadcastReceiver, IntentFilter("bottom_sheet_action")
        )

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss" )
        currentDate = sdf.format(Date())
        colorView.setBackgroundColor(Color.parseColor(selectedColor))

        tvDateTime.text =currentDate

        imgDone.setOnClickListener {
            //saveNote

            saveNote()
        }
       imgBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

//        imgMore.setOnClickListener{
//            var noteBottomSheetFragment = NoteBottomSheetFragment.newInstance()
//            noteBottomSheetFragment.show(requireActivity().supportFragmentManager, "Bottom Bottom Sheet Fragment")
//        }
    }
    private fun saveNote()
    {
        if (etNoteTitle.text.isNullOrEmpty())
        {
            Toast.makeText(context, "Note Title Required", Toast.LENGTH_SHORT).show()
        }
        if (etNoteSubTitle.text.isNullOrEmpty())
        {
            Toast.makeText(context, "Note Sub Title Required", Toast.LENGTH_SHORT).show()
        }
        if (etNoteDesc.text.isNullOrEmpty())
        {
            Toast.makeText(context, "Note Description Required", Toast.LENGTH_SHORT).show()
        }

        launch {
            val notes = Notes()
            notes.title = etNoteTitle.text.toString()
            notes.subTitle = etNoteSubTitle.text.toString()
            notes.noteText = etNoteDesc.text.toString()
            notes.dateTime = currentDate
            notes.color = selectedColor

            context?.let {
                NotesDatabase.getDatabase(it).noteDao().insertNotes(notes)
                etNoteTitle.setText("")
                etNoteSubTitle.setText("")
                etNoteDesc.setText("")


            }

        }
    }
    fun replaceFragment(fragment : Fragment, istransition:Boolean)
    {
        val fragmentTransition = activity!!.supportFragmentManager.beginTransaction()

        if (istransition)
        {
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }

    private val BroadcastReceiver : BroadcastReceiver = object :BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
            var actionColor = intent!!.getStringExtra("action")

            when (actionColor)
            {
                "Blue" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Yellow" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Purple" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Green" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Orange" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }

                "Black" -> {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }
                "image" -> {
                    readStorageTask()
                }

                else ->
                {
                    selectedColor = intent.getStringExtra("selectedColor")!!
                    colorView.setBackgroundColor(Color.parseColor(selectedColor))
                }


            }
        }

    }
    override fun onDestroy() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(BroadcastReceiver)
        super.onDestroy()
    }

    private fun hasReadStoragePerm(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

//    private fun hasWriteStoragePerm(): Boolean {
//        return EasyPermissions.hasPermissions(requireContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//    }

    private fun readStorageTask(){
        if (hasReadStoragePerm()){

            pickImageFromGallery()
            Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
        }else
        {
            EasyPermissions.requestPermissions(
                    requireActivity(),
                    getString(R.string.Storage_Permission_text),
                    READ_STORAGE_PERM,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }
     private fun pickImageFromGallery(){
         var intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
         if (intent.resolveActivity(requireActivity().packageManager) != null){
             startActivityForResult(intent,REQUEST_CODE_IMAGE)
         }
     }

    private fun getPathFromUri(contentUri: Uri): String? {
        var filePath:String? =null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null)
        {
            filePath = contentUri.path
        }else
        {
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                var selectedImageUrl = data.data
                if (selectedImageUrl != null)
                {
                    try {
                        var inputStream = requireActivity().contentResolver.openInputStream(selectedImageUrl)
                        var bitmap  = BitmapFactory.decodeStream(inputStream)
                        imageNote.setImageBitmap(bitmap)
                        imageNote.visibility = View.VISIBLE
                    }catch (e:Exception)
                    {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                    var inputStream = requireActivity().contentResolver.openInputStream(selectedImageUrl)
                    var bitmap  = BitmapFactory.decodeStream(inputStream)
                    imageNote.setImageBitmap(bitmap)
                    imageNote.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, requireActivity())
    }
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        TODO("Not yet implemented")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(requireActivity(), perms))
        {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

}