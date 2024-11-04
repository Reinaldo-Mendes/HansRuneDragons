package behaviour.makeDigsitePendant;

import config.GlobalVariables;
import framework.Branch;
import org.dreambot.api.methods.container.impl.bank.Bank;

public class MakeDigsitePendantBranch extends Branch {
    @Override
    public boolean isValid() {
        return needDigsitePendant();
        //return true;
    }

    private boolean needDigsitePendant(){
        if(!Bank.contains(i -> i.getName().contains("Digsite pendant")) &&
                !Bank.contains(i -> i.getName().contains("Digsite pendant"))){
            GlobalVariables.needPendant = true;
        }
        if(Bank.contains(i -> i.getName().contains("Digsite pendant")) && Bank.count(i -> i.getName().contains("Digsite pendant")) >=
            GlobalVariables.tripsToRestock){
            GlobalVariables.needPendant = false;
        }
        return GlobalVariables.needPendant;
    }
}
