package com.example.bishe.entity.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userid;

    private Integer roleid;


}
