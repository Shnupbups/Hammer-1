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

@file:Suppress("DEPRECATION", "UnstableApiUsage")

package dev.vini2003.hammer.core.api.client.texture

import dev.vini2003.hammer.core.api.client.util.DrawingUtils
import dev.vini2003.hammer.core.api.common.color.Color
import dev.vini2003.hammer.core.api.common.util.extension.toColor
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.texture.Sprite
import net.minecraft.client.util.math.MatrixStack

/**
 * A [TiledFluidTexture] is a tiled texture whose data is located through a fluid,
 * and whose tile size is defined by [tileWidth] and [tileHeight].
 */
class TiledFluidTexture @JvmOverloads constructor(
	/**
	 * Constructs a tiled fluid texture.
	 *
	 * @param fluidVariant the fluid.
	 * @param sprite the fluid's sprite.
	 * @param spriteColor the fluid's sprite color.
	 * @param tileWidth the tile's width.
	 * @param tileHeight the tile's height.
	 * @return the texture.
	 */
	private val fluid: FluidVariant,
	private val sprite: Sprite? = FluidVariantRendering.getSprite(fluid),
	private val spriteColor: Color = FluidVariantRendering.getColor(fluid).toColor(),
	private val tileWidth: Float = sprite?.width?.toFloat() ?: 0.0F,
	private val tileHeight: Float = sprite?.height?.toFloat() ?: 0.0F
) : BaseTexture {
	
	override fun draw(
		matrices: MatrixStack,
		provider: VertexConsumerProvider,
		x: Float,
		y: Float,
		width: Float,
		height: Float
	) {
		if (sprite == null) {
			return
		}
		
		DrawingUtils.drawTiledTexturedQuad(
			matrices,
			provider,
			sprite.id,
			x = x,
			y = y,
			width = width,
			height = height,
			uStart = sprite.minU,
			vStart = sprite.minV,
			uEnd = sprite.maxU,
			vEnd = sprite.maxV,
			color = spriteColor,
			tileWidth = sprite.width.toFloat(),
			tileHeight = sprite.height.toFloat()
		)
	}
}