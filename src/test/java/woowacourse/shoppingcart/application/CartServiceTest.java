package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;
import woowacourse.shoppingcart.dto.CartItemAddRequest.CartItemResponse;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private MemberDao memberDao;

    @BeforeEach
    void createMember() {
        memberDao.save(new Member("abc@woowahan.com", "1q2w3e4r!", "닉네임"));
    }

    @DisplayName("상품 재고보다 더 많은 수량을 장바구니에 담으려 하면 예외를 발생시킨다.")
    @Test
    void addCartItem_InvalidPurchasingQuantity() {
        assertThatThrownBy(() -> cartService.addCart(1L, 1L, 101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 재고가 부족합니다.");
    }

    @DisplayName("존재하지 않는 id의 상품을 장바구니에 담으려 하면 예외를 발생시킨다.")
    @Test
    void addCartItem_InvalidProductId() {
        assertThatThrownBy(() -> cartService.addCart(1L, 4L, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 변경한다.")
    @Test
    void updateQuantity() {
        cartService.addCart(1L, 1L, 10);
        cartService.addCart(1L, 2L, 7);

        List<CartItemResponse> cartItemResponses = cartService.updateQuantity(1L, 2L, 5);
        int secondCartItemQuantity = cartItemResponses.get(1).getQuantity();

        assertThat(secondCartItemQuantity).isEqualTo(5);
    }
}
