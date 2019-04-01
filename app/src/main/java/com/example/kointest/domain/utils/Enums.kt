package com.example.kointest.domain.utils

class Enums {

    companion object {
        interface IGetPriority{
            fun getPriority() : Int
        }

        enum class Priority : IGetPriority{
            LOW{
                override fun getPriority(): Int = 1
            },

            MEDIUM{
                override fun getPriority(): Int = 2
            },

            HIGH{
                override fun getPriority(): Int = 3
            }
        }
    }



}