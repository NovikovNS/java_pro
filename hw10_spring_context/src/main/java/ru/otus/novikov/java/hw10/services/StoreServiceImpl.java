package ru.otus.novikov.java.hw10.services;

import ru.otus.novikov.java.hw10.dao.ProductRepository;
import ru.otus.novikov.java.hw10.domain.Product;

import java.util.List;

public class StoreServiceImpl implements StoreService {
    private final ProductRepository productRepository;
    private final IOService ioService;
    private final Cart cart;

    private static final String createFlag = "c";
    private static final String deleteFlag = "d";

    public StoreServiceImpl(ProductRepository productRepository, IOService ioService, Cart cart) {
        this.productRepository = productRepository;
        this.ioService = ioService;
        this.cart = cart;
    }

    @Override
    public void run() {
        ioService.outString("Добро пожаловать в наш Store. Представляем доступные для покупки продукты:");
        showAllProducts();
        ioService.outString("Ваша корзина пуста. Укажите идентификатор продукта, чтобы добавить его в корзину");
        Long productId = ioService.readLong();
        addProductToCart(productRepository.getProductById(productId));
        while (true) {
            showCurrentCart();
            ioService.outString("Для удаления продукта из корзины укажите \"d\". Для добавления продукта в корзину необходимо указать \"c\"");
            String command = ioService.readString();
            if (command.equals(createFlag)) {
                ioService.outString("Укажите идентификатор продукта для добавления его в корзину:");
                Long createProductId = ioService.readLong();
                addProductToCart(productRepository.getProductById(createProductId));
            }
            if (command.equals(deleteFlag)) {
                ioService.outString("Укажите идентификатор продукта для удаления его из корзины:");
                Long deleteProductId = ioService.readLong();
                deleteProductFromCart(productRepository.getProductById(deleteProductId));
            }
        }
    }

    private void addProductToCart(Product product) {
        cart.addProduct(product);
    }

    private void deleteProductFromCart(Product product) {
        cart.deleteProduct(product);
    }

    private void showAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        products.forEach(product -> ioService.outString(product.toString()));
    }

    private void showCurrentCart() {
        ioService.outString("Продукты в корзине:");
        ioService.outString(cart.getCartProducts().toString());
    }
}
