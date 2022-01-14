package com.example.workmanager.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.R
import com.example.workmanager.data.workers.DownloadWorkerContract
import com.example.workmanager.databinding.FrgDownloadBinding

class DownloadFragment : Fragment(R.layout.frg_download) {

    private var _binding: FrgDownloadBinding? = null
    private val bind: FrgDownloadBinding
        get() = _binding!!

    private val viewModel: DownloadViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrgDownloadBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("SystemLogging", "DownloadFragment/onViewCreated")

        actionInFrg()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFrg() {
        inputListener()
        startDownloadFile()
        stopDownLoad()
        observeData()
    }

    private fun inputListener() {
        bind.enterField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                bind.downloadBtn.isEnabled =
                    bind.enterField.text!!.isNotEmpty() && bind.enterField.text!!.isNotBlank()
            }
        })
    }

    private fun observeData() {
        observeDownloadWorker()
    }

    private fun observeDownloadWorker() {
        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(DownloadWorkerContract.ID)
            .observe(
                viewLifecycleOwner,
                { if (it.isNotEmpty()) changeStateComponentFrg(it.first()) })
    }

    private fun changeStateComponentFrg(workInfo: WorkInfo) {
        val isFinished = workInfo.state.isFinished

        bind.downloadBtn.isVisible = isFinished
        bind.loader.isVisible = !isFinished
        bind.cancelBtn.isVisible = !isFinished
    }

    private fun startDownloadFile() {
        bind.downloadBtn.setOnClickListener {
            val urlToDownload = getUrl()
            viewModel.downloadFile(urlToDownload)
        }
    }

    private fun stopDownLoad() {
        bind.cancelBtn.setOnClickListener {
            viewModel.stopDownload()
        }
    }

    private fun getUrl(): String {
        return bind.enterField.text.toString().let { inputUrl ->
            when {
                inputUrl.length < 8 -> "https://$inputUrl"
                inputUrl.substring(0, 8) != "https://" -> "https://$inputUrl"
                else -> inputUrl
            }
        }
    }
}