package com.utour.dto.product;

import com.utour.domain.Pagination;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends Pagination {

    private Long productId;
    private String hotelId;
    private String displayType;
    private String title;
    private String description;
    private int ordinal;
    private String src;
    private Character useYn;
}
