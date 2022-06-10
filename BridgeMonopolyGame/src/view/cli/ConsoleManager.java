package view.cli;

import model.domain.cell.BridgeCell;
import model.domain.cell.Cell;
import model.domain.cell.ItemCell;
import model.domain.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleManager {

    private final static Scanner SCANNER = new Scanner(System.in);

    public static void printSplash() {
        System.out.println("\n" +
                " _       __     ____                             __                                                           \n" +
                "| |     / /__  / / /________  ____ ___  ___     / /_____                                                      \n" +
                "| | /| / / _ \\/ / / ___/ __ \\/ __ `__ \\/ _ \\   / __/ __ \\                                                     \n" +
                "| |/ |/ /  __/ / / /__/ /_/ / / / / / /  __/  / /_/ /_/ /                                                     \n" +
                "|__/|__/\\___/_/_/\\___/\\____/_/ /_/ /_/\\___/_  \\__/\\____/                  __         ______                   \n" +
                "   / __ )_____(_)___/ /___ ____     /  |/  /___  ____  ____  ____  ____  / /_  __   / ____/___ _____ ___  ___ \n" +
                "  / __  / ___/ / __  / __ `/ _ \\   / /|_/ / __ \\/ __ \\/ __ \\/ __ \\/ __ \\/ / / / /  / / __/ __ `/ __ `__ \\/ _ \\\n" +
                " / /_/ / /  / / /_/ / /_/ /  __/  / /  / / /_/ / / / / /_/ / /_/ / /_/ / / /_/ /  / /_/ / /_/ / / / / / /  __/\n" +
                "/_____/_/  /_/\\__,_/\\__, /\\___/  /_/  /_/\\____/_/ /_/\\____/ .___/\\____/_/\\__, /   \\____/\\__,_/_/ /_/ /_/\\___/ \n" +
                "                   /____/                                /_/            /____/                                \n");
    }

    public static void printMap(@NotNull Cell[][] map, @NotNull ArrayList<Player> players) {

        for (int y = 0; y < map.length; y++) {
            String line1 = "";
            String line2 = "";
            String line3 = "";

            for (int x = 0; x < map[y].length; x++) {
                Cell cell = map[y][x];
                ArrayList<Integer> pId = new ArrayList<>();

                // empty space
                if (cell == null) {
                    line1 += "   ";
                    line2 += "   ";
                    line3 += "   ";
                }
                // bridge
                else if (cell instanceof BridgeCell && ((BridgeCell) cell).getBridgeType() == BridgeCell.BridgeType.BRIDGE) {
                    line1 += "===";
                    line2 += "|||";
                    line3 += "===";
                }
                // cell
                else {
                    // Find the player that is above the cell
                    for (Player p : players) {
                        if (p.getPiecePosition() == cell)
                            pId.add(p.getId());
                    }

                    String pPos[] = {"─", "│", "│", "─"};
                    for (int i = 0; i < pId.size(); i++) {
                        pPos[i] = "" + pId.get(i);
                    }

                    line1 += "┌" + pPos[0] + "┐";
                    line2 += pPos[1] + getCellName(cell) + pPos[2];
                    line3 += "└" + pPos[3] + "┘";

                }

            }
            System.out.println(line1+"\n"+line2+"\n"+line3);
        }
    }

    public static void printPlayerStatus(@NotNull ArrayList<Player> players) {
        String line1 = "";
        String line2 = "";
        String line3 = "";

        for (Player p : players) {
            line1 += "Player " + p.getId() + "\t\t";
            line2 += "score : " + p.getPoint() + "\t\t";
            line3 += "penalty : " + p.getPenalty() + "\t\t";
        }

        System.out.println(line1+"\n"+line2+"\n"+line3);
    }

    public static void clear() {
        try {
            String os = System.getProperty("os.name");

            ProcessBuilder pb;
            if (os.contains("Windows")) {
                pb = new ProcessBuilder("cmd", "/c", "cls");
            } else {
                pb = new ProcessBuilder("clear");
            }

            Process startProcess = pb.inheritIO().start();
            startProcess.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printLine() {
        System.out.println("───────────────────────────────────────────────────────────────────────────────────");
    }

    public static String nextLine() {
        return SCANNER.nextLine();
    }

    public static String next() {
        return SCANNER.next();
    }

    public static boolean hasNextInt() {
        return SCANNER.hasNextInt();
    }

    public static int nextInt() {
        return SCANNER.nextInt();
    }


    // Find cell name
    private static String getCellName(@NotNull Cell cell) {
        if (cell instanceof ItemCell) {
            switch (((ItemCell) cell).getItemType()) {
                case START:
                    return "S";
                case END:
                    return "E";
                case EMPTY:
                    return " ";
                case HAMMER:
                    return "h";
                case SAW:
                    return "s";
                case PHILIPS_DRIVER:
                    return "p";
            }
        } else if (cell instanceof BridgeCell) {
            switch (((BridgeCell) cell).getBridgeType()) {
                case START:
                    return "B";
                case END:
                    return " ";
            }
        }
        return "";
    }
}
