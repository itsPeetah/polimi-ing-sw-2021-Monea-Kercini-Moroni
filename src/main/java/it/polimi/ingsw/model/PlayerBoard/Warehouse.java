package it.polimi.ingsw.model.PlayerBoard;

import it.polimi.ingsw.model.general.ResourceType;
import it.polimi.ingsw.model.general.Resources;
import it.polimi.ingsw.model.cards.LeadCard;

public class Warehouse{
    private Resources content[]; // 3 risorse (1 singola top, 2 uguali, 3 uguali bottom) floor 2/1/0

    private Resources leaderExtraAvailable; //2+2 extra (2 uguali per ogni leader) uso floor 3
    private Resources leaderExtraUsed;


    public void deposit(Resources resources, int floor){
        if (floor<3){
            content[floor].add(resources);
        }else
            leaderExtraUsed.add(resources);
    }

    public Resources withdraw(Resources res) {

        Resources withdrawed = new Resources();

        for (ResourceType tipo : ResourceType.values()) {
        //cambio alla prossima risorsa da cercare

            int curr_res = res.getAmountOf(tipo); // la quantita di risorsa che serve

            for (int i = 0; i < 3; i++) { //controlla tutte e tre floor

                if (content[i].getAmountOf(tipo) >= curr_res) { //se c'e di quella risorsa piu che serve

                    try { content[i].remove(tipo, curr_res); //togli le risorse dal warehouse
                    } catch (Exception e) {
                        e.printStackTrace(); }

                    withdrawed.add(tipo, curr_res); // aggiungi su withdrawed
                    curr_res = 0; // non serve piu prendere la risorsa


                } else { //non abbiamo abbastanza risorse per pagare tutto il withdraw, andremo a cercare nel altro floor e dopo nei leader
                    curr_res -= content[i].getAmountOf(tipo);
                    withdrawed.add(tipo, content[i].getAmountOf(tipo));

                    try { content[i].remove(tipo, content[i].getAmountOf(tipo)); //azzera il warehouse floor di quella risorsa, perche abbiamo usato tutte disponibili
                    } catch (Exception e) {
                        e.printStackTrace(); }
                }
            }

            //stesso procedimemto per le risorse extra dei leader
            if (leaderExtraUsed.getAmountOf(tipo) >= curr_res) {

                try { leaderExtraUsed.remove(tipo, curr_res);
                } catch (Exception e) {
                    e.printStackTrace(); }

                    withdrawed.add(tipo, curr_res);
                    curr_res = 0;
                } else {
                    curr_res -= leaderExtraUsed.getAmountOf(tipo);
                    withdrawed.add(tipo, leaderExtraUsed.getAmountOf(tipo));

                    try {  leaderExtraUsed.remove(tipo, leaderExtraUsed.getAmountOf(tipo)); //azzera il warehouse floor di quella risorsa
                    } catch (Exception e) {
                        e.printStackTrace(); }
            }


        }
        return withdrawed; // ritorna le risorse prese che teoricamente devono essere uguale a quelle richieste
    }

    public void expandWithLeader(LeadCard leader){
        leaderExtraAvailable.add(leader.getExtraWarehouseSpace());
    }

    public Warehouse() {
        this.content = new Resources[3];
        this.leaderExtraAvailable = new Resources();
        this.leaderExtraUsed = new Resources();
    }
}