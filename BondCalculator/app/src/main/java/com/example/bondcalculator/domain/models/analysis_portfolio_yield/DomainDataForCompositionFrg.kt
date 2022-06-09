package com.example.bondcalculator.domain.models.analysis_portfolio_yield

data class DomainDataForCompositionFrg(
    val listShortName: List<String>,
    val counterBonds: Map<String, Int>,
    val percentPrice: Map<String, Double>,
    val nominal: Map<String, Double>
)