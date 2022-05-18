package com.noah.pws.command;

import com.noah.pws.config.ConfigLang;
import com.noah.pws.suite.Suite;
import com.noah.pws.suite.SuiteManager;
import com.noah.pws.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PerWorldServerCommand implements CommandExecutor {

    private static final String PREFIX = StringUtil.colorize("&8[&3PWS&8] &7");

    private SuiteManager suiteManager;
    private ConfigLang language;

    public PerWorldServerCommand(SuiteManager suiteManager, ConfigLang language) {
        this.suiteManager = suiteManager;
        this.language = language;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("suite")) { displayHelpSuites(sender); return true; }
            if (args[0].equalsIgnoreCase("reload")) {
                // TODO reload configs
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
                        sender.sendMessage(PREFIX + StringUtil.colorize("There doesn't seem to be a suite with that name."));
                        return true;
                    }
                    this.suiteManager.destroy(suite);
                    sender.sendMessage(PREFIX + StringUtil.colorize("The suite, &f" + name + "&7, was removed."));
                    return true;
                }
                displayHelpSuites(sender);
                return true;
            }
        }

        if (args.length == 4) {
            if (args[0].equalsIgnoreCase("suite")) {
                String name = args[1];
                String value = args[3];
                if (args[2].equalsIgnoreCase("add")) {
                    // TODO add world
                    return true;
                }
                if (args[2].equalsIgnoreCase("remove")) {
                    // TODO remove world
                    return true;
                }
                if (args[2].equalsIgnoreCase("permission")) {
                    // TODO set permission
                    return true;
                }
                if (args[2].equalsIgnoreCase("rename")) {
                    // TODO set name
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
        builder.append("&3&lPerWorldServer &7- &f&lSuite List\n");
        builder.append("  \n");

        this.suiteManager.getAllSuites().forEach(suite -> builder.append("&7 - &3" + suite.getName() + "  &f" + suite.getWorlds().size() + " world(s)\n"));

        builder.append("  \n");
        builder.append("&7&m----------------------\n");

        sender.sendMessage(StringUtil.colorize(builder.toString()));
    }

    public void displayHelpSuites(CommandSender sender) {
        sender.sendMessage(StringUtil.colorize(
        "&7&m----------------------\n" +
                "&3&lPerWorldServer &7- &f&lSuites\n" +
                "  \n" +
                "&7 - &3/pws suite <name>  &fLists suite information\n" +
                "&7 - &3/pws suite create <name>  &fCreates a suite\n" +
                "&7 - &3/pws suite destroy <name>  &fDestroys a suite\n" +
                "  \n" +
                "&7 - &3/pws suite <name> add <world>  &fAdds a world to a suite\n" +
                "&7 - &3/pws suite <name> remove <world>  &fRemoves a world from a suite\n" +
                "&7 - &3/pws suite <name> permission <world>  &fSets suite permission\n" +
                "&7 - &3/pws suite <name> rename <new name>  &fChanges suite name\n" +
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
                "&7 - &3/pws reload  &fReloads configurations\n" +
                "  \n" +
                "&7&m----------------------"
        ));
    }


}
