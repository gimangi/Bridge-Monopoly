package controller.cli;

import model.data.Direction;
import model.domain.player.Player;
import model.domain.rule.BridgeMonopolyGame;
import view.cli.ConsoleManager;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CLIGameController extends BridgeMonopolyGame {

    private final Scanner sc = new Scanner(System.in);

    @Override
    protected Callable<Boolean> selectUseDefaultMap() {
        Callable<Boolean> call = () -> {
            System.out.println("맵 파일을 불러올 방법을 선택해주세요.\n맵 파일을 직접 선택 : 1 / 기본 맵을 사용 : 2");
            int answer;
            while (true) {
                answer = scanInteger();
                if (answer == 1)
                    return true;
                else if (answer == 2)
                    return false;
            }
        };
        return call;
    }

    @Override
    protected Callable<String> enterMapFile() {
        Callable<String> call = () -> {
            System.out.println("사용할 맵 파일의 이름을 입력해주세요.");
            return sc.next();
        };
        return call;
    }

    @Override
    protected void displayNotFoundMap() {
        System.out.println("파일이 존재하지 않습니다.");
    }

    @Override
    protected Callable<Integer> enterNumberOfPlayers() {
        Callable<Integer> call = () -> {
            System.out.println("플레이어 수를 입력해주세요. (2 ~ 4)");
            int res;
            while (true) {
                res = scanInteger();
                if (2 <= res && res <= 4)
                    return res;
                else
                    System.out.println("플레이어 수는 2 ~ 4의 범위에서 선택할 수 있습니다.");
            }
        };
        return call;
    }

    @Override
    protected void initDisplay() {
        // Displays a message for 2 seconds.
        ConsoleManager.printSplash();

        try {
            Thread.sleep(2000);
            ConsoleManager.clear();

        } catch (InterruptedException e) {

        }
    }

    @Override
    protected void refresh() {
        if (!turn.getAllowMoveBack())
            System.out.println("이제부터 앞으로만 이동할 수 있습니다.");
        ConsoleManager.printMap(board.getAbsoluteMap(), turn.getPlayerList());
        ConsoleManager.printLine();
        System.out.println("Player " + turn.getTurnOwner().getId() + " 님의 차례입니다. ");
        ConsoleManager.printPlayerStatus(turn.getPlayerList());
        ConsoleManager.printLine();
    }

    @Override
    protected Callable<Boolean> selectStay(int playerId) {
        Callable<Boolean> call = () -> {
            System.out.println("턴을 쉬어갈지, 주사위를 굴릴지 선택하세요.\n쉬어가기 : 1, 주사위 굴리기 : 2");

            while (true) {
                int answer = scanInteger();
                if (answer == 1)
                    return true;
                else if (answer == 2)
                    return false;
                else
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        };
        return call;
    }

    @Override
    protected Callable<Integer> rollDice() {
        Callable<Integer> call = () -> {
            Random random = new Random();
            random.setSeed(System.currentTimeMillis());
            int diceResult = random.nextInt(6) + 1;

            int penalty = turn.getTurnOwner().getPenalty();
            System.out.println("주사위 결과 : " + diceResult + ", 패널티 : " + penalty + ", 이동 가능 눈금 : " + (diceResult - penalty));
            return diceResult;
        };
        return call;
    }

    @Override
    protected void displayMoveValueZero(int diceResult, int penalty, int deduct) {
        System.out.println("남은 눈금이 0이하입니다. (주사위 결과 : " + diceResult + ", 패널티 : " + penalty + ", 사용한 눈금 : " + deduct + ")");
        System.out.println("다음 턴을 진행하려면 아무키나 입력해주세요.");
        sc.next();
    }

    @Override
    protected Callable<ArrayList<Direction>> enterDirection(int diceResult, int penalty, int deduct) {
        System.out.println("이동할 방향을 공백없이 순서대로 입력하세요. (위 : U, 아래 : D, 왼쪽 : L, 오른쪽 : R)");
        Callable<ArrayList<Direction>> call = () -> {
            while (true) {

                ArrayList<Direction> dirs = new ArrayList<>();
                boolean success = true;
                String input = sc.next();

                for (int i = 0; i < input.length(); i++) {
                    switch (input.charAt(i)) {
                        case 'U':
                            dirs.add(Direction.UP);
                            break;
                        case 'D':
                            dirs.add(Direction.DOWN);
                            break;
                        case 'L':
                            dirs.add(Direction.LEFT);
                            break;
                        case 'R':
                            dirs.add(Direction.RIGHT);
                            break;
                        default:
                            success = false;
                    }
                }

                if (success)
                    return dirs;
                else
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
            }
        };

        return call;
    }

    @Override
    protected Callable<Boolean> selectMoveBridge(int diceResult, int penalty, int deduct) {
        Callable<Boolean> call = () -> {
            System.out.println("다리로 이동하시겠습니까? (penalty가 증가합니다) \n다리로 이동 : 1, 그냥 진행 : 2");
            while (true) {
                int answer = scanInteger();
                if (answer == 1)
                    return true;
                else if (answer == 2)
                    return false;
            }
        };
        return call;
    }

    @Override
    protected void alertInvalidMove() {
        System.out.println("해당 방향으로 이동할 수 없습니다.");
    }

    @Override
    protected void displayCanNotMoveBack() {
        // TO NOTHING
    }

    @Override
    protected void displayWinner() {
        Player winner = turn.getWinner();
        System.out.println("게임이 종료되었습니다.");
        System.out.println("승리 : Player" + winner.getId());
        System.out.println("획득한 점수 : " + winner.getPoint());
    }

    private int scanInteger() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
        }
        return sc.nextInt();
    }
}
