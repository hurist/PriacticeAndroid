package com.hurist.testapplication

import org.junit.Test

import org.junit.Assert.*
import kotlin.math.round
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun random() {
        repeat(10) {
            println(Random.nextFloat() * 100)
        }
    }

    @Test
    fun max() {
        val min = arrayListOf<Int>(0, 1, 2).min()
        println(min)
    }

    @Test
    fun sort() {
        val data = mutableListOf<Int>(10, 1, 2, 4, 5).sortedDescending()
        print("${data}")
    }

    @Test
    fun testRound() {
        val data = mutableListOf<Float>(1.1f, 1.2f, 1.3f, 1.4f, 1.5f, 1.6f)
        println("sum = ${data.sum()}")
        for (index in data.indices) {
            println("原始数据 ${data[index]} 处理数据 ${round(data[index])}")
            data[index] = round(data[index])
        }
        println("round sum = ${data.sum()}")
    }

    @Test
    fun testCalcAngle() {
        for (i in 0..100) {
            calcAngles(generateNum(10000, 100))
        }

    }

    fun generateNum(seed :Int, size: Int): MutableList<Float> {
        val random = Random(seed)
        val datas = mutableListOf<Float>()
        for (i in 0 until size) {
            val a = random.nextInt(0, seed)
           // print("$a,")
            datas.add(a.toFloat())
        }
        return datas
    }


    fun calcAngles(sourceData: MutableList<Float>): MutableList<Int> {
        val sum = sourceData.sum()
        val sortedData = sourceData.sortedDescending()
        val angles = mutableListOf<Int>()

        //每度等于总数的多少
        val perDegreeValue = sum / 360f

        //计算每个数据最终占了多少度
        for (data in sortedData) {
            var angle = 1
            //如果当前数据大于一度对应的数据，那么计算出实际的度数,如果小于，则直接分配1度，保证能被看见
            if (data == 0f) {
                angle = 0
            } else if (data > perDegreeValue) {
                //度数进行四舍五入保留整数位
                angle = round(data / perDegreeValue).toInt()
            }
            angles.add(angle)
        }


        var angleSum = angles.sum()

        println("first Sum：$angleSum")
        if (angleSum > 360) {
                for (i in angles.indices) {
                    if (angles[i] > 1) {
                        angles[i] = angles[i] - 1
                        if (angles.sum() == 360) {
                            break
                        }
                    }
                }
        }

        println("second Sum: ${angles.sum()}")

        return angles
    }
}
