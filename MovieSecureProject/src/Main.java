import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 현재 로그인한 회원의 권한
        String loginRole = null;

        // 프로그램 반복 여부
        boolean running = true;

        while (running) {

            System.out.println("\n========================");
            System.out.println(" 영화관 회원 보안 프로그램");
            System.out.println("========================");

            if (loginRole == null) {
                System.out.println("현재 상태: 로그인하지 않음");
            } else {
                System.out.println("현재 권한: " + loginRole);
            }

            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 회원 목록");
            System.out.println("4. 로그아웃");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            String menu = scanner.nextLine();

            switch (menu) {

                case "1":
                    // 해시와 솔트를 이용한 회원가입
                    MemberInsert.register(scanner);
                    break;

                case "2":
                    // 로그인 결과를 loginRole에 저장
                    loginRole = SecureLogin.login(scanner);
                    break;

                case "3":
                    // 관리자만 회원 목록 조회 가능
                    MemberList.showMembers(loginRole);
                    break;

                case "4":
                    // 로그인 권한 제거
                    loginRole = null;
                    System.out.println("로그아웃되었습니다.");
                    break;

                case "0":
                    // 프로그램 반복 종료
                    running = false;
                    System.out.println("프로그램을 종료합니다.");
                    break;

                default:
                    System.out.println("올바른 메뉴 번호를 입력하세요.");
            }
        }

        // 프로그램이 완전히 끝날 때 Scanner 종료
        scanner.close();
    }
}