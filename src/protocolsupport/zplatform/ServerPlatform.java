package protocolsupport.zplatform;

import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_12_R1.NetworkManager;
import protocolsupport.api.ServerPlatformIdentifier;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.SpigotPacketFactory;
import protocolsupport.zplatform.impl.spigot.SpigotWrapperFactory;
import protocolsupport.zplatform.impl.spigot.injector.SpigotPlatformInjector;

public class ServerPlatform {

	private static ServerPlatform current;

	public static boolean detect() {
		if (current != null) {
			throw new IllegalStateException("Implementation already detected");
		}
		try {
			NetworkManager.class.getDeclaredFields();
			SpigotConfig.class.getDeclaredFields();
			current = new ServerPlatform(ServerPlatformIdentifier.SPIGOT, new SpigotPlatformInjector(), new SpigotMiscUtils(), new SpigotPacketFactory(), new SpigotWrapperFactory());
		} catch (Throwable t) {
		}
		try {
		} catch (Throwable t) {
		}
		return current != null;
	}

	public static ServerPlatform get() {
		if (current == null) {
			throw new IllegalStateException("Access to implementation before detect");
		}
		return current;
	}

	private final ServerPlatformIdentifier identifier;
	private final PlatformInjector injector;
	private final PlatformUtils utils;
	private final PlatformPacketFactory packetfactory;
	private final PlatformWrapperFactory wrapperfactory;
	private ServerPlatform(ServerPlatformIdentifier identifier, PlatformInjector injector, PlatformUtils miscutils, PlatformPacketFactory packetfactory, PlatformWrapperFactory wrapperfactory) {
		this.identifier = identifier;
		this.injector = injector;
		this.utils = miscutils;
		this.packetfactory = packetfactory;
		this.wrapperfactory = wrapperfactory;
	}

	public ServerPlatformIdentifier getIdentifier() {
		return identifier;
	}

	public void inject() {
		injector.inject();
	}

	public PlatformUtils getMiscUtils() {
		return utils;
	}

	public PlatformPacketFactory getPacketFactory() {
		return packetfactory;
	}

	public PlatformWrapperFactory getWrapperFactory() {
		return wrapperfactory;
	}

}
