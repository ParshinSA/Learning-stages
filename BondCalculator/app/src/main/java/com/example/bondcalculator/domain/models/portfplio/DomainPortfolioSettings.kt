package com.example.bondcalculator.domain.models.portfplio

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.presentation.models.TypeInvestmentAccount
import com.example.bondcalculator.presentation.models.TypeInvestmentCurrency

data class DomainPortfolioSettings(
    val currency: TypeInvestmentCurrency,
    val account: TypeInvestmentAccount,
    val term: Long,
    val isReplenishment: Boolean,
    val startBalance: Double,
    val exchangeRateUsdToRub: Double,
    val bondTopList: List<DomainBondAndCalendar>,
)
