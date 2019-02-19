import Characters.*;
import Characters.Character;
import Item.*;
import Item.Envir_Items.*;
import Item.Item;
import Item.Key.ChestKey;
import Item.Key.GateKey;
import Item.WeaponLevel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Watchable;
import java.util.ArrayList;
import java.util.HashSet;

public class Chapter {
    int Chapter_No;
    Victory_Condition objective;
    Node seize_point;
    HashSet<Node> escape_points = new HashSet<>();
    HashSet<Character> aim_list = new HashSet<>();
    int turn_limit;
    int turn;
    Character Boss;

    static String address = System.getProperty("user.dir") + "/src/main/resources/Icons";
    static String profile = System.getProperty("user.dir") + "/src/main/resources/Characters/Profile";
    static String character = System.getProperty("user.dir") + "/src/main/resources/Characters/Character";
    ArrayList<Character> PLAYER = new ArrayList<>();
    ArrayList<Character> ENEMY = new ArrayList<>();
    ArrayList<Character> PARTNER = new ArrayList<>();
    ArrayList<Character> MONSTER = new ArrayList<>();
    ArrayList<Envir_Item> ITEM = new ArrayList<>();
    ArrayList<Character> Grave = new ArrayList<>();
    Map map;
    Shop shop;

    public static void main(String[] args) {
        try {

            Chapter chapter = new Chapter();

            Character player_1 = new CharacterBuilder("Junk Dog", Gender.Male).camp(Camp.PLAYER).lv(1).exp(0).job(Job.Fighter).profile(1).character_image(1).
                    x(8).y(8).build();
            WeaponLevel[] levels = {WeaponLevel.E, WeaponLevel.D, null, WeaponLevel.B, null, null, null, null};
            Character player_2 = new CharacterBuilder("Alice", Gender.Female).job(Job.Knight).camp(Camp.PLAYER).para(20, 7, 0, 10, 8, 5, 3, 0).lv(1).exp(0).profile(2).character_image(2).x(7).y(7).weapon_ability(levels).build();

            Character player_3 = new CharacterBuilder("GioGio", Gender.Male).camp(Camp.PLAYER).lv(1).exp(0).job(Job.Fighter).profile(1).character_image(1).x(5).y(15).build();
            Character player_4 = new CharacterBuilder("Sister", Gender.Female).camp(Camp.ENEMY).job(Job.Cleric).lv(1).exp(0).profile(1).character_image(1).x(8).y(7).build();
            Character enemy_1 = new CharacterBuilder("Dio", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Knight).para(20, 2, 1, 1, 1, 1, 1, 1).profile(3).character_image(3).x(7).y(8).build();
            Character enemy_2 = new CharacterBuilder("Kaz", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Knight).profile(3).character_image(3).x(5).y(7).build();
            Character enemy_3 = new CharacterBuilder("Diablo", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Knight).profile(3).character_image(3).x(6).y(7).build();
            enemy_3.setHP(20);
            enemy_3.sethp(15);
            enemy_1.sethp(15);

            Item IronSword = Weapon.Iron_Weapon(WeaponType.Sword);
            //        Item.Item IronSword2  =new Item.Weapon.Item.WeaponBuilder(Item.Weapon.Type.Item.Sword, "Iron Item.Sword").might(5).weight(7).hit(90).crit(0).range(1, 1).level(Item.WeaponLevel.E).exp(1).uses(32).image(ImageIO.read(new File(address + "/Icons/IronSword.png"))).build();
            Item IronAxe = new AxeBuilder().name("Iron Axe").might(8).weight(10).hit(75).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(45).physical(true).image(ImageIO.read(new File(address + "/IronAxe.png"))).build();
            Item IronAxe2 = new AxeBuilder().name("Iron Axe").might(8).weight(10).hit(75).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(45).physical(true).image(ImageIO.read(new File(address + "/IronAxe.png"))).build();
            Item IronLance = new LanceBuilder().name("Iron Lance").might(7).weight(8).hit(80).crit(0).range(1, 2).uses(45).level(WeaponLevel.E).exp(1).physical(true).image(ImageIO.read(new File(address + "/IronLance.png"))).build();
            ((Lance) IronLance).setUses(1);
            Item IronBow = Weapon.Iron_Weapon(WeaponType.Bow);
            Item Heavy_Spear = Weapon.Armor_Special(WeaponType.Lance);
            player_2.gainItem(IronSword);
            player_3.gainItem(IronAxe);
            player_2.gainItem(IronAxe2);
            player_2.gainItem(IronLance);
//        player_2.gainItem(Heavy_Spear);
            Staff staff = StaffBuilder.generateExampleHealStaff(WeaponLevel.E);
            Staff heal = StaffBuilder.generateExampleHealStaff(WeaponLevel.E);
//        player_2.gainItem(staff);
            player_2.gainItem(new HealingItem("Vulnerary", 10, 3, 300, ImageIO.read
                    (new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Vulnerary.png"))));
//        PLAYER.add(player_1);
//        character_map[player_1.x][player_1.y] = player_1;

            chapter.map.character_map[player_2.x][player_2.y] = player_2;
//        PLAYER.add(player_3);
//        character_map[player_3.x][player_3.y] = player_3;
            player_3.gainItem(new GateKey());
            player_3.gainItem(new ChestKey());
            chapter.ENEMY.add(player_4);
            player_4.setCamp(Camp.ENEMY);
            player_4.gainItem(heal);
            chapter.map.character_map[player_4.x][player_4.y] = player_4;
            chapter.ENEMY.add(enemy_1);
            chapter.map.character_map[enemy_1.x][enemy_1.y] = enemy_1;
            chapter.ENEMY.add(enemy_2);
            chapter.map.character_map[enemy_2.x][enemy_2.y] = enemy_2;
            chapter.ENEMY.add(enemy_3);
            chapter.map.character_map[enemy_3.x][enemy_3.y] = enemy_3;
            Lance SteelLance = new LanceBuilder().name("Iron Lance:)").might(7).weight(8).hit(80).crit(0).range(1, 2).uses(45).physical(true).level(WeaponLevel.E).exp(1).image(ImageIO.read(new File(address + "/IronLance.png"))).build();
            enemy_1.gainItem(SteelLance);
            Heavy_Spear.setDropable(true);
            enemy_1.gainItem(Heavy_Spear);
            chapter.addTerrain(new Throne(10, 1));
            chapter.addTerrain(new Pillar(3, 3));
            chapter.addTerrain(new Chest(4, 20, new HealingItem("Vulnerary", 10, 3, 300, ImageIO.read
                    (new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Vulnerary.png")))));
            chapter.addTerrain(new Gate(4, 18));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addTerrain(Envir_Item terrain) {
        ITEM.add(terrain);
        map.terrain_map[terrain.getX()][terrain.getY()] = terrain;
    }


    public static Chapter getTest_chapter() {
        Chapter chapter = new Chapter();
        try {
            chapter.map = Map.getTestMap();
            chapter.Chapter_No = 1;

            HashSet<Node> escape_points = new HashSet<>();
            for (int i = 0; i < 4; i++) {
                escape_points.add(new Node(12, 12 + i));
            }
            chapter.set_Escape(escape_points, -1);
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Iron_Weapon(WeaponType.Sword));
            list.add(Weapon.Iron_Weapon(WeaponType.Axe));
            list.add(Weapon.Iron_Weapon(WeaponType.Lance));
            list.add(Weapon.Iron_Weapon(WeaponType.Bow));
            list.add(Weapon.Armor_Special(WeaponType.Sword));
            list.add(Weapon.Armor_Special(WeaponType.Lance));
            list.add(Weapon.Armor_Special(WeaponType.Axe));
            chapter.shop = new Shop(list);


            Character enemy_1 = new CharacterBuilder("Dio", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Knight).para(20, 2, 1, 1, 1, 1, 1, 1).profile(3).character_image(3).x(7).y(8).build();
            Character enemy_2 = new CharacterBuilder("Kaz", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Knight).profile(3).character_image(3).x(5).y(7).build();
            Character enemy_3 = new CharacterBuilder("Diablo", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Knight).profile(3).character_image(3).x(6).y(7).build();
            Character enemy_4 = new CharacterBuilder("Sister", Gender.Female).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Cleric).profile(1).character_image(1).x(5).y(8).build();
            enemy_1.setAi(AI.Positive);
            enemy_2.setAi(AI.Negative);

            Item IronSword = Weapon.Iron_Weapon(WeaponType.Sword);
            Item IronAxe = new AxeBuilder().name("Iron Axe").might(8).weight(10).hit(75).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(45).physical(true).image(ImageIO.read(new File(address + "/Iron Axe.png"))).build();
            Item IronAxe2 = new AxeBuilder().name("Iron Axe").might(8).weight(10).hit(75).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(45).physical(true).image(ImageIO.read(new File(address + "/Iron Axe.png"))).build();
            Item IronLance = new LanceBuilder().name("Iron Lance").might(7).weight(8).hit(80).crit(0).range(1, 2).uses(45).level(WeaponLevel.E).exp(1).physical(true).image(ImageIO.read(new File(address + "/Iron Lance.png"))).build();
            ((Lance) IronLance).setUses(1);
            Item IronBow = Weapon.Iron_Weapon(WeaponType.Bow);
            Item Heavy_Spear = Weapon.Armor_Special(WeaponType.Lance);
            chapter.ENEMY.add(enemy_1);
            chapter.map.character_map[enemy_1.x][enemy_1.y] = enemy_1;
            chapter.ENEMY.add(enemy_2);
            chapter.map.character_map[enemy_2.x][enemy_2.y] = enemy_2;
            chapter.ENEMY.add(enemy_3);
            chapter.map.character_map[enemy_3.x][enemy_3.y] = enemy_3;
            chapter.ENEMY.add(enemy_4);
            chapter.map.character_map[enemy_4.x][enemy_4.y] = enemy_4;
            Lance SteelLance = new LanceBuilder().name("Iron Lance").might(7).weight(8).hit(80).crit(0).range(1, 2).uses(45).physical(true).level(WeaponLevel.E).exp(1).image(ImageIO.read(new File(address + "/Iron Lance.png"))).build();
            enemy_1.gainItem(SteelLance);
            enemy_1.gainItem(HealingItem.getVulnerary());
            enemy_2.gainItem(IronSword.getCopy());
            enemy_2.gainItem(HealingItem.getVulnerary());
            enemy_3.gainItem(IronSword);
            enemy_4.gainItem(Weapon.HealingStaff(WeaponLevel.E));
            enemy_4.gainItem(HealingItem.getVulnerary());
            Heavy_Spear.setDropable(true);
            enemy_1.gainItem(Heavy_Spear);
            chapter.addTerrain(new Throne(10, 1));
            chapter.addTerrain(new Pillar(3, 3));
            chapter.addTerrain(new Chest(4, 20, new HealingItem("Vulnerary", 10, 3, 300, ImageIO.read
                    (new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Vulnerary.png")))));
            chapter.addTerrain(new Fort(7, 8, 0, 0, 0, null));
            chapter.addTerrain(new Gate(4, 18));
            chapter.addTerrain(new Fort(7, 7, 0, 0, 0, null));
            chapter.addTerrain(new Stair(0, 0, 1, 1, 2, enemy_1.getCopy()));
            System.out.println("size " + chapter.ITEM.size());
            chapter.Boss = chapter.ENEMY.get(0);
            for (Character en : chapter.ENEMY) {
                en.first_Weapon();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    public static Chapter getTest0() {
        Chapter chapter = new Chapter();
        try {
            chapter.Chapter_No = 0;
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Iron_Weapon(WeaponType.Sword));
            list.add(HealingItem.getVulnerary());
            chapter.shop = new Shop(list);
            chapter.map = Map.getTest0();
            chapter.objective = Victory_Condition.Defeat;

            Character enemy_1 = new CharacterBuilder("Father", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Villager)
                    .para(20, 2, 0, 4, 5, 1, 2, 1)
                    .profile(profile + "/Villager_1.png").character_image(character + "/Villager1.png")
                    .x(14).y(10).build();
            enemy_1.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));

            Character enemy_2 = new CharacterBuilder("Mother", Gender.Female).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Villager)
                    .para(18, 2, 0, 5, 4, 1, 1, 1)
                    .profile(profile + "/Villager_2.png").character_image(character + "/Villager2.png")
                    .x(14).y(11).build();
            enemy_2.setWeapon_ability(1, WeaponLevel.E);
            enemy_2.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));

            Character enemy_3 = new CharacterBuilder("Villager", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Villager)
                    .para(20, 5, 0, 2, 3, 1, 2, 1)
                    .profile(profile + "/Villager_1.png").character_image(character + "/Villager1.png").ai(AI.Negative)
                    .x(4).y(13).build();
            enemy_3.gainItem(Weapon.Trainer(Sword.class));
            enemy_3.gainItem(HealingItem.getVulnerary(true));

            Character enemy_4 = new CharacterBuilder("Villager", Gender.Female).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Villager)
                    .para(20, 4, 0, 2, 2, 0, 1, 1)
                    .profile(profile + "/Villager_2.png").character_image(character + "/Villager2.png").ai(AI.Negative)
                    .x(13).y(8).build();
            enemy_4.gainItem(Weapon.Trainer(Sword.class));

            Axe axe = new AxeBuilder().name("Felling Axe").might(8).weight(8).hit(80).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(45)
                    .physical(true).price(200).image(ImageIO.read(new File(address + "/Felling Axe.png"))).build();
            axe.setDropable(true);
            enemy_4.gainItem(axe);


            Character enemy_5 = new CharacterBuilder("Villager", Gender.Female).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Villager)
                    .para(19, 2, 0, 1, 1, 1, 1, 1)
                    .profile(profile + "/Villager_1.png").character_image(character + "/Villager1.png").ai(AI.Negative)
                    .x(22).y(8).build();
            enemy_5.gainItem(Weapon.Trainer(Sword.class));
            Weapon sword = new SwordBuilder().name("Long Sword").might(6).weight(6).hit(95).crit(0).range(1, 1).level(WeaponLevel.E).exp(1).uses(40)
                    .physical(true).price(400).image(ImageIO.read(new File(address + "/Long Sword.png"))).build();
            sword.setDropable(true);
            enemy_5.gainItem(sword);

            Character enemy_6 = new CharacterBuilder("Villager", Gender.Female).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Villager)
                    .para(21, 2, 1, 2, 3, 0, 3, 1)
                    .profile(profile + "/Villager_1.png").character_image(character + "/Villager1.png").ai(AI.Negative)
                    .x(21).y(13).build();
            enemy_6.gainItem(Weapon.Trainer(Sword.class));
            enemy_6.gainItem(HealingItem.getVulnerary(true));

            Character enemy_7 = new CharacterBuilder("Header", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Lord)
                    .para(20, 4, 1, 4, 5, 2, 3, 1).ai(AI.Negative)
                    .profile(profile + "/Village_Header.png").character_image(System.getProperty("user.dir") + "/src/main/resources/Characters/Character/Village Header.png")
                    .x(25).y(10).build();
            enemy_7.gainItem(Weapon.Trainer(Sword.class));

            chapter.ENEMY.add(enemy_1);
            chapter.ENEMY.add(enemy_2);
            chapter.ENEMY.add(enemy_3);
            chapter.ENEMY.add(enemy_4);
            chapter.ENEMY.add(enemy_5);
            chapter.ENEMY.add(enemy_6);
            chapter.ENEMY.add(enemy_7);

            chapter.Boss = enemy_7;
            for (Character en : chapter.ENEMY) {
//                chapter.map.character_map[en.x][en.y] = en;
                en.first_Weapon();
            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    public static Chapter getTest1() {
        Chapter chapter = new Chapter();
        chapter.Chapter_No = 1;

        try {
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Iron_Weapon(WeaponType.Sword));
            list.add(Weapon.Iron_Weapon(WeaponType.Axe));
            list.add(HealingItem.getVulnerary());
            chapter.shop = new Shop(list);
            chapter.map = Map.getTest1();
            chapter.objective = Victory_Condition.Rout;

            Character knight = new CharacterBuilder("Hector", Gender.Male).job(Job.Knight).camp(Camp.PARTNER)
                    .para(19, 7, 0, 4, 5, 3, 8, 0).lv(1).exp(0).profile(profile + "/Knight.png").character_image(character + "/Knight.png")
                    .x(14).y(3).ai(AI.Negative).build();
            int[] growth_3 = {80, 50, 20, 40, 40, 40, 50, 40};
            knight.setGrowth(growth_3);

            int[] growth_4 = {50, 35, 50, 25, 40, 60, 15, 40};
            Character cleric = new CharacterBuilder("Elincia", Gender.Female).camp(Camp.PARTNER).job(Job.Cleric).lv(1).exp(0).profile(1).character_image(1).x(15).y(3).ai(AI.Negative).build();
            cleric.setGrowth(growth_4);

            chapter.PARTNER.add(knight);
            knight.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.PARTNER.add(cleric);
            cleric.gainItem(Weapon.HealingStaff(WeaponLevel.E));
            cleric.gainItem(Weapon.Magic(WeaponType.Fire, WeaponLevel.C));


            for (Character pc : chapter.PARTNER) {
                pc.first_Weapon();
                chapter.map.character_map[pc.x][pc.y] = pc;
            }

            Character enemy_1 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Thief).
                    para(16, 3, 0, 2, 9, 0, 2, 0).profile(3).character_image(3).x(13).y(8).build();
            enemy_1.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            Character enemy_2 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Thief).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(3).character_image(3).x(12).y(9).build();
            enemy_2.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            Character enemy_3 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Thief).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(3).character_image(3).x(14).y(9).build();
            enemy_3.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));

            Character bandit_1 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Fighter).
                    para(20, 4, 0, 1, 5, 0, 3, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png").x(9).y(8).build();
            bandit_1.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            Weapon breaker = Weapon.Armor_Special(WeaponType.Sword);
            bandit_1.gainItem(breaker);
            breaker.setDropable(true);
            Character bandit_2 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Fighter).
                    para(21, 4, 0, 2, 4, 0, 2, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png").x(8).y(7).build();
            bandit_2.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            Character bandit_3 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Fighter).
                    para(21, 5, 0, 2, 5, 0, 2, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png").x(8).y(9).build();
            bandit_3.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            Character enemy_4 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Mercenary).
                    para(20, 5, 0, 5, 7, 0, 3, 0).profile(System.getProperty("user.dir") + "/src/main/resources/Characters/Profile/Enemy4.png")
                    .character_image(System.getProperty("user.dir") + "/src/main/resources/Characters/Character/Enemy4.png").x(15).y(10).build();
            enemy_4.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));
            enemy_4.setWeapon_ability(0, WeaponLevel.D);
            chapter.ENEMY.add(enemy_1);
            chapter.ENEMY.add(enemy_2);
            chapter.ENEMY.add(enemy_3);
            chapter.ENEMY.add(bandit_1);
            chapter.ENEMY.add(bandit_2);
            chapter.ENEMY.add(bandit_3);
            chapter.ENEMY.add(enemy_4);
            chapter.Boss = enemy_4;
            for (Character en : chapter.ENEMY) {
                en.gainItem(HealingItem.getVulnerary());
                en.first_Weapon();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    public static Chapter getTest2() {
        Chapter chapter = new Chapter();
        chapter.Chapter_No = 2;

        try {
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Iron_Weapon(WeaponType.Sword));
            list.add(Weapon.Iron_Weapon(WeaponType.Lance));
            list.add(Weapon.Iron_Weapon(WeaponType.Axe));
            list.add(Weapon.HealingStaff(WeaponLevel.E));
            list.add(HealingItem.getVulnerary());
            chapter.shop = new Shop(list);
            chapter.map = Map.getTest2();
            chapter.objective = Victory_Condition.Seize;
            chapter.seize_point = new Node(13, 2);
            chapter.ITEM.add(new Fort(12, 6, 0, 0, 0, null));
            chapter.ITEM.add(new Fort(5, 4, 0, 0, 0, null));
            chapter.ITEM.add(new Castle(13, 2));


            Character bandit_1 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Mercenary).
                    para(21, 4, 0, 3, 5, 2, 5, 0).profile(profile + "/Mercenary_Enemy.png").character_image(character + "/Mercenary_Enemy.png").x(13).y(9).build();
            bandit_1.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(bandit_1);

            Character bandit_2 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Mercenary).
                    para(22, 4, 0, 3, 5, 2, 5, 0).profile(profile + "/Mercenary_Enemy.png").character_image(character + "/Mercenary_Enemy.png").x(11).y(7).build();
            bandit_2.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(bandit_2);

            Character bandit_3 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Mercenary).
                    para(22, 3, 0, 3, 5, 2, 5, 0).profile(profile + "/Mercenary_Enemy.png").character_image(character + "/Mercenary_Enemy.png").x(13).y(5).build();
            bandit_3.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(bandit_3);
            Character bandit_4 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Fighter).
                    para(23, 4, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(1).y(5).build();
            bandit_4.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            chapter.ENEMY.add(bandit_4);

            Character bandit_5 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Fighter).
                    para(25, 4, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(2).y(3).build();
            bandit_5.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            Weapon heavy_spear = Weapon.Armor_Special(WeaponType.Lance);
            heavy_spear.setDropable(true);
            bandit_5.gainItem(heavy_spear);
            chapter.ENEMY.add(bandit_5);

            Character bandit_6 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Fighter).
                    para(24, 5, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(3).y(1).build();
            bandit_6.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            HealingItem Vulnerary = HealingItem.getVulnerary();
            Vulnerary.setDropable(true);
            bandit_6.gainItem(Vulnerary);
            chapter.ENEMY.add(bandit_6);

            Character bandit_7 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Fighter).
                    para(25, 4, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(5).y(1).build();
            bandit_7.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            chapter.ENEMY.add(bandit_7);

            Character boss = new CharacterBuilder("Knight", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Knight).ai(AI.Negative).
                    para(25, 7, 0, 6, 5, 2, 5, 0).profile(profile + "/Knight_3.png").character_image(character + "/Knight_3.png").
                    x(13).y(2).build();
            boss.gainItem(Weapon.Steel_Weapon(WeaponType.Axe));
            boss.setWeapon_ability(2, WeaponLevel.D);
            chapter.ENEMY.add(boss);
            chapter.Boss = boss;

            Character fish_1 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(4).exp(0).job(Job.Soldier).
                    para(25, 5, 0, 3, 6, 0, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(15).y(9).build();
            fish_1.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.MONSTER.add(fish_1);
            Character fish_2 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(4).exp(0).job(Job.Soldier).
                    para(23, 6, 0, 4, 5, 0, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(16).y(7).build();
            fish_2.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.MONSTER.add(fish_2);

            Character fish_3 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(4).exp(0).job(Job.Mercenary).
                    para(24, 5, 0, 3, 5, 0, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(16).y(5).build();
            fish_3.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.MONSTER.add(fish_3);
            Character fish_4 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(4).exp(0).job(Job.Mercenary).
                    para(23, 5, 0, 4, 4, 0, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(17).y(4).build();
            fish_4.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.MONSTER.add(fish_4);

            for (Character en : chapter.ENEMY) {
                en.first_Weapon();
            }
            for (Character mon : chapter.MONSTER) {
                mon.first_Weapon();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }


    public static Chapter getTest3() {
        Chapter chapter = new Chapter();
        chapter.Chapter_No = 3;
        try {
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Ranged_Weapon(WeaponType.Lance));
            list.add(Weapon.Ranged_Weapon(WeaponType.Axe));
            list.add(Weapon.Magic(WeaponType.Fire, WeaponLevel.E));
            list.add(Weapon.HealingStaff(WeaponLevel.E));
            list.add(HealingItem.getVulnerary());
            chapter.shop = new Shop(list);
            chapter.map = Map.getTest3();
            chapter.Chapter_No = 3;
            chapter.objective = Victory_Condition.Defeat;
            Character enemy_1 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(3).y(19).build();
            chapter.ENEMY.add(enemy_1);
            enemy_1.setWeapon_ability(3, WeaponLevel.D);
            enemy_1.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));

            Character enemy_2 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Soldier).
                    para(16, 3, 0, 2, 9, 0, 2, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(9).y(17).build();
            enemy_2.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.ENEMY.add(enemy_2);


            Character enemy_3 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(14).y(19).build();
            enemy_3.setWeapon_ability(3, WeaponLevel.D);
            enemy_3.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));
            chapter.ENEMY.add(enemy_3);

            Character enemy_4 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(19).y(27).build();
            chapter.ENEMY.add(enemy_4);
            enemy_4.setWeapon_ability(3, WeaponLevel.D);
            enemy_4.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));

            Character enemy_5 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(17).y(13).build();
            chapter.ENEMY.add(enemy_5);
            enemy_5.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            Character enemy_6 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(12).y(13).build();
            chapter.ENEMY.add(enemy_6);
            enemy_6.setWeapon_ability(3, WeaponLevel.D);
            enemy_6.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));

            Character enemy_7 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(7).y(13).build();
            chapter.ENEMY.add(enemy_7);
            enemy_7.setWeapon_ability(3, WeaponLevel.D);
            enemy_7.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));

            Character enemy_8 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(2).y(13).build();
            chapter.ENEMY.add(enemy_8);
            enemy_8.setWeapon_ability(3, WeaponLevel.D);
            enemy_8.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));

            Character enemy_9 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(2).y(9).build();
            chapter.ENEMY.add(enemy_9);
            enemy_9.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));

            Character enemy_10 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Mercenary).
                    para(18, 2, 0, 7, 8, 0, 4, 0).profile(System.getProperty("user.dir") + "/src/main/resources/Characters/Profile/Enemy4.png")
                    .character_image(System.getProperty("user.dir") + "/src/main/resources/Characters/Character/Enemy4.png").x(9).y(10).build();
            chapter.ENEMY.add(enemy_10);
            enemy_10.setWeapon_ability(0, WeaponLevel.D);
            enemy_10.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));

            Character enemy_11 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Mercenary).
                    para(18, 2, 0, 7, 8, 0, 4, 0).profile(System.getProperty("user.dir") + "/src/main/resources/Characters/Profile/Enemy4.png")
                    .character_image(System.getProperty("user.dir") + "/src/main/resources/Characters/Character/Enemy4.png").x(8).y(6).build();
            chapter.ENEMY.add(enemy_11);
            enemy_11.setWeapon_ability(0, WeaponLevel.D);
            enemy_11.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));

            Character enemy_12 = new CharacterBuilder("Fighter", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Fighter).
                    para(18, 2, 0, 7, 8, 0, 4, 0).profile(System.getProperty("user.dir") + "/src/main/resources/Characters/Profile/Enemy4.png")
                    .character_image(System.getProperty("user.dir") + "/src/main/resources/Characters/Character/Enemy4.png").x(10).y(6).build();
            chapter.ENEMY.add(enemy_12);
            enemy_12.setWeapon_ability(2, WeaponLevel.D);
            enemy_12.gainItem(Weapon.Steel_Weapon(WeaponType.Axe));

            Character E_knight = new CharacterBuilder("Royal Guard", Gender.Male).camp(Camp.ENEMY).lv(5).exp(0).job(Job.Knight).
                    para(26, 8, 0, 5, 3, 1, 10, 0).profile(profile + "/Knight_Enemy.png").character_image(character + "/Knight_Enemy.png").x(20).y(8).ai(AI.Negative).build();
            E_knight.gainItem(Weapon.Ranged_Weapon_LU(Lance.class));
            WeaponLevel[] ability = {WeaponLevel.E, WeaponLevel.C, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA};
            E_knight.setWeapon_ability(ability);
            chapter.ENEMY.add(E_knight);

            Character enemy_14 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(14).y(2).build();
            chapter.ENEMY.add(enemy_14);
            enemy_14.setWeapon_ability(3, WeaponLevel.D);
            enemy_14.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));
            Character enemy_15 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(4).y(2).build();
            chapter.ENEMY.add(enemy_15);
            enemy_15.setWeapon_ability(3, WeaponLevel.D);
            enemy_15.gainItem(Weapon.Ranged_Weapon(WeaponType.Bow));

            Character Mage = new CharacterBuilder("Mage", Gender.Male).camp(Camp.ENEMY).lv(5).exp(0).job(Job.Mage).
                    para(20, 2, 7, 6, 7, 3, 4, 6).profile(profile + "/Thief_3.png").character_image(character + "/Thief3.png").x(10).y(0).ai(AI.Negative).build();
            WeaponLevel[] ability_m = {WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.D, WeaponLevel.NA, WeaponLevel.NA};
            Mage.setWeapon_ability(ability_m);
            Mage.gainItem(Weapon.Magic(WeaponType.Fire, WeaponLevel.D));
            chapter.Boss = Mage;
            chapter.ENEMY.add(Mage);

            for (Character en : chapter.ENEMY) {
                en.first_Weapon();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    public static Chapter getTest4() {
        Chapter chapter = new Chapter();
        try {
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Iron_Weapon(WeaponType.Sword));
            list.add(Weapon.Iron_Weapon(WeaponType.Lance));
            list.add(Weapon.Iron_Weapon(WeaponType.Axe));
            list.add(Weapon.HealingStaff(WeaponLevel.E));
            list.add(HealingItem.getVulnerary());
            chapter.shop = new Shop(list);
            chapter.map = Map.getTest4();
            chapter.Chapter_No = 4;
            chapter.objective = Victory_Condition.Rout;
            chapter.addTerrain(new Gate(1, 3));
            chapter.addTerrain(new Gate(6, 3));
            chapter.addTerrain(new Gate(11, 3));
            chapter.addTerrain(new Gate(11, 7));
            chapter.addTerrain(new Chest(11, 5, Weapon.Armor_Special(WeaponType.Sword)));
            Character lord = new CharacterBuilder("King", Gender.Male).camp(Camp.PLAYER).lv(6).exp(0).job(Job.Lord).
                    para(26, 8, 0, 10, 10, 9, 8, 0).profile(profile + "/King.png").character_image(character + "/King.png").x(12).y(6).build();
            int[] growth = {50, 45, 5, 45, 45, 80, 25, 10};
            lord.setGrowth(growth);
            chapter.PARTNER.add(lord);
            chapter.map.character_map[lord.x][lord.y] = lord;

            Character enemy_1 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Soldier).
                    para(22, 3, 0, 5, 4, 0, 2, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(1).y(1).build();
            enemy_1.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.ENEMY.add(enemy_1);

            Character enemy_2 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).
                    para(21, 3, 0, 3, 2, 0, 3, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(3).y(4).build();
            enemy_2.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.ENEMY.add(enemy_2);

            Character enemy_3 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Mercenary).
                    para(19, 3, 0, 9, 8, 0, 4, 0).profile(profile + "/Enemy4.png").character_image(character + "/Enemy4.png").x(6).y(5).build();
            enemy_3.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));
            enemy_3.setWeapon_ability(0, WeaponLevel.D);
            chapter.ENEMY.add(enemy_3);

            Character enemy_4 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).
                    para(20, 5, 0, 3, 4, 0, 2, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(6).y(1).build();
            enemy_4.gainItem(Weapon.Ranged_Weapon(WeaponType.Lance));
            chapter.ENEMY.add(enemy_4);

            Character enemy_5 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Soldier).
                    para(23, 3, 0, 3, 5, 0, 3, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(7).y(10).build();
            enemy_5.gainItem(Weapon.Steel_Weapon(WeaponType.Lance));
            enemy_5.setWeapon_ability(1, WeaponLevel.D);
            chapter.ENEMY.add(enemy_5);

            Character enemy_6 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).
                    para(19, 4, 0, 5, 6, 0, 1, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(12).y(10).build();
            enemy_6.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.ENEMY.add(enemy_6);

            Character enemy_7 = new CharacterBuilder("Knight", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Knight).
                    para(16, 3, 0, 2, 9, 0, 2, 0).profile(profile + "/Knight_3.png").character_image(character + "/Knight_3.png").x(11).y(1).build();
            enemy_7.setWeapon_ability(1, WeaponLevel.D);
            enemy_7.gainItem(Weapon.Steel_Weapon(WeaponType.Lance));
            enemy_7.gainItem(new ChestKey(true));
            chapter.ENEMY.add(enemy_7);

            Character enemy_8 = new CharacterBuilder("Royal Mage", Gender.Male).camp(Camp.ENEMY).lv(10).exp(0).job(Job.Mage).
                    para(24, 1, 7, 6, 10, 0, 7, 5).profile(profile + "/Thief_3.png").character_image(character + "/Thief3.png").x(11).y(9).build();
            enemy_8.gainItem(Weapon.Magic(WeaponType.Fire, WeaponLevel.E));
            enemy_8.gainItem(new GateKey(true));
            chapter.ENEMY.add(enemy_8);

            Character enemy_9 = new CharacterBuilder("Royal Guard", Gender.Male).camp(Camp.ENEMY).lv(15).exp(0).job(Job.Knight).
                    para(28, 11, 1, 12, 13, 0, 11, 4).profile(profile + "/Knight_Enemy.png").character_image(character + "/Knight_Enemy.png").x(15).y(4).build();
            enemy_9.setWeapon_ability(1, WeaponLevel.C);
            enemy_9.gainItem(Weapon.Ranged_Weapon_LU(Lance.class));
            enemy_9.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(enemy_9);

            for (Character en : chapter.ENEMY) {
                en.first_Weapon();
            }
            chapter.Boss = enemy_9;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }

    public static Chapter getTest5() {
        Chapter chapter = new Chapter();
        try {
            HashSet<Item> list = new HashSet<Item>();
            list.add(Weapon.Iron_Weapon(WeaponType.Sword));
            list.add(Weapon.Iron_Weapon(WeaponType.Lance));
            list.add(Weapon.Iron_Weapon(WeaponType.Axe));
            list.add(Weapon.HealingStaff(WeaponLevel.E));
            list.add(Weapon.Steel_Weapon(WeaponType.Sword));
            list.add(Weapon.Steel_Weapon(WeaponType.Lance));
            list.add(Weapon.Steel_Weapon(WeaponType.Axe));
            list.add(Weapon.HealingStaff(WeaponLevel.D));
            list.add(HealingItem.getVulnerary());
            chapter.shop = new Shop(list);
            chapter.map = Map.getTest5();
            chapter.objective = Victory_Condition.Survive;
            chapter.turn_limit = 7;
            chapter.seize_point = new Node(5, 2);
            chapter.Chapter_No = 5;


            chapter.addTerrain(new Gate(1, 11));
            chapter.addTerrain(new Gate(9, 1));
            chapter.addTerrain(new Gate(9, 4));
            chapter.addTerrain(new Chest(2, 14, Weapon.HealingStaff(WeaponLevel.D)));
            chapter.addTerrain(new Chest(4, 14, Weapon.Armor_Special(WeaponType.Axe)));
            chapter.addTerrain(new Pillar(3, 5));
            chapter.addTerrain(new Pillar(7, 5));
            chapter.addTerrain(new Pillar(12, 2));
            chapter.addTerrain(new Throne(5, 2));
            Character enemy_1 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Soldier).ai(AI.Positive).
                    para(22, 3, 0, 5, 4, 0, 2, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(1).y(1).build();
            enemy_1.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.addTerrain(new Stair(0, 10, 1, 2, 5, enemy_1));
            Character enemy_2 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).ai(AI.Positive).
                    para(21, 3, 0, 3, 2, 0, 3, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(3).y(4).build();
            enemy_2.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.addTerrain(new Stair(7, 15, 2, 2, 7, enemy_2));
            Character enemy_3 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Mercenary).ai(AI.Positive).
                    para(19, 3, 0, 9, 8, 0, 4, 0).profile(profile + "/Enemy4.png").character_image(character + "/Enemy4.png").x(6).y(5).build();
            enemy_3.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));
            enemy_3.setWeapon_ability(0, WeaponLevel.D);
            chapter.addTerrain(new Stair(8, 15, 1, 2, 7, enemy_3));

            Character enemy_4 = new CharacterBuilder("Soldier", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Soldier).ai(AI.Positive).
                    para(20, 5, 0, 3, 4, 0, 2, 0).profile(profile + "/Soldier.png").character_image(character + "/Soldier.png").x(6).y(1).build();
            enemy_4.gainItem(Weapon.Ranged_Weapon(WeaponType.Lance));
            chapter.addTerrain(new Stair(9, 15, 2, 2, 7, enemy_4));

            Character enemy_5 = new CharacterBuilder("Thief", Gender.Male).camp(Camp.ENEMY).lv(1).exp(0).job(Job.Thief).ai(AI.Positive).
                    para(16, 3, 0, 2, 9, 0, 2, 0).profile(3).character_image(3).x(13).y(8).build();
            enemy_5.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.addTerrain(new Stair(14, 14, 1, 2, 7, enemy_5));

            Character enemy_6 = new CharacterBuilder("Thief", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Thief).ai(AI.Positive).
                    para(23, 5, 0, 5, 9, 0, 3, 0).profile(3).character_image(3).x(13).y(8).build();
            enemy_6.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.addTerrain(new Stair(14, 12, 2, 2, 7, enemy_6));


            Character knight_1 = new CharacterBuilder("Knight", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Knight).ai(AI.Positive).
                    para(16, 3, 0, 2, 9, 0, 2, 0).profile(profile + "/Knight_3.png").
                    character_image(character + "/Knight_3.png").x(4).y(9).build();
            knight_1.setWeapon_ability(1, WeaponLevel.D);
            knight_1.gainItem(Weapon.Steel_Weapon(WeaponType.Lance));

            Character knight_2 = new CharacterBuilder("Knight", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Knight).ai(AI.Positive).
                    para(16, 3, 0, 2, 9, 0, 2, 0).profile(profile + "/Knight_3.png").
                    character_image(character + "/Knight_3.png").x(6).y(9).build();
            knight_2.setWeapon_ability(0, WeaponLevel.D);
            knight_2.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));

            Character enemy_7 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(3).exp(0).job(Job.Mercenary).ai(AI.Positive).
                    para(19, 3, 0, 9, 8, 0, 4, 0).profile(profile + "/Enemy4.png").
                    character_image(character + "/Enemy4.png").x(12).y(2).build();
            enemy_7.gainItem(Weapon.Steel_Weapon(WeaponType.Sword));
            enemy_7.setWeapon_ability(0, WeaponLevel.D);


            Character enemy_8 = new CharacterBuilder("Archer", Gender.Male).camp(Camp.ENEMY).lv(2).exp(0).job(Job.Archer).ai(AI.Positive).
                    para(14, 2, 0, 2, 8, 0, 1, 0).profile(profile + "/Soldier.png").
                    character_image(character + "/Soldier.png").x(12).y(6).build();
            enemy_8.setWeapon_ability(3, WeaponLevel.D);
            enemy_8.gainItem(Weapon.Steel_Weapon(WeaponType.Bow));

            Character enemy_9 = new CharacterBuilder("Royal Guard", Gender.Male).camp(Camp.ENEMY).lv(15).exp(0).job(Job.Knight).ai(AI.Neutral).
                    para(28, 11, 1, 12, 13, 0, 11, 4).profile(profile + "/Knight_Enemy.png")
                    .character_image(character + "/Knight_Enemy.png").x(8).y(16).build();
            enemy_9.setWeapon_ability(1, WeaponLevel.C);
            enemy_9.gainItem(Weapon.Ranged_Weapon_LU(Lance.class));
            enemy_9.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.Boss = enemy_9;

            chapter.ENEMY.add(knight_1);
            chapter.ENEMY.add(knight_2);
            chapter.ENEMY.add(enemy_7);
            chapter.ENEMY.add(enemy_8);
            chapter.ENEMY.add(enemy_9);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chapter;
    }


    private void set_Seize(Node point, int turn_limit) {
        if (point != null) {
            objective = Victory_Condition.Seize;
            seize_point = point;
            if (turn_limit > 0) {
                this.turn_limit = turn_limit;
            }
        } else {
            System.out.println("Please set the Seize Point.");
        }
    }

    private void set_Rout(int turn_limit) {
        objective = Victory_Condition.Rout;
        this.turn_limit = turn_limit;
    }

    private void set_Defeat(Character boss, int turn_limit) {
        if (ENEMY.contains(boss)) {
            this.Boss = boss;
            this.turn_limit = turn_limit;
        }
    }

    private void set_Survive(HashSet<Character> aim_list, int turn_limit) {
        this.objective = Victory_Condition.Survive;
        for (Character pc : aim_list) {
            if (PLAYER.contains(pc)) {
                this.aim_list.add(pc);
            } else {
                System.out.println(pc.getName() + ": There is no such character!");
            }
        }
        this.turn_limit = turn_limit;
    }

    private void set_Escape(HashSet<Node> escape_points, int turn_limit) {
        this.objective = Victory_Condition.Escape;
        this.escape_points = new HashSet<>(escape_points);
        this.turn_limit = turn_limit;
    }

    public boolean isSurvived() {
        if (this.turn >= this.turn_limit) {
            for (Character pc : aim_list) {
                if (!PLAYER.contains(pc)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    public boolean isEscaped() {
        for (Character pc : aim_list) {
            if (PLAYER.contains(pc)) {
                return false;
            }
        }
        return true;
    }

    private void backup() {
        try {
            Chapter chapter = new Chapter();
            Character bandit_1 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Mercenary).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Mercenary_Enemy.png").character_image(character + "/Mercenary_Enemy.png").x(13).y(9).build();
            bandit_1.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(bandit_1);

            Character bandit_2 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Mercenary).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Mercenary_Enemy.png").character_image(character + "/Mercenary_Enemy.png").x(8).y(6).build();
            bandit_2.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(bandit_2);

            Character bandit_3 = new CharacterBuilder("Mercenary", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Mercenary).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Mercenary_Enemy.png").character_image(character + "/Mercenary_Enemy.png").x(4).y(7).build();
            bandit_3.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.ENEMY.add(bandit_3);
            Character bandit_4 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Fighter).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(1).y(5).build();
            bandit_4.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            chapter.ENEMY.add(bandit_4);

            Character bandit_5 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Fighter).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(2).y(3).build();
            bandit_5.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            Weapon heavy_spear = Weapon.Armor_Special(WeaponType.Lance);
            heavy_spear.setDropable(true);
            bandit_5.gainItem(heavy_spear);
            chapter.ENEMY.add(bandit_5);

            Character bandit_6 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Fighter).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(3).y(1).build();
            bandit_6.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            HealingItem Vulnerary = HealingItem.getVulnerary();
            Vulnerary.setDropable(true);
            bandit_6.gainItem(Vulnerary);
            chapter.ENEMY.add(bandit_6);

            Character bandit_7 = new CharacterBuilder("Bandit", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Fighter).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Bandit.png").character_image(character + "/Bandit.png")
                    .x(5).y(1).build();
            bandit_7.gainItem(Weapon.Iron_Weapon(WeaponType.Axe));
            chapter.ENEMY.add(bandit_7);

            Character boss = new CharacterBuilder("Knight", Gender.Male).camp(Camp.ENEMY).lv(6).exp(0).job(Job.Knight).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Knight_3.png").character_image(character + "/Knight_3.png").
                    x(13).y(2).build();
            boss.gainItem(Weapon.Steel_Weapon(WeaponType.Axe));
            boss.setWeapon_ability(2, WeaponLevel.D);
            chapter.ENEMY.add(boss);
            chapter.Boss = boss;

            Character fish_1 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(6).exp(0).job(Job.Soldier).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(13).y(12).build();
            fish_1.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.MONSTER.add(fish_1);
            Character fish_2 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(6).exp(0).job(Job.Soldier).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(14).y(10).build();
            fish_2.gainItem(Weapon.Iron_Weapon(WeaponType.Lance));
            chapter.MONSTER.add(fish_2);

            Character fish_3 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(6).exp(0).job(Job.Mercenary).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(15).y(9).build();
            fish_3.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.MONSTER.add(fish_3);
            Character fish_4 = new CharacterBuilder("Deep One", Gender.Male).camp(Camp.MONSTER).lv(6).exp(0).job(Job.Mercenary).
                    para(25, 7, 0, 3, 5, 2, 5, 0).profile(profile + "/Deep One.png").character_image(character + "/Deep One.png").x(16).y(7).build();
            fish_4.gainItem(Weapon.Iron_Weapon(WeaponType.Sword));
            chapter.MONSTER.add(fish_4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
