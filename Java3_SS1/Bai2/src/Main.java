package BaiTap_SS1;
//public class B2_SS1_IT201 {
//    @Component
//    /**
//     * LỖI CŨ: Mặc định Spring dùng Singleton Scope (Tất cả các máy dùng chung 1 đối tượng PlaySession).
//     * GIẢI PHÁP: Chuyển sang "prototype" để mỗi khi có máy mới đăng nhập,
//     * Spring sẽ tạo ra một thực thể (Instance) hoàn toàn mới và độc lập.
//     */
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    public class PlaySession {
//
//        // playTime bây giờ là riêng tư cho từng máy trạm (Stateful)
//        private double playTime = 0;
//        /**
//         * Cộng thêm thời gian chơi.
//         * Vì đã dùng Prototype, khi máy 1 gọi addTime, nó chỉ tác động lên
//         * biến playTime của chính máy đó, không ảnh hưởng đến máy 2.
//         */
//        public void addTime(double time) {
//            this.playTime += time;
//        }
//
//        public double getPlayTime() {
//            return playTime;
//        }
//    }
//}