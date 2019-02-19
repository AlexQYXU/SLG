package Characters;

import Item.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

public class Transporter {
    ArrayList<Sword> swords;
    ArrayList<Lance> lances;
    ArrayList<Axe> axes;
    ArrayList<Bow> bows;
    ArrayList<Wind> winds;
    ArrayList<Fire> fires;
    ArrayList<Thunder> thunders;
    ArrayList<Staff> staff;
    ArrayList<Item> others;
    Comparator<Weapon> weaponComparator = new Comparator<Weapon>() {
        @Override
        public int compare(Weapon o1, Weapon o2) {
            if (o1.equals(o2)) {
                if (o1.getUses() < o2.getUses()) {
                    return -1;
                } else {
                    return 1;
                }
            }
            if (o1.getMight() < o2.getMight()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

    public Transporter() {
        swords = new ArrayList<>();
        lances = new ArrayList<>();
        axes = new ArrayList<>();
        bows = new ArrayList<>();
        winds = new ArrayList<>();
        fires = new ArrayList<>();
        thunders = new ArrayList<>();
        staff = new ArrayList<>();
        others = new ArrayList<>();
    }

    public ArrayList<? extends Item> getList(int index) {
        switch (index) {
            case 0:
                return swords;
            case 1:
                return lances;
            case 2:
                return axes;
            case 3:
                return bows;
            case 4:
                return winds;
            case 5:
                return fires;
            case 6:
                return thunders;
            case 7:
                return staff;
            case 8:
                return others;
            default:
                return null;
        }
    }

    public void setSwords(ArrayList<Sword> swords) {
        this.swords = swords;
    }

    public void setLances(ArrayList<Lance> lances) {
        this.lances = lances;
    }

    public void setAxes(ArrayList<Axe> axes) {
        this.axes = axes;
    }

    public void setBows(ArrayList<Bow> bows) {
        this.bows = bows;
    }

    public void setWinds(ArrayList<Wind> winds) {
        this.winds = winds;
    }

    public void setFires(ArrayList<Fire> fires) {
        this.fires = fires;
    }

    public void setThunders(ArrayList<Thunder> thunders) {
        this.thunders = thunders;
    }

    public void setStaff(ArrayList<Staff> staff) {
        this.staff = staff;
    }

    public void setOthers(ArrayList<Item> others) {
        this.others = others;
    }

    public void store(Item item) {

        switch (item.getClass().getName()) {
            case "Item.Sword":
                swords.add((Sword) item);
                swords.sort(weaponComparator);
                break;
            case "Item.Lance":
                lances.add((Lance) item);
                lances.sort(weaponComparator);
                break;
            case "Item.Axe":
                axes.add((Axe) item);
                axes.sort(weaponComparator);
                break;
            case "Item.Bow":
                bows.add((Bow) item);
                bows.sort(weaponComparator);
                break;
            case "Item.Wind":
                winds.add((Wind) item);
                winds.sort(weaponComparator);
                break;
            case "Item.Fire":
                fires.add((Fire) item);
                fires.sort(weaponComparator);
                break;
            case "Item.Thunder":
                thunders.add((Thunder) item);
                thunders.sort(weaponComparator);
                break;
            case "Item.Staff":
                staff.add((Staff) item);
                staff.sort(weaponComparator);
                break;
            default:
                others.add(item);
                break;
        }
    }


    public Item getItem(int x, int y) {
        switch (x) {
            case 0:
                if (!swords.isEmpty())
                    return swords.get(y);

            case 1:
                if (!lances.isEmpty())
                    return lances.get(y);

            case 2:
                if (!axes.isEmpty())
                    return axes.get(y);

            case 3:
                if (!bows.isEmpty())
                    return bows.get(y);

            case 4:
                if (!winds.isEmpty())
                    return winds.get(y);

            case 5:
                if (!fires.isEmpty())
                    return fires.get(y);

            case 6:
                if (!thunders.isEmpty())
                    return thunders.get(y);

            case 7:
                if (!staff.isEmpty())
                    return staff.get(y);

            case 8:
                if (!others.isEmpty())
                    return others.get(y);
            default:
                return null;
        }
    }

    public Item SearchFor(Item item) {
        switch (item.getClass().getName()) {
            case "Item.Sword":
                for (Sword sword : swords) {
                    System.out.println(sword.getName() + " : " + item.getName());
                    if (sword.equals(item)) {
                        return sword;
                    }
                }
                return null;

            case "Item.Lance":
                for (Lance lance : lances) {
                    if (lance.equals(item)) {
                        return lance;
                    }
                }
                return null;

            case "Item.Axe":
                for (Axe axe : axes) {
                    if (axe.equals(item)) {
                        return axe;
                    }
                }
                return null;

            case "Item.Bow":
                for (Bow bow : bows) {
                    if (bow.equals(item)) {
                        return bow;
                    }
                }
                return null;

            case "Item.Wind":
                for (Wind wind : winds) {
                    if (wind.equals(item)) {
                        return wind;
                    }
                }
                return null;
            case "Item.Fire":
                for (Fire fire : fires) {
                    if (fire.equals(item)) {
                        return fire;
                    }
                }
                return null;

            case "Item.Thunder":
                for (Thunder thunder : thunders) {
                    if (thunder.equals(item)) {
                        return thunder;
                    }
                }
                return null;

            case "Item.Staff":
                for (Staff staff : staff) {
                    if (staff.equals(item)) {
                        return staff;
                    }
                }
                return null;

            default:
                for (Item i : others) {
                    if (i.equals(item)) {
                        return i;
                    }
                }
                return null;
        }
    }

    public void Remove(Item item) {
        switch (item.getClass().getName()) {
            case "Item.Sword":
                if (swords.contains(item)) {
                    swords.remove(item);
                }
                break;
            case "Item.Lance":
                if (lances.contains(item)) {
                    lances.remove(item);
                }
                break;
            case "Item.Axe":
                if (axes.contains(item)) {
                    axes.remove(item);
                }
                break;

            case "Item.Bow":
                if (bows.contains(item))
                    bows.remove(item);

            case "Item.Wind":
                if (winds.contains(item))
                    winds.remove(item);
                break;
            case "Item.Fire":
                if (fires.contains(item))
                    fires.remove(item);
                break;
            case "Item.Thunder":
                if (thunders.contains(item))
                    thunders.remove(item);
                break;

            case "Item.Staff":
                if (staff.contains(item))
                    staff.remove(item);
                break;

            default:
                if (others.contains(item))
                    others.remove(item);
                break;
        }
    }

}
