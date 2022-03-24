package com.dlu.upms.business.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectSelect {

    private String studentId;

    private String projectId;

    private String id;

    private String teacherId;

    private String score;


}
