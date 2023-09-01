package com.store.models;

import com.store.services.CATEGORY;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    @Setter
    private String nameOfProduct;
    @Getter @Setter
    private CATEGORY CAT;
    @Getter @Setter
    private int price;
    @Getter @Setter
    private int quantity;
}
