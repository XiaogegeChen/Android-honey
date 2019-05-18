package com.github.xiaogegechen.network

class TimeoutParam private constructor(builder: Builder) {

    private val connectTime: Long
    private val readTime: Long
    private val writeTime: Long

    init {
        connectTime = builder.connectTime
        readTime = builder.readTime
        writeTime = builder.writeTime
    }

    class Builder {
        var connectTime: Long = 0
        var readTime: Long = 0
        var writeTime: Long = 0

        fun connectTime(`val`: Long): Builder {
            connectTime = `val`
            return this
        }

        fun readTime(`val`: Long): Builder {
            readTime = `val`
            return this
        }

        fun writeTime(`val`: Long): Builder {
            writeTime = `val`
            return this
        }

        fun build(): TimeoutParam {
            return TimeoutParam(this)
        }
    }
}

