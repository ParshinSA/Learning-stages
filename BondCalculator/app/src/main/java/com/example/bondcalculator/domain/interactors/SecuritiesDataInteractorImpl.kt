package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.interactors.intf.SecuritiesDataInteractor
import com.example.bondcalculator.domain.models.BoardId
import com.example.bondcalculator.domain.models.DomainRequestSecuritiesData
import com.example.bondcalculator.domain.models.DomainSecuritiesData
import com.example.bondcalculator.domain.repositories_intf.RequestSecuritiesDataRepository
import io.reactivex.Observable
import javax.inject.Inject

class SecuritiesDataInteractorImpl @Inject constructor(
    private val repository: RequestSecuritiesDataRepository
) : SecuritiesDataInteractor {

    override fun requestSecuritiesData(): Observable<DomainSecuritiesData> {
        val requestRussianSecuritiesData = DomainRequestSecuritiesData(BoardId.TQOB)
        val requestEuropeanSecuritiesData = DomainRequestSecuritiesData(BoardId.TQOD)

        return Observable.concat(
            repository.requestSecuritiesData(requestRussianSecuritiesData)
                .flatMap {
                         Observable.fromIterable(it)
                },
            repository.requestSecuritiesData(requestEuropeanSecuritiesData)
                .flatMap {
                    Observable.fromIterable(it)
                }
        )

    }

}