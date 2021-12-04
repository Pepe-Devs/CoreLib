package com.pepedevs.corelib.placeholders.managers.customs;

import com.pepedevs.corelib.economy.Balance;
import com.pepedevs.corelib.economy.EconomyManager;
import com.pepedevs.corelib.placeholders.Placeholder;
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
