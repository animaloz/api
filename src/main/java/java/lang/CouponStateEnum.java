package java.lang;

/**
 * 优惠券状态，1：未使用，2：已使用，3：已过期
 * @author gxy
 * @date 2019/1/28
 */
public enum CouponStateEnum{

    UNUSE(1,"未使用"),
    USED(2, "已使用"),
    OVERDUE(3,"已过期");

    private Integer code;
    private String message;
    CouponStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static void main(String[] args) {
    }

}
