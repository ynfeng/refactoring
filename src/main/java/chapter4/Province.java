package chapter4;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author fynwin@gmail.com
 */
public class Province {
    private final Set<Producer> producers = Sets.newHashSet();
    private String name;
    private int totalProduction;
    private int demand;
    private int price;

    public Province(Map<String, Object> doc) {
        name = (String) doc.get("name");
        demand = (Integer) doc.get("demand");
        price = (Integer) doc.get("price");
        List<Map<String, Object>> producersData = (List<Map<String, Object>>) doc.get("producers");
        producersData.forEach(data -> {
            addProducer(new Producer(this, data));
        });
    }

    public void addProducer(Producer arg) {
        producers.add(arg);
        totalProduction += arg.production();
    }

    public String name() {
        return name;
    }

    public Set<Producer> producers() {
        return Collections.unmodifiableSet(producers);
    }

    public int totalProduction() {
        return totalProduction;
    }

    public void totalProduction(int arg) {
        totalProduction = arg;
    }

    public int demand() {
        return demand;
    }

    public void setDemand(int arg) {
        demand = arg;
    }

    public int price() {
        return price;
    }

    public void price(int arg) {
        price = arg;
    }

    public int shortfall() {
        return demand() - totalProduction();
    }

    private int demandCost() {
        int remainingDemand = demand();
        int result = 0;
        List<Producer> sortedProducerList = producers.stream()
            .sorted(Comparator.comparingInt(Producer::cost))
            .collect(Collectors.toList());

        for (Producer producer : sortedProducerList) {
            int contribution = Math.min(remainingDemand, producer.production());
            remainingDemand -= contribution;
            result += contribution * producer.cost();
        }

        return result;
    }

    private int demandValue() {
        return satisfiedDemand() * price();
    }

    private int satisfiedDemand() {
        return Math.min(demand(), totalProduction());
    }

    public static Map<String, Object> sampleProvinceData() {
        List<Map<String, Object>> producers = Lists.newArrayList();
        Map<String, Object> result = Maps.newHashMap();
        result.put("name", "Asia");
        result.put("demand", 30);
        result.put("price", 20);

        Map<String, Object> byzantium = Maps.newHashMap();
        byzantium.put("name", "Byzantium");
        byzantium.put("cost", 10);
        byzantium.put("production", 9);
        producers.add(byzantium);

        Map<String, Object> attalia = Maps.newHashMap();
        attalia.put("name", "Attalia");
        attalia.put("cost", 12);
        attalia.put("production", 10);
        producers.add(attalia);

        Map<String, Object> sinope = Maps.newHashMap();
        sinope.put("name", "Sinope");
        sinope.put("cost", 10);
        sinope.put("production", 6);
        producers.add(sinope);

        result.put("producers", producers);
        return result;
    }
}
