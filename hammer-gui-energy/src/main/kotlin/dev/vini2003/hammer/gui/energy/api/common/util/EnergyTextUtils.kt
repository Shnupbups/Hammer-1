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

package dev.vini2003.hammer.gui.energy.api.common.util

import dev.vini2003.hammer.core.api.common.color.Color
import dev.vini2003.hammer.core.api.common.util.extension.*
import net.minecraft.text.Text
import net.minecraft.text.TextColor
import team.reborn.energy.api.EnergyStorage

object EnergyTextUtils {
	@JvmField
	var COLOR_OVERRIDE: Color = Color(0.67F, 0.89F, 0.47F, 1.0F)
	
	@JvmField
	val ENERGY = "text.hammer.energy".toTranslatableText().styled { style -> style.withColor(COLOR_OVERRIDE.toRGB()) }
	
	@JvmStatic
	fun getDetailedTooltips(storage: EnergyStorage): List<Text> {
		return listOf(ENERGY.styled { style -> style.withColor(COLOR_OVERRIDE.toRGB()) }, "${storage.amount.toPrettyString("E")} / ${storage.capacity.toPrettyString("E")}".toLiteralText().gray())
	}
	
	@JvmStatic
	fun getShortenedTooltips(storage: EnergyStorage): List<Text> {
		return listOf(ENERGY.styled { style -> style.withColor(COLOR_OVERRIDE.toRGB()) }, "${storage.amount.toPrettyShortenedString("E")} / ${storage.capacity.toPrettyShortenedString("E")}".toLiteralText().gray())
	}
}