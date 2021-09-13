package com.ss.android.ugc.bytex.method_trace

import com.ss.android.ugc.bytex.common.BaseExtension

/**
 * @author: archko 2021/8/28 :18:00
 */
open class MethodTraceExtension: BaseExtension() {
    private var whiteList: List<String> = ArrayList()

    override fun getName(): String {
        return "method_trace"
    }

    fun getWhiteList(): List<String> {
        return whiteList
    }

    fun setWhiteList(whiteList: List<String>) {
        this.whiteList = whiteList
    }
}