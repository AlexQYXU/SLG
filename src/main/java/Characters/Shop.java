package Characters;

import Item.Item;

import java.util.ArrayList;
import java.util.HashSet;

public class Shop {
    HashSet<Item> goodslist;

    public Shop(HashSet<Item> list) {
        goodslist = list;
    }

    public HashSet<Item> getGoodslist() {
        return goodslist;
    }
}
