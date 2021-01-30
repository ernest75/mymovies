package com.example.mymovies.ui.common

import timber.log.Timber

class HyperlinkedDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        with(element) {
            return "($fileName:$lineNumber)$methodName()"
        }
    }
}
