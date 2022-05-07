/*
 * MIT License
 *
 * Copyright (c) 2020 - 2022 vini2003
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.vini2003.hammer.core.api.common.util

object NumberUtils {
	private val UNITS = arrayOf("", "k", "M", "G", "T", "P", "E", "Z", "Y")
	
	@JvmStatic
	fun getUnit(exponent: Int): String {
		return UNITS.getOrElse(exponent) { "∞" }
	}

	@JvmStatic
	fun getExponent(number: Number): Int {
		var number = number.toLong()
		
		var exponent = 0
		
		while (number >= 1000) {
			number /= 1000
			
			exponent += 1
		}
		
		return exponent
	}
	
	@JvmStatic
	fun getPrettyString(number: Number, unit: String): String {
		return String.format("%,d%s", number, unit)
	}
	
	@JvmStatic
	fun getPrettyShortenedString(number: Number, unit: String): String {
		val exponent = getExponent(number)
		val symbol = getUnit(exponent)
		
		return when (number) {
			is Float, is Double -> String.format("%,.2f%s%s", number, symbol, unit)
			is Int, is Long -> String.format("%,d%s%s", number, symbol, unit)
			
			else -> "${number}${unit}"
		}
	}
}