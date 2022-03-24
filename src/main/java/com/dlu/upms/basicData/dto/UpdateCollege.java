package com.dlu.upms.basicData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCollege {

    private String id;

    private String collegeKey;

    private String collegeName;


}
