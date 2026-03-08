import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Logger {

    public static void logError(String message) {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String time = LocalDateTime.now().format(formatter);

        System.out.println("[ERROR] " + time + " - " + message);
    }
}