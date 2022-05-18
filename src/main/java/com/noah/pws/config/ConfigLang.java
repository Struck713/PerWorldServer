package com.noah.pws.config;

import com.noah.pws.PerWorldServer;
import com.noah.pws.util.StringUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigLang extends Config {

    public Map<String, String> values;

    public ConfigLang(PerWorldServer plugin) {
        super(plugin, "lang.yml");
        this.values = new HashMap<>();
    }

    @Override
    public void load() {
        YamlConfiguration conf = this.getConfiguration();
        this.read(conf, ""); // recursively read

        //for (Map.Entry<String, String> entry : values.entrySet()) System.out.println(entry.getKey() + " => " + entry.getValue());
    }

    public String get(String key) {
        return this.values.get(key);
    }

    public void read(ConfigurationSection section, String node) {
        for (String key : section.getKeys(false)) {
            String newNode = node + "-" + key;
            if (section.isConfigurationSection(key)) {
                ConfigurationSection next = section.getConfigurationSection(key);
                this.read(next, newNode);
                continue;
            }

            String fullKey = newNode.substring(1);
            if (section.isList(key)) {
                StringBuilder builder = new StringBuilder();
                List<String> listValues = section.getStringList(key);
                listValues.forEach(string -> builder.append("\n" + string)); //make list into line seperated string

                String fullValue = builder.substring(1);
                this.values.put(fullKey, StringUtil.colorize(fullValue));
                continue;
            }

            if (!section.isString(key)) continue;
            String fullValue = section.getString(key);
            this.values.put(fullKey, StringUtil.colorize(fullValue));
        }
    }

}
