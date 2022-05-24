package dev.vini2003.hammer.gui.api.common.screen.handler;

import dev.vini2003.hammer.core.api.client.util.InstanceUtil;
import dev.vini2003.hammer.core.api.common.math.shape.Shape;
import dev.vini2003.hammer.core.api.common.tick.Tickable;
import dev.vini2003.hammer.core.api.common.util.StackUtil;
import dev.vini2003.hammer.gui.api.common.widget.Widget;
import dev.vini2003.hammer.gui.api.common.widget.WidgetCollection;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseScreenHandler extends ScreenHandler implements WidgetCollection.Root, Tickable {
	protected final PlayerEntity player;
	
	protected Collection<Widget> widgets = new ArrayList<>();
	
	protected Shape shape = new Shape.ScreenRectangle(0.0F, 0.0F);
	
	public BaseScreenHandler(@Nullable ScreenHandlerType<?> screenHandlerType, int syncId, PlayerEntity player) {
		super(screenHandlerType, syncId);
		
		this.player = player;
	}
	
	public abstract void init(int width, int height);
	
	@Override
	public void onLayoutChanged() {
		var minimumX = Float.MAX_VALUE;
		var minimumY = Float.MIN_VALUE;
		
		var maximumX = 0.0F;
		var maximumY = 0.0F;
		
		for (var child : getChildren()) {
			if (child.getX() < minimumX) {
				minimumX = child.getX();
			}
			
			if (child.getY() < minimumY) {
				minimumY = child.getY();
			}
			
			if (child.getX() > maximumX) {
				maximumX = child.getX();
			}
			
			if (child.getY() > maximumY) {
				maximumY = child.getY();
			}
		}
		
		shape = new Shape.ScreenRectangle(maximumX - minimumX, maximumY - minimumY).translate(minimumX, minimumY, 0.0F);
		
		if (isClient()) {
			onLayoutChangedClient();
		}
	}
	
	public void onLayoutChangedClient() {
		var client = InstanceUtil.getClient();
		
		var screen = (HandledScreen<?>) client.currentScreen;
		
		screen.x = (int) shape.getStartPos().getX();
		screen.y = (int) shape.getStartPos().getY();
		
		screen.backgroundWidth = (int) shape.getWidth();
		screen.backgroundHeight = (int) shape.getHeight();
	}
	
	@Override
	public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
		if (actionType == SlotActionType.QUICK_MOVE) {
			if (slotIndex >= 0 && slotIndex < slots.size()) {
				var slot = slots.get(slotIndex);
				
				if (!slot.getStack().isEmpty() && slot.canTakeItems(player)) {
					for (var otherSlotIndex = 0; otherSlotIndex < slots.size(); ++otherSlotIndex) {
						var newSlot = slots.get(otherSlotIndex);
						
						if (!newSlot.getStack().isEmpty()) {
							if (newSlot.canInsert(slot.getStack())) {
								if (newSlot != slot && newSlot.inventory != slot.inventory) {
									StackUtil.merge(slot.getStack(), newSlot.getStack(), (stackA, stackB) -> {
										slot.setStack(stackA);
										newSlot.setStack(stackB);
									});
								}
								
								if (slot.getStack().isEmpty()) {
									break;
								}
							}
						}
					}
					
					for (var otherSlotNumber = 0; otherSlotNumber < slots.size(); ++otherSlotNumber) {
						var newSlot = slots.get(otherSlotNumber);
						
						if (newSlot.canInsert(slot.getStack())) {
							if (newSlot != slot && newSlot.inventory != slot.inventory) {
								StackUtil.merge(slot.getStack(), newSlot.getStack(), (stackA, stackB) -> {
									slot.setStack(stackA);
									slot.setStack(stackB);
								});
							}
							
							if (slot.getStack().isEmpty()) {
								break;
							}
						}
					}
				}
			}
		} else {
			super.onSlotClick(slotIndex, button, actionType, player);
		}
	}
	
	public void removeSlot(Slot slot) {
		var id = slot.id;
		
		slots.remove(id);
		
		for (var otherSlot : slots) {
			if (slot.id >= slot.id) {
				slot.id -= 1;
			}
		}
	}
	
	@Override
	public void tick() {
		for (var child : getChildren()) {
			child.tick();
		}
	}
	
	public PlayerEntity getPlayer() {
		return player;
	}
	
	@Override
	public Collection<Widget> getChildren() {
		return widgets;
	}
	
	@Override
	public int getSyncId() {
		return syncId;
	}
	
	@Override
	public boolean isClient() {
		return player.world.isClient;
	}
	
	@Override
	public BaseScreenHandler getScreenHandler() {
		return this;
	}
}