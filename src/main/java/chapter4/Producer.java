package chapter4;

import java.util.Map;

/**
 * @author fynwin@gmail.com
 */
public class Producer {
    private int cost;
    private Province province;
    private String name;
    private int production;

    public Producer(Province province, Map<String, Object> data) {
        this.province = province;
        cost = (Integer) data.get("cost");
        name = (String) data.get("name");
        production = (Integer) data.get("production");
    }

    public String name() {
        return name;
    }

    public int cost() {
        return cost;
    }

    public void cost(int cost){
        this.cost = cost;
    }

    public int production(){
        return production;
    }

    public void production(int amount){
        province.totalProduction(province.totalProduction() + amount - production());
        production = amount;
    }
}
