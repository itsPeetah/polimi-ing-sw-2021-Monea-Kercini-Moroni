package it.polimi.ingsw.model.game.util;

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

    // Getters
    public GameSettingsLevel getMarketTrayLevel() {
        return marketTrayLevel;
    }

    public GameSettingsLevel getDevCardMarketLevel() {
        return devCardMarketLevel;
    }

    public GameSettingsLevel getVaticanReportRangeLevel() {
        return vaticanReportRangeLevel;
    }
}
