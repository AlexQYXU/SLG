import Characters.*;
import Characters.Character;
import Characters.Transporter;
import Display.*;
import Display.Cursor;
import Item.Envir_Items.*;
import Item.Item;
import Item.Key.ChestKey;
import Item.Key.GateKey;
import Item.StaffBuilder;
import Item.TypeAdapters.AxeTypeAdapter;
import Item.WeaponLevel;
import Item.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;


public class GBA extends JFrame {
    static Top_Screen top_screen;
    static Bottom_Screen bottom_screen;
    static Display.Cursor cursor;
    Transporter transporter;
    ArrayList<Character> ALL_PLAYER;
    int asset;
    ArrayList<Character> PLAYER;
    ArrayList<Character> Grave;
    ArrayList<Character> Market;
    ArrayList<Character> ENEMY;
    ArrayList<Character> PARTNER;
    ArrayList<Character> MONSTER;
    ArrayList<Envir_Item> ITEM;
    ArrayList<Character> OBSTACLE;
    Display.Menu Menu = new Display.Menu();
    Character[][] character_map = new Character[22][29];
    Envir_Item[][] terrain_map = new Envir_Item[22][29];
    String address = System.getProperty("user.dir") + "/src/main/resources/Icons";

    boolean in_Menu = false;
    boolean othersTurn;

    Dialogue_Window dialog = new Dialogue_Window();
    Item_Menu Item_Menu = new Item_Menu();
    Display.Attack_Menu Attack_Menu = new Attack_Menu();
    Team_Menu Team_Menu = new Team_Menu();
    Steal_Menu Steal_Menu = new Steal_Menu();
    Exchange_Menu Exchange_Menu = new Exchange_Menu();
    Display.Staff_Menu Staff_Menu = new Staff_Menu();
    Display.HP_Window window_1 = new HP_Window();
    Display.HP_Window window_2 = new HP_Window();
    Key_Menu Key_Menu = new Key_Menu();
    Store_Menu Store_Menu = new Store_Menu();
    Message_Window message_window = new Message_Window();
    EXP_Window exp_window = new EXP_Window();
    Preparation_Interface P_I = new Preparation_Interface();
    Character_Interface C_I = new Character_Interface();
    Chapter_Interface chapter_interface = new Chapter_Interface();
    Item_Interface I_I = new Item_Interface();
    Transporter_Interface T_I = new Transporter_Interface();
    Market_Interface M_I = new Market_Interface();
    Shop_Interface S_I = new Shop_Interface();
    Start_Interface start = new Start_Interface();
    File_Interface F_I = new File_Interface();
    Level_UP lv = new Level_UP();

    Shop shop;
    private Timer hp_timer;
    Timer Timer;
    static int delay;
    static boolean Timer_Trigger;
    KeyListener main_Listener;
    protected Character tempNPC;
    protected Character nextNPC;

    protected Chapter chapter;
    protected Character Boss;
    protected ArrayList<Chapter> chapters;
    ArrayList<SAVE> saves = new ArrayList<>();
    SAVE save;


    int second, min, hour;
    int turn;

    public GBA() {
        transporter = new Transporter();
        ALL_PLAYER = new ArrayList<>();
        PLAYER = new ArrayList<>();
        Grave = new ArrayList<>();
        Market = new ArrayList<>();
        PARTNER = new ArrayList<>();
        ENEMY = new ArrayList<>();
        MONSTER = new ArrayList<>();
        ITEM = new ArrayList<>();
        OBSTACLE = new ArrayList<>();
        cursor = new Cursor();
        asset = 5000;
        chapters = new ArrayList<>();
        chapters.add(Chapter.getTest0());
        chapters.add(Chapter.getTest1());
        chapters.add(Chapter.getTest2());
        chapters.add(Chapter.getTest3());
        chapters.add(Chapter.getTest4());
        chapters.add(Chapter.getTest5());


        main_Listener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!in_Menu) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (cursor.gety() + bottom_screen.top > 0) {//绝对坐标可以移动
                                if (cursor.gety() >= 3 || bottom_screen.top == 0) {
                                    cursor.shift_y(-1);
                                } else {
                                    Map_Shift(Direction.UP);
                                }
                                bottom_screen.repaint();
                            }
                            break;

                        case KeyEvent.VK_DOWN:
                            if (cursor.gety() + bottom_screen.top < bottom_screen.map_y - 1) {
                                if (cursor.gety() <= 4 || bottom_screen.top == bottom_screen.map_y - 8) {
                                    cursor.shift_y(1);
                                } else {
                                    Map_Shift(Direction.DOWN);
                                }
                            }
                            bottom_screen.repaint();
                            break;

                        case KeyEvent.VK_LEFT:
                            if (cursor.getx() + bottom_screen.left > 0) {
                                if (cursor.getx() >= 3 || bottom_screen.left == 0) {
                                    cursor.shift_x(-1);
                                } else {
                                    Map_Shift(Direction.LEFT);
                                }
                                bottom_screen.repaint();
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            if (cursor.getx() + bottom_screen.left < bottom_screen.map_x - 1) {
                                if (cursor.getx() <= 7 || bottom_screen.left == bottom_screen.map_x - 11) {
                                    cursor.shift_x(1);
                                } else {
                                    Map_Shift(Direction.RIGHT);
                                }
                                bottom_screen.repaint();
                            }
                            break;
                        case KeyEvent.VK_Z:
                            System.out.println("main listener in menu" + in_Menu);
                            switch (bottom_screen.z_mark) {
                                //zero means the cursor can move freely on the map.
                                case 0:
                                    if (PLAYER.contains(cursor.getAim()) && cursor.getx() == cursor.getAim().x && cursor.gety() == cursor.getAim().y && cursor.getAim().canMove()) {
                                        System.out.println("This is one of your team.");
                                        bottom_screen.moveRange(cursor.getAim());
                                        bottom_screen.z_mark = 1;//Player Characters.Character under control.
                                        System.out.println("z_mark set to 1");
                                    } else if (ENEMY.contains(cursor.getAim()) && cursor.getx() == cursor.getAim().x && cursor.gety() == cursor.getAim().y) {
                                        bottom_screen.show_AffectRange(cursor.getAim());
                                        bottom_screen.test_switch = !bottom_screen.test_switch;
                                    } else if (MONSTER.contains(cursor.getAim()) && cursor.getx() == cursor.getAim().x && cursor.gety() == cursor.getAim().y) {
                                        bottom_screen.show_AffectRange(cursor.getAim());
                                        bottom_screen.test_switch = !bottom_screen.test_switch;
                                    } else {
                                        try {
                                            if (!in_Menu) {
                                                display_MainMenu();
                                                System.out.println("display menu");
                                                System.out.println(in_Menu);
                                                repaint();
                                            }
                                        } catch (IOException ioe) {
                                            ioe.printStackTrace();
                                        }
                                    }

                                    break;
                                case 1:
                                    if (bottom_screen.checkInMove(cursor.getx(), cursor.gety()) && bottom_screen.check_Blank(cursor.getx(), cursor.gety())) {
                                        System.out.println("Move done.");
                                        in_Menu = true;
                                        cursor.getAim().setSubdir(cursor.getAim().getDir());
                                        bottom_screen.MOVE(cursor.getAim(), cursor.getx(), cursor.gety());
                                        bottom_screen.cleanMoveArea();

                                        break;
                                    } else {
                                        System.out.println("Invalid coordinate." + cursor.getx() + "  " + cursor.gety() + "  ");
                                    }
                                case 2:

                                    break;
                                default:
                                    break;
                            }

                            break;
                        case KeyEvent.VK_S:
                            top_screen.switch_info();
                            break;
                        case KeyEvent.VK_A:
                            Character aim = getNextNPC(cursor.getAim());
                            if (aim == null) {
                                aim = PLAYER.get(0);
                            }
                            Cursor_Fold(aim.x + bottom_screen.left, aim.y + bottom_screen.top);

                            break;

                        case KeyEvent.VK_X:
                            if (bottom_screen.z_mark > 0) {
                                bottom_screen.z_mark--;
                                System.out.println("z_mark set to " + bottom_screen.z_mark);
                                bottom_screen.cleanMoveArea();
                                break;
                            }
                            break;


                        case KeyEvent.VK_U:
                            Chapter_Complete();
                            break;
                        default:
                            break;

                    }
                    show_Info();

                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }


    public static void main(String[] args) {
        try {

            GBA gba = new GBA();
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gba.second++;
                    if (gba.second == 60) {
                        gba.second = 0;
                        gba.min++;
                        if (gba.min == 60) {
                            gba.hour++;
                        }
                    }
                    gba.repaint();
                }
            });
            timer.start();
            gba.setSize(368, 551);

            bottom_screen = new Bottom_Screen(gba);
            top_screen = new Top_Screen(gba);
            bottom_screen.setBounds(0, 256, 352, 256);
            top_screen.setBounds(0, 0, 352, 256);

            gba.add(gba.Menu);
            gba.Menu.setLayout(null);
            gba.Menu.setOpaque(false);
            gba.Menu.setVisible(false);
            gba.add(gba.Team_Menu);

            gba.add(gba.dialog);
            gba.dialog.setBounds(0, 320, 352, 192);
            gba.dialog.setLayout(null);
            gba.dialog.setVisible(false);

            gba.Team_Menu.setBounds(0, 256, 352, 256);
            gba.Team_Menu.setLayout(new GridLayout(0, 6));
            gba.Team_Menu.setOpaque(false);
            gba.Team_Menu.setVisible(false);
            gba.add(gba.Item_Menu);
            gba.Item_Menu.setVisible(false);
            gba.Item_Menu.setLayout(null);
            gba.Item_Menu.setBounds(32, 296, 300, 224);
            gba.add(gba.Steal_Menu);
            gba.Steal_Menu.setBounds(0, 256, 352, 256);
            gba.Steal_Menu.setLayout(null);
            gba.Steal_Menu.setVisible(false);
            gba.add(gba.Exchange_Menu);
            gba.Exchange_Menu.setBounds(22, 316, 318, 200);
            gba.Exchange_Menu.setVisible(false);
            gba.Exchange_Menu.setLayout(null);
            gba.add(gba.Attack_Menu);
            gba.Attack_Menu.setBounds(0, 256, 352, 256);
            gba.Attack_Menu.setVisible(false);
            gba.Attack_Menu.setLayout(null);

            gba.add(gba.Staff_Menu);
            gba.Staff_Menu.setBounds(32, 316, 288, 200);
            gba.Staff_Menu.setVisible(false);
            gba.Staff_Menu.setLayout(null);

            gba.add(gba.Key_Menu);
            gba.Key_Menu.setBounds(32, 316, 288, 200);
            gba.Key_Menu.setVisible(false);
            gba.Key_Menu.setLayout(null);

            gba.add(gba.Store_Menu);
            gba.Store_Menu.setBounds(0, 256, 352, 256);
            gba.Store_Menu.setVisible(false);
            gba.Store_Menu.setLayout(null);

            gba.add(gba.window_1);
            gba.add(gba.window_2);
            gba.window_1.setBounds(32, 416, 128, 68);
            gba.window_2.setBounds(160, 416, 128, 68);
            gba.window_1.setVisible(false);
            gba.window_2.setVisible(false);
            gba.window_1.setLayout(null);
            gba.window_2.setLayout(null);

            gba.add(gba.message_window);
            gba.message_window.setBounds(96, 352, 160, 96);
            gba.message_window.setVisible(false);
            gba.message_window.setLayout(null);

            gba.add(gba.lv);
            gba.lv.setBounds(20, 320, 200, 180);
            gba.lv.setLayout(null);
            gba.lv.setVisible(false);

            gba.add(gba.exp_window);
            gba.exp_window.setBounds(51, 416, 250, 40);
            gba.exp_window.setVisible(false);
            gba.exp_window.setLayout(null);

            gba.add(bottom_screen);
            gba.add(top_screen);
            bottom_screen.setVisible(false);
            top_screen.setVisible(false);

            gba.add(gba.start);
            gba.start.setBounds(0, 0, 352, 512);
            gba.start.setLayout(null);
            gba.start.setVisible(false);

            gba.add(gba.F_I);
            gba.F_I.setBounds(0, 0, 352, 512);
            gba.F_I.setLayout(null);
            gba.F_I.setVisible(false);

            gba.add(gba.P_I);
            gba.P_I.setBounds(0, 0, 352, 512);
            gba.P_I.setVisible(false);
            gba.P_I.setLayout(null);


            gba.add(gba.C_I);
            gba.C_I.setBounds(0, 0, 352, 512);
            gba.C_I.setLayout(null);
            gba.C_I.setVisible(false);

            gba.add(gba.I_I);
            gba.I_I.setBounds(0, 0, 352, 512);
            gba.I_I.setLayout(null);
            gba.I_I.setVisible(false);

            gba.add(gba.M_I);
            gba.M_I.setBounds(0, 0, 352, 512);
            gba.M_I.setLayout(null);
            gba.M_I.setVisible(false);

            gba.add(gba.T_I);
            gba.T_I.setBounds(0, 0, 352, 512);
            gba.T_I.setLayout(null);
            gba.T_I.setVisible(false);

            gba.add(gba.chapter_interface);
            gba.chapter_interface.setBounds(0, 0, 352, 512);
            gba.chapter_interface.setLayout(null);
            gba.chapter_interface.setVisible(false);

            gba.add(gba.S_I);
            gba.S_I.setBounds(0, 0, 352, 512);
            gba.S_I.setLayout(null);
            gba.S_I.setVisible(false);

            gba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gba.setVisible(true);

            gba.Start_Game();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Start_Game() {
        saves.clear();
        start.setVisible(true);
        check_Saves();
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        start.Cursor_UP();
                        break;
                    case KeyEvent.VK_DOWN:
                        start.Cursor_DOWN();
                        break;
                    case KeyEvent.VK_Z:
                        System.out.println("press Z " + start.getCursor_y());
                        switch (start.getCursor_y()) {
                            case 0:
                                System.out.println("save size is " + saves.size());
                                if (saves.size() < 3) {
                                    removeKeyListener(this);
                                    start.setVisible(false);
                                    generate_OriginalPlayer();
                                    save = new SAVE(new ArrayList<Chapter>(), ALL_PLAYER, Grave, Market, asset, transporter);
                                    save.chapters.add(Chapter.getTest0());
                                    saves.add(save);
                                    select_Chapter(save);
                                }
                                break;
                            case 1:
                                removeKeyListener(this);
                                start.setVisible(false);
                                display_FileInterface();
                                break;
                            case 2:
                                //Quit
                                break;
                        }
                        break;
                    default:
                        break;
                }
                start.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void select_Chapter(SAVE save) {
        chapter_interface.setSave(save);
        chapter_interface.setVisible(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        chapter_interface.Cursor_Left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        chapter_interface.Cursor_Right();
                        break;
                    case KeyEvent.VK_Z:
                        removeKeyListener(this);
                        chapter_interface.setVisible(false);
                        System.out.println(chapter_interface.chapter.Chapter_No);
                        load_Chapter(chapter_interface.chapter);
                        display_P_I();
                    default:
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void display_FileInterface() {
        F_I.reset();
        F_I.setSaves(saves);
        F_I.setVisible(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Z:
                        if (F_I.getCursor_y() < saves.size()) {
                            removeKeyListener(this);
                            F_I.setVisible(false);
                            save = saves.get(F_I.getCursor_y());
                            ALL_PLAYER = save.PLAYER;
                            asset = save.asset;
                            for (Character pc : ALL_PLAYER) {
                                pc.first_Weapon();
                            }
                            Market = save.Market;
                            select_Chapter(save);
                        }
                        break;
                    case KeyEvent.VK_X:
                        removeKeyListener(this);
                        F_I.setVisible(false);
                        Start_Game();
                        System.out.println("Return to Start");
                        break;
                    case KeyEvent.VK_UP:
                        F_I.Cursor_UP();
                        break;
                    case KeyEvent.VK_DOWN:
                        F_I.Cursor_DOWN();
                        break;
                    default:
                        break;
                }
                F_I.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void display_P_I() {
        P_I.setVisible(true);


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        P_I.shift_CursorY(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        P_I.shift_CursorY(1);
                        break;
                    case KeyEvent.VK_Z:
                        switch (P_I.cursor_y) {
                            case 0:
                                P_I.setVisible(false);
                                display_CharacterInterface();
                                removeKeyListener(this);
                                repaint();
                                break;
                            case 1:
                                P_I.setVisible(false);
                                display_ItemInterface();
                                removeKeyListener(this);
                                repaint();
                                break;
                            case 2:
                                P_I.setVisible(false);
                                display_TalentMarket();
                                removeKeyListener(this);
                                repaint();
                                break;
                            case 3:
                                P_I.setVisible(false);
                                removeKeyListener(this);
                                display_MapInterface();
                                repaint();
                                break;
                            case 4:
                                save_Record();
                                break;
                            default:
                                break;
                        }
                        break;

                    case KeyEvent.VK_ENTER:
                        P_I.setVisible(false);
                        bottom_screen.setVisible(true);
                        top_screen.setVisible(true);
                        run_Chapter();
                        repaint();
                        removeKeyListener(this);
                        break;

                    default:
                        break;
                }
                P_I.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    public void display_CharacterInterface() {

        C_I.setTeam(ALL_PLAYER);
        C_I.setVisible(true);
        for (Character pl : ALL_PLAYER) {
            if (PLAYER.contains(pl)) {
                C_I.mark(pl);
            }
        }

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        C_I.shift_CursorY(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        C_I.shift_CursorY(1);
                        break;
                    case KeyEvent.VK_LEFT:
                        C_I.shift_CursorX(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        C_I.shift_CursorX(1);
                        break;
                    case KeyEvent.VK_Z:
                        if (PLAYER.contains(C_I.getAim()) && PLAYER.size() > 1) {
                            PLAYER.remove(C_I.getAim());
                            remove_Character(C_I.getAim());
                            C_I.unmark(C_I.getAim());
                            sort_PLAYER();
                            System.out.println("remove " + C_I.getAim().x + " " + C_I.getAim().y);
                        } else {
                            if (chapter.map.original_position.size() > PLAYER.size() && !PLAYER.contains(C_I.getAim())) {
                                PLAYER.add(C_I.getAim());
                                C_I.mark(C_I.getAim());
                                sort_PLAYER();

                                System.out.println("add " + C_I.getAim().x + " " + C_I.getAim().y);
                            }
                        }
                        break;
                    case KeyEvent.VK_X:
                        removeKeyListener(this);
                        C_I.clean();
                        C_I.setVisible(false);
                        sort_AllPlayer();
                        display_P_I();
                        break;
                    case KeyEvent.VK_ENTER:
                        C_I.setVisible(false);
                        sort_AllPlayer();
                        bottom_screen.setVisible(true);
                        top_screen.setVisible(true);
                        run_Chapter();
                        repaint();
                        removeKeyListener(this);
                        break;
                    default:
                        break;
                }
                C_I.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    public void display_ItemInterface() {
        I_I.setIn_sub(false);
        I_I.setIn_Menu(false);
        I_I.setVisible(true);
        I_I.setTeam(ALL_PLAYER);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (!I_I.isIn_Menu()) {
                            I_I.shift_CursorY(-1);
                        } else {
                            if (!I_I.isIn_sub()) {
                                I_I.sub_ShiftUP();
                            }
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!I_I.isIn_Menu()) {
                            I_I.shift_CursorY(1);
                        } else {
                            if (!I_I.isIn_sub()) {
                                I_I.sub_ShiftDown();
                            }
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!I_I.isIn_Menu()) {
                            I_I.shift_CursorX(-1);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!I_I.isIn_Menu()) {
                            I_I.shift_CursorX(1);
                        }
                        break;
                    case KeyEvent.VK_Z:
                        if (I_I.getC1() != null) {
                            if (I_I.getC1() == I_I.getAim()) {
                                I_I.setC1(null);
                            } else {
                                if (!I_I.isIn_sub()) {
                                    I_I.setC2(I_I.getAim());
                                    I_I.setIn_Menu(true);
                                    I_I.setIn_sub(true);
                                    display_ExchangeMenu(I_I.getC1(), I_I.getC2());
                                }
                            }
                        } else {
                            if (!I_I.isIn_Menu() && I_I.getAim() != null) {
                                I_I.setSub_y(0);
                                I_I.setIn_Menu(true);
                            } else {
                                if (!I_I.isIn_sub()) {
                                    switch (I_I.getSub_y()) {
                                        case 0:
                                            //Exchange Menu
                                            I_I.setC1(I_I.getAim());
                                            I_I.setIn_Menu(false);
                                            I_I.shift_CursorX(1);
                                            break;
                                        case 1:
                                            //Item Menu
                                            if (!I_I.getAim().getBag().isEmpty()) {
                                                I_I.setIn_Menu(true);
                                                display_ItemMenu(I_I.getAim());
                                            }
                                            break;
                                        case 2:
                                            //Transporter
                                            if (I_I.getAim() != null) {
                                                I_I.setIn_Menu(true);
                                                I_I.setIn_sub(true);
                                                removeKeyListener(this);
                                                display_Transporter(I_I.getAim(), transporter);
                                            }

                                            break;
                                        case 3:
                                            //Item Shop
                                            if (!chapter.shop.getGoodslist().isEmpty()) {
                                                I_I.setIn_Menu(true);
                                                I_I.setIn_sub(true);
                                                I_I.setVisible(false);
                                                removeKeyListener(this);
                                                display_Shop(shop, I_I.getAim());
                                            }
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (!I_I.isIn_Menu() && !I_I.isIn_sub()) {
                            removeKeyListener(this);
                            I_I.clean();
                            I_I.setVisible(false);
                            display_P_I();
                        } else {
                            if (I_I.getC1() != null) {
                                I_I.resetCursor();
                                I_I.setC1(null);
                            }
                            if (I_I.isIn_Menu() && !I_I.isIn_sub()) {
                                I_I.setIn_Menu(false);
                            }
                        }

                        break;

                }
                I_I.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void display_Shop(Shop shop, Character c) {
        S_I.setShop(shop);
        S_I.setC(c);
        S_I.setAsset(asset);
        S_I.setTransporter(transporter);
        S_I.setVisible(true);


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        switch (S_I.getZ_mark()) {
                            case 0:
                                S_I.setCursor_y(1 - S_I.getCursor_y());
                                break;
                            case 1:
                                if (!S_I.isBuy()) {
                                    S_I.ShopBox_Up();
                                } else {
                                    S_I.setSub_y(1 - S_I.getSub_y());
                                }
                                break;
                            case 2:
                                S_I.ItemBox_Up();
                                break;
                            default:
                                break;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        switch (S_I.getZ_mark()) {
                            case 0:
                                S_I.setCursor_y(1 - S_I.getCursor_y());
                                break;
                            case 1:
                                if (!S_I.isBuy()) {
                                    S_I.ShopBox_Down();
                                } else {
                                    S_I.setSub_y(1 - S_I.getSub_y());
                                }
                                break;
                            case 2:
                                S_I.ItemBox_Down();
                                break;
                            default:
                                break;
                        }
                        break;
                    case KeyEvent.VK_Z:
                        switch (S_I.getZ_mark()) {
                            case 0:
                                if (S_I.getCursor_y() == 0) {
                                    S_I.setZ_mark(1);
                                    S_I.setCursor_y(0);
                                } else {
                                    S_I.setZ_mark(2);
                                    S_I.setCursor_y(0);
                                }
                                break;
                            case 1:
                                if (!S_I.isBuy()) {
                                    S_I.setBuy(true);
                                } else {
                                    if (S_I.getSub_y() == 0) {
                                        buy();
                                        S_I.setBuy(false);
                                    } else {
                                        S_I.setBuy(false);
                                    }
                                }
                                break;
                            case 2:
                                if (!S_I.isSale()) {
                                    S_I.setSale(true);
                                } else {
                                    if (S_I.getSub_y() == 0) {
                                        sale();
                                        S_I.setSale(false);
                                    } else {
                                        S_I.setSale(false);
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                        break;
                    case KeyEvent.VK_X:
                        switch (S_I.getZ_mark()) {
                            case 0:
                                removeKeyListener(this);
                                display_ItemInterface();
                                break;
                            case 1:
                                if (S_I.isBuy()) {
                                    S_I.setBuy(false);
                                } else {
                                    S_I.setZ_mark(0);
                                    S_I.setCursor_y(0);
                                }
                                break;
                            case 2:
                                if (S_I.isSale()) {
                                    S_I.setSale(false);
                                } else {
                                    S_I.setZ_mark(0);
                                    S_I.setCursor_y(0);
                                }
                                break;
                        }
                        break;

                }
                S_I.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void display_TalentMarket() {
        M_I.setVisible(true);
        M_I.setTeam(save.Market);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        M_I.shift_CursorY(-1);
                        break;
                    case KeyEvent.VK_DOWN:
                        M_I.shift_CursorY(1);
                        break;
                    case KeyEvent.VK_LEFT:
                        M_I.shift_CursorX(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        M_I.shift_CursorX(1);
                        break;

                    case KeyEvent.VK_Z:
                        if (M_I.isOption()) {
                            PayToEmploy(M_I.getAim());
                        } else {
                            M_I.setOption(true);
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (M_I.isOption()) {
                            M_I.setOption(false);
                        } else {
                            removeKeyListener(this);
                            M_I.clean();
                            M_I.setVisible(false);
                            display_P_I();
                            break;
                        }
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    private void PayToEmploy(Character target) {
        target.setPosition(0, 0);
        if (target.getWant() == null) {
            if (asset >= 500) {
                asset -= 500;
                target.setCamp(Camp.PLAYER);
                Market.remove(target);
                ALL_PLAYER.add(target);
                M_I.setOption(false);
            }
        } else {
            Item key_item = transporter.SearchFor(target.getWant());
            if (key_item != null) {
                transporter.Remove(key_item);
                target.setCamp(Camp.PLAYER);
                Market.remove(target);
                ALL_PLAYER.add(target);
                M_I.setOption(false);
            }
        }
        repaint();
    }

    public void display_MapInterface() {
        top_screen.setVisible(true);
        bottom_screen.setVisible(true);
        bottom_screen.setIn_Preparation(true);
        Cursor_Fold(bottom_screen.left + PLAYER.get(0).x, bottom_screen.top + PLAYER.get(0).y);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (cursor.gety() + bottom_screen.top > 0) {//绝对坐标可以移动
                            if (cursor.gety() >= 3 || bottom_screen.top == 0) {
                                cursor.shift_y(-1);
                            } else {
                                Map_Shift(Direction.UP);
                            }
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (cursor.gety() + bottom_screen.top < bottom_screen.map_y - 1) {
                            if (cursor.gety() <= 4 || bottom_screen.top == bottom_screen.map_y - 8) {
                                cursor.shift_y(1);
                            } else {
                                Map_Shift(Direction.DOWN);
                            }
                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        if (cursor.getx() + bottom_screen.left > 0) {
                            if (cursor.getx() >= 3 || bottom_screen.left == 0) {
                                cursor.shift_x(-1);
                            } else {
                                Map_Shift(Direction.LEFT);
                            }

                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (cursor.getx() + bottom_screen.left < bottom_screen.map_x - 1) {
                            if (cursor.getx() <= 7 || bottom_screen.left == bottom_screen.map_x - 11) {
                                cursor.shift_x(1);
                            } else {
                                Map_Shift(Direction.RIGHT);
                            }
                        }
                        break;
                    case KeyEvent.VK_Z:
                        int X = bottom_screen.cursor.getx() + bottom_screen.left;
                        int Y = bottom_screen.cursor.gety() + bottom_screen.top;
                        if (chapter.map.original_position.contains(new Node(X, Y))) {
                            System.out.println(X + "  " + Y);
                            if (bottom_screen.caught == null && character_map[X][Y] != null) {
                                bottom_screen.caught = character_map[X][Y];
                                System.out.println(character_map[X][Y].getName() + " caught.");
                            } else if (bottom_screen.caught != null) {
                                bottom_screen.exchange();
                            }
                        }
                        break;

                    case KeyEvent.VK_X:
                        if (bottom_screen.caught != null) {
                            bottom_screen.caught = null;
                        } else {
                            Cursor_Fold(0, 0);
                            removeKeyListener(this);
                            top_screen.setVisible(false);
                            bottom_screen.setVisible(false);
                            display_P_I();
                            repaint();
                        }
                        break;

                    case KeyEvent.VK_S:
                        top_screen.switch_info();
                        break;

                    case KeyEvent.VK_H:
                        bottom_screen.show_MovePoint = !bottom_screen.show_MovePoint;
                        break;

                    default:
                        break;
                }

                show_Info();
                repaint();

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    private void load_Chapter(Chapter chapter) {
        this.chapter = chapter;
        ENEMY.clear();
        MONSTER.clear();
        Boss = null;
        this.shop = chapter.shop;

        bottom_screen.map = chapter.map.map;
        bottom_screen.map_x = chapter.map.map_x;
        bottom_screen.map_y = chapter.map.map_y;
        bottom_screen.move_map = chapter.map.move_map;

        this.character_map = chapter.map.character_map;
        this.PARTNER = chapter.PARTNER;

        //the original ENEMY should be saved.
        for (Character en : chapter.ENEMY) {
            this.ENEMY.add(en.getCopy());
            if (en == chapter.Boss) {
                this.Boss = ENEMY.get(ENEMY.size() - 1);
            }
        }
        for (Character mon : chapter.MONSTER) {
            this.MONSTER.add(mon.getCopy());
        }

        for (Character en : this.ENEMY) {
            en.first_Weapon();
        }
        this.ITEM = chapter.ITEM;
        this.terrain_map = chapter.map.terrain_map;
        for (Envir_Item envir : ITEM) {
            terrain_map[envir.getX()][envir.getY()] = envir;
        }

        int i = 0;
        for (Node n : chapter.map.original_position) {
            if (i < chapter.map.original_position.size() && i < ALL_PLAYER.size()) {
                this.PLAYER.add(ALL_PLAYER.get(i));
                chapter.map.character_map[n.x][n.y] = ALL_PLAYER.get(i);
                ALL_PLAYER.get(i).setPosition(n.x, n.y);
                System.out.println(ALL_PLAYER.get(i).getName() + " " + n.x + " " + n.y);
            }
            i++;
        }
        for (Character en : this.ENEMY) {
            chapter.map.character_map[en.x][en.y] = en;
        }
        for (Character pc : chapter.PLAYER) {
            chapter.map.character_map[pc.x][pc.y] = pc;
        }
        for (Character npc : chapter.PARTNER) {
            chapter.map.character_map[npc.x][npc.y] = npc;
        }
        for (Character mon : this.MONSTER) {
            chapter.map.character_map[mon.x][mon.y] = mon;
        }

    }


    private void sort_PLAYER() {
        int i = 0;
        for (Node n : chapter.map.original_position) {
            if (i < chapter.map.original_position.size() && i < PLAYER.size()) {
                PLAYER.get(i).setPosition(n.x - bottom_screen.left, n.y - bottom_screen.top);
                setPosition(PLAYER.get(i), n.x, n.y);
            }
            i++;
        }
    }

    private void sort_AllPlayer() {
        int i, j;
        for (Character pc : PLAYER) {
            i = ALL_PLAYER.indexOf(pc);
            j = PLAYER.indexOf(pc);
            System.out.println(pc.getName());
            System.out.println(i + " : " + j);
            if (i != j) {
                Collections.swap(ALL_PLAYER, i, j);
            }
        }
    }

    protected void display_CharMenu(Character c, int start_x, int start_y) throws IOException {

        bottom_screen.show_attack = false;
        Menu.getList().clear();
        bottom_screen.show_Effect_Area();
        in_Menu = true;
        Menu.setcursory(0);
        int i = 0;

        if (bottom_screen.Seizeable()) {
            Menu.subOption(i, "Seize");
            Menu.cursor_y[i] = 0;
            i++;
        }
        if (bottom_screen.Attackable()) {
            Menu.subOption(i, "Attack");
            System.out.println("Attack");
            Menu.cursor_y[i] = 1;
            i++;
        }
        if (bottom_screen.Staffable()) {
            Menu.subOption(i, "Staff");
            System.out.println("Staff");
            Menu.cursor_y[i] = 2;
            i++;
        }
        if (bottom_screen.Stealable()) {
            Menu.subOption(i, "Steal");
            System.out.println("Steal");
            Menu.cursor_y[i] = 3;
            i++;
        }

        if (bottom_screen.can_Unlock_Gate()) {
            Menu.subOption(i, "Gate");
            Menu.cursor_y[i] = 4;
            i++;
        }
        if (bottom_screen.can_Unlock_Chest()) {
            Menu.subOption(i, "Chest");
            Menu.cursor_y[i] = 5;
            i++;
        }
        if (hasPartnerAround()) {
            Menu.subOption(i, "Exchange");
            System.out.println("Exchange");
            Menu.cursor_y[i] = 6;
            i++;
        }

        Menu.subOption(i, "Item");
        Menu.cursor_y[i] = 7;
        i++;
        Menu.subOption(i, "End");
        Menu.cursor_y[i] = 8;


        final int j = i;

        Menu.setRow(i + 1);
        Menu.setBounds(144, 256 + 128 - 16 * Menu.getRow(), 64, 32 * Menu.getRow());
        Menu.setVisible(true);
        Menu.repaint();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Menu.getcursory() > 0) {
                            Menu.shift_cursor(-1);
                        } else {
                            Menu.setcursory(j);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Menu.getcursory() < j) {
                            Menu.shift_cursor(1);
                        } else {
                            Menu.setcursory(0);
                        }
                        break;
                    case KeyEvent.VK_Z:
                        bottom_screen.show_attack = false;
                        System.out.println(cursor.getAim().x + "  " + cursor.getAim().y);
                        switch (Menu.cursor_y[Menu.getcursory()]) {
                            case 0:
                                removeMenu(this);
                                Chapter_Complete();
                                repaint();

                                break;
                            case 1:
                                if (bottom_screen.Attackable()) {
                                    removeMenu(this);
                                    display_AttackMenu(start_x, start_y);
                                    Attack_Menu.repaint();
                                }
                                break;
                            case 2:
                                removeMenu(this);
                                display_StaffMenu(cursor.getAim(), start_x, start_y);
                                break;
                            case 3:
                                removeMenu(this);
                                display_StealMenu(cursor.getAim(), start_x, start_y);
                                break;
                            case 4:
                                //thief is able to unlock gate and chest without keys.
                                removeMenu(this);
                                if (c.getJob() == Job.Thief) {
                                    display_Thief(Gate.class, c, start_x, start_y);
                                } else {
                                    display_GateKeyMenu(cursor.getAim(), start_x, start_y);
                                }
                                break;
                            case 5:
                                removeMenu(this);
                                if (c.getJob() == Job.Thief) {
                                    display_Thief(Chest.class, c, start_x, start_y);
                                } else {
                                    display_ChestKeyMenu(cursor.getAim(), start_x, start_y);
                                }
                                break;
                            case 6:
                                if (hasPartnerAround()) {
                                    removeMenu(this);
                                    display_ExchangeMenu(cursor.getAim(), start_x, start_y);
                                    Exchange_Menu.repaint();
                                }
                                break;
                            case 7:
                                if (!cursor.getAim().getBag().isEmpty()) {
                                    removeMenu(this);
                                    display_ItemMenu(start_x, start_y);
                                    Item_Menu.repaint();
                                }
                                break;
                            case 8:
                                removeMenu(this);
                                bottom_screen.z_mark = 0;
                                bottom_screen.repaint();
                                character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                                character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = c;
                                bottom_screen.cleanRedArea();
                                bottom_screen.cleanGreenArea();

                                endAndCheckVictory(c);
                                c.setMovepoint(false);

                                if (chapter.objective == Victory_Condition.Escape) {
                                    if (chapter.escape_points.contains(
                                            new Node(cursor.getAim().x + bottom_screen.left, cursor.getAim().y + bottom_screen.top))) {
                                        System.out.println(cursor.getAim().getName() + " escaped");
                                        PLAYER.remove(cursor.getAim());
                                        remove_Character(cursor.getAim());
                                        if (PLAYER.isEmpty()) {
                                            removeMenu(this);
                                            Chapter_Complete();
                                            repaint();
                                        }
                                    }
                                }
                                break;
                            default:
                                break;

                        }
                        break;

                    case KeyEvent.VK_X:
                        if (c.getMovepoint()) {
                            removeMenu(this);
                            cursor.move(start_x, start_y);
                            c.fold(start_x, start_y);
                            c.setDir(c.getSubdir());
                            bottom_screen.moveRange(c);
                            bottom_screen.cleanRedArea();
                            bottom_screen.cleanGreenArea();
                            bottom_screen.repaint();
                        }
                        break;
                }
                Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    public void display_StealMenu(Character c, int start_x, int start_y) {
        in_Menu = true;
        Steal_Menu.setVisible(true);
        Steal_Menu.setShowMenu(false);
        bottom_screen.cleanGreenArea();
        Steal_Menu.setCursor_y(0);
        Steal_Menu.setThief(c);
        generate_StealAimList(c);
        bottom_screen.aim = bottom_screen.AimList.get(0);
        Steal_Menu.setTarget(bottom_screen.aim);

        FacetoTarget(c, Steal_Menu.getTarget());
        bottom_screen.show_Aim = true;
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Steal_Menu.isShowMenu()) {
                            Steal_Menu.Cursor_Up();
                        } else {
                            bottom_screen.preAim();
                            Steal_Menu.setTarget(bottom_screen.aim);
                            FacetoTarget(c, Steal_Menu.getTarget());
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!Steal_Menu.isShowMenu()) {
                            bottom_screen.preAim();
                            Steal_Menu.setTarget(bottom_screen.aim);
                            FacetoTarget(c, Steal_Menu.getTarget());
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!Steal_Menu.isShowMenu()) {
                            bottom_screen.nextAim();
                            Steal_Menu.setTarget(bottom_screen.aim);
                            FacetoTarget(c, Steal_Menu.getTarget());
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Steal_Menu.isShowMenu()) {
                            Steal_Menu.Cursor_Down();
                        } else {
                            bottom_screen.nextAim();
                            Steal_Menu.setTarget(bottom_screen.aim);
                            FacetoTarget(c, Steal_Menu.getTarget());
                        }
                        break;
                    case KeyEvent.VK_Z:
                        if (Steal_Menu.isShowMenu()) {
                            Item item = Steal_Menu.check_Steal();
                            if (item != null) {
                                Steal(c, Steal_Menu.getTarget(), item);
                                chapter.map.character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                                chapter.map.character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = c;
                                removeKeyListener(this);
                                Steal_Menu.setVisible(false);
                                bottom_screen.show_Aim = false;
                                bottom_screen.GreenArea.clear();
                                bottom_screen.display_EXP(c, 10);
                                c.setMovepoint(false);

                            }
                        } else {
                            Steal_Menu.setShowMenu(true);
                            Steal_Menu.setCursor_y(0);
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (Steal_Menu.isShowMenu()) {
                            Steal_Menu.setShowMenu(false);
                        } else {
                            Steal_Menu.setVisible(false);
                            removeKeyListener(this);
                            try {
                                bottom_screen.show_Aim = false;
                                bottom_screen.GreenArea.clear();
                                display_CharMenu(cursor.getAim(), start_x, start_y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void generate_StealAimList(Character thief) {
        bottom_screen.AimList.clear();
        if (cursor.getAim().getBag().size() < 5) {
            for (Character en : bottom_screen.enemyAround()) {
                if (thief.getSpd() > en.getSpd()) {
                    for (Item item : en.getBag()) {
                        if (item != en.getEquiped_Weapon() && thief.getStr() > item.getWeight()) {
                            bottom_screen.AimList.add(en);
                            bottom_screen.GreenArea.add(new Node(bottom_screen.left + en.x, bottom_screen.top + en.y));
                        }
                    }
                }
            }
        }
    }

    private void Steal(Character thief, Character target, Item item) {
        target.getBag().remove(item);
        bottom_screen.drop = item;
    }


    private void display_AttackMenu(int start_x, int start_y) {
        in_Menu = true;
        bottom_screen.show_attack = true;
        Attack_Menu.setVisible(true);
        Attack_Menu.setShowMenu(true);
        bottom_screen.cleanRedArea();
        Attack_Menu.setCursor_y(0);
        Attack_Menu.setCharacter_1(cursor.getAim());
        Attack_Menu.Weapon_List();
        bottom_screen.add_Affect_Area(Attack_Menu.getCharacter_1().getWeapon());
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Attack_Menu.isShowMenu()) {
                            if (Attack_Menu.getCursor_y() == 0) {
                                Attack_Menu.setCursor_y(Attack_Menu.getsize() - 1);
                            } else {
                                Attack_Menu.shift_CursorY(-1);
                            }
                            Attack_Menu.refresh_Weapon();
                            //When not refresh the aim_weapon in Attack_Weapon, the weapon is previous one because aim_weapon is only
                            //refresh in the Display.Attack_Menu.repaint() method.
                            refresh_EffectArea();

                        } else {
                            bottom_screen.preAim();
                            Attack_Menu.setCharacter_2(bottom_screen.aim);
                            FacetoFace(Attack_Menu.getCharacter_1(), Attack_Menu.getCharacter_2());
                            transportPara();

                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Attack_Menu.isShowMenu()) {
                            if (Attack_Menu.getCursor_y() == Attack_Menu.getsize() - 1) {
                                Attack_Menu.setCursor_y(0);
                            } else {
                                Attack_Menu.shift_CursorY(1);
                            }
                            Attack_Menu.refresh_Weapon();
                            refresh_EffectArea();

                        } else {
                            bottom_screen.nextAim();
                            Attack_Menu.setCharacter_2(bottom_screen.aim);
                            FacetoFace(Attack_Menu.getCharacter_1(), Attack_Menu.getCharacter_2());
                            transportPara();

                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!Attack_Menu.isShowMenu()) {
                            bottom_screen.preAim();
                            Attack_Menu.setCharacter_2(bottom_screen.aim);
                            FacetoFace(Attack_Menu.getCharacter_1(), Attack_Menu.getCharacter_2());
                            transportPara();

                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!Attack_Menu.isShowMenu()) {
                            bottom_screen.nextAim();
                            Attack_Menu.setCharacter_2(bottom_screen.aim);
                            FacetoFace(Attack_Menu.getCharacter_1(), Attack_Menu.getCharacter_2());
                            transportPara();

                        }
                        break;
                    case KeyEvent.VK_Z:
                        if (Attack_Menu.isShowMenu()) {
                            bottom_screen.generate_AimList();
                            if (!bottom_screen.AimList.isEmpty()) {
                                bottom_screen.aim = bottom_screen.AimList.get(0);
                                Attack_Menu.setCharacter_2(bottom_screen.aim);
                                FacetoFace(Attack_Menu.getCharacter_1(), Attack_Menu.getCharacter_2());
                                Attack_Menu.equip();
                                Attack_Menu.setShowMenu(false);
                                bottom_screen.show_Aim = true;
                                transportPara();
                            }
                        } else {
                            Attack_Menu.setShowMenu(false);
                            Attack_Menu.setVisible(false);
                            removeKeyListener(this);
                            bottom_screen.clean();
                            character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                            character_map[Attack_Menu.getCharacter_1().x + bottom_screen.left][Attack_Menu.getCharacter_1().y + bottom_screen.top] = Attack_Menu.getCharacter_1();
                            bottom_screen.Battle(Attack_Menu.getCharacter_1(), Attack_Menu.getCharacter_2());
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (Attack_Menu.isShowMenu()) {
                            Attack_Menu.setVisible(false);
                            removeKeyListener(this);
                            try {
                                display_CharMenu(cursor.getAim(), start_x, start_y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } else {
                            Attack_Menu.setShowMenu(true);
                            bottom_screen.show_Aim = false;
                        }
                    default:
                        break;

                }
                Attack_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    private boolean hasPartnerAround() {
        boolean changable = false;
        Direction[] direction = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        for (Direction dir : direction) {
            if (checkPC(cursor.getAim().x + dir.x, cursor.getAim().y + dir.y)) {
                changable = true;
            }
        }
        return changable;
    }

    private Character PartnerAround(Character c) {
        Direction[] direction = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};
        for (Direction dir : direction) {
            if (checkPC(c.x + dir.x, c.y + dir.y)) {
                return character_map[c.x + dir.x + bottom_screen.left][c.y + dir.y + bottom_screen.top];
            }
        }
        return null;
    }


    //Class (and Camp) of Target depends on Type of Staff.
    private void display_StaffMenu(Character user, int start_x, int start_y) {
        in_Menu = true;
        Staff_Menu.setMenu_switch(true);
        Staff_Menu.setUser(cursor.getAim());
        bottom_screen.show_attack = true;
        bottom_screen.cleanGreenArea();
        Staff_Menu.setCursor_y(0);
        bottom_screen.add_Affect_Area(Staff_Menu.getStaff().get(0));
        if (Staff_Menu.getStaff().get(0) != null) {
            System.out.println("staff");
        } else {
            System.out.println("empty");
        }

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Staff_Menu.isMenu_switch()) {
                            Staff_Menu.shift_CursorY(-1);
                        } else {
                            Staff_Menu.setTarget(bottom_screen.pre_StaffAim(Staff_Menu.getAim_staff()));
                            FacetoTarget(user, Staff_Menu.getTarget());
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (Staff_Menu.isMenu_switch()) {
                            Staff_Menu.shift_CursorY(1);
                        } else {
                            Staff_Menu.setTarget(bottom_screen.next_StaffAim(Staff_Menu.getAim_staff()));
                            FacetoTarget(user, Staff_Menu.getTarget());
                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        if (!Staff_Menu.isMenu_switch()) {
                            Staff_Menu.setTarget(bottom_screen.pre_StaffAim(Staff_Menu.getAim_staff()));
                            FacetoTarget(user, Staff_Menu.getTarget());
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!Staff_Menu.isMenu_switch()) {
                            Staff_Menu.setTarget(bottom_screen.next_StaffAim(Staff_Menu.getAim_staff()));
                            FacetoTarget(user, Staff_Menu.getTarget());
                        }
                        break;

                    case KeyEvent.VK_Z:
                        if (Staff_Menu.isMenu_switch()) {
                            Camp camp;

                            switch (Staff_Menu.getAim_staff().getMagicEffect().getClass().getName()) {
                                case "Item.HealMagic":
                                    camp = Camp.PLAYER;
                                    break;
                                case "Item.UnlockMagic":
                                    camp = Camp.Envir;
                                    break;
                                default:
                                    camp = Camp.ENEMY;
                                    break;
                            }
                            Staff_Menu.setTarget(bottom_screen.generate_TargetList(camp, Staff_Menu.getAim_staff()).get(0));
                            FacetoTarget(user, Staff_Menu.getTarget());
                            if (!bottom_screen.AimList.isEmpty()) {
                                bottom_screen.aim = bottom_screen.AimList.get(0);
                                bottom_screen.item_aim = null;
                            }
                            if (!bottom_screen.LockList.isEmpty()) {
                                bottom_screen.item_aim = bottom_screen.LockList.get(0);
                                bottom_screen.aim = null;
                            }

                            Staff_Menu.setMenu_switch(false);
                            bottom_screen.show_Aim = true;
                        } else {
                            switch (Staff_Menu.getAim_staff().getMagicEffect().getClass().getName()) {
                                case "Item.HealMagic":
                                    display_HP(1, bottom_screen.aim);
                                    Staff_Menu.getAim_staff().staff(bottom_screen.aim, bottom_screen.cursor.getAim());
                                    ;
                                    display_HP_Timer(window_1, Staff_Menu.getAim_staff());
                                    break;

                                case "Item.UnlockMagic":
                                    System.out.println("apply unlock magic to " + bottom_screen.item_aim.getName());
                                    if (bottom_screen.item_aim instanceof Gate) {
                                        unlock_Gate((Gate) bottom_screen.item_aim, Staff_Menu.getAim_staff());

                                    } else if (bottom_screen.item_aim instanceof Chest) {
                                        unlock_Chest(cursor.getAim(), (Chest) bottom_screen.item_aim, Staff_Menu.getAim_staff());
                                    }
                                    cursor.getAim().setMovepoint(false);

                                    break;
                                default:
                                    break;
                            }
                            Staff_Menu.clean();
                            bottom_screen.clean();
                            removeKeyListener(this);
                            character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                            character_map[user.x + bottom_screen.left][user.y + bottom_screen.top] = user;


                        }
                        break;

                    case KeyEvent.VK_X:
                        if (Staff_Menu.isMenu_switch()) {
                            Staff_Menu.setVisible(false);
                            removeKeyListener(this);
                            try {
                                display_CharMenu(cursor.getAim(), start_x, start_y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } else {
                            Staff_Menu.setMenu_switch(true);
                            bottom_screen.show_Aim = false;
                        }
                        break;
                }
                Staff_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        Staff_Menu.setVisible(true);
    }

    private void display_Thief(Class klass, Character thief, int start_x, int start_y) {
        if (klass == Gate.class) {
            bottom_screen.add_Affect_Area();
            bottom_screen.item_aim = bottom_screen.generate_GateList().get(0);

        } else if (klass == Chest.class) {
            bottom_screen.add_Affect_Area();
            bottom_screen.item_aim = bottom_screen.generate_ChestList().get(0);
        }
        bottom_screen.show_Aim = true;
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        bottom_screen.preLock();
                        break;
                    case KeyEvent.VK_DOWN:
                        bottom_screen.nextLock();
                        break;
                    case KeyEvent.VK_LEFT:
                        bottom_screen.preLock();
                        break;
                    case KeyEvent.VK_RIGHT:
                        bottom_screen.nextLock();
                        break;
                    case KeyEvent.VK_Z:

                        if (klass == Gate.class) {
                            unlock_Gate((Gate) bottom_screen.item_aim, thief);
                        } else if (klass == Chest.class) {
                            unlock_Chest(thief, (Chest) bottom_screen.item_aim, thief);
                        }
                        thief.setMovepoint(false);

                        removeKeyListener(this);
                        bottom_screen.LockList.clear();
                        bottom_screen.show_Aim = false;
                        character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                        character_map[thief.x + bottom_screen.left][thief.y + bottom_screen.top] = thief;

                        break;
                    case KeyEvent.VK_X:
                        Key_Menu.setMenu_switch(true);
                        bottom_screen.show_Aim = false;
                        break;
                    default:
                        break;
                }
                repaint();
            }


            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void display_GateKeyMenu(Character user, int start_x, int start_y) {
        in_Menu = true;

        bottom_screen.cleanNoColorArea();
        Key_Menu.setUser(user);
        Key_Menu.GateKey_List();
        Key_Menu.setCursor_y(0);

        bottom_screen.add_Affect_Area();

        Key_Menu.setMenu_switch(true);
        Key_Menu.setVisible(true);
        System.out.println("display_GateKeyMenu");
        System.out.println(Key_Menu.isMenu_switch());
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.shift_CursorY(-1);
                        } else {
                            Key_Menu.setTarget(bottom_screen.preLock());
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.shift_CursorY(1);
                        } else {
                            Key_Menu.setTarget(bottom_screen.nextLock());
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!Key_Menu.isMenu_switch()) {
                            Key_Menu.setTarget(bottom_screen.preLock());
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!Key_Menu.isMenu_switch()) {
                            Key_Menu.setTarget(bottom_screen.nextLock());
                        }
                        break;
                    case KeyEvent.VK_Z:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.setTarget((Gate) bottom_screen.generate_GateList().get(0));
                            bottom_screen.item_aim = bottom_screen.LockList.get(0);
                            Key_Menu.setMenu_switch(false);
                            bottom_screen.show_Aim = true;
                        } else {
                            unlock_Gate((Gate) Key_Menu.getTarget(), Key_Menu.getAim_key());
                            user.setMovepoint(false);
                            Key_Menu.clean();
                            bottom_screen.clean();
                            removeKeyListener(this);
                            Key_Menu.setVisible(false);
                            character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                            character_map[user.x + bottom_screen.left][user.y + bottom_screen.top] = user;

                        }
                        break;
                    case KeyEvent.VK_X:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.setVisible(false);
                            removeKeyListener(this);
                            try {
                                display_CharMenu(cursor.getAim(), start_x, start_y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } else {
                            Key_Menu.setMenu_switch(true);
                            bottom_screen.show_Aim = false;
                        }
                        break;
                    default:
                        break;
                }
                Key_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void display_ChestKeyMenu(Character user, int start_x, int start_y) {
        in_Menu = true;
        bottom_screen.cleanNoColorArea();
        Key_Menu.setUser(user);
        Key_Menu.ChestKey_List();
        Key_Menu.setCursor_y(0);

        bottom_screen.add_Affect_Area();
        Key_Menu.setMenu_switch(true);
        Key_Menu.setVisible(true);
        System.out.println("display_GateKeyMenu");
        System.out.println(Key_Menu.isMenu_switch());
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.shift_CursorY(-1);
                        } else {
                            Key_Menu.setTarget(bottom_screen.preLock());
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.shift_CursorY(1);
                        } else {
                            Key_Menu.setTarget(bottom_screen.nextLock());
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (!Key_Menu.isMenu_switch()) {
                            Key_Menu.setTarget(bottom_screen.preLock());
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!Key_Menu.isMenu_switch()) {
                            Key_Menu.setTarget(bottom_screen.nextLock());
                        }
                        break;
                    case KeyEvent.VK_Z:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.setTarget((Chest) bottom_screen.generate_ChestList().get(0));
                            bottom_screen.item_aim = bottom_screen.LockList.get(0);
                            Key_Menu.setMenu_switch(false);
                            bottom_screen.show_Aim = true;
                        } else {
                            unlock_Chest(user, (Chest) Key_Menu.getTarget(), Key_Menu.getAim_key());
                            Key_Menu.clean();
                            bottom_screen.clean();
                            removeKeyListener(this);
                            Key_Menu.setVisible(false);
                            character_map[start_x + bottom_screen.left][start_y + bottom_screen.top] = null;
                            character_map[user.x + bottom_screen.left][user.y + bottom_screen.top] = user;
                            user.setMovepoint(false);
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (Key_Menu.isMenu_switch()) {
                            Key_Menu.setVisible(false);
                            removeKeyListener(this);
                            try {
                                display_CharMenu(cursor.getAim(), start_x, start_y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } else {
                            Key_Menu.setMenu_switch(true);
                            bottom_screen.show_Aim = false;
                        }
                        break;
                    default:
                        break;
                }
                Key_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void display_ExchangeMenu(Character player_1, int start_x, int start_y) {
        in_Menu = true;
        Exchange_Menu.setMain_exchange(false);
        Exchange_Menu.setVisible(true);
        Exchange_Menu.setCursor_y(0);
        Exchange_Menu.setCursor_x(0);
        Exchange_Menu.setPlayer_1(player_1);
        Exchange_Menu.setPlayer_2(PartnerAround(player_1));
        bottom_screen.show_Aim = true;
        bottom_screen.aim = Exchange_Menu.getPlayer_2();
        bottom_screen.displayExchangeArea(true);

        this.addKeyListener(new KeyListener() {
            int x = start_x;
            int y = start_y;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Exchange_Menu.isMain_exchange()) {
                            if (Exchange_Menu.getCursor_y() > 0) {
                                Exchange_Menu.shift_CursorY(-1);
                            } else {
                                Exchange_Menu.setCursor_y(Exchange_Menu.bag_limit());
                            }
                        } else if (character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top - 1] != null &&
                                character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top - 1].getCamp() == Camp.PLAYER &&
                                character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top - 1] != Exchange_Menu.getPlayer_1()) {
                            Exchange_Menu.setPlayer_2(character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top - 1]);

                            bottom_screen.aim = Exchange_Menu.getPlayer_2();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Exchange_Menu.isMain_exchange()) {
                            if (Exchange_Menu.getCursor_y() < Exchange_Menu.bag_limit()) {
                                Exchange_Menu.shift_CursorY(1);
                            } else {
                                Exchange_Menu.setCursor_y(0);
                            }
                        } else if (character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top + 1] != null &&
                                character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top + 1].getCamp() == Camp.PLAYER &&
                                character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top + 1] != Exchange_Menu.getPlayer_1()) {
                            Exchange_Menu.setPlayer_2(character_map[player_1.x + bottom_screen.left][player_1.y + bottom_screen.top + 1]);

                            bottom_screen.aim = Exchange_Menu.getPlayer_2();
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (Exchange_Menu.isMain_exchange()) {
                            if (Exchange_Menu.getCursor_x() == 0) {
                                Exchange_Menu.setCursor_x(168);
                                Exchange_Menu.setCursor_y(Math.min(Exchange_Menu.getCursor_y(), Exchange_Menu.bag_limit()));
                            } else {
                                Exchange_Menu.setCursor_x(0);
                            }
                            Exchange_Menu.setCursor_y(Math.min(Exchange_Menu.getCursor_y(), Exchange_Menu.bag_limit()));
                        } else if (!Exchange_Menu.isMain_exchange() && character_map[player_1.x + bottom_screen.left - 1][player_1.y + bottom_screen.top] != null &&
                                character_map[player_1.x + bottom_screen.left - 1][player_1.y + bottom_screen.top].getCamp() == Camp.PLAYER &&
                                character_map[player_1.x + bottom_screen.left - 1][player_1.y + bottom_screen.top] != Exchange_Menu.getPlayer_1()) {
                            Exchange_Menu.setPlayer_2(character_map[player_1.x + bottom_screen.left - 1][player_1.y + bottom_screen.top]);

                            bottom_screen.aim = Exchange_Menu.getPlayer_2();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (Exchange_Menu.isMain_exchange()) {
                            if (Exchange_Menu.getCursor_x() == 0) {
                                Exchange_Menu.setCursor_x(168);
                            } else {
                                Exchange_Menu.setCursor_x(0);
                            }
                            Exchange_Menu.setCursor_y(Math.min(Exchange_Menu.getCursor_y(), Exchange_Menu.bag_limit()));
                        }

                        if (Exchange_Menu.isMain_exchange() == false && character_map[player_1.x + bottom_screen.left + 1][player_1.y + bottom_screen.top] != null &&
                                character_map[player_1.x + bottom_screen.left + 1][player_1.y + bottom_screen.top].getCamp() == Camp.PLAYER &&
                                character_map[player_1.x + bottom_screen.left + 1][player_1.y + bottom_screen.top] != Exchange_Menu.getPlayer_1()) {
                            Exchange_Menu.setPlayer_2(character_map[player_1.x + bottom_screen.left + 1][player_1.y + bottom_screen.top]);

                            bottom_screen.aim = Exchange_Menu.getPlayer_2();
                        }
                        break;
                    case KeyEvent.VK_Z:
                        if (Exchange_Menu.isMain_exchange()) {
                            if (!Exchange_Menu.isCatch_item_1()) {
                                if (Exchange_Menu.getCursor_y() <= Exchange_Menu.bag_limit()) {
                                    if (Exchange_Menu.getCursor_x() == 0) {
                                        Exchange_Menu.setItem_1(Exchange_Menu.getCursor_y());
                                        Exchange_Menu.setBag_1(Exchange_Menu.getPlayer_1());
                                        Exchange_Menu.setCatch_item_1(true);
                                        System.out.println("set item_1");
                                    } else {
                                        Exchange_Menu.setItem_1(Exchange_Menu.getCursor_y());
                                        Exchange_Menu.setBag_1(Exchange_Menu.getPlayer_2());
                                        Exchange_Menu.setCatch_item_1(true);
                                        System.out.println("set item_1");
                                    }
                                    Exchange_Menu.setCursor_x(168 - Exchange_Menu.getCursor_x());
                                    Exchange_Menu.setCursor_y(Exchange_Menu.bag_limit());
                                }
                            } else {
                                Exchange_Menu.setItem_2(Exchange_Menu.getCursor_y());
                                if (Exchange_Menu.getCursor_x() == 0) {
                                    Exchange_Menu.setBag_2(Exchange_Menu.getPlayer_1());
                                } else {
                                    Exchange_Menu.setBag_2(Exchange_Menu.getPlayer_2());
                                }
                                Exchange_Menu.exchangeItem();
                                x = player_1.x;
                                y = player_1.y;
                                player_1.setMovepoint(false);
                            }
                        }
                        if (Exchange_Menu.getPlayer_2() != null) {
                            Exchange_Menu.setMain_exchange(true);
                            bottom_screen.show_Aim = false;
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (Exchange_Menu.isMain_exchange()) {
                            if (Exchange_Menu.isCatch_item_1()) {
                                Exchange_Menu.shift_CursorX();
                                Exchange_Menu.setCursor_y(Exchange_Menu.getItem_1());
                                Exchange_Menu.setCatch_item_1(false);
                            } else {
                                Exchange_Menu.setMain_exchange(false);
                                bottom_screen.show_Aim = true;
                            }
                        } else {
                            try {
                                removeKeyListener(this);
                                Exchange_Menu.setVisible(false);
                                bottom_screen.displayExchangeArea(false);
                                bottom_screen.show_Aim = false;
                                bottom_screen.aim = null;
                                display_CharMenu(player_1, x, y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }

                        break;
                }
                Exchange_Menu.repaint();

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    int i = 0;

    private void display_ExchangeMenu(Character player_1, Character player_2) {
        System.out.println(i++);
        Exchange_Menu.setMain_exchange(true);
        Exchange_Menu.setVisible(true);
        Exchange_Menu.setCursor_y(0);
        Exchange_Menu.setCursor_x(0);
        Exchange_Menu.setPlayer_1(player_1);
        Exchange_Menu.setPlayer_2(player_2);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Exchange_Menu.getCursor_y() > 0) {
                            Exchange_Menu.shift_CursorY(-1);
                        } else {
                            Exchange_Menu.setCursor_y(Exchange_Menu.bag_limit());
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (Exchange_Menu.getCursor_y() < Exchange_Menu.bag_limit()) {
                            Exchange_Menu.shift_CursorY(1);
                        } else {
                            Exchange_Menu.setCursor_y(0);
                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        if (Exchange_Menu.getCursor_x() == 0) {
                            Exchange_Menu.setCursor_x(168);
                            Exchange_Menu.setCursor_y(Math.min(Exchange_Menu.getCursor_y(), Exchange_Menu.bag_limit()));
                        } else {
                            Exchange_Menu.setCursor_x(0);
                        }
                        Exchange_Menu.setCursor_y(Math.min(Exchange_Menu.getCursor_y(), Exchange_Menu.bag_limit()));
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (Exchange_Menu.getCursor_x() == 0) {
                            Exchange_Menu.setCursor_x(168);
                        } else {
                            Exchange_Menu.setCursor_x(0);
                        }
                        Exchange_Menu.setCursor_y(Math.min(Exchange_Menu.getCursor_y(), Exchange_Menu.bag_limit()));
                        break;

                    case KeyEvent.VK_Z:
                        if (!Exchange_Menu.isCatch_item_1()) {
                            if (Exchange_Menu.getCursor_y() <= Exchange_Menu.bag_limit()) {
                                if (Exchange_Menu.getCursor_x() == 0) {
                                    Exchange_Menu.setItem_1(Exchange_Menu.getCursor_y());
                                    Exchange_Menu.setBag_1(Exchange_Menu.getPlayer_1());
                                    Exchange_Menu.setCatch_item_1(true);
                                    System.out.println("catch item_1 left");
                                } else {
                                    Exchange_Menu.setItem_1(Exchange_Menu.getCursor_y());
                                    Exchange_Menu.setBag_1(Exchange_Menu.getPlayer_2());
                                    Exchange_Menu.setCatch_item_1(true);
                                    System.out.println("catch item_1 right");
                                }
                                Exchange_Menu.setCursor_x(168 - Exchange_Menu.getCursor_x());
                                Exchange_Menu.setCursor_y(Exchange_Menu.bag_limit());
                            }
                            System.out.println(Exchange_Menu.isCatch_item_1());
                        } else {
                            Exchange_Menu.setItem_2(Exchange_Menu.getCursor_y());
                            System.out.println("Item_2 caught");
                            if (Exchange_Menu.getCursor_x() == 0) {
                                Exchange_Menu.setBag_2(Exchange_Menu.getPlayer_1());
                            } else {
                                Exchange_Menu.setBag_2(Exchange_Menu.getPlayer_2());
                            }
                            Exchange_Menu.exchangeItem();
                            player_1.setMovepoint(false);
                        }
                        break;

                    case KeyEvent.VK_X:
                        if (Exchange_Menu.isCatch_item_1()) {
                            Exchange_Menu.shift_CursorX();
                            Exchange_Menu.setCursor_y(Exchange_Menu.getItem_1());
                            Exchange_Menu.setCatch_item_1(false);
                        } else {
                            Exchange_Menu.setVisible(false);
                            removeKeyListener(this);
                            I_I.setIn_sub(false);
                        }
                        break;
                    default:
                        break;
                }
                Exchange_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    private boolean checkPC(int x, int y) {
        boolean hasPC = false;
        for (Character c : this.PLAYER) {
            if (c.x == x && c.y == y) {
                hasPC = true;
            }
        }
        return hasPC;
    }

    private void display_ItemMenu(int start_x, int start_y) {
        in_Menu = true;
        Item_Menu.setCursor_y(0);
        Item_Menu.setC(cursor.getAim());
        if (!cursor.getAim().getBag().isEmpty())
            Item_Menu.setAim(cursor.getAim().getBag().get(0));
        Item_Menu.setVisible(true);

        this.addKeyListener(new KeyListener() {
            int mark = 0;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (mark == 0) {
                            Item_Menu.shift_CursorY(-1);
                            Item_Menu.setAim(Item_Menu.getC().getBag().get(Item_Menu.getCursor_y()));
                        } else if (mark == 1) {
                            if (Item_Menu.getSubcursor_y() > 0) Item_Menu.shift_SubCursorY(-1);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (mark == 0) {
                            Item_Menu.shift_CursorY(1);
                            Item_Menu.setAim(Item_Menu.getC().getBag().get(Item_Menu.getCursor_y()));
                        } else if (mark == 1) {
                            int row = 0;
                            if (Item_Menu.isEquip()) row++;
                            if (Item_Menu.isUse()) row++;
                            if (Item_Menu.isDrop()) row++;
                            if (Item_Menu.getSubcursor_y() < row - 1) Item_Menu.shift_SubCursorY(1);
                        }
                        break;

                    case KeyEvent.VK_Z:
                        if (mark == 1) {
                            int[] row = new int[3];
                            int i = 0;
                            if (Item_Menu.isEquip()) {
                                row[i] = 1;
                                i++;
                            }
                            if (Item_Menu.isUse()) {
                                row[i] = 2;
                                i++;
                            }
                            if (Item_Menu.isDrop()) {
                                row[i] = 3;
                            }
                            switch (row[Item_Menu.getSubcursor_y()]) {
                                case 1:
                                    if (Item_Menu.aim instanceof Equipable) {
                                        ((Equipable) Item_Menu.aim).equip(Item_Menu.getC());
                                        Item_Menu.setCursor_y(0);
                                        Item_Menu.subMenu(false);
                                        mark = 0;
                                    }
                                    break;
                                case 2:
                                    if (Item_Menu.aim instanceof Usable) {
                                        Character c = cursor.getAim();
                                        if (Item_Menu.aim instanceof HealingItem && c.getHP() > c.gethp()) {
                                            ((HealingItem) Item_Menu.aim).consume();
                                            Stack<String> texts = new Stack<>();
                                            if (((HealingItem) Item_Menu.aim).getUses() == 0) {
                                                texts.push(Item_Menu.aim.getName() + " Broken.");
                                                Item_Menu.getC().getBag().remove(Item_Menu.aim);
                                            }
                                            int val = Math.min(((HealingItem) Item_Menu.aim).getVal(), c.getHP() - c.gethp());
                                            character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = c;
                                            Item_Menu.setVisible(false);
                                            removeKeyListener(this);
                                            in_Menu = false;
                                            bottom_screen.z_mark = 0;
                                            display_Heal(window_1, c, val, texts);
                                            c.setMovepoint(false);
                                            Item_Menu.subMenu(false);
                                            mark = 0;
                                        }
                                    }
                                    break;
                                case 3:
                                    Item_Menu.getC().getBag().remove(Item_Menu.aim);
                                    if (Item_Menu.getCursor_y() > 0) {
                                        Item_Menu.shift_CursorY(-1);
                                        Item_Menu.setAim(Item_Menu.getC().getBag().get(Item_Menu.getCursor_y()));
                                    }
                                    Item_Menu.subMenu(false);
                                    mark = 0;
                                    break;
                                default:
                                    break;
                            }
                        } else if (mark == 0) {
                            Item_Menu.subMenu(true);
                            mark = 1;
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (mark == 0) {
                            Item_Menu.setVisible(false);
                            removeKeyListener(this);
                            System.out.println(mark);
                            try {
                                display_CharMenu(cursor.getAim(), start_x, start_y);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        } else {
                            Item_Menu.subMenu(false);
                            mark = 0;
                        }
                        break;
                    default:
                        break;

                }
                Item_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    private void display_ItemMenu(Character c) {
        I_I.setIn_sub(true);
        Item_Menu.setCursor_y(0);
        Item_Menu.setC(c);
        if (!c.getBag().isEmpty())
            Item_Menu.setAim(c.getBag().get(0));
        Item_Menu.setVisible(true);

        this.addKeyListener(new KeyListener() {
            int mark = 0;


            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (mark == 0) {
                            Item_Menu.shift_CursorY(-1);
                            Item_Menu.setAim(Item_Menu.getC().getBag().get(Item_Menu.getCursor_y()));
                        } else if (mark == 1) {
                            if (Item_Menu.getSubcursor_y() > 0) Item_Menu.shift_SubCursorY(-1);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (mark == 0) {
                            Item_Menu.shift_CursorY(1);
                            Item_Menu.setAim(Item_Menu.getC().getBag().get(Item_Menu.getCursor_y()));
                        } else if (mark == 1) {
                            int row = 0;
                            if (Item_Menu.isEquip()) row++;
                            if (Item_Menu.isUse()) row++;
                            if (Item_Menu.isDrop()) row++;
                            if (Item_Menu.getSubcursor_y() < row - 1) Item_Menu.shift_SubCursorY(1);
                        }
                        break;

                    case KeyEvent.VK_Z:
                        if (mark == 1) {
                            Item_Menu.useItem();
                            Item_Menu.subMenu(false);
                            mark = 0;
                        } else if (mark == 0) {
                            Item_Menu.subMenu(true);
                            mark = 1;
                        }
                        break;
                    case KeyEvent.VK_X:
                        if (mark == 0) {
                            I_I.setIn_sub(false);
                            Item_Menu.setVisible(false);
                            removeKeyListener(this);
                            System.out.println(mark);
                        } else {
                            Item_Menu.subMenu(false);
                            mark = 0;
                        }

                        break;
                    default:
                        break;

                }
                Item_Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void display_Transporter(Character c, Transporter transporter) {
        I_I.setVisible(false);
        T_I.setTransporter(transporter);
        T_I.setUser(c);
        T_I.setVisible(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {

                    case KeyEvent.VK_UP:
                        switch (T_I.getZ_mark()) {
                            case 0:
                                if (T_I.getZ_mark() == 0) {
                                    T_I.setCursor_y(1 - T_I.getCursor_y());
                                }
                                break;
                            case 1:
                                T_I.ItemBox_Up();
                                break;
                            case 2:
                                T_I.TransporterBox_Up();
                            default:
                                break;

                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        switch (T_I.getZ_mark()) {
                            case 0:
                                if (T_I.getZ_mark() == 0) {
                                    T_I.setCursor_y(1 - T_I.getCursor_y());
                                }
                                break;
                            case 1:
                                T_I.ItemBox_Down();
                                break;
                            case 2:
                                T_I.TransporterBox_Down();
                            default:
                                break;

                        }
                        break;

                    case KeyEvent.VK_LEFT:
                        if (T_I.getZ_mark() == 2) {
                            T_I.TransporterBox_Left();
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (T_I.getZ_mark() == 2) {
                            T_I.TransporterBox_Right();
                        }
                        break;

                    case KeyEvent.VK_Z:
                        switch (T_I.getZ_mark()) {
                            case 0:
                                if (T_I.getCursor_y() == 0) {
                                    T_I.setZ_mark(1);
                                } else {
                                    T_I.setCursor_y(0);
                                    T_I.setZ_mark(2);
                                }
                                break;
                            case 1:
                                T_I.store();
                                break;
                            case 2:
                                T_I.take_out();
                                break;
                            default:
                                break;
                        }
                        break;

                    case KeyEvent.VK_X:
                        switch (T_I.getZ_mark()) {
                            case 0:
                                removeKeyListener(this);
                                T_I.clean();
                                T_I.setVisible(false);
                                display_ItemInterface();
                                I_I.repaint();
                                break;
                            case 1:
                                T_I.clean();
                                break;
                            case 2:
                                T_I.clean();
                                break;
                        }
                        break;
                    default:
                        break;
                }

                if (T_I.getZ_mark() != 0) {
                    T_I.refreshAim();
                }

                T_I.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
    }


    private void display_MainMenu() throws IOException {
        in_Menu = true;
        Menu.setcursory(0);
        int row = 3;
        Menu.setBounds(144, 256 + 128 - 16 * row, 64, 32 * row);
        Menu.setRow(row);
        Menu.subOption(0, "Team");
        Menu.subOption(1, "Instruction");
        Menu.subOption(2, "End Turn");
        Menu.setVisible(true);
        Menu.repaint();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (Menu.getcursory() > 0) {
                            Menu.shift_cursor(-1);
                        } else {
                            Menu.setcursory(2);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (Menu.getcursory() < Menu.getRow() - 1) {
                            Menu.shift_cursor(1);
                        } else {
                            Menu.setcursory(0);
                        }
                        break;

                    case KeyEvent.VK_Z:
                        switch (Menu.getcursory()) {
                            case 0:
                                removeMenu(this);
                                display_TeamMenu();
                                break;

                            case 1:
//                                display_Instruction();
                                break;
                            case 2:
                                //End Turn
                                removeMenu(this);
                                run_EndTurn();
                                break;
                        }
                        break;
                    case KeyEvent.VK_X:
                        removeMenu(this);
                        break;
                    default:
                        break;

                }
                Menu.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public void removeMenu(KeyListener key) {
        in_Menu = false;
        System.out.println("in_Menu " + in_Menu);
        System.out.println("remove Menu");
        Menu.setVisible(false);
        Menu.getList().clear();
        this.removeKeyListener(key);
        this.repaint();

    }

    private void display_TeamMenu() {
        in_Menu = true;
        Team_Menu.setPLAYER(PLAYER);
        Team_Menu.setVisible(true);
        Team_Menu.repaint();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        Team_Menu.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        Team_Menu.right();
                        break;
                    case KeyEvent.VK_UP:
                        Team_Menu.up();
                        break;
                    case KeyEvent.VK_DOWN:
                        Team_Menu.down();
                        break;
                    case KeyEvent.VK_X:
                        Team_Menu.removeAll();
                        Team_Menu.setVisible(false);
                        removeKeyListener(this);
                        try {
                            display_MainMenu();
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    int count = 0;

    private void Map_Shift(Direction dir) {
        switch (dir) {
            case UP:
                bottom_screen.top--;
                if (!PLAYER.isEmpty())
                    for (Character pl : PLAYER) {
                        pl.y++;
                    }
                if (!ENEMY.isEmpty())
                    for (Character en : ENEMY) {
                        en.y++;
                    }
                if (!PARTNER.isEmpty()) {
                    for (Character pc : PARTNER) {
                        pc.y++;
                    }
                }
                if (!MONSTER.isEmpty()) {
                    for (Character mon : MONSTER) {
                        mon.y++;
                    }
                }
                break;
            case DOWN:
                bottom_screen.top++;
                if (!PLAYER.isEmpty())
                    for (Character pl : PLAYER) {
                        pl.y--;
                        count++;
                    }
                if (!ENEMY.isEmpty())
                    for (Character en : ENEMY) {
                        en.y--;
                    }
                if (!PARTNER.isEmpty()) {
                    for (Character pc : PARTNER) {
                        pc.y--;
                    }
                }
                if (!MONSTER.isEmpty()) {
                    for (Character mon : MONSTER) {
                        mon.y--;
                    }
                }
                break;
            case LEFT:
                bottom_screen.left--;
                if (!PLAYER.isEmpty())
                    for (Character pl : PLAYER) {
                        pl.x++;
                    }
                if (!ENEMY.isEmpty())
                    for (Character en : ENEMY) {
                        en.x++;
                    }
                if (!PARTNER.isEmpty()) {
                    for (Character pc : PARTNER) {
                        pc.x++;
                    }
                }
                if (!MONSTER.isEmpty()) {
                    for (Character mon : MONSTER) {
                        mon.x++;
                    }
                }
                break;
            case RIGHT:
                bottom_screen.left++;
                if (!PLAYER.isEmpty())
                    for (Character pl : PLAYER) {
                        pl.x--;
                    }
                if (!ENEMY.isEmpty())
                    for (Character en : ENEMY) {
                        en.x--;
                    }
                if (!PARTNER.isEmpty()) {
                    for (Character pc : PARTNER) {
                        pc.x--;
                    }
                }
                if (!MONSTER.isEmpty()) {
                    for (Character mon : MONSTER) {
                        mon.x--;
                    }
                }
                break;

            default:
                break;
        }
    }


    private void show_Info() {
        if (bottom_screen.z_mark == 0) {
            int X = bottom_screen.left + cursor.getx();
            int Y = bottom_screen.top + cursor.gety();
            if (character_map[X][Y] != null) {
                cursor.setAim(character_map[X][Y]);
                top_screen.setCharacter(character_map[X][Y]);
            }
            top_screen.setTerrain(terrain_map[X][Y]);

        }
    }

    private void run_EndTurn() {
        if (is_Victory()) {
            //return to preparation interface, add next chapter into SAVE file.
            System.out.println("CLEAR");
            removeKeyListener(main_Listener);
            Chapter_Complete();
        } else {
            //It is ENEMY PHASE now, no input from player.
            in_Menu = true;
            othersTurn = true;
            bottom_screen.test_switch = false;
            bottom_screen.ENEMY_PHASE = true;
            repaint();
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (bottom_screen.ENEMY_PHASE) {
                        bottom_screen.ENEMY_PHASE = false;
                        ((Timer) e.getSource()).stop();
                        if (!ENEMY.isEmpty()) {
                            nextNPC = ENEMY.get(0);
                        } else if (!MONSTER.isEmpty()) {
                            nextNPC = MONSTER.get(0);
                        } else if (!PARTNER.isEmpty()) {
                            nextNPC = PARTNER.get(0);
                        }

                        next_NPC();
                    }
                }
            });
            timer.start();

        }


    }

    private Stack<Direction> generatePath(Character c, int dx, int dy) {
        Stack<Direction> path = new Stack<>();
        if (dx > 0) {
            for (int i = 0; i < dx; i++) {
                path.push(Direction.RIGHT);
            }
        } else if (dx < 0) {
            for (int i = 0; i < -dx; i++) {
                path.push(Direction.LEFT);
            }
        }
        if (dy > 0) {
            for (int j = 0; j < dy; j++) {
                path.push(Direction.DOWN);
            }
        } else if (dy < 0) {
            for (int j = 0; j < Math.abs(dy); j++) {
                path.push(Direction.UP);
            }
        }
        return path;
    }

    private Stack<Direction> generatePath(int dx, int dy) {
        Stack<Direction> path = new Stack<>();
        if (dx > 0) {
            for (int i = 0; i < dx; i++) {
                path.push(Direction.RIGHT);
            }
        } else if (dx < 0) {
            for (int i = 0; i < -dx; i++) {
                path.push(Direction.LEFT);
            }
        }
        if (dy > 0) {
            for (int j = 0; j < dy; j++) {
                path.push(Direction.DOWN);
            }
        } else if (dy < 0) {
            for (int j = 0; j < Math.abs(dy); j++) {
                path.push(Direction.UP);
            }
        }
        return path;
    }

    private void refresh_EffectArea() {
        bottom_screen.cleanRedArea();
        bottom_screen.add_Affect_Area(Attack_Menu.getAim_weapon());
    }

//    public void addTerrain(Envir_Item terrain) {
//        ITEM.add(terrain);
//        terrain_map[terrain.getX()][terrain.getY()] = terrain;
//    }

    public void FacetoFace(Character c1, Character c2) {
        int dx = c2.x - c1.x;
        int dy = c2.y - c1.y;
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0) {
                c1.setDir(2);
                c2.setDir(1);
            } else {
                c1.setDir(1);
                c2.setDir(2);
            }
        } else {
            if (dy > 0) {
                c2.setDir(3);
                c1.setDir(0);
            } else {
                c2.setDir(0);
                c1.setDir(3);
            }
        }
    }

    //Target of a staff differs because of its magic effect.
    // A chest would never turn its face to you, unless it is a mimic...
    protected void FacetoTarget(Character pc, Object target) {
        int dx, dy;
        if (target instanceof Character) {
            dx = ((Character) target).x - pc.x;
            dy = ((Character) target).y - pc.y;
        } else if (target instanceof Envir_Item) {
            dx = ((Envir_Item) target).getX() - bottom_screen.left - pc.x;
            dy = ((Envir_Item) target).getY() - bottom_screen.top - pc.y;
        } else {
            return;
        }

        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0) {
                pc.setDir(2);
            } else {
                pc.setDir(1);
            }
        } else {
            if (dy > 0) {
                pc.setDir(0);
            } else {
                pc.setDir(3);
            }
        }
    }

    protected void FacetoTarget(Character pc, Character target) {
        int dx = target.x - pc.x;
        int dy = target.y - pc.y;
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0) {
                pc.setDir(2);
            } else {
                pc.setDir(1);
            }
        } else {
            if (dy > 0) {
                pc.setDir(0);
            } else {
                pc.setDir(3);
            }
        }
    }

    protected void FacetoTarget(Character pc, Envir_Item envir) {
        int dx = envir.getX() - bottom_screen.left - pc.x;
        int dy = envir.getY() - bottom_screen.top - pc.y;
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0) {
                pc.setDir(2);
            } else {
                pc.setDir(1);
            }
        } else {
            if (dy > 0) {
                pc.setDir(0);
            } else {
                pc.setDir(3);
            }
        }
    }

    //transport battle parameters to attack menu.
    private void transportPara() {
        int[] para = bottom_screen.transportPara();
        Attack_Menu.setHp_1(para[0]);
        Attack_Menu.setHp_2(para[1]);
        Attack_Menu.setDamage_1(para[2]);
        Attack_Menu.setDamage_2(para[3]);
        Attack_Menu.setHit_1(para[4]);
        Attack_Menu.setHit_2(para[5]);
        Attack_Menu.setCrit_1(para[6]);
        Attack_Menu.setCrit_2(para[7]);
        Attack_Menu.setTurn_1(para[8]);
        Attack_Menu.setTurn_2(para[9]);
    }


    private void display_HP(int num, Character c) {
        if (num == 1) {
            window_1.setC(c);
            window_1.setHP(c.getHP());
            window_1.sethp(c.gethp());
            window_1.setVisible(true);
        }
        if (num == 2) {
            window_2.setC(c);
            window_2.setHP(c.getHP());
            window_2.sethp(c.gethp());
            window_2.setVisible(true);
        }
    }

    public void display_HP(HP_Window window, Character c) {
        window.setC(c);
        window.setHP(c.getHP());
        window.sethp(c.gethp());
        window.setVisible(true);
    }

    public void display_HP_Timer(HP_Window window, Staff staff) {
        hp_timer = new Timer(100, new ActionListener() {
            int val = window.getC().gethp() - window.gethp();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (val > 0) {
                    val--;
                    window.hp_plus();
                } else {
                    val++;
                    window.hp_minus();
                }
                repaint();
                if (val == 0) {
                    window.setVisible(false);
                    in_Menu = false;
                    ((Timer) e.getSource()).stop();
                    display_StaffEXP(staff.getOwner(), staff, new Stack<String>());
                }
            }
        });
        hp_timer.start();
    }

    private void display_Heal(HP_Window window, Character user, Character target, Staff staff) {
        display_HP(window, target);
        staff.staff(target, user);

        hp_timer = new Timer(100, new ActionListener() {
            int val = window.getC().gethp() - window.gethp();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (val > 0) {
                    val--;
                    window.hp_plus();
                } else {
                    val++;
                    window.hp_minus();
                }
                repaint();
                if (val == 0) {
                    window.setVisible(false);
                    in_Menu = false;
                    ((Timer) e.getSource()).stop();
                    if (othersTurn) {
                        next_NPC();
                    }
                }
            }
        });
        hp_timer.start();
    }

    private void display_Heal(HP_Window window, Character target, int value, Stack<String> texts) {
        display_HP(window, target);
        hp_timer = new Timer(100, new ActionListener() {
            int val = value;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (val > 0) {
                    val--;
                    window.hp_plus();
                } else {
                    val++;
                    window.hp_minus();
                }
                repaint();
                if (val == 0) {
                    window.setVisible(false);
                    in_Menu = false;
                    ((Timer) e.getSource()).stop();
                    target.sethp(target.gethp() + value);
                    display_Message(target, texts);
                    if (othersTurn) {
                        next_NPC();
                    }
                }
            }
        });
        hp_timer.start();
    }

    public void switch_on(HP_Window window, Character c) {
        window.setC(c);
        window.sethp(c.gethp());
        window.setHP(c.getHP());
        window.setVisible(true);
    }


    private void unlock_Gate(Gate gate, Object key) {
        gate.unlock(key);
        ITEM.remove(gate);
        terrain_map[gate.getX()][gate.getY()] = null;
        Stack<String> texts = new Stack<>();


        if (key instanceof Staff) {
            display_StaffEXP(cursor.getAim(), Staff_Menu.getAim_staff(), texts);
        } else {
            if (key instanceof Consumables && ((Consumables) key).getUses() == 0) {
                texts.push(((Consumables) key).getName() + "  broken.");
                cursor.getAim().getBag().remove(key);
            }
            display_Message(cursor.getAim(), texts);
        }
    }

    private void unlock_Chest(Character c, Chest chest, Object key) {
        Stack<String> texts = new Stack<>();

        if (chest.getTreasure() instanceof Item) {
            Item item = (Item) chest.unlock(key);

            if ((key instanceof Keys || key instanceof UnlockStaff) && ((Consumables) key).getUses() == 0) {

                texts.push(((Consumables) key).getName() + " broken.");
                System.out.println(((Consumables) key).getName() + " broken.");
                c.getBag().remove(key);
            }
            c.gainItem(item);
            texts.push("Obtain " + item.getName());

        } else {
            Mimic_JumpOut(chest);
        }
        if (key instanceof Staff) {
            display_StaffEXP(cursor.getAim(), Staff_Menu.getAim_staff(), texts);
        } else {
            display_Message(c, texts);
        }

    }

    //if one character get sixth item, one item in the bag should be selected to send to transporter..
    private void check_OutofBag(Character pl) {
        if (pl.getBag().size() > 5) {
            display_StoreMenu(pl);
        }
    }

    private void display_StoreMenu(Character pl) {
        in_Menu = true;
        bottom_screen.z_mark = 1;
        Store_Menu.setC(pl);
        Store_Menu.setVisible(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        Store_Menu.ItemBox_Up();
                        break;
                    case KeyEvent.VK_DOWN:
                        Store_Menu.ItemBox_Down();
                        break;
                    case KeyEvent.VK_Z:
                        transporter.store(pl.getBag().get(Store_Menu.getCursor_y()));
                        pl.getBag().remove(pl.getBag().get(Store_Menu.getCursor_y()));
                        removeKeyListener(this);
                        Store_Menu.setVisible(false);
                        Store_Menu.clean();
                        in_Menu = false;
                        bottom_screen.z_mark = 0;
                        if (is_Victory()) {
                            Chapter_Complete();
                        }
                    default:
                        break;
                }
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    private void Mimic_JumpOut(Chest chest) {
        if (chest.getTreasure() != null && chest.getTreasure() instanceof Character) {
            int X = chest.getX();
            int Y = chest.getY();
            Character mimic = (Character) chest.getTreasure();
            ENEMY.add(mimic);
            terrain_map[X][Y] = null;
            ITEM.remove(chest);
            character_map[X][Y] = mimic;
        }
    }

    private void display_StaffEXP(Character user, Staff staff, Stack<String> texts) {
        in_Menu = true;
        int get_exp = 30 + staff.getPrice() / staff.getUSES() / 20;
        exp_window.setC(user);
        exp_window.setexp(user.getExp());
        exp_window.setVisible(true);
        Timer timer = new Timer(50, new ActionListener() {
            int val = get_exp;
            int EXP = val + user.getExp();

            @Override
            public void actionPerformed(ActionEvent e) {
                val--;
                exp_window.exp_plus();
                exp_window.repaint();

                if (val == 0) {
                    ((Timer) e.getSource()).stop();
                    user.addWeapon_exp(7, staff.getExp());
                    user.setExp(exp_window.getexp());
                    exp_window.setVisible(false);
                    in_Menu = false;
                    user.setMovepoint(false);
                    LV_UP_withoutBattle(user, EXP, texts);
                }
            }
        });
        timer.start();
    }

    private void LV_UP_withoutBattle(Character c, int EXP, Stack<String> texts) {
        if (EXP >= 100) {
            c.setExp(c.getExp() - 100);
            bottom_screen.display_LVUP(c, texts);
        } else {
            display_Message(c, texts);
        }
    }


    protected void display_Message(Character c, Stack<String> texts) {
        if (texts == null || texts.isEmpty()) {
            if (is_Victory()) {
                Chapter_Complete();
            } else if (othersTurn) {
                next_NPC();
            } else {
                in_Menu = false;
                bottom_screen.z_mark = 0;
            }
        } else {
            in_Menu = true;
            bottom_screen.z_mark = 1;
            message_window.drawMessage(texts.pop());
            message_window.setVisible(true);
            System.out.println("displaying message");

            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_Z) {

                        if (!texts.isEmpty()) {
                            message_window.drawMessage(texts.pop());
                        } else {
                            if (!othersTurn) {

                                removeKeyListener(this);
                                message_window.setVisible(false);
                                Timer t = new Timer(500, new ActionListener() {
                                    int i = 0;

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        i++;
                                        in_Menu = true;
                                        bottom_screen.z_mark = 1;
                                        if (i == 2) {
                                            System.out.println(i + " " + in_Menu);
                                            ((Timer) e.getSource()).stop();
                                            System.out.println("in main listener now");
                                            in_Menu = false;
                                            bottom_screen.z_mark = 0;
                                            check_OutofBag(c);
                                        }
                                    }
                                });
                                t.start();
                            } else {
                                message_window.setVisible(false);
                                removeKeyListener(this);
                                next_NPC();
                            }
                        }
                        repaint();
                    } else if (e.getKeyCode() == KeyEvent.VK_X) {
                        if (!texts.isEmpty()) {
                            display_Message(c, texts);
                        } else {
                            if (!othersTurn) {

                                in_Menu = false;
                                bottom_screen.z_mark = 0;
                                message_window.setVisible(false);
                                removeKeyListener(this);
                            } else {
                                message_window.setVisible(false);
                                removeKeyListener(this);
                                next_NPC();
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


    protected boolean check_Defeated(Character c) {
        if (c.gethp() <= 0) {
            switch (c.getCamp()) {
                case PLAYER:
                    PLAYER.remove(c);
                    ALL_PLAYER.remove(c);
                    Grave.add(c);
                    if (PLAYER.isEmpty()) {
                        Game_Over();
                    }
                    break;
                case ENEMY:
                    ENEMY.remove(c);
                    break;
                case MONSTER:
                    MONSTER.remove(c);
                    break;
                case PARTNER:
                    PARTNER.remove(c);
                    chapter.Grave.add(c);
                    break;
                default:
                    break;
            }
            character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = null;
            if (c == top_screen.getCharacter()) top_screen.setCharacter(null);
            top_screen.repaint();
            return true;
        }
        return false;
    }

    private void Game_Over() {
        Start_Game();
    }

    protected Item check_Drops(Character c) {
        //Only one item in enemy's bag can drop.
        for (Item item : c.getBag()) {
            if (item.isDropable()) {
                return item;
            }
        }
        return null;
    }


    HashSet aim_set = new HashSet();

    protected void next_NPC() {
        if (is_Victory()) {
            Chapter_Complete();
        } else {
            tempNPC = nextNPC;
            nextNPC = getNextNPC(tempNPC);
            if (tempNPC != null) {
                Character en = tempNPC;
                destination = null;
                aim_set.clear();
                int end_x = en.x;
                int end_y = en.y;
                int dx = end_x - cursor.getx();
                int dy = end_y - cursor.gety();
                Stack<Direction> path = generatePath(en, dx, dy);

                Timer timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!path.isEmpty()) {
                            Direction dir = path.pop();
                            switch (dir) {
                                case UP:
                                    if (cursor.gety() >= 3 || bottom_screen.top == 0) {
                                        cursor.shift_y(-1);
                                    } else {
                                        Map_Shift(Direction.UP);
                                    }
                                    break;
                                case DOWN:
                                    if (cursor.gety() <= 4 || bottom_screen.top == bottom_screen.map_y - 8) {
                                        cursor.shift_y(1);
                                    } else {
                                        Map_Shift(Direction.DOWN);
                                    }
                                    break;
                                case LEFT:
                                    if (cursor.getx() >= 3 || bottom_screen.left == 0) {
                                        cursor.shift_x(-1);
                                    } else {
                                        Map_Shift(Direction.LEFT);
                                    }
                                    break;
                                case RIGHT:
                                    if (cursor.getx() <= 7 || bottom_screen.left == bottom_screen.map_x - 11) {
                                        cursor.shift_x(1);
                                    } else {
                                        Map_Shift(Direction.RIGHT);
                                    }
                                    break;
                                default:
                                    break;
                            }

                            show_Info();
                            repaint();
                            if (path.isEmpty()) {
                                ((Timer) e.getSource()).stop();
                                Heal_BeforeAction(en);
                            }
                        } else {
                            Heal_BeforeAction(en);
                        }
                    }

                });
                timer.start();
            } else {
                Reinforce(0);
            }
        }
    }

    private void Heal_BeforePhase(int i) {
        if (PLAYER.size() > i) {
            Character pc = PLAYER.get(i);
            bottom_screen.check_Terrain(pc);
            if (pc.getEnvir_HP() > 0 && pc.gethp() < pc.getHP()) {
                int end_x = pc.x;
                int end_y = pc.y;
                int dx = end_x - cursor.getx();
                int dy = end_y - cursor.gety();
                Stack<Direction> path = generatePath(pc, dx, dy);

                Timer timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Direction dir = path.pop();
                        switch (dir) {
                            case UP:
                                if (cursor.gety() >= 3 || bottom_screen.top == 0) {
                                    cursor.shift_y(-1);
                                } else {
                                    Map_Shift(Direction.UP);
                                }
                                break;
                            case DOWN:
                                if (cursor.gety() <= 4 || bottom_screen.top == bottom_screen.map_y - 8) {
                                    cursor.shift_y(1);
                                } else {
                                    Map_Shift(Direction.DOWN);
                                }
                                break;
                            case LEFT:
                                if (cursor.getx() >= 3 || bottom_screen.left == 0) {
                                    cursor.shift_x(-1);
                                } else {
                                    Map_Shift(Direction.LEFT);
                                }
                                break;
                            case RIGHT:
                                if (cursor.getx() <= 7 || bottom_screen.left == bottom_screen.map_x - 11) {
                                    cursor.shift_x(1);
                                } else {
                                    Map_Shift(Direction.RIGHT);
                                }
                                break;
                        }

                        show_Info();
                        repaint();
                        if (path.isEmpty()) {
                            ((Timer) e.getSource()).stop();
                            display_HP(window_1, pc);
                            int heal = Math.min(pc.getEnvir_HP() * pc.getHP() / 100, pc.getHP() - pc.gethp());
                            pc.heal(heal);
                            hp_timer = new Timer(100, new ActionListener() {
                                int val = heal;
                                int num = i;

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (val > 0) {
                                        val--;
                                        window_1.hp_plus();
                                    } else {
                                        val++;
                                        window_1.hp_minus();
                                    }
                                    repaint();
                                    if (val == 0) {
                                        window_1.setVisible(false);
                                        ((Timer) e.getSource()).stop();
                                        Heal_BeforePhase(num + 1);
                                    }
                                }
                            });
                            hp_timer.start();
                        }
                    }
                });
                timer.start();
            } else {
                Heal_BeforePhase(i + 1);
            }
        } else {
            Character pl = PLAYER.get(0);
            aim_set.clear();
            int end_x = pl.x;
            int end_y = pl.y;
            int dx = end_x - cursor.getx();
            int dy = end_y - cursor.gety();
            Stack<Direction> path = generatePath(pl, dx, dy);
            if (!path.isEmpty()) {
                Timer timer = new Timer(50, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Direction dir = path.pop();
                        switch (dir) {
                            case UP:
                                if (cursor.gety() >= 3 || bottom_screen.top == 0) {
                                    cursor.shift_y(-1);
                                } else {
                                    Map_Shift(Direction.UP);
                                }
                                break;
                            case DOWN:
                                if (cursor.gety() <= 4 || bottom_screen.top == bottom_screen.map_y - 8) {
                                    cursor.shift_y(1);
                                } else {
                                    Map_Shift(Direction.DOWN);
                                }
                                break;
                            case LEFT:
                                if (cursor.getx() >= 3 || bottom_screen.left == 0) {
                                    cursor.shift_x(-1);
                                } else {
                                    Map_Shift(Direction.LEFT);
                                }
                                break;
                            case RIGHT:
                                if (cursor.getx() <= 7 || bottom_screen.left == bottom_screen.map_x - 11) {
                                    cursor.shift_x(1);
                                } else {
                                    Map_Shift(Direction.RIGHT);
                                }
                                break;
                            default:
                                break;
                        }

                        show_Info();
                        repaint();
                        if (path.isEmpty()) {
                            ((Timer) e.getSource()).stop();
                            bottom_screen.PLAYER_PHASE = true;
                            repaint();
                            Timer phase = new Timer(1000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (bottom_screen.PLAYER_PHASE) {
                                        ((Timer) e.getSource()).stop();
                                        bottom_screen.PLAYER_PHASE = false;
                                        turn++;
                                        othersTurn = false;
                                        in_Menu = false;
                                        bottom_screen.z_mark = 0;
                                        top_screen.setCharacter(cursor.getAim());
                                        System.out.println("next phase");
                                        Charge(PLAYER);
                                    }
                                }
                            });
                            phase.start();
                        }
                    }

                });
                timer.start();
            } else {
                bottom_screen.PLAYER_PHASE = true;
                repaint();
                Timer phase = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (bottom_screen.PLAYER_PHASE) {
                            ((Timer) e.getSource()).stop();
                            bottom_screen.PLAYER_PHASE = false;
                            turn++;
                            othersTurn = false;
                            in_Menu = false;
                            bottom_screen.z_mark = 0;
                            top_screen.setCharacter(cursor.getAim());
                            System.out.println("next phase");
                            Charge(PLAYER);
                        }
                    }
                });
                phase.start();
            }
        }
    }


    private void Heal_BeforeAction(Character pc) {
        bottom_screen.check_Terrain(pc);
        if (pc.getEnvir_HP() > 0 && pc.gethp() < pc.getHP()) {
            display_HP(window_1, pc);
            int heal = Math.min(pc.getEnvir_HP() * pc.getHP() / 100, pc.getHP() - pc.gethp());
            pc.heal(heal);
            hp_timer = new Timer(200, new ActionListener() {
                int val = heal;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (val > 0) {
                        val--;
                        window_1.hp_plus();
                    } else {
                        val++;
                        window_1.hp_minus();
                    }
                    repaint();
                    if (val == 0) {
                        window_1.setVisible(false);
                        in_Menu = false;
                        ((Timer) e.getSource()).stop();
                        bottom_screen.AI_AffectRange(pc);
                        System.out.println("Temp NPC is " + pc.getName());
                        generate_AimSet(pc, bottom_screen.AI_AffectRange);
                        System.out.println(aim_set);
                        AI_Action(pc);
                    }
                }
            });
            hp_timer.start();
        } else {
            bottom_screen.AI_AffectRange(pc);
            System.out.println("Temp NPC is " + pc.getName());
            generate_AimSet(pc, bottom_screen.AI_AffectRange);
            System.out.println(aim_set);
            AI_Action(pc);
        }
    }

    private void Reinforce(int i) {
        System.out.println(i);
        if (ITEM.size() > i) {
            Envir_Item envir = ITEM.get(i);
            if (envir instanceof Reinforcement && chapter.map.character_map[envir.getX()][envir.getY()] == null) {
                Character reinforce = ((Reinforcement) envir).JumpOut(turn);
                if (reinforce != null) {
                    int end_x = envir.getX() - bottom_screen.left;
                    int end_y = envir.getY() - bottom_screen.top;
                    int dx = end_x - cursor.getx();
                    int dy = end_y - cursor.gety();
                    Stack<Direction> path = generatePath(dx, dy);

                    Timer timer = new Timer(50, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Direction dir = path.pop();
                            switch (dir) {
                                case UP:
                                    if (cursor.gety() >= 3 || bottom_screen.top == 0) {
                                        cursor.shift_y(-1);
                                    } else {
                                        Map_Shift(Direction.UP);
                                    }
                                    break;
                                case DOWN:
                                    if (cursor.gety() <= 4 || bottom_screen.top == bottom_screen.map_y - 8) {
                                        cursor.shift_y(1);
                                    } else {
                                        Map_Shift(Direction.DOWN);
                                    }
                                    break;
                                case LEFT:
                                    if (cursor.getx() >= 3 || bottom_screen.left == 0) {
                                        cursor.shift_x(-1);
                                    } else {
                                        Map_Shift(Direction.LEFT);
                                    }
                                    break;
                                case RIGHT:
                                    if (cursor.getx() <= 7 || bottom_screen.left == bottom_screen.map_x - 11) {
                                        cursor.shift_x(1);
                                    } else {
                                        Map_Shift(Direction.RIGHT);
                                    }
                                    break;
                            }

                            show_Info();
                            repaint();
                            if (path.isEmpty()) {
                                ((Timer) e.getSource()).stop();
                                reinforce.x = cursor.getx();
                                reinforce.y = cursor.gety();
                                ENEMY.add(reinforce);
                                chapter.map.character_map[envir.getX()][envir.getY()] = reinforce;
                                repaint();
                                Timer sub = new Timer(200, new ActionListener() {
                                    int j = 0;

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        j++;
                                        if (j >= 2) {
                                            ((Timer) e.getSource()).stop();
                                            Reinforce(i + 1);
                                        }
                                    }
                                });
                                sub.start();
                            }
                        }

                    });
                    timer.start();
                } else {
                    Reinforce(i + 1);
                }

            } else {
                Reinforce(i + 1);
            }
        } else {
            Heal_BeforePhase(0);
        }
    }

    private Character getNextNPC(Character temp) {
        if (temp == null) return null;
        int index;
        switch (temp.getCamp()) {
            case PLAYER:
                index = PLAYER.indexOf(temp);
                if (index != PLAYER.size() - 1) {
                    return PLAYER.get(index + 1);
                } else {
                    if (!ENEMY.isEmpty()) {
                        return ENEMY.get(0);
                    }
                    if (!MONSTER.isEmpty()) {
                        return MONSTER.get(0);
                    }
                    if (!PARTNER.isEmpty()) {
                        return PARTNER.get(0);
                    }
                    return null;
                }

            case ENEMY:
                index = ENEMY.indexOf(temp);
                if (index != ENEMY.size() - 1) {
                    return ENEMY.get(index + 1);
                } else {
                    if (!MONSTER.isEmpty()) {
                        return MONSTER.get(0);
                    }
                    if (!PARTNER.isEmpty()) {
                        return PARTNER.get(0);
                    }
                    return null;
                }

            case MONSTER:
                index = MONSTER.indexOf(temp);
                if (index != MONSTER.size() - 1) {
                    return MONSTER.get(index + 1);
                } else {
                    if (!PARTNER.isEmpty()) {
                        return PARTNER.get(0);
                    }
                    return null;
                }

            case PARTNER:
                index = PARTNER.indexOf(temp);
                if (index != PARTNER.size() - 1) {
                    return PARTNER.get(index + 1);
                } else {
                    return null;
                }
            default:
                return null;
        }
    }


    private void AI_move(Character c, Stack<Direction> directions, Character target) {

        if (directions.isEmpty()) {
            character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = c;

            if (target != null) {
                FacetoFace(c, target);
                if (!(c.getEquiped_Weapon() instanceof Staff)) {
                    bottom_screen.Battle(c, target);
                } else {
                    System.out.println(c.getName() + c.getEquiped_Weapon().getName());
                    AI_Staff(c, target);
                }

            } else {
                next_NPC();
            }

        } else {
            character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = null;
            Timer timer = new Timer(70, new ActionListener() {
                int step = 0;
                Direction direction;

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
                            default:
                                break;
                        }
                        c.setStep(step);
                    } else {
                        step = 0;
                        switch (direction) {
                            case DOWN:

                                if (c.y < 5 || bottom_screen.top == bottom_screen.map_y - 8) {
                                    c.y++;
                                } else {
                                    c.y++;
                                    Map_Shift(Direction.DOWN);
                                }
                                break;
                            case LEFT:

                                if (c.x > 4 || bottom_screen.left == 0) {
                                    c.x--;
                                } else {
                                    c.x--;
                                    Map_Shift(Direction.LEFT);
                                }
                                break;
                            case RIGHT:

                                if (c.x < 8 || bottom_screen.left == bottom_screen.map_x - 11) {
                                    c.x++;
                                } else {
                                    c.x++;
                                    Map_Shift(Direction.RIGHT);
                                }
                                break;
                            case UP:
                                if (c.y > 4 || bottom_screen.top == 0) {
                                    c.y--;
                                } else {
                                    c.y--;
                                    Map_Shift(Direction.UP);
                                }
                                break;
                            default:
                                break;
                        }
                        c.setDir_x(0);
                        c.setDir_y(0);
                        if (directions.isEmpty()) {
                            if (destination != null) {
                                character_map[destination.x][destination.y] = c;
                            } else {
                                character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = c;
                            }

                            ((Timer) e.getSource()).stop();

                            Timer timer = new Timer(500, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    ((Timer) e.getSource()).stop();
                                    if (target != null) {
                                        FacetoFace(c, target);
                                        if (!(c.getEquiped_Weapon() instanceof Staff)) {
                                            bottom_screen.Battle(c, target);
                                        } else {
                                            System.out.println(c.getName() + c.getEquiped_Weapon().getName());
                                            AI_Staff(c, target);
                                        }
                                    } else {
                                        next_NPC();
                                    }
                                }
                            });
                            timer.start();
                        }

                    }

                    repaint();


                }
            });

            timer.start();
        }

    }

    public void generate_AimSet(Character npc, HashSet<Node> set) {
        aim_set.clear();
        if (npc.first_Weapon() != null) {
            Character c;
            System.out.println(npc.first_Weapon().getName());
            if (npc.getEquiped_Weapon() instanceof Staff) {

                switch (((Staff) npc.getEquiped_Weapon()).getMagicEffect().getClass().getName()) {
                    case "Item.HealMagic":
                        System.out.println("heal staff check Aim");
                        for (Node n : set) {
                            c = character_map[n.x][n.y];
                            if (c != null) {
                                System.out.println(c.getName() + " in Range");
                            }
                            if (c != null && c != npc && check_Partner(npc.getCamp(), c.getCamp()) && c.gethp() < c.getHP()) {
                                aim_set.add(c);
                                System.out.println(c.getName() + "  " + c.getCamp());
                            }
                        }
                        break;
                    case "Item.UnlockMagic":

                        for (Node n : set) {
                            Envir_Item item = terrain_map[n.x + bottom_screen.left][n.y + bottom_screen.top];
                            if (item != null && item instanceof Lock && ((Lock) item).isLocked()) {
                                aim_set.add(item);
                            }
                        }
                        break;
                    case "Item.TransportMagic":
                        for (Node n : set) {
                            c = character_map[n.x + bottom_screen.left][n.y + bottom_screen.top];
                            if (c != null && check_Partner(npc.getCamp(), c.getCamp())) {
                                aim_set.add(c);
                            }
                        }
                        break;
                    case "Item.OffensiveMagic":
                        for (Node n : set) {
                            c = character_map[n.x + bottom_screen.left][n.y + bottom_screen.top];
                            if (c != null && !check_Partner(npc.getCamp(), c.getCamp())) {
                                aim_set.add(c);
                            }
                        }
                        break;
                    default:
                        break;
                }
            } else {
                //Weapons except staff can destroy obstacle.
                for (Node n : set) {
                    c = character_map[n.x][n.y];
                    if (c != null && !check_Partner(npc.getCamp(), c.getCamp())) {
                        aim_set.add(c);
                    }
                }
            }
        } else {
            aim_set.clear();
        }
    }

    protected boolean check_Partner(Camp c1, Camp c2) {
        if (c1 == c2) return true;

        switch (c1) {
            case PLAYER:
                if (c2 == Camp.PARTNER) return true;
            case PARTNER:
                if (c2 == Camp.PLAYER) return true;
            default:
                return false;
        }
    }

//    private Collection getHealableSet(Camp camp) {
//        HashSet<Character> set = new HashSet();
//        switch (camp) {
//            case ENEMY:
//                for (Character c : ENEMY) {
//                    if (c.gethp() < c.getHP()) {
//                        set.add(c);
//                    }
//                }
//                return set;
//            case PLAYER:
//                for (Character c : PLAYER) {
//                    if (c.gethp() < c.getHP()) {
//                        set.add(c);
//                    }
//                }
//                for (Character c : PARTNER) {
//                    if (c.gethp() < c.getHP()) {
//                        set.add(c);
//                    }
//                }
//                return set;
//            case PARTNER:
//                for (Character c : PARTNER) {
//                    if (c.gethp() < c.getHP()) {
//                        set.add(c);
//                    }
//                }
//                for (Character c : PLAYER) {
//                    if (c.gethp() < c.getHP()) {
//                        set.add(c);
//                    }
//                }
//                return set;
//            case MONSTER:
//                for (Character c : MONSTER) {
//                    if (c.gethp() < c.getHP()) {
//                        set.add(c);
//                    }
//                }
//                return set;
//            default:
//                return null;
//
//        }
//    }


    //Destination can be character, Chest or throne etc.
    protected Node destination;

    private void AI_Action(Character npc) {
        switch (npc.getAi()) {
            case Neutral:

                if (!aim_set.isEmpty()) {
                    //calculate for suitable weapon and suitable aim.
                    if (npc.first_Weapon() != null) {
                        generate_Action(npc, new HashSet<Character>(aim_set));
                    } else {
                        next_NPC();
                    }
                } else {
                    next_NPC();
                }
                break;
            case Positive:
                //When opposite characters in Affect Range, positive characters act similar to neutral characters.
                if (!aim_set.isEmpty()) {
                    if (npc.first_Weapon() != null) {
                        generate_Action(npc, new HashSet<Character>(aim_set));
                    } else {
                        next_NPC();
                    }
                } else {
                    AI_move(npc, bottom_screen.AI_Positive_pathFinder(npc), null);
                }
                break;
            case Negative:
                //Never move.
                if (!aim_set.isEmpty()) {
                    if (npc.first_Weapon() != null) {
                        generate_Action(npc, new HashSet<Character>(aim_set));
                    } else {
                        next_NPC();
                    }
                } else {
                    next_NPC();
                }
                break;
        }
    }

    private void generate_Action(Character c, HashSet<Character> set) {
        Character target = select_Aim(c, set);
        move_N_Action(c, destination, target);
    }



    //Priority: weapon priority + no risk > weapon priority > no risk > search for the weakest one(lowest lv)
    private Character select_Aim(Character attacker, HashSet set) {
        destination = null;
        if (!(attacker.getEquiped_Weapon() instanceof Staff)) {
            for (Object o : set) {
                if (o instanceof Character) {
                    ((Character) o).first_Weapon();
                    for (Weapon weapon : attacker.getWeapons()) {
                        if (weapon.isDominant(((Character) o).getEquiped_Weapon()) && compare_Range(weapon, (Character) o)) {
                            //check position
                            for (Node n : generate_SafePosition(weapon, (Character) o)) {
                                if (bottom_screen.AI_MoveRange.contains(n) && check_Blank(attacker, n)) {
                                    destination = n;
                                    attacker.equip(weapon);
                                    return (Character) o;
                                }
                            }

                        }
                    }
                }
            }
            for (Object o : set) {
                if (o instanceof Character) {
                    for (Weapon weapon : attacker.getWeapons()) {
                        if (weapon.isDominant(((Character) o).getEquiped_Weapon())) {
                            for (Node n : generate_AttackPosition(weapon, (Character) o)) {
                                if (bottom_screen.AI_MoveRange.contains(n) && check_Blank(attacker, n)) {
                                    destination = n;
                                    attacker.equip(weapon);
                                    return (Character) o;
                                }
                            }
                        }
                    }
                }
            }
            for (Object o : set) {
                if (o instanceof Character) {
                    for (Weapon weapon : attacker.getWeapons()) {
                        if (compare_Range(weapon, (Character) o)) {
                            for (Node n : generate_SafePosition(weapon, (Character) o)) {
                                if (bottom_screen.AI_MoveRange.contains(n) && check_Blank(attacker, n)) {
                                    destination = n;
                                    attacker.equip(weapon);
                                    return (Character) o;
                                }
                            }
                        }
                    }
                }
            }
            //No suitable aim but still aim_set is not empty, so select the weakest one.

            return getWeakest(attacker, set);

        } else {
            Character aim = null;
            Staff staff = (Staff) attacker.getEquiped_Weapon();
            switch (staff.getMagicEffect().getClass().getName()) {
                case "Item.HealMagic":
                    int hp = 61;
                    for (Object o : set) {
                        if (((Character) o).gethp() < hp) {
                            for (Node n : generate_AttackPosition(staff, (Character) o)) {
                                if (bottom_screen.AI_MoveRange.contains(n) && check_Blank(attacker, n)) {
                                    destination = n;
                                    hp = ((Character) o).gethp();
                                    aim = (Character) o;
                                }
                            }
                        }
                    }
                    System.out.println("temp aim is " + aim.getName());
                    return aim;
                case "Item.OffensiveMagic":
                    int Hit = 0;
                    for (Object o : set) {
                        int hit = bottom_screen.calculate_StaffHit((Staff) attacker.getEquiped_Weapon(), attacker, (Character) o);
                        if (hit > Hit) {
                            Hit = hit;
                            aim = (Character) o;
                        }
                    }
                    return aim;
                default:
                    return null;
            }
        }

    }

    private Character getWeakest(Character attacker, HashSet set) {
        int lv = 21;

        Character aim = null;
        for (Object o : set) {
            if (o instanceof Character) {
                if (((Character) o).getLv() < lv) {
                    lv = ((Character) o).getLv();
                    aim = (Character) o;
                }
            }
        }

        for (Node n : generate_AttackPosition(attacker.getEquiped_Weapon(), aim)) {
            if (bottom_screen.AI_MoveRange.contains(n) && check_Blank(attacker, n)) {
                destination = n;
                System.out.println("destination:" + destination.x + destination.y);
                System.out.println("return aim " + aim.getName());
                return aim;
            }
        }
        if (set.size() == 1) {
            System.out.println(set);
            int distance = 100;
            int dx, dy;
            for (Node n : bottom_screen.AI_MoveRange) {
                dx = Math.abs(n.x - bottom_screen.left - aim.x);
                dy = Math.abs(n.y - bottom_screen.top - aim.y);
                if (dx + dy < distance && character_map[n.x][n.y] == null) {
                    distance = dx + dy;
                    destination = n;
                }
            }
            //There is one target in character's AffectRange, but all action positions are occupied already.
            return null;
        } else {
            set.remove(aim);
            return getWeakest(attacker, set);
        }
    }


    private boolean compare_Range(Character attacker, Character target) {
        if (target.first_Weapon() == null || target.getEquiped_Weapon() instanceof Staff) {
            return true;
        }
        for (Item item : attacker.getBag()) {
            if (item instanceof Weapon && attacker.can_Equip(item)) {
                if (((Weapon) item).getRange_1() < target.getEquiped_Weapon().getRange_1()
                        || ((Weapon) item).getRange_2() > target.getEquiped_Weapon().getRange_2()) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean compare_Range(Weapon weapon, Character target) {
        target.first_Weapon();

        if (target.first_Weapon() == null || target.getEquiped_Weapon() instanceof Staff) {
            return true;
        }

        if (weapon.getRange_1() < target.getEquiped_Weapon().getRange_1() || weapon.getRange_2() > target.getEquiped_Weapon().getRange_2()) {
            return true;
        }
        return false;
    }

    private boolean check_Blank(Character npc, Node node) {
        if (character_map[node.x][node.y] == npc) {
            return true;
        } else {
            if (character_map[node.x][node.y] != null) {
                return false;
            }
            if (bottom_screen.move_map[node.x][node.y] <= 0) {
                return false;
            }
            return true;
        }
    }


    private HashSet<Node> generate_SafePosition(Weapon weapon, Character target) {
        HashSet<Node> positions = new HashSet<>();
        int range_1, range_2;
        if (target.getEquiped_Weapon() == null || target.getEquiped_Weapon() instanceof Staff) {
            //target cannot attack
            range_1 = 0;
            range_2 = 0;
        } else {
            range_1 = target.getEquiped_Weapon().getRange_1();
            range_2 = target.getEquiped_Weapon().getRange_2();
        }

        int x, y;
        int X, Y;
        for (int distance = weapon.getRange_1(); distance <= weapon.getRange_2(); distance++) {
            if (distance < range_1 || distance > range_2) {
                for (x = 0; x <= distance; x++) {
                    y = distance - x;
                    X = target.x + bottom_screen.left;
                    Y = target.y + bottom_screen.top;
                    if (check_InMap(X + x, Y + y)) {
                        positions.add(new Node(X + x, Y + y));
                    }
                    if (check_InMap(X - x, Y + y)) {
                        positions.add(new Node(X - x, Y + y));
                    }
                    if (check_InMap(X + x, Y - y)) {
                        positions.add(new Node(X + x, Y - y));
                    }
                    if (check_InMap(X - x, Y - y)) {
                        positions.add(new Node(X - x, Y - y));
                    }

                }
            }
        }
        return positions;
    }

    private HashSet<Node> generate_AttackPosition(Weapon weapon, Character target) {
        HashSet<Node> positions = new HashSet<>();
        int x, y;
        int X, Y;
        X = target.x + bottom_screen.left;
        Y = target.y + bottom_screen.top;
        for (int distance = weapon.getRange_1(); distance <= weapon.getRange_2(); distance++) {

            for (x = 0; x <= distance; x++) {
                y = distance - x;
                if (check_InMap(X + x, Y + y)) {
                    positions.add(new Node(X + x, Y + y));
                }
                if (check_InMap(X - x, Y + y)) {
                    positions.add(new Node(X - x, Y + y));
                }
                if (check_InMap(X + x, Y - y)) {
                    positions.add(new Node(X + x, Y - y));
                }
                if (check_InMap(X - x, Y - y)) {
                    positions.add(new Node(X - x, Y - y));
                }

            }

        }
        return positions;
    }

    private boolean check_InMap(int X, int Y) {
        if (X >= 0 && X < bottom_screen.map_x && Y >= 0 && Y < bottom_screen.map_y) {
            return true;
        }
        return false;
    }

    private void move_N_Action(Character npc, Node destination, Character target) {

        if (destination == null) destination = new Node(npc.x + bottom_screen.left, npc.y + bottom_screen.top);
        AI_move(npc, bottom_screen.pathFinder(npc, destination.x - bottom_screen.left, destination.y - bottom_screen.top), target);
    }

    private void AI_Staff(Character user, Character target) {

        switch (((Staff) user.getEquiped_Weapon()).getMagicEffect().getClass().getName()) {
            case "Item.HealMagic":
                display_Heal(window_1, user, target, (Staff) user.getEquiped_Weapon());
                break;
            default:
                break;
        }
    }


    private void run_Chapter() {
        bottom_screen.setIn_Preparation(false);
        for (Character pc : PARTNER) {
            if (pc.getCamp() == Camp.PLAYER) {
                PLAYER.add(pc);
            }
        }
        for (Character pc : PLAYER) {
            if (PARTNER.contains(pc) && pc.getCamp() == Camp.PLAYER) {
                PARTNER.remove(pc);
            }
        }
        Charge(PLAYER);
        Charge(ENEMY);
        Charge(PARTNER);
        Charge(MONSTER);
        Cursor_Fold(bottom_screen.left + PLAYER.get(0).x, bottom_screen.top + PLAYER.get(0).y);
        cursor.setAim(PLAYER.get(0));
        top_screen.setCharacter(PLAYER.get(0));

        for (Character en : ENEMY) {
            bottom_screen.check_Terrain(en);
        }
        for (Character en : MONSTER) {
            bottom_screen.check_Terrain(en);
        }
        for (Character pc : PARTNER) {
            bottom_screen.check_Terrain(pc);
        }
        turn = 1;
        addKeyListener(main_Listener);

    }

    private void remove_Character(Character c) {
        character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = null;
    }

    private void setPosition(Character c, int X, int Y) {
        character_map[c.x + bottom_screen.left][c.y + bottom_screen.top] = null;
        character_map[X][Y] = c;

    }


    private void Charge(ArrayList<Character> list) {
        for (Character pc : list) {
            pc.setMovepoint(true);
        }
    }

    public void Discharge(ArrayList<Character> list) {
        for (Character pc : list) {
            pc.setMovepoint(false);
        }
    }

    //buy items from shop;
    private void buy() {
        if (asset >= S_I.getPirce()) {
            Item item = S_I.buy();
            asset -= item.getPrice();
            S_I.setAsset(asset);
        } else {
            System.out.println("cannot afford");
        }
    }

    public void sale() {
        Item item = S_I.sale();
        if (item instanceof Consumables) {
            asset += item.getPrice() * ((Consumables) item).getUses() / ((Consumables) item).getUSES();
        } else {
            asset += item.getPrice();
        }
        S_I.setAsset(asset);
    }

//    public void display_Dialogue(LinkedList<String> texts, Character c1, Character c2) {
//        System.out.println(texts.size());
//        in_Menu = true;
//        bottom_screen.z_mark = 1;
//        dialog.setVisible(true);
//        dialog.setText(texts.poll());
//        dialog.setC1(c1);
//        dialog.setC2(c2);
//        dialog.setIn_conversation(true);
//        addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//
//                switch (e.getKeyCode()) {
//                    case KeyEvent.VK_Z:
//                        if (!texts.isEmpty()) {
//                            dialog.setText(texts.poll());
//                        } else {
//                            removeKeyListener(this);
//                            dialog.setVisible(false);
//                            in_Menu = false;
//                            bottom_screen.z_mark = 0;
//                            dialog.clean();
//                        }
//                        break;
//                    case KeyEvent.VK_X:
//                        display_Dialogue(texts, c1, c2);
//                        break;
//                    case KeyEvent.VK_ENTER:
//                        removeKeyListener(this);
//                        dialog.setVisible(false);
//                        in_Menu = false;
//                        bottom_screen.z_mark = 0;
//                        dialog.clean();
//                        break;
//                    default:
//                        break;
//                }
//                dialog.repaint();
//
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//
//            }
//        });
//    }


//    public void Dialog_Test() {
//        String[] strings = {
//                "Yhorm, old friend.",
//                "I, Siegward of the Knights of Catarina, have come to uphold my promise!",
//                "Let the sun shine upon this Lord of Cinder."
//        };
//        LinkedList<String> str = new LinkedList<String>(List.of(strings));
//        display_Dialogue(str, PLAYER.get(0), null);
//    }


    private void endAndCheckVictory(Character c) {
        c.setMovepoint(false);
        if (is_Victory()) {
            Chapter_Complete();
        }
    }

    protected void Chapter_Complete() {
        turn = 1;
        othersTurn = false;
        removeKeyListener(main_Listener);
        bottom_screen.setVisible(false);
        top_screen.setVisible(false);
        bottom_screen.left = 0;
        bottom_screen.top = 0;
        cursor.reset();
        for (Character pc : chapter.PARTNER) {
            pc.setDir(0);
            pc.sethp(pc.getHP());
        }
        Market.addAll(chapter.PARTNER);
        chapter.PARTNER.clear();
        for (Character pl : PLAYER) {
            pl.setDir(0);
            pl.sethp(pl.getHP());
            if (!ALL_PLAYER.contains(pl)) {
                ALL_PLAYER.add(pl);
            }
        }
        PLAYER.clear();
        System.out.println("Chapter Complete");
        if (save.chapters.indexOf(chapter) == save.chapters.size() - 1 && this.chapters.size() > save.chapters.size()) {
            save.chapters.add(chapters.get(save.chapters.size()));
        }
        select_Chapter(save);
    }

    public void save_Record() {
        try {
            int no = saves.indexOf(save) + 1;
            File file = new File(System.getProperty("user.dir") + "/src/main/resources/SAVE/save_" + no + ".text");
            FileWriter writer = new FileWriter(file);
            System.out.println("Saved");
            writer.write(gson.toJson(save));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Gson gson = new GsonBuilder().registerTypeAdapter(SAVE.class, new SaveTypeAdapter()).create();

    private void check_Saves() {
        try {
            File save;
            for (int i = 1; i < 4; i++) {
                String address = System.getProperty("user.dir") + "/src/main/resources/SAVE/save_" + i + ".text";
                save = new File(address);
                if (save.exists()) {
                    String str;
                    StringBuilder builder = new StringBuilder();
                    FileInputStream in = new FileInputStream(save);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    str = reader.readLine();
                    while (str != null) {
                        builder.append(str);
                        str = reader.readLine();
                    }
                    reader.close();
                    in.close();
                    saves.add(gson.fromJson(builder.toString(), SAVE.class));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String profile = System.getProperty("user.dir") + "/src/main/resources/Characters/Profile";
    String character = System.getProperty("user.dir") + "/src/main/resources/Characters/Character";

    private void generate_OriginalPlayer() {
        try {
            WeaponLevel[] lv1 = {WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.D, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA};
            Character player_1 = new CharacterBuilder("Joe", Gender.Male).camp(Camp.PLAYER).lv(2).exp(0).job(Job.Fighter).weapon_ability(lv1)
                    .para(17, 6, 0, 5, 6, 8, 4, 0)
                    .profile(profile + "/Fighter_1.png").character_image(character + "/Fighter1.png")
                    .x(0).y(0).build();
            int[] growth_1 = {80, 60, 0, 45, 45, 25, 50, 20};
            player_1.setGrowth(growth_1);
            WeaponLevel[] lv2 = {WeaponLevel.D, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA, WeaponLevel.NA};
            Character player_2 = new CharacterBuilder("May", Gender.Female).camp(Camp.PLAYER).lv(2).exp(0).job(Job.Mercenary).weapon_ability(lv2)
                    .para(16, 5, 0, 8, 10, 5, 3, 0)
                    .profile(profile + "/Fighter_2.png").character_image(character + "/Fighter2.png")
                    .x(0).y(0).build();
            int[] growth_2 = {70, 50, 0, 60, 60, 55, 30, 30};
            player_2.setGrowth(growth_2);

            player_1.gainItem(Weapon.Trainer(Axe.class));
            player_2.gainItem(Weapon.Trainer(Sword.class));
            ALL_PLAYER.add(player_1);
            ALL_PLAYER.add(player_2);
            for (Character pc : ALL_PLAYER) {
                pc.first_Weapon();
            }

            Character mercenary_1 = new CharacterBuilder("Ike", Gender.Male).camp(Camp.PARTNER).lv(1).exp(0).job(Job.Thief)
                    .para(17, 4, 0, 4, 12, 8, 3, 1)
                    .profile(profile + "/Thief.png").character_image(character + "/Thief.png")
                    .x(0).y(0).build();
            mercenary_1.setWant(Weapon.Trainer(Sword.class));
            int[] growth = {75, 40, 0, 40, 65, 45, 25, 20};
            mercenary_1.setGrowth(growth);

            Character mage = new CharacterBuilder("Cyrus", Gender.Male).camp(Camp.PARTNER).lv(3).exp(0).job(Job.Mage).
                    para(19, 1, 7, 7, 8, 4, 3, 5).profile(profile + "/Scholar.png").character_image(character + "/Scholar.png").x(0).y(0).build();
            int[] growth_4 = {50, 0, 70, 30, 55, 55, 20, 45};
            mage.setGrowth(growth_4);
            mage.setWeapon_ability(4, WeaponLevel.E);
            mage.setWeapon_ability(6, WeaponLevel.E);
            mage.gainItem(Weapon.Magic(WeaponType.Fire, WeaponLevel.E));
            mage.setWant(Weapon.Magic(WeaponType.Fire, WeaponLevel.C));

            Market.add(mercenary_1);
            Market.add(mage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean is_Victory() {
        switch (chapter.objective) {
            case Rout:
                if (ENEMY.isEmpty()) {
                    return true;
                } else {
                    return false;
                }

            case Defeat:
                if (!ENEMY.contains(this.Boss)) {
                    return true;
                } else {
                    return false;
                }

            case Survive:
                if (this.turn >= chapter.turn_limit) {
                    for (Character pc : chapter.aim_list) {
                        if (!PLAYER.contains(pc)) {
                            return false;
                        }
                    }
                    return true;
                } else {
                    return false;
                }
            case Escape:
                return chapter.isEscaped();
            default:
                return false;
        }
    }

    private void Cursor_Fold(int X, int Y) {

        if (X >= 0 && X < bottom_screen.map_x && Y >= 0 && Y < bottom_screen.map_y) {
            int original_x = bottom_screen.left;
            int original_y = bottom_screen.top;
            if (X < 5) {
                bottom_screen.left = 0;
                cursor.setx(X);
            } else if (X <= bottom_screen.map_x - 6) {
                bottom_screen.left = X - 5;
                cursor.setx(5);
            } else {
                bottom_screen.left = bottom_screen.map_x - 11;
                cursor.setx(X + 11 - bottom_screen.map_x);
            }
            if (Y < 4) {
                bottom_screen.top = 0;
                cursor.sety(Y);
            } else if (Y <= bottom_screen.map_y - 5) {
                bottom_screen.top = Y - 4;
                cursor.sety(4);
            } else {
                bottom_screen.top = bottom_screen.map_y - 8;
                cursor.sety(Y + 8 - bottom_screen.map_y);
            }

            int dx = bottom_screen.left - original_x, dy = bottom_screen.top - original_y;

            for (Character pc : PLAYER) {
                pc.x -= dx;
                pc.y -= dy;
            }
            for (Character pc : PARTNER) {
                pc.x -= dx;
                pc.y -= dy;
            }
            for (Character pc : ENEMY) {
                pc.x -= dx;
                pc.y -= dy;
            }
            for (Character pc : MONSTER) {
                pc.x -= dx;
                pc.y -= dy;
            }
            repaint();

        }
    }
}
