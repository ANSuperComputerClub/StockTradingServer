package com.anhssupercomputer.stocktradingserver.Trader;

import com.anhssupercomputer.stocktradingserver.Exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TraderService {
    /**
     * An in-memory cache of traders (decide later if we actually need this)
     */
    private ArrayList<Trader> traderList;

    public TraderService() {
        // TODO: Actually initialize a database connection
        traderList = new ArrayList<>();
    }

    /**
     * @return a list of all traders
     */
    public List<Trader> getAllTraders() {
       // TODO: Grab the traders from the database, not just those in memory
       return traderList;
    }

    /**
     * @param id The id of the user to retrieve
     * @return The user, if it found
     * @throws NotFoundException If the user cannot be found
     */
    public Trader getTraderById(int id) throws NotFoundException {
        // TODO: Include the database
        for(Trader trader: traderList) {
            if(trader.getId() == id) {
                return trader;
            }
        }
        throw new NotFoundException();
    }

    /**
     * Adds a trader to the trader list
     * @param trader
     */
    public void addTrader(Trader trader) {
        traderList.add(trader);
    }

    public void clearTraders() {
        traderList.clear();
    }

    public int getTraderNumber() {
        return traderList.size();
    }

}
