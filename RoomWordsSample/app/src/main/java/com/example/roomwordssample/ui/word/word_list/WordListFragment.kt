package com.example.roomwordssample.ui.word.word_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomwordssample.R
import com.example.roomwordssample.data.db.word_db.DataBase
import com.example.roomwordssample.data.db.word_model.Word
import com.example.roomwordssample.databinding.FragmentWordListBinding
import com.example.roomwordssample.ui.word.WordViewModel

class WordListFragment : Fragment(R.layout.fragment_word_list) {

    private var _bind: FragmentWordListBinding? = null
    private val bind: FragmentWordListBinding
        get() = _bind!!

    private lateinit var adapterListWordRV: AdapterWordListRV
    private val wordViewModel: WordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FragmentWordListBinding.inflate(inflater, container, false)
        Log.d("AppLogging", "WordListFragment / onCreateView")
        actionInFragment()
        return bind.root
    }

    private fun initDataBase() {
        DataBase.initDB(requireContext())
    }

    private fun actionInFragment() {
        initDataBase()
        observeData()
        initRecyclerView()
        getAllWords()
        deletedAllWords()
        transitionAddWordFragment()
    }

    private fun deletedAllWords() {
        bind.deleteAllWordsFAB.setOnClickListener {
            wordViewModel.deleteAllWords()
        }
    }

    private fun observeData() {
        wordViewModel.wordListLiveData.observe(viewLifecycleOwner) { listWords ->
            updateRecyclerView(listWords)
        }
    }

    private fun getAllWords() {
        wordViewModel.getAllWords()
    }

    private fun updateRecyclerView(listWords: List<Word>) {
        adapterListWordRV.submitList(listWords)
    }

    private fun transitionAddWordFragment() {
        bind.addWordFAB.setOnClickListener {
            findNavController().navigate(R.id.addWordFragment)
        }
    }

    private fun initRecyclerView() {
        adapterListWordRV = AdapterWordListRV()
        with(bind.wordListRV) {
            adapter = adapterListWordRV
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

}