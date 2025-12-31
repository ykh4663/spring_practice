package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@DiscriminatorValue("B")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Book extends Item {
    private String author;
    private String isbn;
}
