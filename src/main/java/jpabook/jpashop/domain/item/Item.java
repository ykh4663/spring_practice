package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.global.error.ApplicationException;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static jpabook.jpashop.global.error.ItemErrorCode.NOT_ENOUGH_STOCK;

@Table(name = "item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //===== 비즈니스 로직 =====//

    /**
     * 
     * 재고 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     *
     * 재고 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if(restStock < 0){
            throw new ApplicationException(NOT_ENOUGH_STOCK);
        }
        this.stockQuantity = restStock;
    }




}
