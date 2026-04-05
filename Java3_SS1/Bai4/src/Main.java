package BaiTap_SS1;

/**
 * BÁO CÁO PHÂN TÍCH: LỰA CHỌN GIẢI PHÁP TIÊM PHỤ THUỘC
 * * So sánh giữa Constructor Injection và Field Injection:
 * +-------------------+---------------------------------------+---------------------------------------+
 * | Tiêu chí          | Constructor Injection                 | Field Injection                       |
 * +-------------------+---------------------------------------+---------------------------------------+
 * | Tính bất biến     | Có (Dùng được từ khóa final)          | Không (Field phải để mutable)         |
 * | Unit Test         | Dễ (Không cần Spring Container)       | Khó (Phải dùng Reflection/Mockito)    |
 * | Độ an toàn        | Đảm bảo đối tượng không bao giờ null  | Dễ bị NullPointerException tại runtime|
 * | Khuyến nghị       | Standard Best Practice (Spring 5+)    | Không nên dùng (Deprecated dần)       |
 * +-------------------+---------------------------------------+---------------------------------------+
 * * -> Constructor Injection.
 */