package com.demo.yedunath_demo.data.mapper

import com.demo.yedunath_demo.data.model.HoldingResponse
import com.demo.yedunath_demo.data.model.PortfolioData
import com.demo.yedunath_demo.data.model.PortfolioResponse
import com.demo.yedunath_demo.domain.model.HoldingDomain
import com.demo.yedunath_demo.domain.model.PortfolioDomain
import com.demo.yedunath_demo.utils.Constants

fun HoldingResponse.toDomain(): HoldingDomain {
    return HoldingDomain(
        symbol = this.symbol ?: Constants.DEFAULT_STRING,
        quantity = this.quantity ?: Constants.DEFAULT_INT,
        ltp = this.ltp ?: Constants.DEFAULT_DOUBLE,
        avgPrice = this.avgPrice ?: Constants.DEFAULT_DOUBLE,
        close = this.close ?: Constants.DEFAULT_DOUBLE,
    )
}

fun PortfolioResponse.toDomain(): PortfolioDomain {
    val holdings = this.data?.userHolding?.map { it.toDomain() } ?: emptyList()
    return PortfolioDomain(holdings = holdings)
}
