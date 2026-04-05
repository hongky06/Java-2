package BaiTap_SS1;

//1. Xác định dữ liệu Đầu vào (Input) & Đầu ra (Output)
//Để hệ thống hiểu được khách hàng muốn gì, chúng ta cần định nghĩa cấu trúc dữ liệu rõ ràng:
//Dữ liệu đầu vào (Input):
//userId (Long): Định danh của hội viên đang thực hiện đặt món.
//foodId (Long): Mã định danh của món ăn (ví dụ: ID của "Mì xào bò").
//quantity (int): Số lượng món khách muốn đặt.

//Kết quả mong đợi (Output):
//Thành công: Trả về thông báo xác nhận, trừ tiền tài khoản thành công và giảm số lượng trong kho.
//Thất bại (Xử lý bẫy dữ liệu): Trả về thông báo lỗi cụ thể như "Sản phẩm đã hết hàng" hoặc "Số dư tài khoản không đủ để thanh toán".

//2. Thiết kế Kiến trúc hệ thống (Loose Coupling & DI)
//Áp dụng nguyên lý Dependency Inversion. OrderFoodService sẽ không tự khởi tạo các Repository mà sẽ nhận chúng qua Constructor Injection.
//InventoryRepository (Interface): Chịu trách nhiệm truy vấn và cập nhật kho hàng.
//UserAccountRepository (Interface): Chịu trách nhiệm kiểm tra và trừ tiền tài khoản.
//OrderFoodService (Service): Lớp chứa logic nghiệp vụ chính, kết nối hai Repository trên.

//3. Thiết kế luồng xử lý Logic (Workflow)
//Quy trình xử lý một đơn hàng sẽ tuân thủ các bước nghiêm ngặt để tránh lỗi dữ liệu:
//Tiếp nhận yêu cầu: Nhận userId, foodId, và quantity.
//Kiểm tra tồn kho (Bẫy dữ liệu 1):
//Gọi InventoryRepository lấy số lượng hiện có.
//Nếu stock < quantity: Ném ra ngoại lệ OutOfStockException (Dừng quy trình).
//Tính toán chi phí: Lấy đơn giá từ kho và nhân với số lượng.
//Kiểm tra tài chính (Bẫy dữ liệu 2):
//Gọi UserAccountRepository lấy số dư của userId.
//Nếu balance < totalCost: Ném ra ngoại lệ InsufficientBalanceException (Dừng quy trình).
//Thực thi giao dịch (Atomic Operation):
//Nếu mọi điều kiện thỏa mãn: Cập nhật giảm kho và trừ tiền tài khoản.
//Phản hồi: Trả về kết quả thành công cho máy trạm của khách.