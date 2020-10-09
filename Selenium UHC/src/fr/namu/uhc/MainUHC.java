package fr.namu.uhc;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import fr.namu.uhc.commands.CupCMD;
import fr.namu.uhc.commands.HostCMD;
import fr.namu.uhc.commands.TeamCMD;
import fr.namu.uhc.listener.*;
import fr.namu.uhc.manager.*;
import fr.namu.uhc.menu.MenuManager;
import fr.namu.uhc.scoreboard.FastBoard;
import fr.namu.uhc.scoreboard.ScoreBoard;
import fr.namu.uhc.stuff.GameStuff;
import fr.namu.uhc.stuff.LobbyStuff;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class MainUHC extends JavaPlugin {

    public HashMap<UUID, PlayerUHC> puhc = new HashMap<>();
    public HashMap<UUID, FastBoard> boards = new HashMap<>();

    public final ScoreBoard score = new ScoreBoard(this);

    public final InfoUHC info = new InfoUHC(this);

    public final SetupManager setup = new SetupManager(this);
    public final TeamManager team = new TeamManager(this);
    public final ColorManager color = new ColorManager(this);
    public final JoinLeaveEvent join = new JoinLeaveEvent(this);
    public final TimerManager timer = new TimerManager(this);
    public final MenuManager menu = new MenuManager(this);
    public final StartManager start = new StartManager(this);
    public final WinManager win = new WinManager(this);
    public final PointsManager points = new PointsManager(this);

    public final LobbyStuff LobbyStuff = new LobbyStuff(this);
    public final GameStuff GameStuff = new GameStuff(this);

    public MVWorldManager mv = JavaPlugin.getPlugin(MultiverseCore.class).getMVWorldManager();


    @Override
    public void onEnable() {
        setup.setup();
        join.resetPlayers();


        enableListeners();
        enableCommands();
    }

    private void enableListeners() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new JoinLeaveEvent(this), this);
        pm.registerEvents(new DamageEvent(this), this);
        pm.registerEvents(new InteractEvent(this), this);
        pm.registerEvents(new ClickEvent(this), this);
        pm.registerEvents(new DeathEvent(this), this);
        pm.registerEvents(new CraftItemEvent(this), this);
        pm.registerEvents(new ItemSpawnEvent(this), this);
    }

    private void enableCommands() {
        getCommand("team").setExecutor(new TeamCMD(this));
        getCommand("host").setExecutor(new HostCMD(this));
        getCommand("cup").setExecutor(new CupCMD(this));
    }

}
