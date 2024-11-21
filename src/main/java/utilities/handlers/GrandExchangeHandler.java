package utilities.handlers;

import config.GlobalVariables;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.GrandExchange;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import utilities.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dreambot.api.utilities.Logger.log;

public class GrandExchangeHandler {
    private static int currentPercentage = 5;
    public static void sellItemList(List<String> items, int maximumPercentageDiscount) {
        if (!GrandExchange.isOpen()) {
            API.status = "Opening GE";
            if (GrandExchange.open()) {
                Sleep.sleepUntil(() -> GrandExchange.isOpen(), 2000, 100);
            }
        } else {
            if (GrandExchange.getOpenSlots() == 0) {
                log("We don't have any open slots... Let's open up slots.");
                if (GrandExchange.isReadyToCollect()) {
                    API.status = "Collecting GE";
                    log("We are ready to collect.");
                    if (GrandExchange.collect()) {
                        Sleep.sleepUntil(() -> !GrandExchange.isReadyToCollect(), 2000, 100);
                    }
                } else {
                    API.status = "Cancelling offers";
                    if (GrandExchange.cancelAll()) {
                        log("Cancelled all offers.");
                        Sleep.sleepUntil(() -> GrandExchange.isReadyToCollect(), 2000, 100);
                    }
                }
            } else {
                int currentDiscount = 5;
                for (String item : items) {
                    log("Price of item: " + LivePrices.get(item));
                    double multiplier = (100 - currentDiscount);
                    multiplier = multiplier / 100;
                    log("Multiplier: " + multiplier);
                    //double currentPrice = LivePrices.get(item) * multiplier;
                    double currentPrice = LivePrices.get(item) * multiplier;
                    log("Current price: " + currentPrice);
                    if (Inventory.contains(item)) {
                        API.status = "Selling "+item;
                        if (GrandExchange.sellItem(item, Inventory.count(item), (int) currentPrice)) {
                            if (Sleep.sleepUntil(GrandExchange::isReadyToCollect, 15000)) {
                                API.status = "Collecting "+item;
                                if (GrandExchange.collect()) {
                                    log("We were able to sell the item... ");
                                    currentDiscount = 5;
                                }
                            } else {
                                log("Failed to sell " + item + "... Current discount percentage: " + currentDiscount);
                                if (currentDiscount < maximumPercentageDiscount) {
                                    currentDiscount += 5;

                                }
                            }
                        }
                    }
                }
            }

        }

    }

    public static void buyHashMap(HashMap<String, Integer> items, int maximumPercentage) {
        log("Buy hashmap method");
        if (!GrandExchange.isOpen()) {
            log("Ge is not open");
            API.status = "Opening GE";
            if (GrandExchange.open()) {
                if (Sleep.sleepUntil(GrandExchange::isOpen, 15000)) {
                    log("Opened Ge");
                }
            }
        } else {
            log("Ge is already open");
            if (GrandExchange.getOpenSlots() == 0) {
                log("We don't have any open slots... Let's open up slots.");
                if (GrandExchange.isReadyToCollect()) {
                    log("We are ready to collect.");
                    API.status = "Collecting GE";
                    if (GrandExchange.collect()) {
                        Sleep.sleepUntil(() -> !GrandExchange.isReadyToCollect(), 2000, 100);
                    }
                } else {
                    API.status = "Cancelling offers";
                    if (GrandExchange.cancelAll()) {
                        log("Cancelled all offers.");
                        Sleep.sleepUntil(() -> GrandExchange.isReadyToCollect(), 2000, 100);
                    }
                }
            } else {
                log("We have open slots... " + GrandExchange.getOpenSlots());
                for (Map.Entry<String, Integer> entry : items.entrySet()) {
                    if (Inventory.getEmptySlots() < 3) {
                        log("Our empty slots is less than 3... Let's bank our stuff before buying more...");
                        if (!Bank.isOpen()) {
                            API.status = "Opening bank";
                            if (Bank.open()) {
                                if (Sleep.sleepUntil(Bank::isOpen, 15000)) {
                                    if (Bank.depositAllItems()) {
                                        Sleep.sleepUntil(Inventory::isEmpty, 15000);
                                    }
                                }
                            }
                        }
                    }
                    log("Price of item " + entry.getKey() + ": " + LivePrices.get(entry.getKey()));
                    double multiplier = 100 + currentPercentage;
                    multiplier = multiplier / 100;
                    log("Multiplier: "+multiplier);
                    double currentPrice = LivePrices.get(entry.getKey()) * multiplier;
                    log("Current price: "+currentPrice);
                    if (entry.getValue() > 0) {//Avoiding buying items like digsite pendant for example (-1) or items that we dont need to buy cause we have enough in bank.
                        API.status = "Buying "+entry.getValue() + " "+entry.getKey();
                        if (GrandExchange.buyItem(entry.getKey(), entry.getValue(), (int) currentPrice)) {
                            if (Sleep.sleepUntil(GrandExchange::isReadyToCollect, 15000)) {
                                API.status = "Collecting GE";
                                if (GrandExchange.collect()) {
                                    log("We were able to buy the item.");
                                    currentPercentage = 5;
                                }
                            } else {
                                log("Failed to buy the item");
                                if (currentPercentage < maximumPercentage) {
                                    currentPercentage += 5;
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public static void buyItemList(List<String> itemList, int maximumPercentage) {
        log("Buy items method");
        if (!GrandExchange.isOpen()) {
            log("Ge is not open");
            API.status = "Opening GE";
            if (GrandExchange.open()) {
                if (Sleep.sleepUntil(GrandExchange::isOpen, 15000)) {
                    log("Opened Ge");
                }
            }
        } else {
            log("Ge is already open");
            if (GrandExchange.getOpenSlots() == 0) {
                log("We don't have any open slots... Let's open up slots.");
                if (GrandExchange.isReadyToCollect()) {
                    log("We are ready to collect.");
                    API.status = "Collecting GE";
                    if (GrandExchange.collect()) {
                        Sleep.sleepUntil(() -> !GrandExchange.isReadyToCollect(), 2000, 100);
                    }
                } else {
                    API.status = "Cancelling offers";
                    if (GrandExchange.cancelAll()) {
                        log("Cancelled all offers.");
                        Sleep.sleepUntil(() -> GrandExchange.isReadyToCollect(), 2000, 100);
                    }
                }
            } else {
                log("We have open slots... " + GrandExchange.getOpenSlots());
                int currentPercentage = 5;
                for (String item : itemList) {
                    if (Inventory.getEmptySlots() < 3) {
                        log("Our empty slots is less than 3... Let's bank our stuff before buying more...");
                        if (!Bank.isOpen()) {
                            API.status = "Opening bank";
                            if (Bank.open()) {
                                if (Sleep.sleepUntil(Bank::isOpen, 15000)) {
                                    API.status = "Depositing all";
                                    if (Bank.depositAllItems()) {
                                        Sleep.sleepUntil(Inventory::isEmpty, 15000);
                                    }
                                }
                            }
                        }
                    }
                    log("Price of item " + item + ": " + LivePrices.get(item));
                    double multiplier = 100 + currentPercentage;
                    multiplier = multiplier / 100;
                    double currentPrice = LivePrices.get(item) * multiplier;
                    API.status = "Buying 1 "+item;
                    if (GrandExchange.buyItem(item, 1, (int) currentPrice)) {
                        if (Sleep.sleepUntil(GrandExchange::isReadyToCollect, 15000)) {
                            if (GrandExchange.collect()) {
                                log("We were able to buy the item.");
                                currentPercentage = 5;
                            }
                        } else {
                            log("Failed to buy the item");
                            if (currentPercentage < maximumPercentage) {
                                currentPercentage += 5;
                            }
                        }
                    }

                }
            }

        }
    }

    public static HashMap<String, Integer> calculateItemsToBuy(HashMap<String, Integer> hashmap) {
        HashMap<String, Integer> itemsToBuy = new HashMap<>();

        hashmap.forEach((itemName, requiredAmount) -> {
            int totalNeeded = requiredAmount * GlobalVariables.tripsToRestock; // Calcula o total necessário
            int availableAmount = getAvailableAmount(itemName); // Obtém a quantidade disponível exata

            if (availableAmount < totalNeeded) {
                int amountToBuy = totalNeeded - availableAmount;
                itemsToBuy.put(itemName, amountToBuy); // Adiciona ao HashMap com o nome exato
            }
        });

        return itemsToBuy;
    }

    private static int getAvailableAmount(String itemName) {
        int inventoryAmount = Inventory.all().stream()
                .filter(item -> item != null && item.getName().equals(itemName)) // Compara com o nome exato
                .mapToInt(Item::getAmount)
                .sum();

        int bankAmount = Bank.all().stream()
                .filter(item -> item != null && item.getName().equals(itemName)) // Compara com o nome exato
                .mapToInt(Item::getAmount)
                .sum();

        return inventoryAmount + bankAmount;
    }
}


