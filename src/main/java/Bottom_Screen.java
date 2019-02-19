import Characters.*;
import Characters.Character;
import Display.Cursor;
import Display.HP_Window;
import Display.Staff_Menu;
import Item.*;
import Item.Envir_Items.Chest;
import Item.Envir_Items.Gate;
import Item.Envir_Items.Obstacle;
import Item.Key.ChestKey;
import Item.Key.GateKey;
import Item.Staff;
import Item.Weapon;
import Item.WeaponType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.management.MonitorInfo;
import java.util.*;

public class Bottom_Screen extends JPanel {
     GBA gba;
     String path = System.getProperty("user.dir") + "/src/main/java/Tilesets";

     BufferedImage map;
    static int map_x;
    static int map_y;

    private BufferedImage BlueTileset;
    private BufferedImage RedTileset;
    private BufferedImage GreenTileset;
    private BufferedImage YellowTileset;

     int left = 0, top = 0;
     Cursor cursor;
     int z_mark;
     boolean show_attack;

     HashSet<Node> moveArea = new HashSet<>();
     HashSet<Node> GreenArea = new HashSet<>();
     HashSet<Node> RedArea = new HashSet<>();
     HashSet<Node> NoColorArea = new HashSet<>();
     HashSet<Node> AI_MoveRange = new HashSet<>();
     HashSet<Node> AI_AffectRange = new HashSet<>();

    BufferedImage blue_flag;
    BufferedImage red_flag;
    BufferedImage green_flag;
    BufferedImage yellow_flag;
    BufferedImage grey_flag;
    BufferedImage shield;

     boolean show_Aim;
     Character aim;
     Envir_Item item_aim;
     ArrayList<Character> AimList = new ArrayList<>();
     ArrayList<Envir_Item> LockList = new ArrayList<>();
    int[][] direction = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static final Random random = new Random();
    int[][] move_map;
    public boolean test_switch;
    Font Big = new Font("Dialog", 1, 25);

    boolean in_Preparation;
    boolean ENEMY_PHASE;
    boolean PLAYER_PHASE;
    int textwidth;
    Character caught;

    boolean show_MovePoint;

    public Bottom_Screen(GBA gba) throws IOException {
        this.gba = gba;
        this.cursor = gba.cursor;
        BlueTileset = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/BlueTileset.png"));
        GreenTileset = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/GreenTileset.png"));
        RedTileset = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/RedTileset.png"));
        YellowTileset = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Characters/YellowTileset.png"));
        blue_flag = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Blue_Flag.png"));
        red_flag = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Red_Flag.png"));
        green_flag = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Green_Flag.png"));
        yellow_flag = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Yellow_Flag.png"));
        grey_flag = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Grey_Flag.png"));
        shield = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/Icons/Shield.png"));
//        map = ImageIO.read(new File(System.getProperty("user.dir") + "/src/main/resources/map/new.png"));
//        map_x = map.getWidth() / tile_Width;
//        map_y = map.getHeight() / tile_Height;

    }


    @Override
    public void paint(Graphics g) {
        try {

            g.drawImage(map.getSubimage(32 * left, 32 * top, 352, 256), 0, 0, null);
            if (z_mark == 1 && !moveArea.isEmpty()) {
                for (Node n : moveArea) {
                    g.drawImage(BlueTileset, 32 * (n.x - left), 32 * (n.y - top), 32, 32, null);
                }
            }
            for (Envir_Item item : gba.ITEM) {
                g.drawImage(item.draw(), 32 * (item.getX() - left), 32 * (item.getY() - item.getHeight() + 1 - top), 32 * item.getWidth(), 32 * item.getHeight(), null);
            }
            if (gba.chapter.seize_point != null) {
                g.drawImage(YellowTileset, 32 * (gba.chapter.seize_point.x - left), 32 * (gba.chapter.seize_point.y - top), 32, 32, null);
            }
            if (!gba.chapter.escape_points.isEmpty()) {
                for (Node n : gba.chapter.escape_points) {
                    g.drawImage(YellowTileset, 32 * (n.x - left), 32 * (n.y - top), 32, 32, null);
                }
            }
            if (!GreenArea.isEmpty()) {
                for (Node n : GreenArea) {
                    g.drawImage(GreenTileset, 32 * (n.x - left), 32 * (n.y - top), 32, 32, null);
                }
            }
            if (!RedArea.isEmpty() && show_attack) {
                for (Node n : RedArea) {
                    g.drawImage(RedTileset, 32 * (n.x - left), 32 * (n.y - top), 32, 32, null);
                }
            }

            if (in_Preparation) {
                for (Node n : gba.chapter.map.original_position) {
                    g.drawImage(BlueTileset, 32 * (n.x - left), 32 * (n.y - top), 32, 32, null);
                }
                if (caught != null) {
                    g.drawImage(GreenTileset, 32 * (caught.x), 32 * caught.y, null);
                }
            }
            if (!AI_AffectRange.isEmpty() && test_switch) {
                for (Node n : AI_AffectRange) {
                    g.drawImage(RedTileset, 32 * (n.x - left), 32 * (n.y - top), 32, 32, null);
                }
            }

            for (Character c : gba.MONSTER) {
                g.drawImage(c.draw(), 32 * c.x + 11 * c.getDir_x(), 32 * c.y + 11 * c.getDir_y(), null);
                g.drawImage(yellow_flag, 32 * c.x + 11 * c.getDir_x(), 32 * c.y + 11 * c.getDir_y(), null);
                if (c == gba.Boss) {
                    g.drawImage(shield, 25 + 32 * c.x + 11 * c.getDir_x(), 20 + 32 * c.y + 11 * c.getDir_y(), null);
                }
            }

            for (Character c : gba.ENEMY) {
                g.drawImage(c.draw(), 32 * c.x + 11 * c.getDir_x(), 32 * c.y + 11 * c.getDir_y(), null);
                g.drawImage(red_flag, 32 * c.x + 11 * c.getDir_x(), 32 * c.y + 11 * c.getDir_y(), null);
                if (c == gba.Boss) {
                    g.drawImage(shield, 25 + 32 * c.x + 11 * c.getDir_x(), 20 + 32 * c.y + 11 * c.getDir_y(), null);
                }
            }
            for (Character pl : gba.PARTNER) {
                g.drawImage(pl.draw(), 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                if (pl.getMovepoint()) {
                    g.drawImage(green_flag, 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                } else {
                    g.drawImage(grey_flag, 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                }
            }

            for (Character pl : gba.chapter.PLAYER) {
                g.drawImage(pl.draw(), 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                if (pl.getMovepoint()) {
                    g.drawImage(blue_flag, 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                } else {
                    g.drawImage(grey_flag, 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                }
            }
            for (Character pl : gba.PLAYER) {
                g.drawImage(pl.draw(), 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                if (pl.getMovepoint()) {
                    g.drawImage(blue_flag, 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                } else {
                    g.drawImage(grey_flag, 32 * pl.x + 11 * pl.getDir_x(), 32 * pl.y + 11 * pl.getDir_y(), null);
                }
            }

            g.drawImage(cursor.draw(), cursor.getx() * 32, cursor.gety() * 32, null);
            if (show_Aim && aim != null) {
                g.drawImage(cursor.draw(), 32 * aim.x, 32 * aim.y, null);
            }
            if (show_Aim && item_aim != null) {
                g.drawImage(cursor.draw(), 32 * (item_aim.getX() - left), 32 * (item_aim.getY() - top), null);
            }

            if (ENEMY_PHASE) {
                g.setFont(Big);
                g.setColor(Color.RED);
                drawCentre(g, "ENEMY PHASE", 176, 128);
            }
            if (PLAYER_PHASE) {
                g.setFont(Big);
                g.setColor(Color.BLUE);
                drawCentre(g, "PLAYER PHASE", 176, 128);
            }
            //For Test
            if (show_MovePoint) {
                for (int x = 0; x < map_x; x++) {
                    for (int y = 0; y < map_y; y++) {
                        if (move_map[x][y] > 0) {
                            g.drawImage(BlueTileset, 32 * (x - left), 32 * (y - top), null);
                        } else {
                            g.drawImage(RedTileset, 32 * (x - left), 32 * (y - top), null);
                        }
                    }
                }
                g.setColor(Color.WHITE);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawCentre(Graphics g, String text, int x, int y) {
        textwidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, x - textwidth / 2, y);
    }

    protected void show_Effect_Area() {
        calculate_AttackArea(cursor.getAim());
        if (Attackable()) {
            show_attack = true;
            System.out.println("can attack");
        }

    }

    protected void MOVE(Character c, int x, int y) {

        if (c.x == x && c.y == y) {
            try {
                check_Terrain(c);
                gba.display_CharMenu(cursor.getAim(), cursor.getx(), cursor.gety());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            move(c, pathFinder(c, cursor.getx(), cursor.gety()));
        }
    }

    public void move(Character c, Stack<Direction> directions) {
        c.setStep(1);
        int start_x = c.x;
        int start_y = c.y;

        Timer timer = new Timer(70, new ActionListener() {
            Direction direction;
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step == 0) {
                    direction = directions.pop();
                }

                if (step < 2) {
                    step++;
                    switch (direction) {
                        case DOWN:
                            c.setDir(0);
                            c.setDir_x(0);
                            c.setDir_y(step);

                            break;
                        case LEFT:
                            c.setDir(1);
                            c.setDir_x(-step);
                            c.setDir_y(0);
                            break;
                        case RIGHT:
                            c.setDir(2);
                            c.setDir_x(step);
                            c.setDir_y(0);

                            break;
                        case UP:
                            c.setDir(3);
                            c.setDir_x(0);
                            c.setDir_y(-step);
                            break;
                    }
                    c.setStep(step);
                } else {
                    step = 0;
                    switch (direction) {
                        case DOWN:
                            c.y++;
                            break;
                        case LEFT:
                            c.x--;
                            break;
                        case RIGHT:
                            c.x++;
                            break;
                        case UP:
                            c.y--;
                            break;
                    }
                    c.setStep(step);
                    c.setDir_x(0);
                    c.setDir_y(0);
                    if (directions.isEmpty()) {
                        ((Timer) e.getSource()).stop();
                        //Display.Cursor does not move during character moving.
                        try {
                            check_Terrain(c);
                            gba.display_CharMenu(cursor.getAim(), start_x, start_y);
                            calculate_AttackArea(cursor.getAim());
                            if (Attackable()) {
                                show_attack = true;
                            }
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                }


                gba.repaint();

            }
        });

        timer.start();
    }


    protected Stack<Direction> pathFinder(Character c, int x, int y) {
        Stack<Direction> path = new Stack<>();
        LinkedList<Node> list = new LinkedList<>();
        ArrayList<Node> array = new ArrayList<>();
        if (c.x == x && c.y == y) {
            return path;
        }

        Node start = new Node(c.x, c.y, 0, c.getMove(), null);
        Node temp;
        Node next;
        int nxt_x, nxt_y;
        int map_x, map_y;
        list.add(start);
        while (!list.isEmpty()) {
            temp = list.poll();
            for (int[] dir : direction) {

                nxt_x = temp.x + dir[0];
                nxt_y = temp.y + dir[1];

                map_x = nxt_x + left;
                map_y = nxt_y + top;
                if (map_x < 0 || map_y < 0 || map_x >= this.map_x || map_y >= this.map_y) {
                    continue;
                }
                if (check_NotEnemy(c, gba.character_map[map_x][map_y]) && move_map[map_x][map_y] > 0 && temp.move >= move_map[map_x][map_y]) {

                    next = new Node(nxt_x, nxt_y, temp.round++, temp.move - move_map[map_x][map_y], temp);
                    if (!array.contains(next)) {
                        array.add(next);
                        list.add(next);
                        if (next.x == x && next.y == y) {
                            while (next.pre != null) {
                                path.push(generateDir(next));
                                next = next.pre;
                            }
                            return path;
                        }
                    }
                    continue;
                }

            }
        }
        return path;
    }

    private Direction generateDir(Node node) {

        int x = node.x - node.pre.x;
        int y = node.y - node.pre.y;
        if (x == 1) return Direction.RIGHT;
        if (x == -1) return Direction.LEFT;
        if (y == 1) return Direction.DOWN;
        if (y == -1) return Direction.UP;
        return null;
    }

    protected void moveRange(Character c) {
        LinkedList<Node> list = new LinkedList<>();
        ArrayList<Node> array = new ArrayList<>();
        Node start = new Node(c.x, c.y, 0, c.getMove(), null);
        Node temp;
        Node next;
        int nxt_x, nxt_y;
        int map_x, map_y;
        list.add(start);

        while (!list.isEmpty()) {
            temp = list.poll();
            for (int[] dir : direction) {
                nxt_x = temp.x + dir[0];
                nxt_y = temp.y + dir[1];
                map_x = nxt_x + left;
                map_y = nxt_y + top;
                if (map_x < 0 || map_y < 0) {
                    continue;
                }

                if (check_canArrive(map_x, map_y) && move_map[map_x][map_y] > 0 && temp.move >= move_map[map_x][map_y]) {
                    next = new Node(nxt_x, nxt_y, temp.round++, temp.move - move_map[map_x][map_y], temp);
                    if (!array.contains(next)) {
                        array.add(next);
                        list.add(next);
                        moveArea.add(new Node(map_x, map_y));

                    }
                    continue;
                }
            }
        }
    }

    private void AI_moveRange(Character c) {
        System.out.println(c.getName());
        LinkedList<Node> list = new LinkedList<>();
        ArrayList<Node> array = new ArrayList<>();
        AI_MoveRange.clear();
        Node start = new Node(c.x, c.y, 0, c.getMove(), null);
        Node temp;
        Node next;
        int nxt_x, nxt_y;
        int map_x, map_y;
        list.add(start);
        AI_MoveRange.add(new Node(c.x + left, c.y + top));
        while (!list.isEmpty()) {
            temp = list.poll();

            for (int[] dir : direction) {
                nxt_x = temp.x + dir[0];
                nxt_y = temp.y + dir[1];
                map_x = nxt_x + left;
                map_y = nxt_y + top;
                if (map_x < 0 || map_y < 0 || map_x >= this.map_x || map_y >= this.map_y) {
                    continue;
                }
                if (check_AIcanArrive(c, map_x, map_y) && move_map[map_x][map_y] > 0 && temp.move >= move_map[map_x][map_y]) {

                    next = new Node(nxt_x, nxt_y, temp.round++, temp.move - move_map[map_x][map_y], temp);
                    if (!array.contains(next)) {
                        array.add(next);
                        list.add(next);
                        AI_MoveRange.add(new Node(map_x, map_y));
                    }
                    continue;
                }
            }
        }
    }

    protected Stack<Direction> AI_Positive_pathFinder(Character c) {
        if (c.getCamp() == Camp.ENEMY || c.getCamp() == Camp.MONSTER) {
            int x = gba.PLAYER.get(0).x;
            int y = gba.PLAYER.get(0).y;
            System.out.println(x + "  " + y);
            Stack<Direction> path = new Stack<>();
            LinkedList<Node> list = new LinkedList<>();
            ArrayList<Node> array = new ArrayList<>();
            if (c.x == x && c.y == y) {
                return path;
            }

            Node start = new Node(c.x, c.y, 0, c.getMove(), null);
            System.out.println("move: " + c.getMove());
            Node temp;
            Node next;
            int nxt_x, nxt_y;
            int map_x, map_y;
            list.add(start);
            while (!list.isEmpty()) {
                temp = list.poll();
                for (int[] dir : direction) {

                    nxt_x = temp.x + dir[0];
                    nxt_y = temp.y + dir[1];

                    map_x = nxt_x + left;
                    map_y = nxt_y + top;
                    System.out.println("nxt x & y " + nxt_x + " " + nxt_y);
                    if (map_x < 0 || map_y < 0 || map_x >= this.map_x || map_y >= this.map_y) {
                        continue;
                    }
                    if (check_AIcanArrive(c,map_x, map_y) && move_map[map_x][map_y] > 0 && check_NotEnemy(c, gba.character_map[map_x][map_y]) || gba.character_map[map_x][map_y] == gba.PLAYER.get(0)) {

                        next = new Node(nxt_x, nxt_y, temp.round++, temp.move - move_map[map_x][map_y], temp);
                        if (!array.contains(next)) {
                            array.add(next);
                            list.add(next);
                            if (next.x == x && next.y == y) {
                                while (next.pre != null) {
                                    if (next.move >= 0) {

                                        path.push(generateDir(next));
                                        System.out.println("next is " + next.x + " " + next.y);
                                    }
                                    next = next.pre;
                                }
                                return path;
                            }
                        }
                        continue;
                    }
                }
            }

            return path;
        }
        return null;
    }

    protected boolean checkInMove(int x, int y) {
        Node n = new Node(left + x, top + y);
        if (moveArea.contains(n)) return true;
        return false;
    }

    protected boolean check_Blank(int x, int y) {
        if (x == cursor.getAim().x && y == cursor.getAim().y) {
            return true;
        }
        for (Character c : gba.PLAYER) {
            if (c.x == x && c.y == y) return false;
        }
        return true;
    }

    protected boolean checkPC(int x, int y) {
        for (Character pc : gba.PLAYER) {
            if (pc.x == x && pc.y == y) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkEnemy(int x, int y) {
        for (Character en : gba.ENEMY) {
            if (en.x == x && en.y == y) {
                return true;
            }
        }
        for (Character mon : gba.MONSTER) {
            if (mon.x == x && mon.y == y) {
                return true;
            }
        }
        return false;
    }

    protected boolean check_NotEnemy(Character c1, Character c2) {
        if (c2 == null) {
            return true;
        } else {
            return gba.check_Partner(c1.getCamp(), c2.getCamp());
        }
    }

    protected boolean checkEnvir(int X, int Y) {
        for (Envir_Item i : gba.ITEM) {
            if (i.getX() == X && i.getY() == Y) {
                return true;
            }
        }
        return false;

    }

    protected boolean check_Gate(int X, int Y) {
        if (gba.terrain_map[X][Y] instanceof Gate) {
            return true;
        }
        return false;
    }

    protected boolean check_Chest(int X, int Y) {
        if (gba.terrain_map[X][Y] instanceof Chest) {
            return true;
        }
        return false;
    }

//    protected boolean check_Obstacle(int X, int Y) {
//        if (gba.terrain_map[X][Y] instanceof Obstacle) {
//            return true;
//        }
//        return false;
//    }

//    protected boolean check_Obstacle(HashSet<Node> set) {
//        for (Node n : set) {
//            if (gba.terrain_map[n.x][n.y] instanceof Obstacle) {
//                return true;
//            }
//        }
//        return false;
//    }

    protected boolean check_canArrive(int X, int Y) {
        if (X >= map_x || Y >= map_y) return false;
        if (checkEnemy(X - left, Y - top)) return false;
        if (move_map[X][Y] <= 0) return false;
        if (check_Gate(X, Y)) return false;
        if (check_Chest(X, Y)) return false;

        return true;
    }

    protected boolean check_AIcanArrive(Character npc, int X, int Y) {

        if (move_map[X][Y] <= 0) return false;
        if (check_Gate(X, Y)) return false;
        if (check_Chest(X, Y)) return false;
        if (gba.character_map[X][Y] != null) {
            switch (npc.getCamp()) {
                case PLAYER:
                    if (gba.character_map[X][Y].getCamp() != Camp.PLAYER && gba.character_map[X][Y].getCamp() != Camp.PARTNER) {
                        return false;
                    } else {
                        return true;
                    }
                case ENEMY:
                    if (gba.character_map[X][Y].getCamp() != Camp.ENEMY) {
                        return false;
                    } else {
                        return true;
                    }
                case PARTNER:
                    if (gba.character_map[X][Y].getCamp() != Camp.PLAYER && gba.character_map[X][Y].getCamp() != Camp.PARTNER) {
                        return false;
                    } else {
                        return true;
                    }
                case MONSTER:
                    if (gba.character_map[X][Y].getCamp() != Camp.MONSTER) {
                        return false;
                    } else {
                        return true;
                    }
                default:
                    return false;
            }
        }
        return true;
    }


    protected void cleanMoveArea() {
        moveArea.clear();
    }

    protected void displayExchangeArea(boolean Switch) {
        if (Switch == true) {
            for (int[] dir : direction) {
                GreenArea.add(new Node(cursor.getx() + dir[0] + left, cursor.gety() + dir[1] + top));
            }
        } else {
            cleanGreenArea();
        }
    }

    protected void cleanGreenArea() {
        GreenArea.clear();
    }

    protected void cleanRedArea() {
        RedArea.clear();
    }

    protected void cleanNoColorArea() {
        NoColorArea.clear();
    }

    protected void generate_AimList() {
        AimList.clear();
        for (Node n : RedArea) {
            if (checkEnemy(n.x - left, n.y - top)) {
                AimList.add(gba.character_map[n.x][n.y]);
                System.out.println(gba.character_map[n.x][n.y].getName());
            }
        }
    }

    protected ArrayList generate_LockList() {
        LockList.clear();
        for (Node n : NoColorArea) {
            if (gba.terrain_map[n.x][n.y] instanceof Lock) {
                LockList.add(gba.terrain_map[n.x][n.y]);
            }
        }
        return LockList;
    }

    protected ArrayList<Envir_Item> generate_GateList() {
        LockList.clear();
        for (Node n : NoColorArea) {
            if (gba.terrain_map[n.x][n.y] instanceof Gate) {
                LockList.add(gba.terrain_map[n.x][n.y]);
            }
        }
        return LockList;
    }

    protected ArrayList<Envir_Item> generate_ChestList() {
        LockList.clear();
        for (Node n : NoColorArea) {
            if (gba.terrain_map[n.x][n.y] instanceof Chest) {
                LockList.add(gba.terrain_map[n.x][n.y]);
            }
        }
        return LockList;
    }


    protected void generate_TargetList(Camp camp) {
        AimList.clear();
        LockList.clear();
        switch (camp) {
            case PLAYER:
                for (Node n : GreenArea) {
                    if (checkPC(n.x - left, n.y - top)) {
                        AimList.add(gba.character_map[n.x][n.y]);
                    }
                }
                break;

            case ENEMY:
                for (Node n : GreenArea) {
                    if (checkEnemy(n.x - left, n.y - top)) {
                        AimList.add(gba.character_map[n.x][n.y]);
                    }
                    break;
                }
            default:
                break;
        }

    }

    protected ArrayList generate_TargetList(Camp camp, Staff staff) {
        AimList.clear();
        LockList.clear();
        switch (camp) {
            case PLAYER:
                for (Node n : GreenArea) {
                    if (checkPC(n.x - left, n.y - top) && staff.getMagicEffect().isApplicable(gba.character_map[n.x][n.y])) {
                        AimList.add(gba.character_map[n.x][n.y]);
                    }
                }
                return AimList;
            case Envir:
                for (Node n : GreenArea) {
                    if (checkEnvir(n.x, n.y) && staff.getMagicEffect().isApplicable(gba.terrain_map[n.x][n.y])) {
                        LockList.add(gba.terrain_map[n.x][n.y]);
                    }
                }
                return LockList;

            case ENEMY:
                for (Node n : GreenArea) {
                    if (checkEnemy(n.x - left, n.y - top) && staff.getMagicEffect().isApplicable(gba.character_map[n.x][n.y])) {
                        AimList.add(gba.character_map[n.x][n.y]);
                    }
                    return AimList;
                }
            default:
                return null;
        }

    }


    protected Object next_StaffAim(Staff s) {
        switch (s.getMagicEffect().getClass().getName()) {
            case "Item.HealMagic":
                return nextAim();
            case "Item.UnlockMagic":
                return nextLock();
            default:
                return null;
        }
    }

    protected Object pre_StaffAim(Staff s) {
        if (s == null) {
            System.out.println("no aim staff");
        }

        switch (s.getMagicEffect().getClass().getName()) {
            case "Item.HealMagic":
                return preAim();

            case "Item.UnlockMagic":
                return preLock();
            default:
                return null;
        }
    }

    protected Character nextAim() {
        int i = AimList.indexOf(aim);

        if (i == AimList.size() - 1) {
            i = 0;
        } else {
            i++;
        }
        aim = AimList.get(i);
        gba.FacetoTarget(cursor.getAim(), aim);
        return AimList.get(i);
    }

    protected Character preAim() {
        int i = AimList.indexOf(aim);
        if (i == 0) {
            i = AimList.size() - 1;
        } else {
            i--;
        }
        aim = AimList.get(i);
        gba.FacetoTarget(cursor.getAim(), aim);
        return AimList.get(i);
    }

    protected Envir_Item nextLock() {
        int i = LockList.indexOf(item_aim);
        if (i == LockList.size() - 1) {
            i = 0;
        } else {
            i++;
        }
        item_aim = LockList.get(i);
        gba.FacetoTarget(cursor.getAim(), item_aim);
        return LockList.get(i);
    }

    protected Envir_Item preLock() {
        int i = LockList.indexOf(item_aim);
        if (i == 0) {
            i = LockList.size() - 1;
        } else {
            i--;
        }
        item_aim = LockList.get(i);
        gba.FacetoTarget(cursor.getAim(), item_aim);
        return LockList.get(i);
    }


    protected void calculate_AttackArea(Character c) {
        for (Item item : c.getBag()) {
            if (c.can_Equip(item) && item instanceof Weapon) {
                add_Affect_Area((Weapon) item);
            }
        }
    }

    protected void add_Affect_Area(Weapon weapon) {
        HashSet<Node> set = RedArea;
        if (weapon instanceof Staff) {
            set = GreenArea;
        }
        int x;
        int y;
        for (int i = weapon.getRange_1(); i <= weapon.getRange_2(); i++) {

            for (x = 0; x <= i; x++) {
                y = i - x;
                addTileset(set, x, y);
            }
        }
    }

    protected void add_Affect_Area() {
        for (int[] dir : direction) {
            NoColorArea.add(new Node(left + cursor.getx() + dir[0], top + cursor.gety() + dir[1]));
        }
    }


    protected void addTileset(HashSet<Node> set, int x, int y) {
        if (cursor.getx() + x + left >= 0 && cursor.getx() + x + left < map_x && cursor.gety() + y + top >= 0 && cursor.gety() + y + top < map_y) {
            set.add(new Node(cursor.getx() + x + left, cursor.gety() + y + top));
        }
        if (cursor.getx() - x + left >= 0 && cursor.getx() - x + left < map_x && cursor.gety() + y + top >= 0 && cursor.gety() + y + top < map_y) {
            set.add(new Node(cursor.getx() - x + left, cursor.gety() + y + top));
        }
        if (cursor.getx() + x + left >= 0 && cursor.getx() + x + left < map_x && cursor.gety() - y + top >= 0 && cursor.gety() - y + top < map_y) {
            set.add(new Node(cursor.getx() + x + left, cursor.gety() - y + top));
        }
        if (cursor.getx() - x + left >= 0 && cursor.getx() - x + left < map_x && cursor.gety() - y + top >= 0 && cursor.gety() - y + top < map_y) {
            set.add(new Node(cursor.getx() - x + left, cursor.gety() - y + top));
        }
    }

    protected boolean Seizeable() {
        if (gba.chapter.objective == Victory_Condition.Seize && gba.chapter.seize_point != null && cursor.getAim().x + left == gba.chapter.seize_point.x && cursor.getAim().y + top == gba.chapter.seize_point.y) {
            return true;
        } else {
            return false;
        }
    }


    protected boolean Attackable() {
        for (Node n : RedArea) {
            if (checkEnemy(n.x - left, n.y - top)) {
                return true;
            }
        }
        return false;
    }

    protected boolean Staffable() {
        gba.Staff_Menu.clearList();
        for (Item item : cursor.getAim().getBag()) {
            if (item instanceof Staff && cursor.getAim().can_Equip(item)) {
                Staff staff = (Staff) item;
                System.out.println(staff.getMagicEffect().getClass().getName());
                switch (staff.getMagicEffect().getClass().getName()) {
                    case "Item.HealMagic":
                        for (Character pc : gba.PLAYER) {
                            if (check_inRange(pc, staff) && staff.getMagicEffect().isApplicable(pc)) {
                                gba.Staff_Menu.addStaff(staff);
                            }
                        }
                        break;
                    case "Item.UnlockMagic":
                        for (Envir_Item ei : gba.ITEM) {
                            if (check_inRange(ei, staff) && staff.getMagicEffect().isApplicable(ei)) {
                                gba.Staff_Menu.addStaff(staff);
                            }
                        }
                        break;

                    case "TransportStaff":
                        break;
                    default:
                        break;
                }
            }
        }

        if (gba.Staff_Menu.getStaff().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    //Requirement for Steal: 1.item is not equipped. 2.thief's Str > item's Weight. 3. thief's Spd > en's SPD 4.thief can hold more item.
    protected boolean Stealable() {
        if (cursor.getAim().getJob() == Job.Thief && cursor.getAim().getBag().size() < 5) {
            for (Character en : enemyAround()) {
                if (cursor.getAim().getSpd() > en.getSpd()) {
                    for (Item item : en.getBag()) {
                        if (item != en.getEquiped_Weapon()) {
                            if (cursor.getAim().getStr() > item.getWeight()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    protected HashSet<Character> enemyAround() {
        HashSet<Character> list = new HashSet<>();
        Direction[] direction = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        for (Direction dir : direction) {
            if (checkEnemy(cursor.getAim().x + dir.x, cursor.getAim().y + dir.y)) {
                list.add(gba.character_map[cursor.getAim().x + dir.x + left][cursor.getAim().y + dir.y + top]);
            }
        }
        return list;
    }

    protected boolean can_Unlock_Gate() {
        if (Gate_Around()) {
            if (cursor.getAim().getJob() == Job.Thief) {
                return true;
            }
            for (Item item : cursor.getAim().getBag()) {
                if (item instanceof GateKey) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean Gate_Around() {
        for (int[] dir : direction) {
            int X = left + cursor.getx() + dir[0];
            int Y = top + cursor.gety() + dir[1];
            if (isInMap(X, Y) && gba.terrain_map[X][Y] != null &&
                    gba.terrain_map[left + cursor.getx() + dir[0]][top + cursor.gety() + dir[1]] instanceof Gate) {
                return true;
            }
        }
        return false;
    }

    protected boolean can_Unlock_Chest() {
        if (Chest_Around()) {
            if (cursor.getAim().getJob() == Job.Thief) {
                return true;
            }
            for (Item item : cursor.getAim().getBag()) {
                if (item instanceof ChestKey) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean Chest_Around() {
        for (int[] dir : direction) {
            int X = left + cursor.getx() + dir[0];
            int Y = top + cursor.gety() + dir[1];
            if (isInMap(X, Y) && gba.terrain_map[X][Y] != null &&
                    gba.terrain_map[left + cursor.getx() + dir[0]][top + cursor.gety() + dir[1]] instanceof Chest) {

                return true;
            }
        }
        return false;
    }

    protected boolean check_inRange(ArrayList<Character> Cs, Weapon weapon) {
        int distance;
        if (Cs.isEmpty()) {
            return false;
        }
        for (Character c : Cs) {
            distance = Math.abs(cursor.getAim().x - c.x) + Math.abs(cursor.getAim().y - c.y);
            if (distance >= weapon.getRange_1() && distance <= weapon.getRange_2()) {
                return true;
            }
        }
        return false;
    }

    protected boolean check_inRange(Envir_Item e_i, Staff staff) {

        if (e_i == null) return false;

        int distance = Math.abs(cursor.getAim().x + left - e_i.getX()) + Math.abs(cursor.getAim().y + top - e_i.getY());
        if (distance >= staff.getRange_1() && distance <= staff.getRange_2()) return true;

        return false;

    }

    protected boolean check_inRange(Character c, Weapon w) {

        if (c == null) {
            return false;
        }
        int distance = Math.abs(cursor.getAim().x - c.x) + Math.abs(cursor.getAim().y - c.y);
        if (distance >= w.getRange_1() && distance <= w.getRange_2()) {
            return true;
        }
        return false;
    }

    protected boolean check_inRange(HashSet aim_set, HashSet<Node> set) {
        for (Object o : aim_set) {
            if (o instanceof Character) {
                for (Node n : set) {
                    if (((Character) o).x != n.x || ((Character) o).y != n.y) {
                        aim_set.remove(o);
                    }
                }
            } else if (o instanceof Envir_Item) {
                for (Node n : set) {
                    if (((Envir_Item) o).getX() - left != n.x || ((Envir_Item) o).getY() - top != n.y) {
                        aim_set.remove(o);
                    }
                }
            }
        }
        if (!aim_set.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isIn_Preparation() {
        return in_Preparation;
    }

    public void setIn_Preparation(boolean in_Preparation) {
        this.in_Preparation = in_Preparation;
    }

//    public Stack<int[]> action_Attack(Character c_1, Character c_2) {
//        int[] action_shift = new int[4];
//        int[] action_shift_2 = new int[4];
//        int[] action_shift_3 = new int[4];
//
//        switch (c_1.getDir()) {
//            case 0:
//                action_shift[0] = 0;
//                action_shift[1] = 1;
//                action_shift[2] = 0;
//                action_shift[3] = 1;
//                break;
//            case 1:
//                action_shift[0] = -1;
//                action_shift[1] = 0;
//                action_shift[2] = -1;
//                action_shift[3] = 0;
//                break;
//            case 2:
//                action_shift[0] = 1;
//                action_shift[1] = 0;
//                action_shift[2] = 1;
//                action_shift[3] = 0;
//                break;
//            case 3:
//                action_shift[0] = 0;
//                action_shift[1] = -1;
//                action_shift[2] = 0;
//                action_shift[3] = -1;
//                break;
//            default:
//                break;
//        }
//
//        Stack<int[]> stack = new Stack<>();
//        stack.push(action_shift);
//        for (int i = 0; i < 4; i++) {
//            action_shift_2[i] = -action_shift[i];
//        }
//
//        stack.push(action_shift_2);
//
//        stack.push(action_shift);
//
//        return stack;
//
//    }

    private int[] getAttack_move(Character c) {
        int[] action_shift = new int[2];
        switch (c.getDir()) {
            case 0:
                action_shift[0] = 0;
                action_shift[1] = 1;

                break;
            case 1:
                action_shift[0] = -1;
                action_shift[1] = 0;

                break;
            case 2:
                action_shift[0] = 1;
                action_shift[1] = 0;

                break;
            case 3:
                action_shift[0] = 0;
                action_shift[1] = -1;

                break;
            default:
                break;
        }
        return action_shift;
    }

    private int[] getAvoid_move(Character c) {
        int[] action_shift = new int[2];
        switch (c.getDir()) {
            case 0:
                action_shift[0] = 0;
                action_shift[1] = -1;
                break;
            case 1:
                action_shift[0] = 1;
                action_shift[1] = 0;
                break;
            case 2:
                action_shift[0] = -1;
                action_shift[1] = 0;
                break;
            case 3:
                action_shift[0] = 0;
                action_shift[1] = 1;
                break;
            default:
                break;
        }
        return action_shift;
    }


    protected void Battle(Character c1, Character c2) {
        LinkedList<int[]> move = generage_BattleProcess(c1, c2);
        exp_1 = 1;
        exp_2 = 1;
        w_exp_1 = 0;
        w_exp_2 = 0;
        gba.switch_on(gba.window_1, c1);
        gba.switch_on(gba.window_2, c2);
        sub_Battle(move, c1, c2);
    }


    protected LinkedList<int[]> generage_BattleProcess(Character c1, Character c2) {
        calculate_BattlePara(c1, c2);
        LinkedList<int[]> list = new LinkedList<>();
        int onoff = sub_round(0, c1, c2, list, 1);
        onoff = sub_round(1, c1, c2, list, onoff);
        if (turn_1 == 2) {
            sub_round(0, c1, c2, list, onoff);
        } else if (turn_2 == 2) {
            sub_round(1, c1, c2, list, onoff);
        }
        for (int[] i : list) {
            for (int j : i) {
                System.out.print(j);
            }
            System.out.println("");
        }
        return list;
    }

    static final int[] reset = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private int sub_round(int turn_no, Character c1, Character c2, LinkedList<int[]> list, int onoff) {
        int[] sub = {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        int[] attack_shift;
        int[] avoid_shift;

        if (list.isEmpty() || onoff == 1) {
            sub[0] = turn_no;
            if (turn_no == 0) {
                if (uses_1 > 0) {
                    attack_shift = getAttack_move(c1);
                    sub[1] = attack_shift[0];
                    sub[2] = attack_shift[1];
                    if (roll_Dice(HIT_1)) {
                        uses_1--;
                        sub[7] = 1;
                        sub[5] = damage_1;
                        sub[8] = 1;
                        if (roll_Dice(CRIT_1)) {
                            sub[8] = 3;
                        }
                    } else {
                        avoid_shift = getAvoid_move(c2);
                        sub[7] = 0;
                        sub[8] = 0;
                        sub[5] = 0;
                        sub[3] = avoid_shift[0];
                        sub[4] = avoid_shift[1];
                    }
                    hp_2 -= sub[5] * sub[8];

                }
                if (hp_2 > 0) {
                    sub[9] = 1;
                }
            } else if (turn_no == 1) {
                if (!(c2.getEquiped_Weapon() instanceof Staff) && uses_2 > 0) {
                    attack_shift = getAttack_move(c2);
                    sub[3] = attack_shift[0];
                    sub[4] = attack_shift[1];


                    if (roll_Dice(HIT_2)) {
                        uses_2--;
                        sub[7] = 1;
                        sub[6] = damage_2;
                        sub[8] = 1;
                        if (roll_Dice(CRIT_2)) {
                            sub[8] = 3;
                        }
                    } else {
                        avoid_shift = getAvoid_move(c1);
                        sub[1] = avoid_shift[0];
                        sub[2] = avoid_shift[1];
                        sub[6] = 0;
                        sub[7] = 0;
                        sub[8] = 0;
                    }
                    hp_1 -= sub[6] * sub[8];

                }
                if (hp_1 > 0) {
                    sub[9] = 1;
                }
            }
            if (sub[1] != 0 || sub[2] != 0 || sub[3] != 0 || sub[4] != 0) {
                list.add(sub);
                list.add(reset);
            }
        }

        return sub[9];
    }

    private boolean roll_Dice(int Range) {
        int hit = random.nextInt(100) + 1;
        if (hit <= Range) {
            return true;
        } else {
            return false;
        }

    }


    protected void display_LVUP(Character c, Stack<String> texts) {

        gba.in_Menu = true;
        z_mark = 1;
        for (int i = 0; i < 8; i++) {
            gba.lv.getUp()[i] = false;
        }
        gba.lv.setVisible(true);
        gba.lv.setC(c);
        c.setLv(c.getLv() + 1);

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roll(c);
                gba.lv.repaint();
                if (i == 8) {
                    i = 0;
                    ((Timer) e.getSource()).stop();
                    gba.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                        }

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_Z || e.getKeyCode() == KeyEvent.VK_X) {
                                gba.lv.setVisible(false);
                                gba.removeKeyListener(this);
                                gba.lv.clean();
                                if (!texts.isEmpty()) {
                                    gba.display_Message(c, texts);
                                } else {
                                    if (gba.othersTurn) {
                                        gba.next_NPC();
                                    } else {
                                        if (gba.is_Victory()) {
                                            gba.Chapter_Complete();
                                        } else {
                                            gba.in_Menu = false;
                                            z_mark = 0;
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {

                        }
                    });
                }
            }
        });
        timer.start();
    }

    int i = 0;

    private void roll(Character c) {
        System.out.println(c.getName() + " i = " + i);
        if (c.getGrowth() != null && i < 8) {
            gba.lv.getUp()[i] = roll_Dice(c.getGrowth()[i]);
            i++;
            if (!gba.lv.getUp()[i - 1]) {
                roll(c);
            } else {
                c.Ability_UP(i - 1);
            }
        }
    }

    public int[] transportPara() {
        int[] para = new int[10];
        calculate_BattlePara(cursor.getAim(), aim);
        para[0] = hp_1;
        para[1] = hp_2;
        para[2] = damage_1;
        para[3] = damage_2;
        para[4] = HIT_1;
        para[5] = HIT_2;
        para[6] = CRIT_1;
        para[7] = CRIT_2;
        para[8] = turn_1;
        para[9] = turn_2;
        return para;
    }

    int hp_1, hp_2;
    int atk_1, atk_2;
    int uses_1, uses_2;
    int hit_bonus_1, hit_bonus_2;
    int might_bonus_1, might_bonus_2;
    int def_1, def_2;
    int damage_1, damage_2;
    int HIT_1, HIT_2;
    int hit_1, hit_2;
    int avo_1, avo_2;
    int crit_1, crit_2;
    int crit_avo_1, crit_avo_2;
    int CRIT_1, CRIT_2;
    int spd_1, spd_2;
    int turn_1, turn_2;
    int exp_1 = 1, exp_2 = 1;
    int w_exp_1, w_exp_2;
    Item drop;


    private void calculate_BattlePara(Character c1, Character c2) {
        hp_1 = c1.gethp();
        hp_2 = c2.gethp();
        c1.first_Weapon();
        c2.first_Weapon();

        compare_Weapon(c1.getEquiped_Weapon(), c2.getEquiped_Weapon());
        if (c1.getEquiped_Weapon().Slayer_check(c2)) {
            atk_1 = c1.getMight() + (c1.getEquiped_Weapon().getMight() + might_bonus_1) * 2;
        } else {
            atk_1 = c1.getMight() + c1.getEquiped_Weapon().getMight() + might_bonus_1;
        }
        uses_1 = c1.getEquiped_Weapon().getUses();

        int distance = Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);

        if (c2.getEquiped_Weapon() != null && !(c2.getEquiped_Weapon() instanceof Staff) && c2.getEquiped_Weapon().getRange_2() >= distance && c2.getEquiped_Weapon().getRange_1() <= distance) {
            uses_2 = c2.getEquiped_Weapon().getUses();
            if (c2.getEquiped_Weapon().Slayer_check(c1)) {
                atk_2 = c2.getMight() + (c2.getEquiped_Weapon().getMight() + might_bonus_2) * 2;
            } else {
                atk_2 = c2.getMight() + c2.getEquiped_Weapon().getMight() + might_bonus_2;
            }
            def_1 = c1.getDefence(c2.getEquiped_Weapon()) + c1.getEnvir_DEF();
            def_2 = c2.getDefence(c1.getEquiped_Weapon()) + c2.getEnvir_DEF();
            damage_1 = Math.max(0, atk_1 - def_2);
            damage_2 = Math.max(0, atk_2 - def_1);
            hit_1 = c1.getEquiped_Weapon().getHit() + c1.getSkl() * 2 + c1.getLuk() + hit_bonus_1;
            hit_2 = c2.getEquiped_Weapon().getHit() + c2.getSkl() * 2 + c2.getLuk() + hit_bonus_2;
            int weight = c1.getEquiped_Weapon().getWeight() - c1.getStr();
            if (weight < 0) {
                weight = 0;
            }
            spd_1 = c1.getSpd() - weight;

            weight = c2.getEquiped_Weapon().getWeight() - c2.getStr();
            if (weight < 0) {
                weight = 0;
            }
            spd_2 = c2.getSpd() - weight;
            avo_1 = spd_1 * 2 + c1.getLuk() + c1.getEnvir_AVO();
            avo_2 = spd_2 * 2 + c2.getLuk() + c2.getEnvir_AVO();
            HIT_1 = hit_1 - avo_2;
            HIT_2 = hit_2 - avo_1;
            crit_1 = c1.getEquiped_Weapon().getCrit() + c1.getSkl() / 2;
            crit_2 = c2.getEquiped_Weapon().getCrit() + c2.getSkl() / 2;
            crit_avo_1 = c1.getLuk();
            crit_avo_2 = c2.getLuk();
            CRIT_1 = Math.max(crit_1 - crit_avo_2, 0);
            CRIT_2 = Math.max(crit_2 - crit_avo_1, 0);
            turn_1 = 1;
            turn_2 = 1;
            if (spd_1 >= spd_2 + 4 && uses_1 >= 2) {
                turn_1++;
            }
            if (spd_2 >= spd_1 + 4 && uses_2 >= 2) {
                turn_2++;
            }
        } else {
            uses_2 = 0;
            if (c1.getEquiped_Weapon().Slayer_check(c2)) {
                atk_1 = c1.getMight() + (c1.getEquiped_Weapon().getMight() + might_bonus_1) * 2;
            } else {
                atk_1 = c1.getMight() + c1.getEquiped_Weapon().getMight() + might_bonus_1;
            }
            def_1 = c1.getDefence(c2.getEquiped_Weapon()) + c1.getEnvir_DEF();
            def_2 = c2.getDefence(c1.getEquiped_Weapon()) + c2.getEnvir_DEF();
            damage_1 = atk_1 - def_2;
            damage_2 = 0;

            int weight = c1.getEquiped_Weapon().getWeight() - c1.getStr();
            if (weight < 0) {
                weight = 0;
            }
            spd_1 = c1.getSpd() - weight;
            spd_2 = c2.getSpd();
            avo_1 = spd_1 * 2 + c1.getLuk() + c1.getEnvir_AVO();
            avo_2 = spd_2 * 2 + c2.getLuk() + c2.getEnvir_AVO();
            hit_1 = c1.getEquiped_Weapon().getHit() + c1.getSkl() * 2 + c1.getLuk() + hit_bonus_1;
            HIT_1 = hit_1 - avo_2;
            CRIT_1 = crit_1 - crit_avo_2;
            HIT_2 = 0;
            CRIT_2 = 0;
            turn_1 = 1;
            turn_2 = 1;
            if (spd_1 >= spd_2 + 4) {
                turn_1++;
            }
            if (spd_2 >= spd_1 + 4) {
                turn_2++;
            }
        }
    }


    private void compare_Weapon(Weapon w1, Weapon w2) {
        if (w1 != null && w2 != null) {
            if (w1.isDominant(w2)) {
                hit_bonus_1 = 10;
                might_bonus_1 = 1;
                hit_bonus_2 = -10;
                might_bonus_2 = -1;
                String type_2 = "Item." + w2.getType();
                if (type_2 == w2.Priority_Type()) {
                    hit_bonus_2 = 20;
                    might_bonus_2 = 2;
                }
            } else if (w2.isDominant(w1)) {
                hit_bonus_1 = -10;
                might_bonus_1 = -1;
                hit_bonus_2 = 10;
                might_bonus_2 = 1;
                String type_1 = "Item." + w1.getType();
                if (type_1 == w1.Priority_Type()) {
                    hit_bonus_1 = 20;
                    might_bonus_1 = 2;
                }
            } else {
                hit_bonus_2 = 0;
                hit_bonus_1 = 0;
                might_bonus_2 = 0;
                might_bonus_1 = 0;
            }
        } else {
            hit_bonus_2 = 0;
            hit_bonus_1 = 0;
            might_bonus_2 = 0;
            might_bonus_1 = 0;
        }
    }


    private void sub_Battle(LinkedList<int[]> move, Character c1, Character c2) {
        int[] turn = move.pollFirst();
        int[] reset = move.pollFirst();

        Stack<int[]> stack = new Stack<>();
        stack.push(reset);
        stack.push(turn);

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] action = stack.pop();
                c1.setDir_x(action[1]);
                c1.setDir_y(action[2]);
                c2.setDir_x(action[3]);
                c2.setDir_y(action[4]);
                c1.getDamage(action[6] * action[8]);
                c2.getDamage(action[5] * action[8]);
                if (action[7] == 1) {
                    if (action[0] == 0) {
                        c1.getEquiped_Weapon().consume();
                        exp_1 = 10 + Math.max(c2.getLv() - c1.getLv(), -5);
                        w_exp_1 += c1.getEquiped_Weapon().getExp();
                    }
                    if (action[0] == 1) {
                        c2.getEquiped_Weapon().consume();
                        exp_2 = 10 + Math.max(c1.getLv() - c2.getLv(), -5);
                        w_exp_2 += c2.getEquiped_Weapon().getExp();
                    }
                }

                gba.repaint();
                if (stack.isEmpty()) {
                    ((Timer) e.getSource()).stop();
                    display_HPShift(move, c1, c2);
                }
            }
        });
        timer.start();
    }

    private void display_HPShift(LinkedList<int[]> move, Character c1, Character c2) {

        Timer timer = new Timer(100, new ActionListener() {
            int val_1 = c1.gethp() - gba.window_1.gethp();
            int val_2 = c2.gethp() - gba.window_2.gethp();


            @Override
            public void actionPerformed(ActionEvent e) {
                if (val_1 == 0 && val_2 == 0) {
                    ((Timer) e.getSource()).stop();
                    if (!move.isEmpty()) {
                        sub_Battle(move, c1, c2);
                    } else {
                        gba.window_1.setVisible(false);
                        gba.window_2.setVisible(false);
                        gba.in_Menu = false;
                        clean();
                        aim = null;
                        if (gba.check_Defeated(c1)) exp_2 = 30 + 2 * Math.max(c1.getLv() - c2.getLv(), -5);
                        if (gba.check_Defeated(c2)) exp_1 = 30 + 2 * Math.max(c2.getLv() - c1.getLv(), -5);

                        battle_result(c1, c2);
                    }
                }
                if (val_1 > 0) {
                    val_1--;
                    gba.window_1.hp_plus();
                } else if (val_1 < 0) {
                    val_1++;
                    gba.window_1.hp_minus();
                }
                if (val_2 > 0) {
                    val_2--;
                    gba.window_2.hp_plus();
                } else if (val_2 < 0) {
                    val_2++;
                    gba.window_2.hp_minus();
                }
                gba.window_1.repaint();
                gba.window_2.repaint();

            }
        });
        timer.start();
    }

    private void battle_result(Character c1, Character c2) {
        Character c = null;
        int exp = 0;
        int w_exp = 0;
        if (c1.getCamp() == Camp.PLAYER || c1.getCamp() == Camp.PARTNER) {
            c = c1;
            w_exp = w_exp_1;
            exp = exp_1;
            if (c2.gethp() <= 0) {
                drop = gba.check_Drops(c2);
                if (drop != null) drop.setDropable(false);
            }
        }
        if (c2.getCamp() == Camp.PLAYER || c2.getCamp() == Camp.PARTNER) {
            c = c2;
            exp = exp_2;
            w_exp = w_exp_2;
            if (c1.gethp() <= 0) {
                drop = gba.check_Drops(c1);
                if (drop != null) {
                    drop.setDropable(false);
                }
            }
        }
        if (c != null && c.gethp() > 0) {
            int i;
            if (c.getEquiped_Weapon() != null) {
                switch (c.getEquiped_Weapon().getClass().getName()) {
                    case "Item.Sword":
                        i = 0;
                        break;
                    case "Item.Lance":
                        i = 1;
                        break;
                    case "Item.Axe":
                        i = 2;
                        break;
                    case "Item.Bow":
                        i = 3;
                        break;
                    case "Item.Wind":
                        i = 4;
                        break;
                    case "Item.Fire":
                        i = 5;
                        break;
                    case "Item.Thunder":
                        i = 6;
                        break;
                    default:
                        i = 8;
                        break;
                }
                if (i < 8) {
                    c.addWeapon_exp(i, w_exp);
                }
            }
            exp = exp * 2;
            display_EXP(c, exp);
        } else {
            if (gba.is_Victory()) {
                gba.Chapter_Complete();
            } else {
                if (gba.othersTurn) gba.next_NPC();
            }

        }

    }

    protected void display_EXP(Character c, int exp) {
        gba.in_Menu = true;
        gba.exp_window.setC(c);
        gba.exp_window.setexp(c.getExp());
        gba.exp_window.setVisible(true);

        Timer timer = new Timer(30, new ActionListener() {
            int val = exp;
            int EXP = val + c.getExp();

            @Override
            public void actionPerformed(ActionEvent e) {
                val--;
                gba.exp_window.exp_plus();
                gba.exp_window.repaint();

                if (val == 0) {
                    ((Timer) e.getSource()).stop();
                    c.setExp(gba.exp_window.getexp());
                    gba.exp_window.setVisible(false);
                    gba.in_Menu = false;
                    if (!gba.othersTurn) {
                        c.setMovepoint(false);
                    }
                    display_LV_UP(c, EXP);
                }
            }
        });
        timer.start();
    }

    protected void display_LV_UP(Character c, int EXP) {
        Stack<String> texts = new Stack<>();
        if (c.first_Weapon() != null && c.first_Weapon().getUses() == 0) {
            Weapon weapon = c.getEquiped_Weapon();
            c.getBag().remove(weapon);
            c.setEquiped_Weapon(null);
            texts.push(weapon.getName() + " Broken...");
        }
        if (EXP >= 100) {
            c.setExp(EXP - 100);
            if (drop != null) {
                texts.push("Obtain " + drop.getName());
                c.gainItem(drop);
                drop = null;
                gba.top_screen.repaint();
            }
            display_LVUP(c, texts);

        } else {
            if (drop != null) {
                texts.push("Obtain " + drop.getName());
                c.gainItem(drop);
                drop = null;
                if (gba.othersTurn) gba.top_screen.repaint();
            }
            gba.display_Message(c, texts);
        }
    }


    protected void show_AffectRange(Character npc) {
        AI_AffectRange.clear();
        if (npc.first_Weapon() != null) {
            AI_moveRange(npc);
            for (Node n : AI_MoveRange) {
                for (Item item : npc.getBag()) {
                    if (item instanceof Weapon && npc.can_Equip(item)) {
                        AI_AffectArea((Weapon) item, n);
                    }
                }
            }
        }
    }

    protected void AI_AffectRange(Character npc) {
        AI_AffectRange.clear();
        if (npc.first_Weapon() != null) {
            if (npc.getAi() != AI.Negative) {
                AI_moveRange(npc);
            } else {
                AI_MoveRange.clear();
                AI_MoveRange.add(new Node(npc.x + left, npc.y + top));
            }

            for (Node n : AI_MoveRange) {
                for (Item item : npc.getBag()) {
                    if (item instanceof Weapon && npc.can_Equip(item)) {
                        AI_AffectArea((Weapon) item, n);
                    }
                }
            }
        }
    }

    protected void AI_AffectArea(Weapon weapon, Node n) {

        int x;
        int y;
        for (int i = weapon.getRange_1(); i <= weapon.getRange_2(); i++) {

            for (x = 0; x <= i; x++) {
                y = i - x;
                add_Node(AI_AffectRange, n, x, y);
            }
        }
    }

    protected void add_Node(HashSet<Node> set, Node n, int x, int y) {
        if (n.x + x >= 0 && n.x + x < map_x && n.y + y >= 0 && n.y + y < map_y) {
            set.add(new Node(n.x + x, n.y + y));
        }
        if (n.x - x >= 0 && n.x - x < map_x && n.y + y >= 0 && n.y + y < map_y) {
            set.add(new Node(n.x - x, n.y + y));
        }
        if (n.x + x >= 0 && n.x + x < map_x && n.y - y >= 0 && n.y - y < map_y) {
            set.add(new Node(n.x + x, n.y - y));
        }
        if (n.x - x >= 0 && n.x - x < map_x && n.y - y >= 0 && n.y - y < map_y) {
            set.add(new Node(n.x - x, n.y - y));
        }
    }


    protected int calculate_StaffHit(Staff staff, Character attacker, Character target) {
        int hit = 30 + 5 * (attacker.getMag() - target.getRes()) + attacker.getSkl() - 2 * (Math.abs(attacker.x - target.x) + Math.abs(attacker.y - target.y));
        return hit;
    }

    protected boolean isInMap(int X, int Y) {
        if (X >= 0 && Y >= 0 && X < map_x && Y < map_y) {
            return true;
        }
        return false;
    }

    protected void clean() {
        z_mark = 0;
        show_Aim = false;
        repaint();
        cleanRedArea();
        cleanGreenArea();
        cleanNoColorArea();
    }

    //exchange original positions in map interface.
    protected void exchange() {
        Character c = gba.character_map[cursor.getx() + left][cursor.gety() + top];
        if (c == null) {
            gba.character_map[caught.x + left][caught.y + top] = null;
            caught.fold(cursor.getx(), cursor.gety());
            gba.character_map[cursor.getx() + left][cursor.gety() + top] = caught;
            caught = null;
        } else {
            int x = c.x;
            int y = c.y;
            c.setPosition(caught.x, caught.y);
            caught.setPosition(x, y);
            gba.character_map[c.x + left][c.y + top] = c;
            gba.character_map[caught.x + left][caught.y + top] = caught;
            caught = null;
        }
    }

    protected void check_Terrain(Character c) {
        int X = c.x + left;
        int Y = c.y + top;
        Envir_Item terrain = gba.chapter.map.terrain_map[X][Y];
        if (terrain != null) {
            c.setEnvir_DEF(terrain.getDEF_Bonus());
            c.setEnvir_AVO(terrain.getAVO_Bonus());
            c.setEnvir_HP(terrain.getHEAL_Bonus());
        } else {
            c.setEnvir_DEF(0);
            c.setEnvir_AVO(0);
            c.setEnvir_HP(0);
        }
    }
    public void setMap(BufferedImage map) {
        this.map = map;
    }



}
