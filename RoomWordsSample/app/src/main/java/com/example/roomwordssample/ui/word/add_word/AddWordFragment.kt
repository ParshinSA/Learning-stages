package com.example.roomwordssample.ui.word.add_word

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.roomwordssample.R
import com.example.roomwordssample.data.db.word_model.Word
import com.example.roomwordssample.databinding.FragmentAddWordBinding
import com.example.roomwordssample.ui.word.WordViewModel

class AddWordFragment : Fragment(R.layout.fragment_add_word) {

    private var _bind: FragmentAddWordBinding? = null
    private val bind: FragmentAddWordBinding
        get() = _bind!!

    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentAddWordBinding.inflate(inflater, container, false)
        actionInFragment()
        return bind.root
    }

    private fun actionInFragment() {
        listenerInputWord()
        isEnabledSaveBtn()
        addWord()
    }

    private fun isEnabledSaveBtn(): Boolean {
        return !bind.inputWordET.text.let { it.isEmpty() && it.isBlank() }
    }

    private fun listenerInputWord() {
        bind.inputWordET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                isEnabledSaveBtn()
            }
        })
    }

    private fun addWord() {
        bind.saveWordBTN.setOnClickListener {
            getAndAddWordInViewModel()
            closedThisFragment()
        }
    }

    private fun closedThisFragment() {
        parentFragment?.activity?.onBackPressed()
    }

    private fun getAndAddWordInViewModel() {
        val newWord = Word(bind.inputWordET.text.toString())
        wordViewModel.insertWord(newWord)
    }
}