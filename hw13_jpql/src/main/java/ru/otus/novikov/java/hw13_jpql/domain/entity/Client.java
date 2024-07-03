package ru.otus.novikov.java.hw13_jpql.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
    name = "client-address-phone",
    attributeNodes = {
        @NamedAttributeNode("address"),
        @NamedAttributeNode("phones")
    }
)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "client")
    private Set<Phone> phones;

    public void addPhone(Phone phone) {
        if (this.phones == null) {
            this.phones = new HashSet<>();
        }
        this.phones.add(phone);
        phone.setClient(this);
    }
}
