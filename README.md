[![Sales Service Project (CI included) | © 2026](https://github.com/phamngophat/sales-service-junit/actions/workflows/maven.yml/badge.svg)](https://github.com/phamngophat/sales-service-junit/actions/workflows/maven.yml)

# Sales Service Project - Unit Testing & Code Coverage Report

Dự án kiểm thử tự động hệ thống tính toán bán hàng `sales-service-junit` sử dụng **JUnit 5**, kỹ thuật phân tích giá trị biên (**Boundary Value Analysis**), kiểm thử tham số hóa (**Parameterized Testing**), và đo lường độ bao phủ mã nguồn (**JaCoCo**).

---

## 1. Cấu trúc cây thư mục dự án

```text
sales-service-junit/
├── src/
│   ├── main/java/com/phatpn/salesservice/
│   │   ├── Product.java             # Entity lưu thông tin sản phẩm và ràng buộc validation
│   │   ├── SalesService.java        # Nghiệp vụ tính tiền, chiết khấu, ship và phân loại khách
│   │   └── SalesServiceJunit.java   # Hàm main ban đầu phục vụ chạy thử thủ công
│   └── test/java/com/phatpn/salesservice/
│       └── SalesServiceTest.java    # Bộ kiểm thử tự động toàn diện JUnit 5
├── pom.xml                          # Quản lý dependency (JUnit 5, JaCoCo, Maven Surefire)
└── README.md                        # Báo cáo tổng hợp tiến trình và kết quả kiểm thử
