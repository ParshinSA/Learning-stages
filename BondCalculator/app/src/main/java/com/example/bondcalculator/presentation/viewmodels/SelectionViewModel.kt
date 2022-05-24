package com.example.bondcalculator.presentation.viewmodels

import android.util.Log
import android.widget.Button
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bondcalculator.R
import com.example.bondcalculator.common.*
import com.example.bondcalculator.domain.interactors.SelectedFrgInteractor
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.download_progress.DownloadProgress
import com.example.bondcalculator.domain.models.download_progress.ProgressData
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.presentation.common.ResourcesProvider
import com.example.bondcalculator.presentation.common.SingleLiveEvent
import com.example.bondcalculator.presentation.models.TypeBoard.TQOB
import com.example.bondcalculator.presentation.models.TypeBoard.TQOD
import com.example.bondcalculator.presentation.models.TypeInvestmentAccount
import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency.RUB
import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency.USD
import io.reactivex.schedulers.Schedulers

class SelectionViewModel(
    private val interactor: SelectedFrgInteractor,
    private val resourcesProvider: ResourcesProvider,
    downloadProgress: DownloadProgress
) : BaseViewModel() {

    init {
        compositeDisposable.add(
            interactor.getExchangerRateUsdToRub()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { usdToRub ->
                        exchangeRateUsdToRub = usdToRub.value
                    },
                    { error ->
                        error("error getExchangeRate $error")
                    }
                )
        )
    }

    init {
        compositeDisposable.add(
            downloadProgress.getProgressData()
                .subscribe(
                    { progressData ->
                        listenerDownloadProgressMutLiveData.postValue(progressData)
                    }, { error ->
                        error("subscribeDownloadProgress: ERROR progress $error")
                    }
                )
        )
    }

    private var exchangeRateUsdToRub = 0.0
    private var selectedCurrency = RUB
    private var selectedAccount = TypeInvestmentAccount.IIS
    private var isReplenish = false

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

    private val errorMessageSingleLiveEvent = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessageSingleLiveEvent

    private val isLoadingMutLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> get() = isLoadingMutLiveData

    private val isThereCalculateDataMutLiveData = MutableLiveData(false)
    val isThereCalculateDataLiveData: LiveData<Boolean> get() = isThereCalculateDataMutLiveData

    private val listenerDownloadProgressMutLiveData = MutableLiveData<ProgressData>()
    val listenerDownloadProgressLiveData: LiveData<ProgressData> get() = listenerDownloadProgressMutLiveData

    fun setValueInvestmentTerm(value: Int) {
        changeSettingsPortfolio()
        investmentTermValueMutLiveData.value = value
    }

    fun setValueInvestmentAmount(value: Int) {
        changeSettingsPortfolio()
        investmentAmountValueMutLiveData.value = value
    }

    fun isReplenishBalance(state: Boolean) {
        if (state != isReplenish) {
            changeSettingsPortfolio()
            isReplenish = state
        }
    }

    fun clickButton(button: Button) {
        changeSettingsPortfolio()
        try {
            when (button.id) {
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
            Log.d(TAG, "clickButton: Incorrect button id -> ${button.id} ")
            errorMessageSingleLiveEvent.postValue(
                resourcesProvider.getString(R.string.dialog_unknown_error)
            )
        }
    }

    private fun getBalance(): Double {
        return if (selectedCurrency == USD) {
            (investmentAmountValueLiveDta.value!! / exchangeRateUsdToRub).roundDouble()
        } else investmentAmountValueLiveDta.value!!.toDouble()
    }

    fun collectPortfolio() {
        Log.d(TAG, "collectPortfolio: ")
        isLoadingMutLiveData.postValue(true)

        compositeDisposable.add(
            interactor.getProfitableBonds(getRequest())
                .map { bondAndCalendarList ->
                    getPortfolioData(bondAndCalendarList)
                }
                .flatMap { portfolioData ->
                    interactor.calculateYieldPortfolio(portfolioData)
                }
                .subscribeOn(Schedulers.computation())
                .subscribe({ result ->
                    isThereCalculateDataMutLiveData.postValue(true)
                    interactor.saveCalculate(result)

                    val listShortName = result.purchaseHistory.map { it.key.shortName }.toSet()
                    val counter = mutableMapOf<String, Int>()

                    listShortName.forEach { shortName ->
                        result.purchaseHistory.forEach { (bond, amount) ->
                            if (bond.shortName == shortName)
                                if (counter.containsKey(shortName))
                                    counter[shortName] = counter.getValue(shortName) + amount
                                else counter[shortName] = amount
                        }
                    }
                    Log.d(TAG, "collectPortfolio: COMPLETE result $counter")
                }, {
                    errorMessageSingleLiveEvent.postValue(
                        resourcesProvider.getString(R.string.dialog_unknown_error)
                    )
                    Log.d(TAG, "collectPortfolio: ERROR $it")
                },{
                    isLoadingMutLiveData.postValue(false)
                })
        )
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

    private fun changeSettingsPortfolio() {
        isThereCalculateDataMutLiveData.postValue(false)
    }

    private fun getRequest(): DomainRequestBondList {
        return DomainRequestBondList(if (selectedCurrency == RUB) TQOB.name else TQOD.name)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}