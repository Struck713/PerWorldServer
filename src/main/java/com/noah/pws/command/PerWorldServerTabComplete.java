package com.noah.pws.command;

import com.noah.pws.suite.Suite;
import com.noah.pws.suite.SuiteManager;
import com.noah.pws.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PerWorldServerTabComplete implements TabCompleter {

    private SuiteManager suiteManager;

    public PerWorldServerTabComplete(SuiteManager suiteManager) {
        this.suiteManager = suiteManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) return Arrays.asList("suite", "reload");

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("suite")) {
                List<String> toRet = getSuites();
                toRet.addAll(Arrays.asList("list", "reload", "create", "destroy"));
                return toRet;
            }
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("suite")) {
                if (args[1].equalsIgnoreCase("destroy")) {
                    return getSuites();
                }
                return Arrays.asList("add", "remove", "permission", "rename");
            }
        }

        return new ArrayList<>();
    }

    private List<String> getSuites() {
        return this.suiteManager.getAllSuites()
                .stream()
                .map(Suite::getName)
                .collect(Collectors.toList());
    }

}
