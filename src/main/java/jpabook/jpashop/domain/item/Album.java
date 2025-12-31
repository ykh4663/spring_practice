package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("A")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Album extends Item {
    private String artist;
    private String etc;
}
