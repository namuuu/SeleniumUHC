package fr.namu.uhc.manager;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.TeamUHC;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PointsManager {

    private MainUHC main;
    private File file;
    private FileConfiguration config;

    public HashMap<TeamUHC, Integer> scores = new HashMap<>();

    public PointsManager(MainUHC main) {
        this.main = main;
    }

    public void addKillScore(Player player) {
        addKillScore(player, 2);
        System.out.println(player.getName() + " has just earned 2 points ! He now has " + getScore(player) + " points.");
    }

    public void addPlacementScore(Player player) {
        int teamleft = this.main.info.getTeamLeft();

        switch (teamleft) {
            case 1:
                addPlacementScore(player, 30);
                return;
            case 2:
                addPlacementScore(player, 23);
                return;
            case 3:
                addPlacementScore(player, 20);
                return;
            case 4:
                addPlacementScore(player, 18);
                return;
        }

        if(teamleft <= 10) {
            addPlacementScore(player, 15);
            return;
        }

        if(teamleft <= 15) {
            addPlacementScore(player, 12);
            return;
        }

        if(teamleft <= 22) {
            addPlacementScore(player, 8);
            return;
        }

        addPlacementScore(player, 5);
    }










    public void setup() {
        if(!this.main.getDataFolder().exists()) {
            this.main.getDataFolder().mkdir();
        }

        File file = new File(this.main.getDataFolder(),  "scores.yml");

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.file = file;
        this.config = getConfig(file);
    }

    public void saveFile() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile() {
        this.file.delete();
        setup();

        for(Player player : Bukkit.getOnlinePlayers()) {
            saveScore(player, 0);
        }
    }

    public void refreshPoints() {
        scores.clear();

        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
            String uuid = player.getUniqueId().toString();

            if(config.contains(uuid) && puhc.getTeam() != null) {
                if(!scores.containsKey(puhc.getTeam())) {
                    scores.put(puhc.getTeam(),0);
                }
                int actualScore = scores.get(puhc.getTeam());
                actualScore += config.getInt(uuid + ".score");
                actualScore += config.getInt(uuid + ".placement");
                scores.replace(puhc.getTeam(), actualScore);
            }
        }
    }

    public void saveScore(Player player, Integer score) {
        String uuid = player.getUniqueId().toString();

        if(!config.isConfigurationSection(uuid)) {
            config.createSection(uuid + ".score");
            config.createSection(uuid + ".placement");
        }

        config.set(uuid + ".score", score);
        saveFile();
    }

    public void addKillScore(Player player, Integer score) {
        String uuid = player.getUniqueId().toString();

        if(!config.isConfigurationSection(uuid)) {
            config.createSection(uuid + ".score");
            config.createSection(uuid + ".placement");
            config.set(uuid + ".score", 0);
            config.set(uuid + ".placement", 0);
        }

        config.set(uuid + ".score", score + config.getInt(uuid + ".score"));
        saveFile();
    }

    public void addPlacementScore(Player player, Integer score) {
        String uuid = player.getUniqueId().toString();

        System.out.println(player.getName() + " has just earned placement points ! He earned " + score + ".");

        if(!config.isConfigurationSection(uuid)) {
            config.createSection(uuid + ".score");
            config.createSection(uuid + ".placement");
            config.set(uuid + ".score", 0);
            config.set(uuid + ".placement", 0);
        }

        config.set(uuid + ".placement", score + config.getInt(uuid + ".placement"));
        saveFile();
    }

    public int getScore(Player player) {
        String uuid = player.getUniqueId().toString();

        if(!config.isConfigurationSection(uuid)) {
            config.createSection(uuid + ".score");
            config.createSection(uuid + ".placement");
            config.set(uuid + ".score", 0);
            config.set(uuid + ".placement", 0);
        }

        return config.getInt(uuid + ".score");
    }

    public int getTeamScore(TeamUHC team) {
        int score = 0;
        for(Player player : team.getPlayers()) {
            String uuid = player.getUniqueId().toString();

            if(!config.isConfigurationSection(uuid)) {
                config.createSection(uuid + ".score");
                config.createSection(uuid + ".placement");
                config.set(uuid + ".score", 0);
                config.set(uuid + ".placement", 0);
            }

            score += config.getInt(uuid + ".score");
            score += config.getInt(uuid + ".placement");
        }

        return score;
    }












    private FileConfiguration getConfig(File file) {
        return (FileConfiguration) YamlConfiguration.loadConfiguration(file);
    }
}
