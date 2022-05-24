package dev.vini2003.hammer.gui.registry.common;

import dev.vini2003.hammer.core.HC;
import dev.vini2003.hammer.gui.api.common.event.base.Event;
import dev.vini2003.hammer.gui.api.common.event.type.EventType;
import dev.vini2003.hammer.gui.api.common.screen.handler.BaseScreenHandler;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class HGUINetworking {
	public static Identifier SYNC_SCREEN_HANDLER = HC.id("sync_screen_handler");
	public static Identifier SYNC_WIDGET_EVENT = HC.id("sync_widget_event");
	
	public static void init() {
		ServerPlayNetworking.registerGlobalReceiver(SYNC_SCREEN_HANDLER, (server, player, handler, buf, responseSender) -> {
			var width = buf.readInt();
			var height = buf.readInt();
			
			server.execute(() -> {
				var screenHandler = (BaseScreenHandler) player.currentScreenHandler;
				
				screenHandler.getChildren().clear();
				
				screenHandler.slots.clear();
				
				screenHandler.init(width, height);
			});
		});
		
		ServerPlayNetworking.registerGlobalReceiver(SYNC_WIDGET_EVENT, (server, player, handler, buf, responseSender) -> {
			var hashCode = buf.readInt();
			
			var event = Event.fromBuf(buf);
			
			server.execute(() -> {
				var screenHandler = (BaseScreenHandler) player.currentScreenHandler;
				
				for (var child : screenHandler.getAllChildren()) {
					if (child.hashCode() == hashCode) {
						child.dispatchEvent(event);
					}
				}
			});
		});
	}
}