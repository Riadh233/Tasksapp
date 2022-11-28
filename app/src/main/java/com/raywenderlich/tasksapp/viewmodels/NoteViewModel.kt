package com.raywenderlich.tasksapp.viewmodels

import android.app.Application
import android.content.Context
import android.widget.EditText
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.raywenderlich.tasksapp.NoteRepository
import com.raywenderlich.tasksapp.data.Note
import com.raywenderlich.tasksapp.data.NoteDatabase
import com.raywenderlich.tasksapp.fragments.UpdateFragmentArgs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository
    private val noteDB = NoteDatabase.getInstance(application)
    private var allNotes: LiveData<List<Note>>

    private val _navigateToAddFragment = MutableLiveData<Note>()
    val navigateToAddFragment : LiveData<Note>
    get() = _navigateToAddFragment
    val deleteMenuState: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        repository = NoteRepository(noteDB.dao())
        allNotes = repository.getAllNotes()
    }



    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }
    fun getAllNotes() : LiveData<List<Note>>{
         return allNotes
    }
    fun displayUpdateScreen(note : Note){
        _navigateToAddFragment.value = note
    }
    fun navigateToUpdateScreenFinished(){
        _navigateToAddFragment.value = null
    }
    fun searchDatabase(query : String) : LiveData<List<Note>>{
        return repository.searchDatabase(query)
    }
    fun insertDataToDatabase(context : Context, navController: NavController, etTitle : EditText, etDescription : EditText, date : String){
        viewModelScope.launch {
            repository.insertDataToDatabase(context,navController,etTitle,etDescription,date)
        }
    }
    fun updateData(context : Context, navController : NavController,
                   etTitle : EditText, etDescription : EditText,
                   date: String , args : UpdateFragmentArgs
    ){
        viewModelScope.launch {
            repository.updateData(context,navController,etTitle,etDescription,date,args)
        }
    }
}
