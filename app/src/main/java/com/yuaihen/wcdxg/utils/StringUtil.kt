package com.yuaihen.wcdxg.utils

import java.util.regex.Pattern


/**
 * Created by Yuaihen.
 * on 2021/6/16
 */
object StringUtil {

    fun removeAllBank(str: String?): String {
        var s = ""
        str?.let {
            val p = Pattern.compile("\\s*|\t|\r|\n")
            val m = p.matcher(str)
            s = m.replaceAll("")
        }
        return s
    }

    fun removeAllBank(str: String?, count: Int): String {
        var s = ""
        str?.let {
            val p = Pattern.compile("\\s{$count,}|\t|\r|\n")
            val m = p.matcher(str)
            s = m.replaceAll(" ")
        }
        return s
    }
}