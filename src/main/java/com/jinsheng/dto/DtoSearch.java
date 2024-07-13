package com.jinsheng.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DtoSearch {

    private String search;

    private Integer pageNum;

    private Integer pageSize;


}
