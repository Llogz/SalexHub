package jar.llogzz.salexhub;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class SalexHub extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("hub").setExecutor(this);
        getCommand("lobby").setExecutor(this);

        saveDefaultConfig();

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getLogger().info("SalexHub enabled successfully! Running on version " + getServer().getVersion());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("hub") || cmd.getName().equalsIgnoreCase("lobby")) {
            // Connect player to lobby server
            player.sendMessage("§aConnecting to hub...");
            connectToServer(player, "lobby");
            return true;
        }

        return false;
    }

    private void connectToServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            out.writeUTF("Connect");
            out.writeUTF(server);

            player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
        } catch (IOException e) {
            player.sendMessage("§cFailed to connect to hub!");
            getLogger().warning("Failed to send player " + player.getName() + " to hub: " + e.getMessage());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("SalexHub disabled successfully!");
    }
}
