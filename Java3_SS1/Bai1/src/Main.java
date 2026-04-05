public class Main {
    /**
     * Đoạn code sai
     * public RechargeService() {
     *     // Lỗi: Tự khởi tạo thủ công (Hard-code dependency)
     *     this.gateway = new InternalPaymentGateway();
     * }
     * Cách viết này tạo ra liên kết cứng giữa RechargService và InternalPaymentGateway
     * Điều này làm cho mã khó bảo trì và mở rộng, vì nếu muốn thay đổi phương thức thanh toán, bạn phải sửa đổi mã của RechargeService
     * Dễ mất kiểm soát vi phạm IoC
     */

//    Sau khi sửa
//    public class RechargeService {
//        private final PaymentGateway gateway; // Sử dụng Interface
//
//        // Cấp quyền kiểm soát cho bên ngoài (IoC)
//        public RechargeService(PaymentGateway gateway) {
//            this.gateway = gateway;
//        }
//
//        public void processRecharge(String username, double amount) {
//            gateway.pay(amount);
//            System.out.println("Nạp " + amount + " cho " + username);
//        }
//    }
}