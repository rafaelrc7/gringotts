package org.gestern.gringotts.api.impl;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;
import org.bukkit.OfflinePlayer;
import org.gestern.gringotts.Gringotts;
import org.gestern.gringotts.api.Account;
import org.gestern.gringotts.api.Eco;
import org.gestern.gringotts.api.TransactionResult;

import java.util.ArrayList;
import java.util.List;

import static org.gestern.gringotts.Language.LANG;

/**
 * Provides the vault interface, so that the economy adapter in vault does not need to be changed. 
 *
 * @author jast
 *
 */
public class VaultConnector implements Economy {

    private final Eco eco = new GringottsEco();

    public VaultConnector() {
    }


    @Override
    public boolean isEnabled(){
        return Gringotts.G != null && Gringotts.G.isEnabled();
    }

    @Override
    public String getName() {
        return "Gringotts";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return eco.currency().fractionalDigits();
    }

    @Override
    public String format(double amount) {
        return eco.currency().format(amount);
    }

    @Override
    public String currencyNamePlural(){
        return eco.currency().namePlural();
    }

    @Override
    public String currencyNameSingular(){
        return eco.currency().name();
    }

    @Override
    public boolean hasAccount(String playerName) {
        return eco.account(playerName).exists();
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer) {
        return false;
    }

    @Override
    public double getBalance(String playerName){
        return eco.account(playerName).balance();
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer) {
        return 0;
    }

    @Override
    public boolean has(String playerName, double amount) {
        return eco.account(playerName).has(amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, double v) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {

        Account account = eco.account(playerName);
        TransactionResult removed = account.remove(amount);

        switch (removed) {
        case SUCCESS:
            return new EconomyResponse(amount, account.balance(), ResponseType.SUCCESS, null);
        case INSUFFICIENT_FUNDS:
            return new EconomyResponse(0, account.balance(), ResponseType.FAILURE, LANG.plugin_vault_insufficientFunds);
        case ERROR:
        default: 
            return new EconomyResponse(0, account.balance(), ResponseType.FAILURE, LANG.plugin_vault_error);
        }
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount){

        Account account = eco.account(playerName);
        TransactionResult added = account.add(amount);

        switch (added) {
        case SUCCESS:
            return new EconomyResponse(amount, account.balance(), ResponseType.SUCCESS, null);
        case INSUFFICIENT_SPACE:
            return new EconomyResponse(0, account.balance(), ResponseType.FAILURE, LANG.plugin_vault_insufficientSpace);
        case ERROR:
        default: 
            return new EconomyResponse(0, account.balance(), ResponseType.FAILURE, LANG.plugin_vault_error);
        }
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, double v) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //        BankAccount bank = eco.bank(name).addOwner(player);
        //        if (bank.exists())
        //        	return new EconomyResponse(0, 0, ResponseType.FAILURE, "Unable to create bank!");
        //        else
        //        	return new EconomyResponse(0, 0, ResponseType.SUCCESS, "Created bank " + name);
    }

    @Override
    public EconomyResponse createBank(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //    	Account deleted = eco.bank(name).delete();
        //    	if (deleted.exists())
        //    		return new EconomyResponse(0, 0, ResponseType.FAILURE, "Unable to delete bank account!");
        //    	else
        //    		return new EconomyResponse(0, 0, ResponseType.SUCCESS, "Deleted bank account (or it didn't exist)");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //    	double balance = eco.bank(name).balance();
        //        return new EconomyResponse(0, balance, 
        //        		ResponseType.SUCCESS, "Balance of bank "+ name +": "+ balance);
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //    	BankAccount bank = eco.bank(name);
        //    	double balance = bank.balance();
        //    	if (bank.has(amount))
        //    		return new EconomyResponse(0, balance, ResponseType.SUCCESS, "Bank " + name + " has at least " + amount );
        //    	else
        //    		return new EconomyResponse(0, balance, ResponseType.FAILURE, "Bank " + name + " does not have at least " + amount );
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //    	BankAccount bank = eco.bank(name);
        //    	TransactionResult result = bank.remove(amount);
        //    	if (result == TransactionResult.SUCCESS)
        //    		return new EconomyResponse(amount, bank.balance(), ResponseType.SUCCESS, "Removed " + amount + " from bank " + name);
        //    	else 
        //    		return new EconomyResponse(0, bank.balance(), ResponseType.SUCCESS, "Failed to remove " + amount + " from bank " + name);
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //    	BankAccount bank = eco.bank(name);
        //    	TransactionResult result = bank.add(amount);
        //    	if (result == TransactionResult.SUCCESS)
        //    		return new EconomyResponse(amount, bank.balance(), ResponseType.SUCCESS, "Added " + amount + " to bank " + name);
        //    	else 
        //    		return new EconomyResponse(0, bank.balance(), ResponseType.SUCCESS, "Failed to add " + amount + " to bank " + name);
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //        return new EconomyResponse(0, 0, eco.bank(name).isOwner(playerName)? ResponseType.SUCCESS : FAILURE, "");
    }

    @Override
    public EconomyResponse isBankOwner(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0,0, ResponseType.NOT_IMPLEMENTED, LANG.plugin_vault_notImplemented);
        //    	return new EconomyResponse(0, 0, eco.bank(name).isMember(playerName)? ResponseType.SUCCESS : FAILURE, "");
    }

    @Override
    public EconomyResponse isBankMember(String s, OfflinePlayer offlinePlayer) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return new ArrayList<>();
        //        return new ArrayList<String>(eco.getBanks());
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer) {
        return false;
    }


    @Override
    public boolean createPlayerAccount(String playerName, String world) {
        return hasAccount(playerName); // TODO multiworld support
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }


    @Override
    public EconomyResponse depositPlayer(String player, String world, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }


    @Override
    public double getBalance(String player, String world) {
        return getBalance(player);
    }

    @Override
    public double getBalance(OfflinePlayer offlinePlayer, String s) {
        return 0;
    }


    @Override
    public boolean has(String player, String world, double amount) {
        return has(player, amount);
    }

    @Override
    public boolean has(OfflinePlayer offlinePlayer, String s, double v) {
        return false;
    }


    @Override
    public boolean hasAccount(String player, String world) {
        return hasAccount(player);
    }

    @Override
    public boolean hasAccount(OfflinePlayer offlinePlayer, String s) {
        return false;
    }


    @Override
    public EconomyResponse withdrawPlayer(String player, String world, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) {
        return null;
    }

}
