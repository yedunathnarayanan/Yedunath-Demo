package com.demo.yedunath_demo.utils

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.demo.yedunath_demo.R
import java.text.DecimalFormat

object UIUtils {
    
    private val decimalFormat = DecimalFormat(Constants.DECIMAL_FORMAT_PATTERN)
    
    /**
     * Sets text color based on P&L value (positive = green, negative = red)
     */
    fun TextView.setPnlColor(context: Context, value: Double) {
        val colorRes = if (value >= 0) R.color.green else R.color.red
        setTextColor(ContextCompat.getColor(context, colorRes))
    }
    
    /**
     * Formats currency value with proper formatting
     */
    fun formatCurrency(value: Double): String {
        return decimalFormat.format(value)
    }
    
    /**
     * Formats percentage value with proper formatting
     */
    fun formatPercentage(value: Double): String {
        return decimalFormat.format(value)
    }
    
    /**
     * Gets formatted currency text with prefix
     */
    fun getCurrencyText(context: Context, value: Double): String {
        return "${context.getString(R.string.currency_prefix)}${formatCurrency(value)}"
    }
    
    /**
     * Gets formatted percentage text with format
     */
    fun getPercentageText(context: Context, value: Double): String {
        return context.getString(R.string.percentage_format, formatPercentage(value))
    }
    
    /**
     * Gets LTP formatted text
     */
    fun getLtpText(context: Context, value: Double): String {
        return "${context.getString(R.string.ltp_prefix)}${formatCurrency(value)}"
    }
    
    /**
     * Gets P&L formatted text
     */
    fun getPnlText(context: Context, value: Double): String {
        return "${context.getString(R.string.pl_prefix)}${formatCurrency(value)}"
    }
    
    /**
     * Determines if P&L value is negative
     */
    fun isNegativePnl(value: Double): Boolean {
        return value < 0
    }
    
    /**
     * Sets P&L text with proper formatting and color
     */
    fun TextView.setPnlTextAndColor(context: Context, value: Double) {
        text = getPnlText(context, value)
        setPnlColor(context, value)
    }
    
    /**
     * Sets currency text with proper formatting and color based on P&L
     */
    fun TextView.setCurrencyTextAndColor(context: Context, value: Double, pnlValue: Double) {
        text = getCurrencyText(context, value)
        setPnlColor(context, pnlValue)
    }
}
