package com.pepedevs.radium.placeholders.managers.customs;

import com.pepedevs.radium.economy.Balance;
import com.pepedevs.radium.economy.EconomyManager;
import com.pepedevs.radium.placeholders.Placeholder;
import org.bukkit.entity.Player;

public class VaultPlaceholder implements Placeholder {

    @Override
    public String getId() {
        return "vault";
    }

    @Override
    public String resolve(Player player, String id) {
        Balance economy = EconomyManager.get(player);
        double balance = economy == null ? 0 : economy.get();

        switch (id) {
            case "eco_balance":
                return String.valueOf(balance);
            case "eco_balance_fixed":
                return String.valueOf((long) balance);
        }

        return null;
    }
}
