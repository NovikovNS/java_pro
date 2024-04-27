package ru.otus.novikov.java.hw5;

import lombok.Data;

@Data
public class Product{
    private final Long id;
    private final String title;
    private final String description;
    private final Double cost;
    private final Long weight;
    private final Long width;
    private final Long length;
    private final Long height;

    private Product(ProductBuilder builder) {
        id = builder.id;
        title = builder.title;
        description = builder.description;
        cost = builder.cost;
        weight = builder.weight;
        width = builder.width;
        length = builder.length;
        height = builder.height;
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public static class ProductBuilder {
        private Long id;
        private String title;
        private String description;
        private Double cost;
        private Long weight;
        private Long width;
        private Long length;
        private Long height;

        public Product build() {
            return new Product(this);
        }

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProductBuilder title(String title) {
            this.title = title;
            return this;
        }
        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }
        public ProductBuilder cost(Double cost) {
            this.cost = cost;
            return this;
        }
        public ProductBuilder weight(Long weight) {
            this.weight = weight;
            return this;
        }
        public ProductBuilder width(Long width) {
            this.width = width;
            return this;
        }
        public ProductBuilder length(Long length) {
            this.length = length;
            return this;
        }
        public ProductBuilder height(Long height) {
            this.height = height;
            return this;
        }

    }
}
