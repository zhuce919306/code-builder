package com.pl.code.core.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClasssName KeyValueTemplateData
 * @Description
 * @Author liuds
 * @Date 2021/5/25
 * @Version V0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeyValueTemplateData {
    private String key;
    private String value;
}
