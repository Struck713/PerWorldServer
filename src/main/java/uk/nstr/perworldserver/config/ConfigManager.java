package uk.nstr.perworldserver.config;

import java.util.List;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import org.bukkit.util.FileUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import uk.nstr.perworldserver.util.string.StringUtil;

public class ConfigManager
{

    private String name;
    private JavaPlugin plugin;
    private FileConfiguration conf;

    public ConfigManager(final JavaPlugin plugin, final String name) {
        this.name = name;
        this.plugin = plugin;
    }

    public boolean defaults() {
        final File configFile = this.getConfigFile();
        if (!configFile.exists()) {
            this.saveDefault();
            return false;
        }
        this.reload();
        final String configVersion = this.conf.getString("config-version");
        if (configVersion != null && configVersion.equalsIgnoreCase(this.plugin.getDescription().getVersion())) {
            return false;
        }
        this.backup();
        return true;
    }

    public void reload() {
        final File configFile = this.getConfigFile();
        if (!configFile.exists()) {
            this.saveDefault();
            return;
        }
        this.conf = YamlConfiguration.loadConfiguration(configFile);
    }

    public void backup() {
        final File configFile = this.getConfigFile();
        FileUtil.copy(configFile, new File(configFile.getParentFile(), this.name + ".backup"));
        this.saveDefault();
    }

    public void saveDefault() {
        final File configFile = this.getConfigFile();
        final InputStream stream = this.plugin.getResource(this.name);
        this.conf = YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8));
        try {
            this.conf.save(configFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getPrefix() {
        return this.getString("messages.prefix");
    }

    public String getLanguageValue(final String node) {
        return this.getPrefix() + StringUtil.color(this.getString(node));
    }

    public String getLanguageList(final String node) {
        final List<String> lists = (List<String>)this.conf.getStringList(node);
        final StringBuilder sb = new StringBuilder();
        for (final String s : lists) {
            sb.append(StringUtil.color(s) + "\n");
        }
        return sb.substring(0, sb.lastIndexOf("\n"));
    }

    public String getString(final String node) {
        final String retrive = this.conf.getString(node);
        return StringUtil.color(retrive);
    }

    public boolean getBoolean(final String node) {
        return this.conf.getBoolean(node);
    }

    public File getConfigFile() {
        final File folder = this.plugin.getDataFolder();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        final File configFile = new File(folder, this.name);
        return configFile;
    }
}