package jpabook.jpashop.domain.common;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {
    private String city;
    private String street;
    private String zipcode;


}
