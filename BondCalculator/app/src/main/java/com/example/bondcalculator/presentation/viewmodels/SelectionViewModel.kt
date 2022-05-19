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
    private val resourcesProvider: ResourcesProvider
) : BaseViewModel() {

    init {
        compositeDisposable.add(
            interactor.getExchangerRateUsdToRub()
                .subscribeOn(Schedulers.newThread())
                .subscribe({ usdToRub ->
                    exchangeRateUsdToRub = usdToRub.value
                },
                    {
                        error("error getExchangeRate $it")
                    })
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

    private val errorMessageMutLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessageMutLiveData

    private val isLoadingMutLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean> get() = isLoadingMutLiveData

    private val isThereCalculateDataMutLiveData = MutableLiveData(false)
    val isThereCalculateDataLiveData: LiveData<Boolean> get() = isThereCalculateDataMutLiveData

    fun setValueInvestmentTerm(value: Int) {
        changeSettingsPortfolio()
        investmentTermValueMutLiveData.value = value
    }

    fun setValueInvestmentAmount(value: Int) {
        changeSettingsPortfolio()
        investmentAmountValueMutLiveData.value = value
    }

    fun isReplenishBalance(state: Boolean) {
        changeSettingsPortfolio()
        isReplenish = state
    }

    fun clickButton(button: Button) {
        changeSettingsPortfolio()

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
            else -> error("Incorrect button id -> ${button.id} ")
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
                    isLoadingMutLiveData.postValue(false)
                    isThereCalculateDataMutLiveData.postValue(true)

                    Log.d(TAG, "collectPortfolio: COMPLETE result $result")
                }, {
                    isLoadingMutLiveData.postValue(false)
                    errorMessageMutLiveData.postValue(
                        resourcesProvider.getString(R.string.dialog_unknown_error)
                    )
                    Log.d(TAG, "collectPortfolio: ERROR $it")
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

    private fun changeSettingsPortfolio(){
        isThereCalculateDataMutLiveData.postValue(false)
    }

    private fun getRequest(): DomainRequestBondList {
        return DomainRequestBondList(if (selectedCurrency == RUB) TQOB.name else TQOD.name)
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}