// 20241982/안동건//AI빅데이터학과
//<주제>
//java로 주식을 해보자.
// 1. 사용자가 미국증시, 한국증시, 금,비트코인 이중에서 어떤 종목을 할지 정한다.
// 2. 그리고 사용자는 얼마다 구매를 할지 정한다.
// 3. 사용자가 주기혹은 날을 입력을 하면 random으로 변동이 일어나게 한다.

import java.util.Scanner;
// 사용자의 입력을 받기 위해 모듈을 가져왔습니다.
import java.util.Random;
// 이거는 주식에서 변동률 위해 넣은 random 모듈입니다.
// 한 주기가 지날때 마다 한전의 random을 씁니다.

public class project {

    public static void main(String[] args) {
        Scanner user_input = new Scanner(System.in);
        // 사용자의 입력을 받기 위해
        int total_money = 10000;
        // 초기 자산을 설정
        while (true) {// while 문을 사용하여 한번으로 끝나는게 아니라 계속하게 만듬
            System.out.println("usa_stock, korea_stock, gold, bitcoin 중에 무엇을 선택할 건가요? (종료: exit): ");
            String input = user_input.next();
            // 여기는 사용자가 "미국증시, 한국증시, 금,비트코인 이중에서 어떤 종목을 할지 정한다."를 하는 곳
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            // while문이기 때문에 끝내는 명령어 설정
            main_parent selectedAsset = null;
            // 사용자가 어떤 종목을 사용할지 모르기 때문에 일단 먼저 초기화 한다
            // 다음 기말 프로잭트 때는 이 부분을 지우고 바로 자식 코드로 가는 것으로 수정 해보겠습니다.
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
            // 여기는 사용자의 입력에 따라 class를 소환하는 것 그리고 이상한 말을 하면 다시 하라고 명령을 하는 부분
            // ---------------------------------------------------------------------------------------------------------------------------------
            // 이제 부터는 사용자가 얼마나 투자 하는지 user_input을 받는 곳
            System.out.println("투자할 금액을 입력해주세요 (현재 자산: " + total_money + "): ");
            int invest_money = user_input.nextInt();
            if (invest_money > total_money) {
                System.out.println("투자 금액이 부족합니다. 다시 입력해주세요.");
                continue;
            }
            // 사용자가 얼마를 투자하는지 받는 곳
            // 그리고 오류가 안생기기 위해 가지고 있는 자본금을 넘는 금액은 if문으로 차단
            total_money -= invest_money;
            // 돈을 쓰고 얼마가 남는지 알려 주기 위해 total_money설정
            int price_per_share = selectedAsset.get_random_value();
            int shares = invest_money / price_per_share;
            // 밑에 main_parent에 있는 코드를 사용하여 변화율을 랜덤으로 하게 만듬
            System.out.println("당신은 " + shares + " 주를 구매했습니다 (1주당 가격: " + price_per_share + ").");
            System.out.println("남은 총 자산: " + total_money);
            // --------------------------------------------------------------------------------------------------------------------
            // 여기는 사용자가 몇주기 혹은 몇일 을 지나게 한는지 설정을 하는것
            System.out.println("기간(주기 수)을 입력해주세요: ");
            int cycle = user_input.nextInt();
            for (int i = 1; i <= cycle; i++) {
                System.out.println("=== 주기 " + i + " ===");
                selectedAsset.up_or_down(); // 변동성 업데이트 ------------------------------------------
                price_per_share = selectedAsset.get_random_value(); // 새로운 가격 계산
                int current_value = price_per_share * shares; // 현재 주식의 총 가치
                System.out.println("현재 1주당 가격: " + price_per_share);
                System.out.println("현재 주식의 총 가치: " + current_value);
                // 자산 업데이트
                if (current_value > invest_money) {
                    int profit = current_value - invest_money;
                    total_money += profit; // 수익 추가
                    System.out.println("이익을 얻었습니다! 이익: " + profit);
                } else {
                    int loss = invest_money - current_value;
                    total_money -= loss; // 손실 반영
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

// -----------------------------------------------------------------------
// <저희 조가 변동률을 랜덤으로 한 방법>
// 1. 먼저 list를 만든다.ex)[1,2,3,4,5,6,7,8,9,10,11,12,13,14]
// 2. 그 다음 random을 사용하여 여기 리스트 중 하나의 값을 선택하게 한다 -> 이 값이 곧 변동률이다.
// 3. 선택한 값을 부여
// -----------------------------------------------------------------------
// 공통으로 되는 것을 정의 하기 위해 부모를 먼저 생성
class main_parent {
    protected Random random;
    // 다른 패키지에서 못쓰게 protected사용
    protected int[] number_restore;

    // 다른 패키지에서 못쓰게 protected사용
    public main_parent() {
        number_restore = new int[14];
        random = new Random();
        for (int i = 0; i < 14; i++) {
            number_restore[i] = i + 2;
        }
    }

    // 다른 패키지에서도 사용 가능하게 public사용
    // 여기는 list를 for문을 사용하여 만드는 과정
    public int get_random_value() {
        return number_restore[random.nextInt(number_restore.length)];
        // length는 배열의 크기 중의 하나를 고르기 위해서 투입
    }

    // 여기는 list에서 하나를 선택하는 부분
    public void up_or_down() {
        for (int i = 0; i < number_restore.length; i++) {
            int change = random.nextBoolean() ? 1 : -1;

            number_restore[i] += change;

            if (number_restore[i] < 1) {
                number_restore[i] = 1;
            }
        }
    }
    // 여기는 그 선택한 값을 값에 부여하는 과정
}

// ---------------------------------------------------------------------------------------------
// 자식 코드
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
        // 금은 0.5%씩 더 적게 변동
        for (int i = 0; i < number_restore.length; i++) {
            int baseChange = (int) (number_restore[i] * 0.005); // 0.5% 변동
            int change = random.nextBoolean() ? baseChange : -baseChange;

            number_restore[i] += change;

            if (number_restore[i] < 1) {
                number_restore[i] = 1;
            }
        }
    }
    // override를 사용하여 부모에 있는 메서드를 재정의 하는 과정입니다.
    // 금은 상대적으로 적당히 변하기 때문에 선택된 값에 0.5를 곱하는 과정입니다.
}

class bitcoin extends main_parent {
    public bitcoin() {
        super();
    }

    @Override
    public void up_or_down() {
        // 비트코인은 5배씩 변동
        for (int i = 0; i < number_restore.length; i++) {
            int baseChange = 5; // 5배씩 변동
            int change = random.nextBoolean() ? baseChange : -baseChange;

            number_restore[i] += change;

            if (number_restore[i] < 1) {
                number_restore[i] = 1;
            }
        }
    }
    // override를 사용하여 부모에 있는 메서드를 재정의 하는 과정입니다.
    // 비트코인은 상대적으로 많이 변하기 때문에 5배를 했습니다.
}

// ---------------------------
// 여기는 overide되기전의 메서드 입니다.
// public void up_or_down() {
// for (int i = 0; i < number_restore.length; i++) {
// int change = random.nextBoolean() ? 1 : -1;
// number_restore[i] += change;

// if (number_restore[i] < 1) {
// number_restore[i] = 1;
// }
// }
// }