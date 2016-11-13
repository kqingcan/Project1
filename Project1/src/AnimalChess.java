import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by kongqingcan on 2016/11/13.
 */
public class AnimalChess {   private static int compare(int a, int b) {
    if (a > b) return 1;
    if (a < b) return -1;
    if (a == b) return 0;
    return 0;
}

    private static void printMap(String[][] map) {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(map[i][j] + "");
            }
            System.out.println();
        }
    }
    //  下面是动物左右移动时的方法
    public static boolean admove(int[] laststep, int[] currentstep, boolean player, String[][] map, String[][] tile, char number, int[] thisstep, int[][] site, int nextstep, int nextstep1) {
        //判断是否走出边界
        if (nextstep < 0 || nextstep > 8) {
            System.out.println("不能走出边界，请重新输入");
            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
            player = !player;
        }
        else {
            //前方是是空地直接走
            if (map[thisstep[0]][nextstep] == " 　 ") {
                map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                thisstep[1] = nextstep;
                printMap(map);
                System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                currentstep[0]++;
                laststep[0] = 0;
            }
            else {
                if (map[thisstep[0]][nextstep] != " 　 ") {
                    //判断在陆地上下一步是否是同类
                    if (map[thisstep[0]][nextstep].charAt((player) ? 0 : 2) != ' ' && tile[thisstep[0]][nextstep] != " 水 ") {
                        System.out.println("不能吃己方棋子，请重新输入");
                        System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                        player = !player;
                    }
                    else if (map[thisstep[0]][nextstep].charAt((player) ? 2 : 0) != ' ' && tile[thisstep[0]][nextstep] != " 水 ") {
                        //实现水中老鼠不能吃岸上大象
                        if (number == '1' && map[thisstep[0]][nextstep].charAt((player) ? 2 : 0) == '8') {
                            if (tile[thisstep[0]][thisstep[1]] == " 水 ") {
                                System.out.println("水中老鼠不能吃岸上大象，请重新输入");
                                System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                player = !player;
                            }
                            //实现鼠在陆地上可以吃大象
                            else {
                                map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                thisstep[1] = nextstep;
                                printMap(map);
                                System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                currentstep[0]++;
                                laststep[0] = 0;
                            }
                        }
                        //实现正常时大象不能吃鼠
                        else if (number == '8' && map[thisstep[0]][nextstep].charAt((player) ? 2 : 0) == '1') {
                            System.out.println("不能吃比自己高等的动物，请重新输入");
                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                            player = !player;
                        }
                        else {
                            //正常通过比较大小判断能否互吃
                            int d = compare((int) number, (int) map[thisstep[0]][nextstep].charAt((player) ? 2 : 0));
                            if (d == 1 || d == 0) {
                                map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                thisstep[1] = nextstep;
                                printMap(map);
                                System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                currentstep[0]++;
                                laststep[0] = 0;
                            }
                            if (d == -1) {
                                if (tile[thisstep[0]][nextstep] == " 陷 "&& (nextstep == ((player)? 0:8)||nextstep ==((player)? 1:7))) {
                                    map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[1] = nextstep;
                                    printMap(map);
                                    System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                    currentstep[0]++;
                                    laststep[0] = 0;
                                }
                                else {
                                    System.out.println("不能吃比自己高等的动物，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                            }
                        }
                    }
                    //下面是玩家下一步地形地图不是空地时的情况
                    else if (((map[thisstep[0]][nextstep].charAt(2) == ' ') && (map[thisstep[0]][nextstep].charAt(0) == ' ')) || tile[thisstep[0]][nextstep] == " 水 ") {
                        switch (tile[thisstep[0]][nextstep].charAt(1)) {
                            case '水':
                                //鼠可以下河
                                if (number == '1') {
                                    map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[1] = nextstep;
                                    printMap(map);
                                    System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                    currentstep[0]++;
                                    laststep[0] = 0;
                                }
                                else if (number == '6' || number == '7') {
                                    //狮虎左右跳河，和是否有老鼠阻挡的判断
                                    if ((((player) ? site[8][0] : site[0][0]) == thisstep[0]) && ((player) ? site[8][1] : site[0][1]) > 2 && ((player) ? site[8][1] : site[0][1]) <=5) {
                                        System.out.println("前方有老鼠阻挡，不能跳过小河，请重新输入");
                                        System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                        player = !player;
                                    }
                                    else {
                                        if (map[thisstep[0]][nextstep1] == " 　 ") {
                                            map[thisstep[0]][nextstep1] = map[thisstep[0]][thisstep[1]];
                                            map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                            thisstep[1] = nextstep1;
                                            printMap(map);
                                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                            currentstep[0]++;
                                            laststep[0] = 0;
                                        }
                                        else {
                                            if (map[thisstep[0]][nextstep1] != " 　 ") {
                                                //判断狮虎跳河时要跳的位置是否有动物，己方动物不能吃，可以吃敌方比自己小的或同样的动物
                                                if (map[thisstep[0]][nextstep1].charAt((player) ? 0 : 2) != ' ') {
                                                    System.out.println("不能吃己方棋子，请重新输入");
                                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                                    player = !player;
                                                }
                                                else if (map[thisstep[0]][nextstep1].charAt((player) ? 2 : 0) != ' ') {
                                                    int d = compare((int) number, (int) map[thisstep[0]][nextstep1].charAt((player) ? 2 : 0));
                                                    if (d == 1 || d == 0) {
                                                        map[thisstep[0]][nextstep1] = map[thisstep[0]][thisstep[1]];
                                                        map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                                        thisstep[1] = nextstep1;
                                                        printMap(map);
                                                        System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                                        currentstep[0]++;
                                                        laststep[0] = 0;
                                                    }
                                                    if (d == -1) {
                                                        if (tile[thisstep[0]][nextstep1] == " 陷 " && (nextstep == ((player)? 0:8)||nextstep ==((player)? 1:7))){
                                                            map[thisstep[0]][nextstep1] = map[thisstep[0]][thisstep[1]];
                                                            map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                                            thisstep[1] = nextstep1;
                                                            printMap(map);
                                                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                                            currentstep[0]++;
                                                            laststep[0] = 0;
                                                        }
                                                        else {
                                                            System.out.println("不能吃比自己高等的动物，请重新输入");
                                                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                                            player = !player;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    System.out.println("该动物不能下水，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                                break;
                            case '家':
                                //限制己方动物不能进入自己家，敌方动物进入自己家就胜利的胜利条件
                                if (thisstep[0] == 3 && nextstep == ((player) ? 0 : 8)) {
                                    System.out.println("己方动物不能进入自己家，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                                else {
                                    map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[1] = nextstep;
                                    printMap(map);
                                    System.out.println("恭喜" + ((player) ? '左' : '右') + "方获得胜利");
                                    System.exit(0);
                                }
                                break;
                            case '陷':
                                //实现陷阱里的动物最小
                                if (map[thisstep[0]][nextstep].charAt((player) ? 0 : 2) != ' ') {
                                    System.out.println("不能吃己方棋子，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                                else {
                                    map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[1] = nextstep;
                                    printMap(map);
                                    System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                    currentstep[0]++;
                                    laststep[0] = 0;
                                }
                                break;
                        }
                    }
                }
            }
        }
        return player;
    }
    //  下面是动物上下移动时的方法
    public static boolean wsmove(int[] laststep, int[] currentstep, char d1, boolean player, String[][] map, String[][] tile, char number, int[] thisstep, int[][] site, int nextstep, int nextstep1) {
        //判断玩家是否走出边界
        if (nextstep < 0 || nextstep > 6) {
            System.out.println("不能走出边界，请重新输入");
            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
            player = !player;
        }
        else {
            //正常移动
            if (map[nextstep][thisstep[1]] == " 　 ") {
                map[nextstep][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                thisstep[0] = nextstep;
                printMap(map);
                System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                currentstep[0]++;
                laststep[0] = 0;
            }
            else {
                //不能吃己方棋子
                if (map[nextstep][thisstep[1]] != " 　 ") {
                    if (map[nextstep][thisstep[1]].charAt((player) ? 0 : 2) != ' ' && tile[nextstep][thisstep[1]] != " 水 ") {
                        System.out.println("不能吃己方棋子，请重新输入");
                        System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                        player = !player;
                    }
                    else if (map[nextstep][thisstep[1]].charAt((player) ? 2 : 0) != ' ' && tile[nextstep][thisstep[1]] != " 水 ") {
                        //实现水中鼠不能吃岸上大象，其他情况可以吃
                        if (number == '1' && map[nextstep][thisstep[1]].charAt((player) ? 2 : 0) == '8') {
                            if (tile[thisstep[0]][thisstep[1]] == " 水 ") {
                                System.out.println("水中老鼠不能吃岸上大象，请重新输入");
                                System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                player = !player;
                            }
                            else {
                                map[nextstep][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                thisstep[0] = nextstep;
                                printMap(map);
                                System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                currentstep[0]++;
                                laststep[0] = 0;
                            }
                        }
                        //实现大象不能吃鼠
                        else if (number == '8' && map[thisstep[0]][nextstep].charAt((player) ? 2 : 0) == '1') {
                            System.out.println("不能吃比自己高等的动物，请重新输入");
                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                            player = !player;
                        }
                        else {
                            //正常比较大小判断能否互吃
                            int d = compare((int) number, (int) map[nextstep][thisstep[1]].charAt((player) ? 2 : 0));
                            if (d == 1 || d == 0) {
                                map[nextstep][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                thisstep[0] = nextstep;
                                printMap(map);
                                System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                currentstep[0]++;
                                laststep[0] = 0;
                            }
                            if (d == -1) {
                                //实现陷阱中的动物最小
                                if (tile[nextstep][thisstep[1]] == " 陷 "&&(thisstep[1] ==((player)? 0 : 8)||thisstep[1] ==((player)? 1 : 7))) {
                                    map[nextstep][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[0] = nextstep;
                                    for (int n = 0; n < 7; n++) {
                                        for (int m = 0; m < 9; m++) {
                                            System.out.print(map[n][m] + "");
                                        }
                                        System.out.println();
                                    }
                                    System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                    currentstep[0]++;
                                    laststep[0] = 0;
                                }
                                else {
                                    System.out.println("不能吃比自己高等的动物，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                            }
                        }
                    }
                    //判断地形地图不为空地时的情况
                    else if (((map[nextstep][thisstep[1]].charAt(2) == ' ') && (map[nextstep][thisstep[1]].charAt(0) == ' ')) || tile[nextstep][thisstep[1]] == " 水 ") {
                        switch (tile[nextstep][thisstep[1]].charAt(1)) {
                            case '水':
                                //鼠可以下河
                                if (number == '1') {
                                    map[nextstep][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[0] = nextstep;
                                    printMap(map);
                                    System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                    currentstep[0]++;
                                    laststep[0] = 0;
                                }
                                //狮虎可以跳河，以及狮虎上下跳河是否有敌方老鼠阻挡的判断
                                else if (number == '6' || number == '7') {
                                    if (((((player) ? site[8][1] : site[0][1]) == thisstep[1]) && ((player) ? site[8][0] : site[0][0]) > 0 && ((player) ? site[8][0] : site[0][0]) < 3 && thisstep[0] == 0) ||
                                            ((((player) ? site[8][1] : site[0][1]) == thisstep[1]) && ((player) ? site[8][0] : site[0][0]) > 3 && ((player) ? site[8][0] : site[0][0]) < 6 && thisstep[0] == 6) ||
                                            ((((player) ? site[8][1] : site[0][1]) == thisstep[1]) && ((player) ? site[8][0] : site[0][0]) > 0 && ((player) ? site[8][0] : site[0][0]) < 3 && thisstep[0] == 3 && d1 == 'w') ||
                                            ((((player) ? site[8][1] : site[0][1]) == thisstep[1]) && ((player) ? site[8][0] : site[0][0]) > 3 && ((player) ? site[8][0] : site[0][0]) < 6 && thisstep[0] == 3 && d1 == 's')) {
                                        System.out.println("前方有老鼠阻挡，不能跳过小河，请重新输入");
                                        System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                        player = !player;
                                    }
                                    else {
                                        if (map[nextstep1][thisstep[1]] == " 　 ") {
                                            map[nextstep1][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                            map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                            thisstep[0] = nextstep1;
                                            printMap(map);
                                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                            currentstep[0]++;
                                            laststep[0] = 0;
                                        }
                                        else {
                                            //狮虎跳河要跳到的位置上动物能否被狮虎吃的判断
                                            if (map[nextstep1][thisstep[1]] != " 　 ") {
                                                if (map[nextstep1][thisstep[1]].charAt((player) ? 0 : 2) != ' ') {
                                                    System.out.println("不能吃己方棋子，请重新输入");
                                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                                    player = !player;
                                                }
                                                else if (map[nextstep1][thisstep[1]].charAt((player) ? 2 : 0) != ' ') {
                                                    int d = compare((int) number, (int) map[nextstep1][thisstep[1]].charAt((player) ? 2 : 0));
                                                    if (d == 1 || d == 0) {
                                                        map[nextstep1][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                                        map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                                        thisstep[0] = nextstep1;
                                                        printMap(map);
                                                        System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                                        currentstep[0]++;
                                                        laststep[0] = 0;
                                                    }
                                                    if (d == -1) {
                                                        if (tile[nextstep1][thisstep[1]] == " 陷 "&&(thisstep[1] ==((player)? 0 : 8)||thisstep[1] ==((player)? 1 : 7))) {
                                                            map[nextstep1][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                                            map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                                            thisstep[0] = nextstep1;
                                                            printMap(map);
                                                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                                            currentstep[0]++;
                                                            laststep[0] = 0;
                                                        }
                                                        else {
                                                            System.out.println("不能吃比自己高等的动物，请重新输入");
                                                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                                            player = !player;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                else {
                                    System.out.println("该动物不能下水，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                                break;
                            case '家':
                                //限制己方动物不能进入自己家，敌方动物进入后胜利的判断
                                if (nextstep == 3 && thisstep[1] == ((player) ? 0 : 8)) {
                                    System.out.println("己方动物不能进入自己家，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                                else {
                                    map[thisstep[0]][nextstep] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[1] = nextstep;
                                    printMap(map);
                                    System.out.println("恭喜" + ((player) ? '左' : '右') + "方获得胜利");
                                    System.exit(0);
                                }
                                break;
                            case '陷':
                                //实现己方动物不能吃陷阱中的己方动物，以及陷阱中动物最小
                                if (map[nextstep][thisstep[1]].charAt((player) ? 0 : 2) != ' ') {
                                    System.out.println("不能吃己方棋子，请重新输入");
                                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                                    player = !player;
                                }
                                else {
                                    map[nextstep][thisstep[1]] = map[thisstep[0]][thisstep[1]];
                                    map[thisstep[0]][thisstep[1]] = tile[thisstep[0]][thisstep[1]];
                                    thisstep[0] = nextstep;
                                    printMap(map);
                                    System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                                    currentstep[0]++;
                                    laststep[0] = 0;
                                }
                                break;
                        }
                    }
                }
            }
        }

        return player;
    }
    //  下面两个是复制数组用的方法
    private static String[][] copyArray(String[][] array) {
        String[][] newArray = new String[7][9];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 9; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }

    private static int[][] copyArray(int[][] array) {
        int[][] newArray = new int[16][2];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 2; j++) {
                newArray[i][j] = array[i][j];
            }
        }
        return newArray;
    }
    //  下面是当用户输入help时输出的内容，以及游戏开头输出的内容
    public static void help(){
        System.out.println("指令介绍：");
        System.out.println("1、移动指令\n" +
                "移动指令由两部分组成\n" +
                "第一部分是数字1-8，根据战斗力分别对应鼠（1），猫（2），狼（3），狗（4），豹（5），虎（6），狮（7），象（8）\n" +
                "第二部分是字母wasd中的一个，w对应上方向，a对应左方向，s对应下方向，d对应右方向\n" +
                "比如指令“1d”表示鼠向右走，“4w”表示狗向上走");
        System.out.println("2、游戏指令\n" +
                "输入restart重新开始游戏\n" +
                "输入help查看帮助\n" +
                "输入undo悔棋\n" +
                "输入redo取消悔棋\n" +
                "输入exit退出游戏");
    }
    // 下面是将地形地图复制到数组中的方法
    public static void tileInput(Scanner scanner, String [][] map ) {
        for(int j = 0;j < 7;j++) {
            String theLine;
            theLine = scanner.nextLine();
            for (int i = 0; i < theLine.length(); i++) {
                char thechar = theLine.charAt(i);
                switch (thechar) {
                    case '0':
                        map[j][i] = " 　 ";
                        break;
                    case '1':
                        map[j][i] = " 水 ";
                        break;
                    case '2':
                        map[j][i] = " 陷 ";
                        break;
                    case '3':
                        map[j][i] = " 家 ";
                        break;
                    case '4':
                        map[j][i] = " 陷 ";
                        break;
                    case '5':
                        map[j][i] = " 家 ";

                }
            }
        }
    }
    //  下面是当获取用户输入数字后，动物的四方向整体移动的方法
    public static boolean move(int i, String direction, int[] laststep, int[] currentstep, boolean player, String[][] map, String[][] tile, char number, int[][] site){
        if ( (player) ? map[site[i][0]][site[i][1]].charAt(0) == ' ' :map[site[i][0]][site[i][1]].charAt(0) != ' ') {
            System.out.println("该棋子已死，请重新输入");
            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
            player = !player;
        } else if ((player) ? map[site[i][0]][site[i][1]].charAt(0) != ' ':map[site[i][0]][site[i][1]].charAt(0) == ' ') {
            char direction1 = direction.charAt(1);
            switch (direction1) {
                case 'a':
                    player = admove(laststep, currentstep, player, map, tile, number, site[i], site, site[i][1] - 1, site[i][1] - 4);
                    break;
                case 's':
                    player = wsmove(laststep, currentstep, direction1, player, map, tile, number, site[i], site, site[i][0] + 1, site[i][0] + 3);
                    break;
                case 'd':
                    player = admove(laststep, currentstep, player, map, tile, number, site[i], site, site[i][1] + 1, site[i][1] + 4);
                    break;
                case 'w':
                    player = wsmove(laststep, currentstep, direction1, player, map, tile, number, site[i], site, site[i][0] - 1, site[i][0] - 3);
                    break;
                default:
                    System.out.println("输入的指令有误，请重新输入");
                    System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                    player = !player;
            }
        }
        return  player;
    }
    //  下面是将动物地图复制到数组中的方法
    public static  void animalInput(Scanner scanner1, String[][] map, String[][] tile){
        for(int j = 0;j < 7;j++) {
            String theLine1 = scanner1.nextLine();
            String theleft = theLine1.substring(0,4);
            for (int i = 0; i < theleft.length(); i++) {
                char thechar1 = theleft.charAt(i);
                switch (thechar1) {
                    case '0':
                        map[j][i] = tile [j][i];
                        break;
                    case '1':
                        map[j][i] ="1鼠 ";
                        break;
                    case '2':
                        map[j][i] ="2猫 ";
                        break;
                    case '3':
                        map[j][i] ="3狼 ";
                        break;
                    case '4':
                        map[j][i] ="4狗 ";
                        break;
                    case '5':
                        map[j][i] ="5豹 ";
                        break;
                    case '6':
                        map[j][i] ="6虎 ";
                        break;
                    case '7':
                        map[j][i] ="7狮 ";
                        break;
                    case '8':
                        map[j][i] ="8象 ";
                }
            }
            String theright = theLine1.substring(4,9);
            for (int i = 4; i < theright.length() + 4; i++) {
                char thechar1 = theright.charAt(i-4);
                switch (thechar1) {
                    case '0':
                        map[j][i] = tile [j][i];
                        break;
                    case '1':
                        map[j][i] =" 鼠1";
                        break;
                    case '2':
                        map[j][i] =" 猫2";
                        break;
                    case '3':
                        map[j][i] =" 狼3";
                        break;
                    case '4':
                        map[j][i] =" 狗4";
                        break;
                    case '5':
                        map[j][i] =" 豹5";
                        break;
                    case '6':
                        map[j][i] =" 虎6";
                        break;
                    case '7':
                        map[j][i] =" 狮7";
                        break;
                    case '8':
                        map[j][i] =" 象8";
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        stop:
        for (int m = 0; m < 1000; m++) {
            help();
            String[][][] mapHistory = new String[10000][][];
            int[][][] siteHistory = new int[10000][][];
            int[][] site = {{6,2},{1,1},{2,2},{5,1},{4,2},{0,0},{6,0},{0,2},{0,6},{5,7},{4,6},{1,7},{2,6},{6,8},{0,8},{6,6}};
            int nextstep = 0;
            int[] laststep = {0};
            int[] currentstep = {0};
            boolean player = true;
            String [][] map = new String[7][9];
            String [][]tile = new String[7][9];
            mapHistory[0] = copyArray(map);
            siteHistory[0] = copyArray(site);
            //下面地形地图
            Scanner scanner;
            scanner = new Scanner(new File("tile.txt"));
            tileInput(scanner, map);
            Scanner scanner2;
            scanner2 = new Scanner(new File("tile.txt"));
            tileInput(scanner2, tile);
            //下面动物地图
            Scanner scanner1 = new Scanner(new File("animal.txt"));
            animalInput(scanner1, map, tile);
            printMap(map);
            System.out.println("左方玩家行动：");
            //Scanner input = new Scanner(System.in);
            for (int y = 0; y < 10000; y++) {
                while (player == true) {
                    String direction = input.next();
                    if (direction.equals("exit")) break stop;
                    else if (direction.equals("restart")) continue stop;
                    else if (direction.equals("undo")) {
                        nextstep = currentstep[0] - 1;
                        if (nextstep >= 0) {
                            map = copyArray(mapHistory[nextstep]);
                            site = copyArray(siteHistory[nextstep]);
                            printMap(map);
                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                            player = !player;
                            currentstep[0] = nextstep;
                            laststep[0]++;
                        }
                        else {
                            System.out.println("已经退回初始状态，请重新输入");
                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                        }
                    }
                    else if (direction.equals("redo")){
                        nextstep = currentstep[0] + 1 ;
                        if (laststep[0] >= 1){
                            map = copyArray(mapHistory[nextstep]);
                            site = copyArray(siteHistory[nextstep]);
                            printMap(map);
                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                            player = !player;
                            currentstep[0] = nextstep;
                            laststep[0]--;
                        }
                        else{
                            System.out.println("已经没有悔棋步数，请重新输入");
                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                        }
                    }
                    else if (direction.equals("help")) {
                        help();
                        System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                    }
                    else {
                        direction = direction + "          ";
                        char number = direction.charAt(0);
                        if (number == '1') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '2') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '3') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '4') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '5') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '6') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '7') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '8') {
                            player = move(number - '1', direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else {
                            System.out.println(number + "不对应任何动物");
                            System.out.println("左方玩家行动：");
                            player = !player;
                        }
                        if (map[site[8][0]][site[8][1]].charAt(2) == ' ' && map[site[9][0]][site[9][1]].charAt(2) == ' ' && map[site[10][0]][site[10][1]].charAt(2) == ' ' && map[site[11][0]][site[11][1]].charAt(2) == ' ' &&
                                map[site[12][0]][site[12][1]].charAt(2) == ' ' && map[site[13][0]][site[13][1]].charAt(2) == ' ' && map[site[14][0]][site[14][1]].charAt(2) == ' ' && map[site[15][0]][site[15][1]].charAt(2) == ' ') {
                            System.out.println("对方棋子已死光，左方玩家胜利");
                            break stop;
                        }
                        player = !player;
                    }
                    mapHistory[currentstep[0]] = copyArray(map);
                    siteHistory[currentstep[0]] = copyArray(site);
                }
                while (player == false) {
                    String direction = input.next();
                    if (direction.equals("exit")) break stop;
                    else if (direction.equals("restart")) continue stop;
                    else if (direction.equals("undo")) {
                        nextstep = currentstep[0] - 1;
                        if (nextstep >= 0) {
                            map = copyArray(mapHistory[nextstep]);
                            site = copyArray(siteHistory[nextstep]);
                            printMap(map);
                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                            player = !player;
                            currentstep[0] = nextstep;
                            laststep[0]++;
                        }
                        else {
                            System.out.println("已经退回初始状态，请重新输入");
                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                        }
                    }
                    else if (direction.equals("redo")){
                        nextstep = currentstep[0] + 1 ;
                        if (laststep[0] >= 1){
                            map = copyArray(mapHistory[nextstep]);
                            site = copyArray(siteHistory[nextstep]);
                            printMap(map);
                            System.out.println(((player) ? '右' : '左') + "方玩家行动：");
                            player = !player;
                            currentstep[0] = nextstep;
                            laststep[0]--;
                        }
                        else{
                            System.out.println("已经没有悔棋步数，请重新输入");
                            System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                        }
                    }
                    else if (direction.equals("help")) {
                        help();
                        System.out.println(((player) ? '左' : '右') + "方玩家行动：");
                    }
                    else {
                        direction = direction + "          ";
                        char number = direction.charAt(0);
                        if (number == '1') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '2') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '3') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '4') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '5') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '6') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '7') {
                            player = move(number - '1' + 8 , direction, laststep, currentstep, player, map, tile, number, site);
                        }
                        else if (number == '8') {
                            player = move(number - '1' + 8 , direction,laststep,currentstep,player,map,tile,number,site);
                        }
                        else {
                            System.out.println(number + "不对应任何动物");
                            System.out.println("右方玩家行动：");
                            player = !player;
                        }
                        if (map[site[0][0]][site[0][1]].charAt(0) == ' ' && map[site[1][0]][site[1][1]].charAt(0) == ' ' && map[site[2][0]][site[2][1]].charAt(0) == ' ' && map[site[3][0]][site[3][1]].charAt(0) == ' ' &&
                                map[site[4][0]][site[4][1]].charAt(0) == ' ' && map[site[5][0]][site[5][1]].charAt(0) == ' ' && map[site[6][0]][site[6][1]].charAt(0) == ' ' && map[site[7][0]][site[7][1]].charAt(0) == ' ') {
                            System.out.println("对方棋子已死光，右方玩家胜利");
                            break stop;
                        }
                        player = !player;
                    }
                    mapHistory[currentstep[0]] = copyArray(map);
                    siteHistory[currentstep[0]] = copyArray(site);
                }
            }
        }
    }
}
