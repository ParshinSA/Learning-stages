package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.instruction.download_progress.DownloadProgress
import com.example.bondcalculator.domain.interactors.SelectedFrgInteractor
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.download_progress.DomainDownloadProgressData
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.models.TypeBoard.TQOB
import com.example.bondcalculator.presentation.models.TypeBoard.TQOD
import com.example.bondcalculator.presentation.models.TypeInvestmentAccount
import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency.RUB
import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency.USD
import io.reactivex.schedulers.Schedulers

class SelectionViewModel(
    private val interactor: SelectedFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
    private val downloadProgress: DownloadProgress
) : BaseViewModel() {

    private var exchangeRateUsdToRub = 0.0
    private var selectedCurrency = RUB
    private var selectedAccount = TypeInvestmentAccount.IIS
    private var isReplenish = false

    init {
        compositeDisposable.add(
            interactor.getExchangerRateUsdToRub()
                .subscribeOn(Schedulers.newThread())
                .subscribe({ usdToRub ->
                    exchangeRateUsdToRub = usdToRub.value
                }, {
                    Log.d("TAG", "ERROR: $it")
                    errorMessage(resourcesProvider.getString(R.string.dialog_error_update_exchange_rate))
                }
                )
        )
    }

    private val colorButtonRubMutLiveData = MutableLiveData(DEFAULT_COLOR_SELECTED_BUTTON)
    val colorButtonRubLiveData: LiveData<Int> get() = colorButtonRubMutLiveData

    private val colorButtonUsdMutLiveData = MutableLiveData(DEFAULT_COLOR_UNSELECTED_BUTTON)
    val colorButtonUsdLiveData: LiveData<Int> get() = colorButtonUsdMutLiveData

    private val colorButtonIisMutLiveData = MutableLiveData(DEFAULT_COLOR_SELECTED_BUTTON)
    val colorButtonIisLiveData: LiveData<Int> get() = colorButtonIisMutLiveData

    private val colorButtonNormalMutLiveData = MutableLiveData(DEFAULT_COLOR_UNSELECTED_BUTTON)
    val colorButtonNormalLiveData: LiveData<Int> get() = colorButtonNormalMutLiveData

    private val investmentTermValueMutLiveData = MutableLiveData(DEFAULT_INVESTMENT_TERM_SEEKBAR)
    val investmentTermValueLiveDta: LiveData<Int> get() = investmentTermValueMutLiveData

    private val investmentAmountValueMutLiveData =
        MutableLiveData(DEFAULT_INVESTMENT_AMOUNT_SEEKBAR)
    val investmentAmountValueLiveDta: LiveData<Int> get() = investmentAmountValueMutLiveData

    private val isLoadingMutLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> get() = isLoadingMutLiveData

    private val isThereCalculateDataMutLiveData = MutableLiveData(false)
    val isThereCalculateDataLiveData: LiveData<Boolean> get() = isThereCalculateDataMutLiveData

    private val listenerDownloadProgressMutLiveData = MutableLiveData<DomainDownloadProgressData>()
    val listenerDownloadProgressLiveData: LiveData<DomainDownloadProgressData> get() = listenerDownloadProgressMutLiveData

    fun setValueInvestmentTerm(value: Int) {
        investmentTermValueMutLiveData.value = value
    }

    fun setValueInvestmentAmount(value: Int) {
        investmentAmountValueMutLiveData.value = value
    }

    fun isReplenishBalance(state: Boolean) {
        isReplenish = state
    }

    fun clickButton(buttonId: Int) {
        try {
            when (buttonId) {
                R.id.buttonInvestmentCurrencyRUB -> {
                    colorButtonRubMutLiveData.value = R.color.selectedButton
                    colorButtonUsdMutLiveData.value = R.color.unSelectedButton
                    selectedCurrency = RUB
                }
                R.id.buttonInvestmentCurrencyUSD -> {
                    colorButtonUsdMutLiveData.value = R.color.selectedButton
                    colorButtonRubMutLiveData.value = R.color.unSelectedButton
                    selectedCurrency = USD
                }
                R.id.buttonInvestmentAccountIIS -> {
                    colorButtonIisMutLiveData.value = R.color.selectedButton
                    colorButtonNormalMutLiveData.value = R.color.unSelectedButton
                    selectedAccount = TypeInvestmentAccount.IIS
                }
                R.id.buttonInvestmentAccountNormal -> {
                    colorButtonNormalMutLiveData.value = R.color.selectedButton
                    colorButtonIisMutLiveData.value = R.color.unSelectedButton
                    selectedAccount = TypeInvestmentAccount.NORMAL
                }
                else -> error("")
            }
        } catch (t: Throwable) {
            errorMessage(resourcesProvider.getString(R.string.dialog_error_incorrect_id_button))
        }
    }

    private fun getBalance(): Double {
        return if (selectedCurrency == USD) {
            (investmentAmountValueLiveDta.value!! / exchangeRateUsdToRub).roundDouble()
        } else investmentAmountValueLiveDta.value!!.toDouble()
    }

    fun collectPortfolio() {
        startLoading()
        compositeDisposable.add(
            interactor.getProfitableBonds(getRequest())
                .map { bondAndCalendarList ->
                    getPortfolioData(bondAndCalendarList)
                }
                .flatMap { portfolioData ->
                    interactor.calculatePortfolioYield(portfolioData)
                }
                .subscribeOn(Schedulers.computation())
                .subscribe({ result ->
                    isThereCalculateDataMutLiveData.postValue(true)
                    interactor.analysisPortfolioYield(result)
                }, {
                    Log.d("TAG", "collectPortfolio: ERROR $it")
                    errorMessage(resourcesProvider.getString(R.string.dialog_error_calculate_portfolio))
                }, {
                    stopLoading()
                })
        )
    }

    private fun startLoading() {
        isLoadingMutLiveData.postValue(true)

        compositeDisposable.add(
            downloadProgress.start()
                .subscribe({ progressData ->
                    listenerDownloadProgressMutLiveData.postValue(progressData)
                }, {
                    Log.d("TAG", "startLoading: $it")
                    errorMessage(resourcesProvider.getString(R.string.dialog_error_unknown))
                }
                )
        )
    }

    private fun stopLoading() {
        isLoadingMutLiveData.postValue(false)
        downloadProgress.stop()
    }

    private fun getPortfolioData(bondYtmCalendarList: List<DomainBondAndCalendar>): DomainPortfolioSettings {
        return DomainPortfolioSettings(
            currency = selectedCurrency,
            account = selectedAccount,
            term = investmentTermValueLiveDta.value!!,
            isReplenishment = isReplenish,
            startBalance = getBalance(),
            exchangeRateUsdToRub = exchangeRateUsdToRub,
            bondTopList = bondYtmCalendarList
        )
    }

    private fun getRequest(): DomainRequestBondList {
        return DomainRequestBondList(if (selectedCurrency == RUB) TQOB.name else TQOD.name)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "SelectionViewModel"
    }
}