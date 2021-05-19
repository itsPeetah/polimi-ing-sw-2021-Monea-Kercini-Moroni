package it.polimi.ingsw.application.cli.components.scenes.game;

import it.polimi.ingsw.application.cli.components.CLIScene;
import it.polimi.ingsw.view.data.GameData;
import it.polimi.ingsw.view.data.momentary.LeadersToChooseFrom;

public class CLILeadChoice extends CLIScene implements ICLIGameSubScene {

    LeadersToChooseFrom leadersToChooseFrom;

    public CLILeadChoice()
    {
        super();
        leadersToChooseFrom = null;
    }

    @Override
    public void update(GameData data) {
        leadersToChooseFrom = data.getMomentary().getLeaders();
    }

    @Override
    public void show() {
        print("LEADERS TO CHOOSE FROM:"); print("");
        print(leadersToChooseFrom.toString());
        print("");
    }
}
