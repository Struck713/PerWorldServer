package com.noah.pws.command;

import com.noah.pws.config.ConfigLang;
import com.noah.pws.config.ConfigSettings;
import com.noah.pws.suite.Suite;
import com.noah.pws.suite.SuiteManager;
import com.noah.pws.util.LocationUtil;
import com.noah.pws.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PerWorldServerCommand implements CommandExecutor {

    private static final String PREFIX = StringUtil.colorize("&8[&3PWS&8] &7");

    private SuiteManager suiteManager;
    private ConfigSettings settings;

    public PerWorldServerCommand(SuiteManager suiteManager, ConfigSettings settings) {
        this.suiteManager = suiteManager;
        this.settings = settings;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("suite")) { displayHelpSuites(sender); return true; }
            if (args[0].equalsIgnoreCase("addons")) { return true; }
            if (args[0].equalsIgnoreCase("reload")) {
                this.settings.reload(true);
                sender.sendMessage(PREFIX + StringUtil.colorize("Successfully reloaded &fconfig.yml&7."));
                return true;
            }
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("suite")) {
                if (args[1].equalsIgnoreCase("list")) {
                    displaySuiteList(sender);
                    return true;
                }
                if (args[1].equalsIgnoreCase("reload")) {
                    this.suiteManager.saveAll();
                    this.suiteManager.loadAll();
                    sender.sendMessage(PREFIX + StringUtil.colorize("Successfully reloaded &f" + this.suiteManager.size() + " suite(s)&7."));
                    return true;
                }

                String name = args[1];
                Suite suite = this.suiteManager.getSuiteByName(name);
                if (suite == null) {
                    sender.sendMessage(PREFIX + StringUtil.colorize("&f" + name + "&f doesn't seem to be a suite."));
                    return true;
                }

                displaySuiteInfo(suite, sender);
                return true;
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("suite")) {
                String name = args[2];
                if (args[1].equalsIgnoreCase("create")) {
                    this.suiteManager.create(name);
                    sender.sendMessage(PREFIX + StringUtil.colorize("The suite, &f" + name + "&7, was created."));
                    return true;
                }
                if (args[1].equalsIgnoreCase("destroy")) {
                    Suite suite = this.suiteManager.getSuiteByName(name);
                    if (suite == null) {
                        sender.sendMessage(PREFIX + StringUtil.colorize("&f" + name + "&f doesn't seem to be a suite."));
                        return true;
                    }
                    this.suiteManager.destroy(suite);
                    sender.sendMessage(PREFIX + StringUtil.colorize("The suite, &f" + name + "&7, was removed."));
                    return true;
                }

                if (args[2].equalsIgnoreCase("spawn")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(PREFIX + "Please use this command in-game.");
                    }
                    Player player = (Player)sender;
                    Location location = player.getLocation();
                    World world = player.getWorld();

                    Suite suite = this.suiteManager.getSuiteByWorld(world);
                    suite.setSpawn(location);

                    sender.sendMessage(PREFIX + StringUtil.colorize("Set &f" + name + "&7's spawn to &f" + LocationUtil.toString(location) + "&7."));
                    return true;
                }
                displayHelpSuites(sender);
                return true;
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("suite")) {
                String name = args[1];

                Suite suite = this.suiteManager.getSuiteByName(name);
                if (suite == null) {
                    sender.sendMessage(PREFIX + StringUtil.colorize("&f" + name + "&7 doesn't seem to be a suite."));
                    return true;
                }

                String value = args[3];
                if (args[2].equalsIgnoreCase("add")) {
                    World world = Bukkit.getWorld(value);
                    if (world == null) {
                        sender.sendMessage(PREFIX + StringUtil.colorize("&f" + value + "&7 doesn't seem to be a world."));
                        return true;
                    }
                    if (this.suiteManager.getSuiteByWorld(world) != null) {
                        sender.sendMessage(PREFIX + StringUtil.colorize("&f" + value + "&7 is already apart of another suite."));
                        return true;
                    }
                    suite.addWorld(world);
                    sender.sendMessage(PREFIX + StringUtil.colorize("The world, &f" + value + "&7, was added to &f" + name + "&7."));
                    return true;
                }
                if (args[2].equalsIgnoreCase("remove")) {
                    World world = Bukkit.getWorld(value);
                    if (world == null) {
                        sender.sendMessage(PREFIX + StringUtil.colorize("&f" + value + "&7 doesn't seem to be a world."));
                        return true;
                    }
                    if (!suite.hasWorld(world)) {
                        sender.sendMessage(PREFIX + StringUtil.colorize("&f" + value + "&7 is not apart of this suite."));
                        return true;
                    }
                    suite.removeWorld(world);
                    sender.sendMessage(PREFIX + StringUtil.colorize("The world, &f" + value + "&7, was removed from &f" + name + "&7."));
                    return true;
                }
                if (args[2].equalsIgnoreCase("permission")) {
                    suite.setPermission(value);
                    sender.sendMessage(PREFIX + StringUtil.colorize("Set &f" + name + "&7's permission to &f" + value + "&7."));
                    return true;
                }
                if (args[2].equalsIgnoreCase("rename")) {
                    suite.setName(value);
                    sender.sendMessage(PREFIX + StringUtil.colorize("Set &f" + name + "&7's name to &f" + value + "&7."));
                    return true;
                }

                displayHelpSuites(sender);
                return true;
            }
        }

        // help menu
        displayHelp(sender);
        return true;
    }

    public void displaySuiteList(CommandSender sender) {
        StringBuilder builder = new StringBuilder();
        builder.append("&7&m----------------------\n");
        builder.append("&3&lPerWorldServer &7: &f&lSuite List\n");
        builder.append("  \n");

        this.suiteManager.getAllSuites().forEach(suite -> builder.append("&7 - &3" + suite.getName() + "  &f" + suite.getWorlds().size() + " world(s)\n"));

        builder.append("  \n");
        builder.append("&7&m----------------------\n");

        sender.sendMessage(StringUtil.colorize(builder.toString()));
    }

    public void displaySuiteInfo(Suite suite, CommandSender sender) {
        StringBuilder builder = new StringBuilder();

        builder.append("&7&m----------------------\n");
        builder.append("&3&lPerWorldServer &7: &f&lSuite Information\n");
        builder.append("  \n");
        builder.append("  &3File: &f" + suite.getFile() + "\n");
        builder.append("  &3Name: &f" + suite.getName() + "\n");
        builder.append("  &3Permission: &f" + suite.getPermission() + "\n");
        builder.append("  &3Spawn: &f" + LocationUtil.toString(suite.getSpawn()) + "\n");

        if (!suite.getWorlds().isEmpty()) {
            builder.append("  &3Worlds: \n");
            suite.getWorlds().forEach(world -> builder.append("    &7- &f" + world.getName() + "\n"));
        } else {
            builder.append("  &3Worlds: &fNone\n");
        }

        builder.append("  \n");
        builder.append("&7&m----------------------");

        sender.sendMessage(StringUtil.colorize(builder.toString()));
    }

    public void displayHelpSuites(CommandSender sender) {
        sender.sendMessage(StringUtil.colorize(
        "&7&m----------------------\n" +
                "&3&lPerWorldServer &7: &f&lSuites\n" +
                "  \n" +
                "&7 - &3/pws suite <name>  &fLists suite information\n" +
                "&7 - &3/pws suite create <name>  &fCreates a suite\n" +
                "&7 - &3/pws suite destroy <name>  &fDestroys a suite\n" +
                "  \n" +
                "&7 - &3/pws suite <name> add <world>  &fAdds a world to a suite\n" +
                "&7 - &3/pws suite <name> remove <world>  &fRemoves a world from a suite\n" +
                "&7 - &3/pws suite <name> permission <world>  &fSets suite permission\n" +
                "&7 - &3/pws suite <name> rename <new name>  &fChanges suite name\n" +
                "&7 - &3/pws suite <name> spawn  &fSets suite spawn to current location\n" +
                "  \n" +
                "&7 - &3/pws suite list  &fLists all suites\n" +
                "&7 - &3/pws suite reload  &fSaves and reloads all suites\n" +
                "  \n" +
                "&7&m----------------------"
        ));
    }

    public void displayHelp(CommandSender sender) {
        sender.sendMessage(StringUtil.colorize(
        "&7&m----------------------\n" +
                "&3&lPerWorldServer\n" +
                "  \n" +
                "&7 - &3/pws  &fShows this menu\n" +
                "&7 - &3/pws suite  &fShows suites help menu\n" +
                "&7 - &3/pws addon  &fShows addons help menu\n" +
                "&7 - &3/pws reload  &fReloads configurations\n" +
                "  \n" +
                "&7&m----------------------"
        ));
    }


}
