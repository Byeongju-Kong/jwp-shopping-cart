package woowacourse.shoppingcart.ui;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowacourse.auth.support.AuthenticationPrincipal;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartItemAddRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityUpdateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

@RestController
@RequestMapping("/api/carts/products")
@Validated
public class CartItemController {

    private final CartService cartService;

    public CartItemController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> addCartItem(@AuthenticationPrincipal long memberId,
                                            @Valid @RequestBody CartItemAddRequest cartItemAddRequest) {
        cartService.addCart(memberId, cartItemAddRequest.getId(), cartItemAddRequest.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> findAllCartItems(@AuthenticationPrincipal long memberId) {
        return ResponseEntity.ok(cartService.findAll(memberId));
    }

    @PatchMapping
    public ResponseEntity<List<CartItemResponse>> updateQuantity(@AuthenticationPrincipal long memberId,
                                                                 @Valid @RequestBody CartItemQuantityUpdateRequest
                                                                         cartItemQuantityUpdateRequest) {
        List<CartItemResponse> cartItemResponses = cartService.updateQuantity(
                memberId, cartItemQuantityUpdateRequest.getId(), cartItemQuantityUpdateRequest.getQuantity());
        return ResponseEntity.ok(cartItemResponses);
    }

    @DeleteMapping
    public ResponseEntity<List<CartItemResponse>> deleteCartItem(@AuthenticationPrincipal long memberId,
                                                                 @RequestParam long id) {
        return ResponseEntity.ok(cartService.deleteCartItem(memberId, id));
    }
}
