package com.yuaihen.policeinfo.utils
import android.graphics.Matrix

/**
 * Created by Yuaihen.
 * on 2020/11/5
 */
object MatrixUtil {

    /**
     * 存储Matrix矩阵的9个值
     * Matrix是一个3 x 3的矩阵
     *
     */
    private val matrixValues = FloatArray(9)


    /**
     * 获取Matrix在平移缩放之后的X坐标
     * @param x 缩放操作前的X坐标
     * @param matrix 变化的Matrix矩阵
     */
    fun getMatrixScaleX(x: Float, matrix: Matrix): Float {
        matrix.getValues(matrixValues)
        val mscale_x = matrixValues[0]
        val mtrans_x = matrixValues[2]
        return x * mscale_x + 1 * mtrans_x


    }

    /**
     * 获取Matrix在平移缩放之后的X坐标
     * @param y 缩放操作前的Y坐标
     * @param matrix 变化的Matrix矩阵
     */
    fun getMatrixScaleY(y: Float, matrix: Matrix): Float {
        matrix.getValues(matrixValues)
        val mscale_y = matrixValues[4]
        val mtrans_y = matrixValues[5]
        return y * mscale_y + 1 * mtrans_y
    }

}