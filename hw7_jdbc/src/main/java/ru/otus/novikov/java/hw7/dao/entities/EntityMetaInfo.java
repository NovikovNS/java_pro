package ru.otus.novikov.java.hw7.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityMetaInfo {
    private EntityField idField;
    private List<EntityField> fields;
}
