package it.polimi.ingsw.model.game.util;

/**
 * Contains the settings for creating a custom game.
 */
public class GameCustomizationSettings {

    // Members
    private final GameSettingsLevel marketTrayLevel;
    private final GameSettingsLevel devCardMarketLevel;
    private final GameSettingsLevel vaticanReportRangeLevel;

    // Constructors
    /**
     * Initialize a game with the default settings (M, H, H).
     */
    public GameCustomizationSettings() {
        this.marketTrayLevel = GameSettingsLevel.MEDIUM;
        this.devCardMarketLevel = GameSettingsLevel.HIGH;
        this.vaticanReportRangeLevel = GameSettingsLevel.HIGH;
    }

    /**
     * Create a custom game based on the desired settings.
     *
     * @param marketTrayLevel         Market Tray resources amount.
     * @param devCardMarketLevel      DevCard amount.
     * @param vaticanReportRangeLevel Vatican Report participation range.
     */
    public GameCustomizationSettings(GameSettingsLevel marketTrayLevel, GameSettingsLevel devCardMarketLevel, GameSettingsLevel vaticanReportRangeLevel) {
        this.marketTrayLevel = marketTrayLevel;
        this.devCardMarketLevel = devCardMarketLevel;
        this.vaticanReportRangeLevel = vaticanReportRangeLevel;
    }

    // Static factories

    /**
     * Returns default game settings. Same as 0-parameter constructor.
     */
    public static GameCustomizationSettings getDefaultSettings() {
        return new GameCustomizationSettings(GameSettingsLevel.MEDIUM, GameSettingsLevel.HIGH, GameSettingsLevel.HIGH);
    }

    /**
     * Returns maxed out game settings.
     */
    public static  GameCustomizationSettings getMaxSettings(){
        return new GameCustomizationSettings(GameSettingsLevel.HIGH, GameSettingsLevel.HIGH, GameSettingsLevel.HIGH);
    }

    /**
     * Returns all-medium game settings.
     */
    public static  GameCustomizationSettings getMediumSettings() {
        return new GameCustomizationSettings(GameSettingsLevel.MEDIUM, GameSettingsLevel.MEDIUM, GameSettingsLevel.MEDIUM);
    }

    /**
     * Returns lowest game settings.
     */
    public static GameCustomizationSettings getMinSettings() {
        return new GameCustomizationSettings(GameSettingsLevel.LOW, GameSettingsLevel.LOW, GameSettingsLevel.LOW);
    }

    // Getters
    /**
     * MarketTray level getter.
     */
    public GameSettingsLevel getMarketTrayLevel() {
        return marketTrayLevel;
    }

    /**
     * DevCardMarket level getter.
     */
    public GameSettingsLevel getDevCardMarketLevel() {
        return devCardMarketLevel;
    }

    /**
     * Vatican report range level getter.
     */
    public GameSettingsLevel getVaticanReportRangeLevel() {
        return vaticanReportRangeLevel;
    }
}
