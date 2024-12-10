import java.util.Scanner;

import java.util.Random;

public class clean {

    public static void main(String[] args) {
        Scanner user_input = new Scanner(System.in);

        int total_money = 10000;

        while (true) {
            System.out.println("usa_stock, korea_stock, gold, bitcoin 중에 무엇을 선택할 건가요? (종료: exit): ");
            String input = user_input.next();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }

            main_parent selectedAsset = null;

            if (input.equals("usa_stock")) {
                selectedAsset = new usa();
            } else if (input.equals("korea_stock")) {
                selectedAsset = new korea();
            } else if (input.equals("gold")) {
                selectedAsset = new gold();
            } else if (input.equals("bitcoin")) {
                selectedAsset = new bitcoin();
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
                continue;
            }

            System.out.println("투자할 금액을 입력해주세요 (현재 자산: " + total_money + "): ");
            int invest_money = user_input.nextInt();

            if (invest_money > total_money) {
                System.out.println("투자 금액이 부족합니다. 다시 입력해주세요.");
                continue;
            }

            total_money -= invest_money;

            int price_per_share = selectedAsset.get_random_value();
            int shares = invest_money / price_per_share;

            System.out.println("당신은 " + shares + " 주를 구매했습니다 (1주당 가격: " + price_per_share + ").");
            System.out.println("남은 총 자산: " + total_money);

            System.out.println("기간(주기 수)을 입력해주세요: ");
            int cycle = user_input.nextInt();

            for (int i = 1; i <= cycle; i++) {
                System.out.println("=== 주기 " + i + " ===");
                selectedAsset.up_or_down();
                price_per_share = selectedAsset.get_random_value();
                int current_value = price_per_share * shares;

                System.out.println("현재 1주당 가격: " + price_per_share);
                System.out.println("현재 주식의 총 가치: " + current_value);

                if (current_value > invest_money) {
                    int profit = current_value - invest_money;
                    total_money += profit;
                    System.out.println("이익을 얻었습니다! 이익: " + profit);
                } else {
                    int loss = invest_money - current_value;
                    total_money -= loss;
                    System.out.println("손실을 보았습니다. 손실: " + loss);
                }

                System.out.println("현재 남은 총 자산: " + total_money);
                System.out.println("=====================");
            }

            System.out.println("기간이 종료되었습니다.");
        }

        user_input.close();
    }
}

class main_parent {

    protected Random random;

    protected int[] number_restore;

    public main_parent() {
        number_restore = new int[14];
        random = new Random();
        for (int i = 0; i < 14; i++) {
            number_restore[i] = i + 2;
        }
    }

    public int get_random_value() {
        return number_restore[random.nextInt(number_restore.length)];

    }

    public void up_or_down() {
        for (int i = 0; i < number_restore.length; i++) {
            int change = random.nextBoolean() ? 1 : -1;

            number_restore[i] += change;

            if (number_restore[i] < 1) {
                number_restore[i] = 1;
            }

        }
    }

}

class usa extends main_parent {
    public usa() {
        super();
    }

}

class korea extends main_parent {
    public korea() {
        super();
    }
}

class gold extends main_parent {
    public gold() {
        super();
    }

    @Override
    public void up_or_down() {

        for (int i = 0; i < number_restore.length; i++) {
            int baseChange = (int) (number_restore[i] * 0.005);
            int change = random.nextBoolean() ? baseChange : -baseChange;

            number_restore[i] += change;

            if (number_restore[i] < 1) {
                number_restore[i] = 1;
            }
        }
    }
}

class bitcoin extends main_parent {
    public bitcoin() {
        super();
    }

    @Override
    public void up_or_down() {

        for (int i = 0; i < number_restore.length; i++) {
            int baseChange = 5;
            int change = random.nextBoolean() ? baseChange : -baseChange;

            number_restore[i] += change;

            if (number_restore[i] < 1) {
                number_restore[i] = 1;
            }
        }
    }
}