import java.util.Scanner;

public class Driver {
    private static class ChatFilter {
        public static String filterLogs(String[] logs, String keyword) {
            StringBuilder result = new StringBuilder();
            for (String log : logs) {
                if (log.contains(keyword)) {
                    result.append(log).append(System.lineSeparator());
                }
            }
            return result.toString().trim();
        }
    }

    public static void main(String[] args) {
        String[] logs = {
            "10:05 alice Hello there",
            "10:06 bob How are you?",
            "10:07 InvalidLine"
        };
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();
        String result = ChatFilter.filterLogs(logs, keyword);
        System.out.println(result);
        sc.close();
    }
}
