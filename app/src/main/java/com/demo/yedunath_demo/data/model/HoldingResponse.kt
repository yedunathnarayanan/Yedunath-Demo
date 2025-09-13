package com.demo.yedunath_demo.data.model

import com.demo.yedunath_demo.utils.Constants
import com.google.gson.annotations.SerializedName

data class HoldingResponse(
    @SerializedName(Constants.JSON_SYMBOL)
    val symbol: String?,
    @SerializedName(Constants.JSON_QUANTITY)
    val quantity: Int?,
    @SerializedName(Constants.JSON_LTP)
    val ltp: Double?,
    @SerializedName(Constants.JSON_AVG_PRICE)
    val avgPrice: Double?,
    @SerializedName(Constants.JSON_CLOSE)
    val close: Double?
)

data class PortfolioResponse(
    @SerializedName(Constants.JSON_DATA)
    val data: PortfolioData?
)

data class PortfolioData(
    @SerializedName(Constants.JSON_USER_HOLDING)
    val userHolding: List<HoldingResponse>?
)
