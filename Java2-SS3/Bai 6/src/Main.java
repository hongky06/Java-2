import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

record Post(String title, List<String> tags) {}

public class Main {
    public static void main(String[] args) {
        List<Post> posts = Arrays.asList(
                new Post("Java Basics", Arrays.asList("java", "backend")),
                new Post("Python for Data", Arrays.asList("python", "data")),
                new Post("Web Dev", Arrays.asList("frontend", "react"))
        );

        // 2. Sử dụng flatMap để làm phẳng cấu trúc dữ liệu
        List<String> allTags = posts.stream()
                // Biến mỗi Post thành một Stream<String> chứa các tags
                .flatMap(post -> post.tags().stream())
                // Thu thập kết quả vào một List duy nhất
                .collect(Collectors.toList());

        // 3. In kết quả ra màn hình
        System.out.println("--- Danh sách tất cả các tag đã được làm phẳng ---");
        System.out.println(allTags);
    }
}