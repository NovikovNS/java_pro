package ru.otus.novikov.java.hw12.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
@NamedEntityGraph(
    name = "customer-products",
    attributeNodes = {
        @NamedAttributeNode("products")
    }
)
public class Customer {
    @Id
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "customers", fetch = FetchType.LAZY)
    private Set<Product> products;

    public void removeProduct(Product product) {
        this.products.remove(product);
        product.getCustomers().remove(this);
    }
}
