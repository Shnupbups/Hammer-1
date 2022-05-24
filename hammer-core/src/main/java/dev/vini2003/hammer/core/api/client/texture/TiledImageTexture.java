package dev.vini2003.hammer.core.api.client.texture;

import dev.vini2003.hammer.core.api.client.color.Color;
import dev.vini2003.hammer.core.api.client.texture.base.Texture;
import dev.vini2003.hammer.core.api.client.util.DrawingUtil;
import dev.vini2003.hammer.core.api.client.util.InstanceUtil;
import dev.vini2003.hammer.core.api.client.util.LayerUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TiledImageTexture implements Texture {
	private final Identifier textureId;
	
	private final float tileWidth;
	private final float tileHeight;
	
	private final float maxTilesX;
	private final float maxTilesY;
	
	private final float stepTilesX;
	private final float stepTilesY;
	
	public TiledImageTexture(Identifier textureId, float tileWidth, float tileHeight) {
		this.textureId = textureId;
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		this.maxTilesX = Integer.MAX_VALUE;
		this.maxTilesY = Integer.MAX_VALUE;
		
		this.stepTilesX = 0.0F;
		this.stepTilesY = 0.0F;
	}
	
	public TiledImageTexture(Identifier textureId, float tileWidth, float tileHeight, float maxTilesX, float maxTilesY, float stepTilesX, float stepTilesY) {
		this.textureId = textureId;
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		this.maxTilesX = maxTilesX;
		this.maxTilesY = maxTilesY;
		
		this.stepTilesX = stepTilesX;
		this.stepTilesY = stepTilesY;
	}
	
	@Override
	public void draw(MatrixStack matrices, VertexConsumerProvider provider, float x, float y, float width, float height) {
		var client = InstanceUtil.getClient();
		
		var scaledX = x - (x % (client.getWindow().getScaledWidth() / (float) client.getWindow().getWidth()));
		var scaledY = y - (y % (client.getWindow().getScaledHeight() / (float) client.getWindow().getHeight()));
		
		var layer = LayerUtil.get(textureId);
		
		DrawingUtil.drawTiledTexturedQuad(
				matrices,
				provider,
				textureId,
				scaledX, scaledY, 0.0F,
				width, height,
				tileWidth, tileHeight,
				Integer.MAX_VALUE, Integer.MAX_VALUE,
				0.0F, 0.0F,
				0.0F, 0.0F,
				1.0F, 1.0F,
				0.0F, 0.0F, 0.0F,
				DrawingUtil.DEFAULT_OVERLAY,
				DrawingUtil.DEFAULT_LIGHT,
				Color.WHITE,
				layer
		);
	}
}